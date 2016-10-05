/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package autoconverter.controller;

import ij.IJ;
import ij.ImagePlus;
import java.awt.CardLayout;
import java.awt.Color;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import autoconverter.model.CaptureImage;
import autoconverter.model.ImageSet;
import autoconverter.view.BaseFrame;
import autoconverter.view.WaitDialog;
import java.util.regex.Pattern;

/**
 *
 * @author yfujita
 */
public class ApplicationController implements ApplicationMediator {

	private final BaseFrame baseFrame;
	private int cardIndex;
	private final int cardSize;
	private static final Logger logger = AutoConverterUtils.getLogger();
	private HashSet<String> messageList;
	private Object oldSearchPath;
	private ImageSet imageSet;
	private int rangeSliderHighValue;
	private int rangeSliderLowValue;
	private SpinnerNumberModel minSpinnerModel;
	private SpinnerNumberModel maxSpinnerModel;
	private ImagePlus imp;
	private HashMap<String, Integer> storedMaxValues;
	private HashMap<String, Integer> storedMinValues;
	private HashMap<String, Boolean> storedAuto;
	private HashMap<String, String> storedColor;
	private HashMap<String, String> storedMode;
	private String lastSelectedFilter;
	private static boolean loading = false;

	public ApplicationController(BaseFrame _base) {
		cardIndex = 0;
		cardSize = BaseFrame.MAX_CARD_SIZE;
		messageList = new HashSet<String>();
		oldSearchPath = null;
		imageSet = new ImageSet();
		rangeSliderHighValue = 4095;
		rangeSliderLowValue = 0;
		baseFrame = _base;
		imp = null;
		storedMaxValues = new HashMap<String, Integer>();
		storedMinValues = new HashMap<String, Integer>();
		storedAuto = new HashMap<String, Boolean>();
		storedColor = new HashMap<String, String>();
		storedMode = new HashMap<String, String>();
		lastSelectedFilter = "filter";
	}

	public CardLayout getCardLayout() {
		return (CardLayout) baseFrame.getCenterPanel().getLayout();
	}

	public void setImageSet(ImageSet _imp) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void nextImage() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void previousImage() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * 画像を_imageIDのものに差し替える.
	 *
	 * @param _imageID
	 */
	public void updateImage(String _imageID) {
		CaptureImage _cimg = imageSet.getCaptureImageAt(_imageID);
		if (_cimg == null) {
			logger.log(Level.FINE, "ID:{0} is not found. skip.", _imageID);
			baseFrame.getMessageLabel().setText("ID:" + _imageID + " is not found.");
			baseFrame.getMessageLabel().setForeground(Color.RED);
			return;
		} else {
			baseFrame.getMessageLabel().setText(_cimg.getFile().getAbsolutePath());
			baseFrame.getMessageLabel().setForeground(Color.BLACK);
		}
		String filter1 = _cimg.getFilter();

		imp = new ImagePlus(_cimg.getFile().getAbsolutePath());
		baseFrame.getImageDisplayPanel().setImp(getImp());
		baseFrame.getPlotPanel().setImp(getImp());
		baseFrame.getPlotPanel().repaint();

		// 設定をロードする
		this.loadCurrentFilterSettings();
	}

	/**
	 * 画像selector用のコンボボックスのitemを初期化して、_imgSetに存在する 項目を設定する.
	 *
	 * @param _imgSet
	 */
	private void initSelectorComboBoxes(ImageSet _imgSet) {
		// 全部のselectorコンボボックスを初期化
		for (Iterator<JComboBox> it = baseFrame.getSelectCBoxes().iterator(); it.hasNext();) {
			JComboBox cbox = it.next();
			cbox.removeAllItems();
		}

		String srcPath = baseFrame.getSourceText().getText();
		baseFrame.getDirSelectCBox().addItem("folder");
		for (Iterator<String> it = _imgSet.getDirectories().iterator(); it.hasNext();) {
			String item;
			item = it.next();
			item = item.replaceAll("^" + srcPath, "");
			baseFrame.getDirSelectCBox().addItem(item);
		}
		baseFrame.getWellSelectCBox().addItem("well");
		for (Iterator<String> it = _imgSet.getWellNames().iterator(); it.hasNext();) {
			String item = it.next();
			baseFrame.getWellSelectCBox().addItem(item);
		}
		baseFrame.getPositionSelectCBox().addItem("position");
		for (Iterator<String> it = _imgSet.getPositions().iterator(); it.hasNext();) {
			String item = it.next();
			baseFrame.getPositionSelectCBox().addItem(item);
		}
		baseFrame.getzSelectCBox().addItem("z");
		for (Iterator<String> it = _imgSet.getSlices().iterator(); it.hasNext();) {
			String item = it.next();
			baseFrame.getzSelectCBox().addItem(item);
		}
		baseFrame.getTimeSelectCBox().addItem("time");
		for (Iterator<String> it = _imgSet.getTimes().iterator(); it.hasNext();) {
			String item = it.next();
			baseFrame.getTimeSelectCBox().addItem(item);
		}
		baseFrame.getFilterSelectCBox().addItem("filter");
		for (Iterator<String> it = _imgSet.getFilters().iterator(); it.hasNext();) {
			String item = it.next();
			baseFrame.getFilterSelectCBox().addItem(item);
		}

		for (Iterator<JComboBox> it = baseFrame.getSelectCBoxes().iterator(); it.hasNext();) {
			JComboBox cbox = it.next();
			if (cbox.getModel().getSize() > 0) {
				cbox.setSelectedIndex(1);
			} else {
				cbox.setSelectedIndex(0);
			}
		}
	}

	public void nextCard() {
		// get file list if cardIndex == 0, that is, at first page.
		if (cardIndex == 0) {
			if (!this.initFileList()) {
				return;
			}
			imp = new ImagePlus(this.imageSet.getShotAt(0).get(0).getFile().getAbsolutePath());
			this.initSelectorComboBoxes(imageSet);

			//baseFrame.getPlotPanel().setImp(_imp);
			//ImageCanvas canvus = _imp.getCanvas();
			//if( canvus == null){
			//  logger.fine("_imp.getCanvas() == null");
			//} else {
			baseFrame.getImageDisplayPanel().setImp(getImp());
			//}

			// 保存しているfilter情報を初期化
			this.storedMaxValues.clear();
			this.storedMinValues.clear();
			this.storedAuto.clear();
			this.storedMode.clear();
			this.storedColor.clear();
			for (String s : this.imageSet.getFilters()) {
				this.storedAuto.put(s, Boolean.FALSE);
				this.storedMaxValues.put(s, new Integer(4095));
				this.storedMinValues.put(s, new Integer(0));
				this.setScaleValues(0, 4095);
				this.baseFrame.getManualRadioButton().setSelected(true);
				this.baseFrame.getAutoRadioButton().setSelected(false);
			}

		} else if(cardIndex == 1){
			// cardIndex == 1 => フィルタセッティング終わり
			this.storeCurrentFilterSettings();
			// summary を表示
			JTextArea area = this.baseFrame.getSummaryDisplayArea();
			area.setText("");
			for(String s: this.imageSet.getFilters()){
				area.append("Filter name: " + s + "\n");
				area.append("Color: " + this.storedColor.get(s) + "\n");
				area.append("Range: " + this.storedMinValues.get(s) + "-" + this.storedMaxValues.get(s) + "\n");
				area.append("Method: ");
				area.append(this.storedAuto.get(s) ? "auto" : "manual");
				area.append("\n");
				area.append("Mode: " + this.storedMode.get(s) + "\n");
				area.append("\n");
			}
		}

		// change next card
		if (cardIndex < this.getCardSize()) {
			cardIndex++;
			this.getCardLayout().next(baseFrame.getCenterPanel());
		}
		this.updateWizerdButton();

		// 1 slide: cardIndex == 0
		// 2 slide: cardIndex == 1
		// 3 slide: cardIndex == 2

	}

	public void previousCard() {
		if (cardIndex > 0) {
			this.getCardLayout().previous(baseFrame.getCenterPanel());
			cardIndex--;
		}
		this.updateWizerdButton();
	}

	public int getCardIndex() {
		return this.cardIndex;
	}

	public int getCardSize() {
		return this.cardSize;
	}

	/**
	 * Tiff fileのリストを取得する. Tiff ファイルが存在しない場合は、falseを返す.
	 *
	 * @return Tiffファイルの取得に成功したかどうか.
	 */
	public boolean initFileList() {
		String srcPath;
		srcPath = baseFrame.getSourceText().getText(); // can't click next button if sourceText is blank.
		if (this.oldSearchPath == null || !srcPath.equals(oldSearchPath)) {
			WaitDialog _wd = new WaitDialog(baseFrame, true, baseFrame.getRecursiveRadioButton().isSelected());
			imageSet = _wd.getImageSet(srcPath);
			if (imageSet.size() == 0) {
				JOptionPane.showMessageDialog(baseFrame, "Tiff file not found in \"" + srcPath + "\"", "File not found",
					JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		return true;
	}

	public void updateRangeSlider() {
		 baseFrame.getScaleRangeSlider().firePropertyChange("lowValue", this.rangeSliderLowValue, baseFrame.getScaleRangeSlider().getLowValue());
		 baseFrame.getScaleRangeSlider().firePropertyChange("highValue", this.rangeSliderHighValue, baseFrame.getScaleRangeSlider().getHighValue());
		 this.rangeSliderHighValue = baseFrame.getScaleRangeSlider().getHighValue();
		 this.rangeSliderLowValue = baseFrame.getScaleRangeSlider().getLowValue();
	}

	public void updateWizerdButton() {
		switch (cardIndex) {
			case 0:
				if (this.validateSlide1()) { // slide1. file selection slide.
					baseFrame.getNextButton().setEnabled(true);
				} else {
					baseFrame.getNextButton().setEnabled(false);
				}
				baseFrame.getBackButton().setEnabled(false);
				baseFrame.getConvertButton().setEnabled(false);
				break;
			case 1:
				baseFrame.getNextButton().setEnabled(true);
				baseFrame.getBackButton().setEnabled(true);
				baseFrame.getConvertButton().setEnabled(false);
				break;
			case 2:
				baseFrame.getNextButton().setEnabled(false);
				baseFrame.getBackButton().setEnabled(true);
				baseFrame.getConvertButton().setEnabled(true);
				break;
			default:
				break;
		}

		StringBuffer _msg = new StringBuffer("");
		for (String msg : getMessageList()) {
			_msg.append(msg + " ");
		}
		baseFrame.getMessageLabel().setText(_msg.toString());
	}

	/**
	 * @return the messageList
	 */
	public HashSet<String> getMessageList() {
		return messageList;
	}

	/**
	 * Validate first slide input value. Source directory and destination
	 * directory have been specified?
	 *
	 * @return
	 */
	private boolean validateSlide1() {
		getMessageList().clear();
		String _src = baseFrame.getSourceText().getText();
		String _dst = baseFrame.getDestinationText().getText();
		if (_src == null || _src.equals("")) {
			getMessageList().add("Source directory is blank.");
			return false;
		}
		if (_dst == null || _dst.equals("")) {
			getMessageList().add("Destination directory is blank.");
			return false;
		}
		File _srcF = new File(_src);
		File _dstF = new File(_dst);
		if (_srcF.isDirectory() && _dstF.isDirectory() && _srcF.canWrite() && _dstF.canWrite()) {
			return true;
		}
		if (!_srcF.isDirectory()) {
			getMessageList().add(_srcF.getAbsoluteFile() + " is not directory.");
			return false;
		}
		if (!_dstF.isDirectory()) {
			getMessageList().add(_dstF.getAbsoluteFile() + " is not directory.");
			return false;
		}
		if (!_srcF.canWrite()) {
			getMessageList().add("Can't create file in " + _srcF.getAbsolutePath() + ".");
			return false;
		}
		if (!_dstF.canWrite()) {
			getMessageList().add("Can't create file in " + _dstF.getAbsolutePath() + ".");
			return false;
		}
		return false;
	}

	private boolean validateSlide2() {
		return true;
	}

	private boolean validateSlide3() {
		return true;
	}

	/**
	 * スケールの最大値と最小値のspinnerの値をセットする.
	 *
	 * @param min
	 * @param max
	 */
	public void setScaleValues(int min, int max) {
		JSpinner minSpinner = baseFrame.getMinSpinner();
		JSpinner maxSpinner = baseFrame.getMaxSpinner();
		//ChangeListener[] minl = minSpinner.getChangeListeners();
		//for(ChangeListener l : minl){ logger.fine("remove " + l); minSpinner.removeChangeListener(l);}
		//ChangeListener[] maxl = maxSpinner.getChangeListeners();
		//for(ChangeListener l : maxl){ maxSpinner.removeChangeListener(l);}
		minSpinner.setValue(min);
		maxSpinner.setValue(max);
		//for(ChangeListener l : minl){ minSpinner.addChangeListener(l);}
		//for(ChangeListener l : maxl){ maxSpinner.addChangeListener(l);}
	}

	/**
	 * 自動で設定する.
	 */
	public void adjustValues(){
		if(imp == null){
			return;
		}
		IJ.run(imp, "Enhance Contrast", "saturated=0.35");
		//this.getImp().setDisplayRange(min, max);
		int max = (int) this.getImp().getDisplayRangeMax();
		int min = (int) this.getImp().getDisplayRangeMin();
		if(max > 4095){ max = 4095;}
		if(min < 0){min = 0;}
		baseFrame.getMinSpinner().setValue(min);
		baseFrame.getMaxSpinner().setValue(max);
		baseFrame.getScaleRangeSlider().setLowerValue(min);
		baseFrame.getScaleRangeSlider().setUpperValue(max);
		logger.fine("max, min = " + max + ", " + min);
	}

	/**
	 * スケールの最大値のspinnerの値をセットする.
	 *
	 * @param max
	 */
	public void setScaleMaxValues(int max) {
		JSpinner maxSpinner = baseFrame.getMaxSpinner();
		maxSpinner.setValue(max);
	}

	/**
	 * maxSpinner の最小値を変更する.
	 *
	 * @param min
	 */
	public void setMaxSpinnerMin(int min) {
		JSpinner maxSpinner = baseFrame.getMaxSpinner();
		SpinnerNumberModel snm = (SpinnerNumberModel) maxSpinner.getModel();
		snm.setMinimum(min);
	}

	/**
	 * minSpinner の最大値を変更する.
	 *
	 * @param max
	 */
	public void setMinSpinnerMax(int max) {
		JSpinner minSpinner = baseFrame.getMinSpinner();
		SpinnerNumberModel snm = (SpinnerNumberModel) minSpinner.getModel();
		snm.setMaximum(max);
	}

	/**
	 * 輝度分布の描画を更新する.
	 */
	public void updateDensityPlot() {
		JSpinner minSpinner = baseFrame.getMinSpinner();
		JSpinner maxSpinner = baseFrame.getMaxSpinner();
		Integer min = (Integer) minSpinner.getValue();
		Integer max = (Integer) maxSpinner.getValue();
		baseFrame.getPlotPanel().setLowLimit(min);
		baseFrame.getPlotPanel().setHighLimit(max);
		if (this.getImp() != null) {
			//logger.fine("update imagePlus (" + imp.getTitle() + ") display range (" + min + ", " + max + ").");
			this.getImp().setDisplayRange(min, max);
			//this.imp.updateChannelAndDraw();
			//this.imp.show();
			// updateImage -> repaint() で表示が更新される!!
			this.getImp().updateImage();
			this.baseFrame.getImageDisplayPanel().repaint();
		}
	}

	/**
	 * 現在のminSpinnerの値を返す.
	 *
	 * @return
	 */
	public int getMinSpinnerValue() {
		JSpinner minSpinner = baseFrame.getMinSpinner();
		Integer min = (Integer) minSpinner.getValue();
		return min.intValue();
	}

	/**
	 * 現在のmaxSpinnerの値を返す.
	 *
	 * @return
	 */
	public int getMaxSpinnerValue() {
		JSpinner maxSpinner = baseFrame.getMaxSpinner();
		Integer min = (Integer) maxSpinner.getValue();
		return min.intValue();
	}

	public void storeCurrentFilterSettings() {
		if(loading == true){
			return;
		}
		String filter;
		filter = (String) baseFrame.getFilterSelectCBox().getSelectedItem();
		if (filter.equals("filter")) {
			return;
		}

		int max = this.getMaxSpinnerValue();
		this.storedMaxValues.put(filter, max);
		int min = this.getMinSpinnerValue();
		this.storedMinValues.put(filter, min);
		logger.log(Level.FINE, "Store min, max ({0}) = {1}, {2}", new Object[]{filter, min, max});
		// カラー, モード取得
		String mode;
		String color;
		mode = (String)baseFrame.modeSelecter.getSelectedItem();
		color = (String)baseFrame.colorChannelSelector.getSelectedItem();
		this.storedColor.put(filter, color);
		this.storedMode.put(filter, mode);

		// 選択されているものを調べる.
		//baseFrame.getBrightnessAutoGroup();
		if (baseFrame.getAutoRadioButton().isSelected()) {
			Boolean old = this.storedAuto.put(filter, Boolean.TRUE);
		        logger.fine("Store auto (" + filter + ") = true");
		} else if (baseFrame.getManualRadioButton().isSelected()) {
			Boolean old = this.storedAuto.put(filter, Boolean.FALSE);
		        logger.fine("Store auto (" + filter + ") = false");
		}
	}

	public void loadCurrentFilterSettings(){
		loading = true;
		String filter;
		filter = (String) baseFrame.getFilterSelectCBox().getSelectedItem();
		if (filter.equals("filter")) {
			return;
		}
		Boolean auto = this.storedAuto.get(filter);
		Integer min = this.storedMinValues.get(filter);
		Integer max = this.storedMaxValues.get(filter);
		logger.fine("Loading auto = " + auto);
		logger.fine("Loading min = " + min);
		logger.fine("Loading max = " + max);

		if(min != null && max != null){
		  this.setScaleValues(min, max);
		}
		if(auto != null){
		  this.setAutoSelected(auto);
		}
		String color = this.storedColor.get(filter);
		logger.fine("Load color = " + color);
		if(color != null){
			ComboBoxModel model = this.baseFrame.colorChannelSelector.getModel();
			model.setSelectedItem(color);
		} else {
			this.baseFrame.colorChannelSelector.setSelectedIndex(0);
		}
		this.setColor();
		loading = false;
	}



	/**
	 * @return the lastSelectedFilter
	 */
	public String getLastSelectedFilter() {
		return lastSelectedFilter;
	}

	/**
	 * @param lastSelectedFilter the lastSelectedFilter to set
	 */
	public void setLastSelectedFilter(String lastSelectedFilter) {
		this.lastSelectedFilter = lastSelectedFilter;
	}

	public void setAutoSelected(boolean auto) {
		if(auto == true){
		  this.baseFrame.getAutoRadioButton().setSelected(true);
		  this.baseFrame.getManualRadioButton().setSelected(false);
		  this.enableAutoRelatedComponents(false);
		  this.adjustValues();
		} else
		{
		  this.baseFrame.getAutoRadioButton().setSelected(false);
		  this.baseFrame.getManualRadioButton().setSelected(true);
		  this.enableAutoRelatedComponents(true);
		}
	}

	/**
	 * AutoとManual関連のボタンの有効、無効を設定する.
	 * @param bool 
	 */
	public void enableAutoRelatedComponents(boolean bool){
		this.baseFrame.getMinSpinner().setEnabled(bool);
		this.baseFrame.getMaxSpinner().setEnabled(bool);
		this.baseFrame.getAdjustButton().setEnabled(bool);
		this.baseFrame.getScaleRangeSlider().setEnabled(bool);
	}

	/**
	 * 画像の色を設定する.
	 * @param toString 
	 */
	public void setColor(String toString) {
		logger.fine("In setColor" + toString);

                //LutLoader loader = new LutLoader(this);
                //loader.run(toString.toLowerCase());
		IJ.run(this.getImp(), toString, "");
		logger.fine("Done loader.run()");
		//this.imp.setColor(Color.red);
		this.getImp().updateImage();
		this.baseFrame.getImageDisplayPanel().repaint();
		this.storeCurrentFilterSettings();
	}
	/**
	 * 現在のcolor channel combobox の色にする.
	 */
	public void setColor(){
		String color = (String)this.baseFrame.colorChannelSelector.getModel().getSelectedItem();
		this.setColor(color);
	}

	/**
	 * @return the imp
	 */
	public ImagePlus getImp() {
		return imp;
	}

	/**
	 * イメージ全部をコンバートする.
	 */
	public void convertImages(){
		if(imageSet == null){
			IJ.showMessage("No images found.");
			return;
		}
		for(CaptureImage _cm : imageSet.getFiles()){
			String _path = _cm.getFile().getAbsolutePath();
			String filter = _cm.getFilter();
			Integer min = this.storedMinValues.get(filter);
			Integer max = this.storedMaxValues.get(filter);
			Boolean auto = this.storedAuto.get(filter);
			String mode = this.storedMode.get(filter);

			ImagePlus _imp = IJ.openImage(_path);
			if(auto == true){
		           IJ.run(_imp, "Enhance Contrast", "saturated=0.35");
			} else {
				//IJ.setMinAndMax(_imp, (int) min, (int) max);
			}

			//_imp.setDisplayRange(min, max);
		}

	}

	public void updateImage() {
		String imageID = this.getCurrentSelectedImageID();
		this.updateImage(imageID);
	}

	/**
	 * 現在選択中のImageIDを返す.
	 *
	 * @return
	 */
	public String getCurrentSelectedImageID() {
		String dir = this.baseFrame.getSourceText().getText() + (String) this.baseFrame.getDirSelectCBox().getSelectedItem();
		logger.fine("dir = " + dir);
		String wellname = (String) this.baseFrame.getWellSelectCBox().getSelectedItem();
		String position = (String) this.baseFrame.getPositionSelectCBox().getSelectedItem();
		String slice = (String) this.baseFrame.getzSelectCBox().getSelectedItem();
		String time = (String) this.baseFrame.getTimeSelectCBox().getSelectedItem();
		String filter = (String) this.baseFrame.getFilterSelectCBox().getSelectedItem();
		String imageID = Pattern.quote(dir + "-" + wellname + "-" + position + "-" + slice + "-" + time + "-" + filter);
		//this.updateImage(imageID);
		//this.updateDensityPlot();
		return imageID;
	}
}

