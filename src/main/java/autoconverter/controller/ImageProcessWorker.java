/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoconverter.controller;

import autoconverter.model.CaptureImage;
import autoconverter.model.ExImagePlus;
import autoconverter.view.Auto_Converter;
import autoconverter.view.BaseFrame;
import autoconverter.view.ImagePanel;
import ij.IJ;
import ij.ImagePlus;
import ij.gui.Roi;
import ij.plugin.ImageCalculator;
import ij.plugin.RGBStackMerge;
import ij.plugin.filter.ThresholdToSelection;
import ij.process.ImageProcessor;
import ij.process.ImageStatistics;
import ij.process.LUT;
import java.awt.Color;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

/**
 *
 * @author yfujita
 */
public class ImageProcessWorker extends SwingWorker<Integer, String> {

	private static final Logger logger = AutoConverterUtils.getLogger();
	ArrayList<ArrayList<String>> stat_data = new ArrayList<ArrayList<String>>();
	private ApplicationController appController = ApplicationController.getInstance();
	private BaseFrame bf = BaseFrame.getInstance();
	private JTextArea textArea = bf.getSummaryDisplayArea();
	final String dst = appController.getDestinationDirectoryPath();
	final String src = appController.getSourceDirectoryPath();

	final boolean remove_char = bf.getRemoveSpecialCharRadioButton().isSelected();
	final String type = (String) bf.getImageFormatComboBox().getSelectedItem();
	final boolean addparam = bf.getAddParamRadioButton().isSelected();
	private final ImageCalculator ic = new ImageCalculator();
	private ThresholdToSelection tts = new ThresholdToSelection();


	public ImageProcessWorker(){
		ArrayList<ArrayList<String>> stat_data = new ArrayList<ArrayList<String>>();
		appController = ApplicationController.getInstance();
		bf = BaseFrame.getInstance();
		textArea = bf.getSummaryDisplayArea();
	}

	/**
	 *
	 * @return @throws Exception
	 */
	@Override
	protected Integer doInBackground() throws Exception {
		logger.fine("bf_ID:" + java.lang.System.identityHashCode(bf));
		int mode = appController.getImageMode();
		if (mode == BaseFrame.IMAGE_MODE_SINGLE) {
			this.convertSingleImage();
		} else if (mode == BaseFrame.IMAGE_MODE_MERGE) {
			this.convertMergeImage();
		} else if (mode == BaseFrame.IMAGE_MODE_RELATIVE) {
			this.convertRelativeImage();
		} else if (mode == BaseFrame.IMAGE_MODE_THRESHOLD) {
			this.convertThresholdImage();
		} else {
			logger.fine("BUG: モードが選択されていない @ doInBackground(), conversion");
		}
		return 0;
	}

	/**
	 *
	 * @param chunks
	 */
	protected void process(java.util.List<String> chunks) {
		for (String s : chunks) {
			textArea.append(s);
			textArea.setCaretPosition(textArea.getText().length());
		}
	}

	@Override
	public void done() {
		bf.getConvertButton().setEnabled(true);
		if (isCancelled()) {
			return;
		}
		textArea.append("Conversion finished.\n");
		Calendar now = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
		String date = sdf.format(now.getTime());

		String memoPath = dst + File.separator + "conversion_log" + date + ".txt";
		try {
			//BufferedWriter bw = new BufferedWriter(new FileWriter(new File(memoPath)));
			//bw.write(textArea.getText());
			//bw.close();
			// 改行コードをOSに合わせて出力する
			BufferedReader br = new BufferedReader(new StringReader(textArea.getText()));
			PrintWriter pw = new PrintWriter(new FileWriter(new File(memoPath)));
			br.lines().forEach(line -> pw.println(line));
		} catch (IOException ex) {
			textArea.append("Fail to write log to " + memoPath);
		}
		if (appController.getImageMode() == BaseFrame.IMAGE_MODE_RELATIVE) {
			String statPath = dst + File.separator + "conversion_stat" + date + ".tsv";
			try {
				// Relative なら統計データを書き出す.
				BufferedWriter bw = new BufferedWriter(new FileWriter(new File(statPath)));
				bw.write("Relative image\tTarget image\tReference image\tTotal area\tArea\tMin\tMax\tMean\tStdev\tMedian\n");
				for (ArrayList<String> rec : stat_data) {
					bw.write(rec.stream().collect(Collectors.joining("\t")) + "\n");
				}
				bw.close();
			} catch (IOException ex) {
				Logger.getLogger(ApplicationController.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	private ExImagePlus imageCropAndResize(ExImagePlus _imp) {
		ImagePanel imgPanel = bf.getImageDisplayPanel();
		/*
		int crop_height = imgPanel.getRoiHeight();
		int crop_width = imgPanel.getRoiWidth();
		int crop_x = imgPanel.getLeftTopX();
		int crop_y = imgPanel.getLeftTopY();
		*/
		int crop_height = Integer.parseInt(appController.getBaseFrame().gethTextField().getText());
		int crop_width = Integer.parseInt(appController.getBaseFrame().getwTextField().getText());
		int crop_x = Integer.parseInt(appController.getBaseFrame().getxTextField().getText());
		int crop_y = Integer.parseInt(appController.getBaseFrame().getyTextField().getText());
		int resize_x = appController.getResizeX();
		if (crop_height != 0 && crop_width != 0) { // crop 領域が設定されている.
			_imp.setRoi(crop_x, crop_y, crop_width, crop_height);
			IJ.run(_imp, "Crop", "");
		}
		int width = _imp.getWidth();
		int height = _imp.getHeight();
		int resize_y = 0;
		if (width != resize_x && resize_x != 0) {
			resize_y = height * resize_x / width;
		}

		// resize
		if (resize_x > 0 && resize_y > 0) {
			IJ.run(_imp, "Size...", "width=" + resize_x + " height=" + resize_y + "512 constrain average interpolation=Bilinear");
		}
		return _imp;
	}

	private int convertMergeImage() {
		logger.fine("======================== in covertMergeImage() =====================");
		int number = appController.getImageSet().getShotSize();
		int count = 1;

		// 選択されているフィルタ名を取得
		ArrayList<String> filters = appController.getSelectedFilters();
		for (int i = 0; i < number; i++) {
			if (isCancelled()) {
				return (22);
			}
			ArrayList<CaptureImage> image_set = appController.getImageSet().getShotAt(i);
			ArrayList<ExImagePlus> eximps = new ArrayList<ExImagePlus>();
			for (CaptureImage _cimg : image_set) {
				if( filters.contains(_cimg.getFilter()) ){
					eximps.add(_cimg.getImagePlus());
				}
			}
			ExImagePlus flat_image = appController.updateMergeImage(eximps);
			flat_image = this.imageCropAndResize(flat_image);
			logger.fine("Creating destination path...");
			//ExImagePlus _first_image = image_set.get(0).getImagePlus();
			ExImagePlus _first_image = eximps.get(0);
			logger.fine("milestone0");
			String fname = this.getDistPath(eximps, appController.getImageMode());
			String abssrc = _first_image.getFile().getAbsolutePath();
			logger.fine("milestone1");

			this.saveFile(flat_image, fname, count, number, abssrc);
			logger.fine("milestone5");
			flat_image.close();

			logger.fine("milestone6");
			logger.fine("count=" + count);
			count++;

		}
		return 0;
	}

	private int convertRelativeImage() {
		int number = appController.getImageSet().getShotSize();
		int count = 1;
		logger.fine("number=" + number);
		String ref_filter = appController.getReferenceFilter();
		String tgt_filter = appController.getTargetFilter();
		ArrayList<String> sel_filters = appController.getSelectedFilters();
		stat_data.clear();
		double range_min = (double) bf.getScaleRangeSlider().getLowValue();
		double range_max = (double) bf.getScaleRangeSlider().getUpperValue();
		String filter = (String) bf.getFilterSelectCBox().getSelectedItem();
		range_min = range_min / ApplicationController.RELATIVE_MULTIPLICITY;
		range_max = range_max / ApplicationController.RELATIVE_MULTIPLICITY;
		try {
			for (int i = 0; i < number; i++) {
				ArrayList<CaptureImage> image_set = appController.getImageSet().getShotAt(i);
				ExImagePlus[] imps_array = new ExImagePlus[2];
				ArrayList<ExImagePlus> imps = new ArrayList<ExImagePlus>();
				ArrayList<ExImagePlus> all_imps = new ArrayList<ExImagePlus>();
				if (isCancelled()) {
					return (22);
				}
				if (image_set.size() < 2) {
					publish("(" + count + "/" + number + "): shot at " + i + " does not exist\n");
					continue;
				}
				String shotID = image_set.get(0).getShotID();
				publish("Converting shotID: " + shotID + " ...\n");
				logger.fine("Converting:" + shotID + " ...\n");
				for (CaptureImage _cimg : image_set) {
					if (_cimg.getFilter().equals(ref_filter)) {
						//imps.add(1, _cimg.getImagePlus());
						imps_array[1] = _cimg.getImagePlus();
					} else if (_cimg.getFilter().equals(tgt_filter)) {
						//imps.add(0, _cimg.getImagePlus());
						imps_array[0] = _cimg.getImagePlus();
					}
					all_imps.add(_cimg.getImagePlus());
				}
				imps = new ArrayList<ExImagePlus>(Arrays.asList(imps_array));
				logger.fine("before updateRelativeImage()");
				ImagePlus rel_imp = appController.updateRelativeImage(all_imps);
				logger.fine("Done.");
				ImageProcessor ip = rel_imp.getProcessor();
				Roi roi = rel_imp.getRoi();
				try {
					if (roi == null) {
						throw new ArrayIndexOutOfBoundsException("Roi==null");
					}
					logger.fine("roi.stat=" + roi.getStatistics());
					logger.fine("roi.stat.area=" + roi.getStatistics().area);
				} catch (IllegalArgumentException e) {
					logger.fine(e.toString());
					publish("No region in (min, max) = (" + range_min + ", " + range_max + ") in " + filter + "\n");
					logger.fine("No region in (min, max) = (" + range_min + ", " + range_max + ") in " + filter);
				} catch (ArrayIndexOutOfBoundsException e) {
					publish("No region in (min, max) = (" + range_min + ", " + range_max + ") in " + filter + "\n");
					logger.fine("No region in (min, max) = (" + range_min + ", " + range_max + ") in " + filter);
					logger.fine(e.toString());
				}
				double total_area = ip.getStatistics().area;
				double s_area = 0;
				double s_min = 0;
				double s_max = 0;
				double s_mean = 0;
				double s_sd = 0;
				double s_median = 0;
				ImageStatistics stat = ip.getStatistics();
				if (roi != null) {
					stat = roi.getStatistics();
				}
				if (stat != null) {
					s_area = stat.area;
					s_min = stat.min;
					s_max = stat.max;
					s_mean = stat.mean;
					s_sd = stat.stdDev;
					s_median = stat.median;
					logger.fine("area=" + s_area + ", min=" + s_min + ", max=" + s_max + ", mean=" + s_mean + ", sd=" + s_sd + ", median=" + s_median);
				}

				String fpath = this.getDistPath(imps, appController.getImageMode());
				String fname = new File(fpath).getName();

				ArrayList<String> record = new ArrayList<String>();
				record.add(fname);
				record.add(imps.get(0).getFile().getName()); // target
				record.add(imps.get(1).getFile().getName()); // reference
				record.add(Double.toString(total_area));
				record.add(Double.toString(s_area));
				if (s_area == 0) {
					record.add("NaN");
					record.add("NaN");
					record.add("NaN");
					record.add("NaN");
					record.add("NaN");
				} else {
					record.add(Double.toString(s_min));
					record.add(Double.toString(s_max));
					record.add(Double.toString(s_mean));
					record.add(Double.toString(s_sd));
					record.add(Double.toString(s_median));
				}
				stat_data.add(record);

				ImagePlus flat_image = rel_imp.flatten();
				flat_image = this.imageCropAndResize(new ExImagePlus("dummy", flat_image.getImage(), "dummy"));
				String abssrc = imps.get(0).getFile().getAbsolutePath();
				this.saveFile(flat_image, fpath, count, number, abssrc);
				flat_image.clone();
				rel_imp.close();

				count++;
			}
		} catch (Exception e) {
			logger.fine(AutoConverterUtils.stacktrace(e));
		}
		return count;
	}

	private int convertSingleImage() {
		int number = appController.getImageSet().size();
		int count = 1;

		for (CaptureImage _cm : appController.getImageSet().getFiles()) {
			if (isCancelled()) {
				return (22);
			}
			// 画像処理
			//imp = this.imageProcessing(imp);
			ExImagePlus imp = _cm.getImagePlus();
			//imp = appController.processImage(imp);
			imp = appController.updateSingleImage(imp);
			imp = this.imageCropAndResize(imp);

			// 保存
			ImagePlus flatten = imp.flatten();
			String abssrc = _cm.getFile().getAbsolutePath();

			String fpath = this.getDistPath(imp, appController.getImageMode());
			this.saveFile(flatten, fpath, count, number, abssrc);
			imp.close();
			count++;
		}
		return 0;
	}

	private int convertThresholdImage() {
		int number = appController.getImageSet().size();
		int count = 1;

		for (CaptureImage _cm : appController.getImageSet().getFiles()) {
			if (isCancelled()) {
				return (22);
			}
			ExImagePlus imp = _cm.getImagePlus();
			String abssrc = _cm.getFile().getAbsolutePath();
			//imp = this.imageProcessing(imp);
			//imp = appController.processImage(imp);
			imp = appController.updateThresholdImage(imp);
			Roi roi = imp.getRoi();
			ImageProcessor ip = imp.getProcessor();
			logger.fine("roi=" + roi);
			logger.fine("ip=" + ip);
			if (roi != null && ip != null) {
				logger.fine("setting color = yellow");
				ip.setColor(Color.YELLOW);
				ip.draw(roi);
			}
			imp = this.imageCropAndResize(imp);

			String fpath = this.getDistPath(imp, appController.getImageMode());
			this.saveFile(imp, fpath, count, number, abssrc);
			imp.close();
			count++;
		}
		return 0;
	}

	private String removeExtension(String fileName) {
		String newName;

		int lastPosition = fileName.lastIndexOf('.');
		if (lastPosition > 0) {
			newName = fileName.substring(0, lastPosition);
		} else {
			newName = fileName;
		}

		return newName;
	}

	private String getParamString(ExImagePlus _cm) {
		StringBuffer param_str = new StringBuffer("");
		String filter = _cm.getFilter();
		int min = appController.getMinHash().get(filter);
		int max = appController.getMaxHash().get(filter);
		Boolean auto = appController.getAutoHash().get(filter);
		String auto_type = appController.getTypeHash().get(filter);
		String color = appController.getColorHash().get(filter);
		int ballsize = appController.getBallHash().get(filter);
		ImagePanel imgPanel = bf.getImageDisplayPanel();
		int crop_height = imgPanel.getRoiHeight();
		int crop_width = imgPanel.getRoiWidth();
		int crop_x = imgPanel.getLeftTopX();
		int crop_y = imgPanel.getLeftTopY();
		int resize_x = appController.getResizeX();

		if (ballsize != 0) {
			param_str.append("_Ball" + ballsize);
		}
		if (auto) {
			param_str.append("_AUTO" + auto_type);
		} else {
			param_str.append("_RANG" + min + "-" + max);
		}
		if (crop_height != 0 && crop_width != 0) { // crop 領域が設定されている.
			param_str.append("_CROPx" + crop_x + "y" + crop_y + "w" + crop_width + "h" + crop_height);
		}
		return param_str.toString();
	}

	/**
	 * 出力先のパスを返す
	 *
	 * @param imps 変換元のファイル. single convert なら1つ、relative convertなら0番目がtarget,
	 * 1番目がreference, merge convert ならマージされる画像すべてが入る
	 * @param mode 変換モード
	 * @return
	 */
	private String getDistPath(ArrayList<ExImagePlus> imps, int mode) {
		ExImagePlus imp = imps.get(0);
		String abssrc = imp.getFile().getAbsolutePath();
		String fname = imp.getFile().getName();
		String relsrc = abssrc.replaceFirst(Pattern.quote(src), "");
		String dstpath = dst + relsrc;
		logger.fine("dstpath=" + dstpath);
		File dstdir = new File(dstpath).getParentFile();
		logger.fine("distdir==" + dstdir);
		if (!dstdir.exists()) { //ディレクトリが無い!
			dstdir.mkdirs();
		} else if (!dstdir.isDirectory()) {
			// ディレクトリ以外!
			IJ.showMessage(dstdir + " is not directory. stop.");
			return null;
		}
		ArrayList<String> filters = new ArrayList<String>();
		for (ExImagePlus _imp : imps) {
			logger.fine("_imp.getFilter()=" + _imp.getFilter());
			filters.add(_imp.getFilter());
		}
		int well_number = 0;
		int total_well_number = 0;
		/*
		if (!"No change".equals(bf.getPlateSelectComboBox().getSelectedItem())) { // well 番号から well 名への変更
			try {
				String src_well_name = imp.getWellName();
				logger.fine("src_well_name=" + src_well_name);
				String well_name = src_well_name.replaceAll("[^\\d]+", "");
				well_number = Integer.parseInt(well_name);
				logger.fine("well_name==" + well_name);
				logger.fine("well_number==" + well_number);
				String plate = (String) bf.getPlateSelectComboBox().getSelectedItem();
				logger.fine("plate==" + plate);
				String total_well = plate.replaceAll("[^\\d]+", "");
				logger.fine("total_well==" + total_well);
				total_well_number = Integer.parseInt(total_well);
				int plate_rown = 1;
				int plate_coln = 1;
				if (total_well_number == 0) {
				} else if (total_well_number == 6) {
					plate_rown = 2;
				} else if (total_well_number == 12) {
					plate_rown = 3;
				} else if (total_well_number == 24) {
					plate_rown = 4;
				} else if (total_well_number == 48) {
					plate_rown = 6;
				} else if (total_well_number == 96) {
					plate_rown = 8;
				} else if (total_well_number == 384) {
					plate_rown = 16;
				} else if (total_well_number == 1536) {
					plate_rown = 32;
				} else {
					total_well_number = 1;
					plate_rown = 1;
				}
				plate_coln = total_well_number / plate_rown;
				// ウェル番号から何行目かを計算
				int rown = 0;
				int coln = 0;
				// 24 well -> plate_coln==6
				// well_number==1 => rown == 1
				// well_number==6 => rown == 1
				rown = (well_number - 1) / plate_coln + 1;
				coln = (well_number - 1) % plate_coln + 1;
				logger.fine("plate_rown==" + plate_rown);
				logger.fine("plate_coln==" + plate_coln);
				logger.fine("rown==" + rown);
				logger.fine("coln==" + coln);

				char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
				String row_alphabet = "";
				String col_number = "";

				try {
					row_alphabet = Character.toString(alphabet[rown - 1]);
					col_number = String.format("%02d", coln);
					String new_well_name = row_alphabet + col_number;
					fname = fname.replaceFirst(src_well_name, new_well_name);
				} catch (ArrayIndexOutOfBoundsException e) {
					logger.fine(AutoConverterUtils.stacktrace(e));
				}
			} catch (NumberFormatException e) {
				logger.fine(AutoConverterUtils.stacktrace(e));
			} catch (Exception e) {
				logger.fine(AutoConverterUtils.stacktrace(e));
			}
		}
		*/
		fname = removeExtension(fname);
		logger.fine("fname=" + fname);
		if(bf.getReadCQ1ConfigRadioButton().isSelected()){
			// CQ1の場合は末尾のC1-C4 をreplace
			fname = fname.replaceFirst("C\\d+$", imp.getFilter());
			logger.fine("fname=" + fname);
			fname = fname.replaceFirst("W\\d+", imp.getWellName());
			logger.fine("fname=" + fname);
		}
		if (mode == BaseFrame.IMAGE_MODE_SINGLE) {
			fname = removeExtension(fname);
		} else if (mode == BaseFrame.IMAGE_MODE_RELATIVE) {
			fname = removeExtension(fname).replaceFirst("(?s)(.*)" + imp.getFilter(), "$1" + filters.stream().collect(Collectors.joining("_div_")));
		} else if (mode == BaseFrame.IMAGE_MODE_MERGE) {
			fname = removeExtension(fname).replaceFirst("(?s)(.*)" + imp.getFilter(), "$1" + filters.stream().collect(Collectors.joining("-")));
		} else if (mode == BaseFrame.IMAGE_MODE_THRESHOLD) {
			//fname = removeExtension(fname).replaceFirst(imp.getFilter(), "Thres" + imp.getFilter());
			fname = removeExtension(fname).replaceFirst("(?s)(.*)" + imp.getFilter(), "$1" + "Thres" + imp.getFilter());
		}

		logger.fine("fname==" + fname);
		if (remove_char) {
			fname = AutoConverterUtils.tr("()[]{} *?/:;!<>#$%&'\"\\", "______________________", fname).replaceAll("_+", "_").replaceAll("_-_", "-").replaceAll("_+\\.", ".").replaceAll("_+$", "");
		}
		//String dstbase = removeExtension(dstdir + File.separator + fname);
		String dstbase = dstdir + File.separator + fname;
		logger.fine("dstbase=" + dstbase);
		if (addparam) {
			dstbase = dstbase + this.getParamString(imp);
		}

		//ImagePlus flatten = imp.flatten();
		String fpath = "";
		if (type.equals("jpg")) {
			fpath = dstbase + ".jpg";
			//	IJ.saveAs(flatten, "jpg", fpath);
		} else if (type.equals("png") || type.equals("ping")) {
			fpath = dstbase + ".png";
			//	IJ.saveAs(flatten, "png", fpath);
		} else if (type.equals("tif") || type.equals("8bit tiff")) {
			fpath = dstbase + ".tif";
			//	IJ.run(flatten, "RGB Color", null);
			//	IJ.saveAsTiff(flatten, fpath);
		}

		logger.fine("fpath (getDistpath)=" + fpath);

		return fpath;
	}

	private String getDistPath(ExImagePlus imp, int mode) {
		ArrayList<ExImagePlus> imps = new ArrayList<ExImagePlus>();
		imps.add(imp);
		String path = this.getDistPath(imps, mode);
		return path;
	}

	private void saveFile(ImagePlus flatten_image, String fpath, int count, int number, String abssrc) {
		logger.fine("fpath=" + fpath);
		logger.fine("abssrc=" + abssrc);

		if (type.equals("jpg")) {
			IJ.saveAs(flatten_image, "jpg", fpath);
		} else if (type.equals("png") || type.equals("ping")) {
			IJ.saveAs(flatten_image, "png", fpath);
		} else if (type.equals("tif") || type.equals("8bit tiff")) {
			IJ.run(flatten_image, "RGB Color", null);
			IJ.saveAsTiff(flatten_image, fpath);
		}

		publish("(" + count + "/" + number + ") " + abssrc + "  ==>   " + fpath + "\n");
	}

}
