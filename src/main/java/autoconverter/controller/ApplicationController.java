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
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import autoconverter.model.CaptureImage;
import autoconverter.model.ImageSet;
import autoconverter.view.BaseFrame;
import autoconverter.view.ImagePanel;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Pattern;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

/**
 *
 * @author yfujita
 */
public class ApplicationController implements ApplicationMediator {

	/**
	 * パラメータ(JComboBox に含まれる選択肢)からimageIDを作成する.
	 *
	 * @param directory
	 * @param well
	 * @param pos
	 * @param zpos
	 * @param time
	 * @param filter
	 * @return
	 */
	public static String createImageID(String directory, String well, String pos, String zpos, String time, String filter) {
		String _shotID = ApplicationController.createShotID(directory, well, pos, zpos, time);
		String _imageID = "";
		if (filter == null) {
			_imageID = _shotID + ":" + "NOSPECIFIED";
		} else {
			_imageID = _shotID + ":" + filter;
		}
		return _imageID;
	}

	/**
	 * パラメータ(JComboBox に含まれる選択肢)からshotID (filter以外を含む情報)を作成する.
	 *
	 * @param directory
	 * @param well
	 * @param pos
	 * @param zpos
	 * @param time
	 * @return
	 */
	public static String createShotID(String directory, String well, String pos, String zpos, String time) {
		StringBuffer _shotID = new StringBuffer(directory);
		if (well == null) {
			_shotID.append(":");
		} else {
			_shotID.append(":" + well);
		}
		if (pos == null) {
			_shotID.append(":");
		} else {
			_shotID.append(":" + pos);
		}
		if (zpos == null) {
			_shotID.append(":");
		} else {
			_shotID.append(":" + zpos);
		}
		if (time == null) {
			_shotID.append(":");
		} else {
			_shotID.append(":" + time);
		}
		return _shotID.toString();
	}

	private final BaseFrame baseFrame;
	private int cardIndex;
	private final int cardSize;
	private static final Logger logger = AutoConverterUtils.getLogger();
	private HashSet<String> messageList;
	private Object oldSearchPath;
	private ImageSet imageSet;
	private SpinnerNumberModel minSpinnerModel;
	private SpinnerNumberModel maxSpinnerModel;
	private HashMap<String, Integer> storedMaxValues;
	private HashMap<String, Integer> storedMinValues;
	private HashMap<String, Boolean> storedAuto;
	private HashMap<String, String> storedColor;
	private HashMap<String, String> storedMode;
	private HashMap<String, Integer> storedBallSizes;
	private HashMap<String, String> storedAutoType;
	private String lastSelectedFilter;
	// loadCurrentFilterSettings() 実行中かどうか
	private static boolean loading = false;
	private static int loading_stack = 1;
	// updateImage() 実行中かどうか
	private static boolean updating = false;
	// 初期化中かどうか. 基本的に画像設定の画面を表示するとき
	private static boolean initializing = false;
	private static ApplicationController self = null;
	private String pattern_string;
	private Pattern pattern;
	private SwingWorker<Integer, String> convert_swing_worker;
	public static final int ADJUST_MIN_MAX = 1;
	public static final int ADJUST_MIN_TWO_THIRD_MAX = 2;
	private boolean running_store_process = false;
	public ApplicationController(BaseFrame _base) {
		cardIndex = 0;
		cardSize = BaseFrame.MAX_CARD_SIZE;
		messageList = new HashSet<String>();
		oldSearchPath = null;
		imageSet = new ImageSet();
		baseFrame = _base;
		storedMaxValues = new HashMap<String, Integer>();
		storedMinValues = new HashMap<String, Integer>();
		storedAuto = new HashMap<String, Boolean>();
		storedAutoType = new HashMap<>();
		storedColor = new HashMap<String, String>();
		storedMode = new HashMap<String, String>();
		storedBallSizes = new HashMap<String, Integer>();
		lastSelectedFilter = "filter";
		running_store_process = false;
		self = this;
	}

	public void enableSaveCurrentFilterSettings(boolean flg){
		if(flg){
			loading_stack++;
		} else {
			loading_stack--;
		}
		if(loading_stack > 0){
			loading = true;
		} else {
			loading = false;
		}
		//logger.fine("loading_stack = " + loading_stack);
	}

	/**
	 * ApplicationController の最も新しいinstanceを返す
	 *
	 * @return
	 */
	public static ApplicationController getInstance() {
		return self;
	}

	public CardLayout getCardLayout() {
		return (CardLayout) baseFrame.getCenterPanel().getLayout();
	}

	public void setImageSet(ImageSet _imp) {
		imageSet = _imp;
	}

	public void nextImage() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	public void previousImage() {
		throw new UnsupportedOperationException("Not supported yet.");
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

	/**
	 * 現在選択中の画像でアップデートする.
	 */
	public void updateImage() {
		String imageID = this.getCurrentSelectedImageID();
		this.updateImage(imageID);
	}

	/**
	 * 画像を_imageIDのものに差し替える.
	 *
	 * @param _imageID
	 */
	public void updateImage(String _imageID) {
		if (updating == true) {
			return;
		}
		try {
			updating = true;
			CaptureImage _cimg = getImageSet().getCaptureImageAt(_imageID);
			if (_cimg == null) {
				this.setMessageLabel("ID:" + _imageID + " is not found.", Color.RED);
				return;
			} else {
				this.setMessageLabel(_cimg.getFile().getAbsolutePath());
			}

			// 毎回元のファイルからimgPlusを作って毎回filter条件を適用する.
			ImagePlus imp = new ImagePlus(_cimg.getFile().getAbsolutePath());
			this.updateImage(imp);

		} finally {
			updating = false;
		}
	}

	/**
	 * 設定にしたがって画像をアップデートする.
	 * @param imp 
	 */
	public void updateImage(ImagePlus imp) {
		// 設定をロードする
		this.loadCurrentFilterSettings();
		if (imp != null) {
			// ImagePlus を表示領域に乗せたら、各種設定をapplyしていく.
			// このあたりはもう少しすっきりした下記arterytoarteryをしないとバグの温床になりそう.
			baseFrame.getImageDisplayPanel().setImp(imp);
			Integer val = (Integer) this.baseFrame.getBallSizeSpinner().getValue();
			if (val != null && val != 0) {
				this.subtractBackground(imp, val.intValue());
			}
			if (baseFrame.getAutoRadioButton().isSelected()) {
				this.adjustValues(imp);
			}
			this.setColor();

			baseFrame.getPlotPanel().setImp(imp);
			this.updateDensityPlot();
			baseFrame.getPlotPanel().repaint();
		}

	}

	/**
	 *
	 * messageLabelへメッセージを書き込む.
	 *
	 * @param msg
	 * @param color
	 */
	public void setMessageLabel(String msg, Color color) {
		JLabel label = baseFrame.getMessageLabel();
		label.setText(msg);
		label.setForeground(color);
	}

	/**
	 * messageLabelへメッセージを書き込む.
	 *
	 * @param msg
	 */
	public void setMessageLabel(String msg) {
		this.setMessageLabel(msg, Color.BLACK);
	}

	/**
	 * 画像selector用のコンボボックスのitemを初期化して、_imgSetに存在する 項目を設定する.
	 *
	 * @param _imgSet
	 */
	private void initSelectorComboBoxes(ImageSet _imgSet) {
		// 全部のselectorコンボボックスを初期化
		this.baseFrame.enableListener(false);
		for (Iterator<JComboBox> it = baseFrame.getSelectCBoxes().iterator(); it.hasNext();) {
			JComboBox cbox = it.next();
			cbox.removeAllItems();
		}

		String srcPath = baseFrame.getSourceText().getText();
		baseFrame.getDirSelectCBox().addItem("FOLDER");
		for (Iterator<String> it = _imgSet.getDirectories().iterator(); it.hasNext();) {
			String item;
			item = it.next();
			item = item.replaceAll(Pattern.quote(srcPath), "");
			baseFrame.getDirSelectCBox().addItem(item);
		}
		baseFrame.getWellSelectCBox().addItem("<WELL>");
		for (Iterator<String> it = _imgSet.getWellNames().iterator(); it.hasNext();) {
			String item = it.next();
			baseFrame.getWellSelectCBox().addItem(item);
		}
		baseFrame.getPositionSelectCBox().addItem("<POS>");
		for (Iterator<String> it = _imgSet.getPositions().iterator(); it.hasNext();) {
			String item = it.next();
			baseFrame.getPositionSelectCBox().addItem(item);
		}
		baseFrame.getzSelectCBox().addItem("<ZPOS>");
		for (Iterator<String> it = _imgSet.getSlices().iterator(); it.hasNext();) {
			String item = it.next();
			baseFrame.getzSelectCBox().addItem(item);
		}
		baseFrame.getTimeSelectCBox().addItem("<TIME>");
		for (Iterator<String> it = _imgSet.getTimes().iterator(); it.hasNext();) {
			String item = it.next();
			baseFrame.getTimeSelectCBox().addItem(item);
		}
		baseFrame.getFilterSelectCBox().addItem("<FILTER>");
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
		this.baseFrame.enableListener(true);
	}

	/**
	 * ディレクトリやリサイズ等の情報を指定するペインから 実際の画像の設定を行う画面へ移行する際に実行される.
	 */
	public void initializeImageConfigurationPane() {
		if (getImageSet().size() < 1) {
			IJ.showMessage("No shot found");
			return;
		}
		this.baseFrame.enableListener(false); // 初期化中はlistener 無効化
		initializing = true;
		try {
			// Crop Area のパネルを有効化
			this.baseFrame.getCropAreaPanel().setVisible(true);

			// intensityのspinnerの最大値を設定する. 
			JSpinner sp = this.baseFrame.getMaxSpinner();
			SpinnerNumberModel model = (SpinnerNumberModel) sp.getModel();
			model.setMaximum(Integer.parseInt((String) this.baseFrame.getDisplayRangeComboBox().getModel().getSelectedItem()));

			// ファイル情報をlogメッセージに書き出す.
			//getImageSet().logFileInfo();
			int max_value = this.getMaxDisplayRangeValue();
			this.baseFrame.getScaleRangeSlider().setMaximum(max_value);
			this.baseFrame.getPlotPanel().setOriginalMax(max_value + 1);

			this.initSelectorComboBoxes(getImageSet());

			// 保存しているfilter情報を初期化
			this.storedMaxValues.clear();
			this.storedMinValues.clear();
			this.storedAuto.clear();
			this.storedMode.clear();
			this.storedColor.clear();
			this.storedBallSizes.clear();;
			this.baseFrame.getBallSizeSpinner().setValue(0);
			this.baseFrame.getImageScrollPane().getVerticalScrollBar().setUnitIncrement(25);
			this.baseFrame.getImageScrollPane().getHorizontalScrollBar().setUnitIncrement(25);

			for (String s : this.getImageSet().getFilters()) {
				this.storedAuto.put(s, Boolean.FALSE);
				// color setting
				String _color = AutoConverterConfig.getConfig(s, null, AutoConverterConfig.PREFIX_COLOR);
				if (_color != null) {
					this.storedColor.put(s, _color);
				}
				String _max = AutoConverterConfig.getConfig(s, "4095", AutoConverterConfig.PREFIX_MAX);
				String _min = AutoConverterConfig.getConfig(s, "0", AutoConverterConfig.PREFIX_MIN);
				String _ball = AutoConverterConfig.getConfig(s, "0", AutoConverterConfig.PREFIX_BALL);
				this.storedMaxValues.put(s, new Integer(_max));
				this.storedMinValues.put(s, new Integer(_min));
				this.storedBallSizes.put(s, new Integer(_ball));
				String _auto = AutoConverterConfig.getConfig(s, null, AutoConverterConfig.PREFIX_AUTO);
				if (_auto != null && _auto.equals("true")) {
					this.storedAuto.put(s, true);
				} else {
					this.storedAuto.put(s, false);
				}

				String _auto_type = AutoConverterConfig.getConfig(s, null, AutoConverterConfig.PREFIX_AUTO_TYPE);
				if (_auto_type != null) {
					this.storedAutoType.put(s, _auto_type);
				}

			}
			ImagePlus imp = new ImagePlus(this.getImageSet().getShotAt(0).get(0).getFile().getAbsolutePath());

			this.updateImage(imp);
		} finally {
			this.baseFrame.enableListener(true);
			initializing = false;
		}

	}

	/**
	 * 画像変換の設定が終わってnextをおして最終確認のペインを表示するときに使用
	 */
	public void showFinalSetting() {
		// cardIndex == 1 => フィルタセッティング終わり (ファイル保存する)
		this.storeCurrentFilterSettings();
		// summary を表示
		JTextArea area = this.baseFrame.getSummaryDisplayArea();
		area.setText("");
		area.append("================ summary ==============\n");
		area.append("From: " + this.baseFrame.getSourceText().getText() + "\n");
		area.append("To: " + this.baseFrame.getDestinationText().getText() + "\n\n");
		area.append("Image format: " + this.baseFrame.getImageFormatComboBox().getSelectedItem() + "\n\n");
		area.append("Remove special chars: ");
		if (this.baseFrame.getRemoveSpecialCharRadioButton().isSelected()) {
			area.append("YES");
		} else {
			area.append("NO");
		}
		area.append("\n\n");
		area.append("Include parametars in filename: ");
		if (this.baseFrame.getAddParamRadioButton().isSelected()) {
			area.append("YES");
		} else {
			area.append("NO");
		}
		area.append("\n\n");
		area.append("Cropping: ");
		ImagePanel imgPanel = this.baseFrame.getImageDisplayPanel();
		if (imgPanel.isSelected()) {
			area.append("YES\n");
			area.append("x: " + imgPanel.getLeftTopX() + "\n");
			area.append("y: " + imgPanel.getLeftTopY() + "\n");
			area.append("width: " + imgPanel.getRoiWidth() + "\n");
			area.append("height: " + imgPanel.getRoiHeight() + "\n\n");
		} else {
			area.append("NO\n\n");
		}

		area.append("Resize: ");
		if (this.getResizeX() > 0) {
			area.append(Integer.toString(this.getResizeX()) + " pixel (width)");
		} else {
			area.append("NO");
		}
		area.append("\n\n");
		area.append("Total file: " + getImageSet().size() + "\n");

		area.append("\n");
		for (String s : this.getImageSet().getFilters()) {
			area.append("Filter name: " + s + "\n");
			area.append("Color: " + this.storedColor.get(s) + "\n");
			Boolean method = this.storedAuto.get(s);
			if (method) {
				area.append("Method: auto (saturated=" + this.storedAutoType.get(s) + "%)");
				area.append("\n");
				area.append("Range: variable\n");
			} else {
				area.append("Method: manual\n");
				area.append("Range: " + this.storedMinValues.get(s) + "-" + this.storedMaxValues.get(s) + "\n");
			}
			String mode = this.storedMode.get(s);
			if (mode == null) {
				mode = "Undefined (Single)";
			}
			area.append("Mode: " + mode + "\n");
			Integer bs = this.storedBallSizes.get(s);
			String ball_str = "None";
			if (bs == null || bs == 0) {
				ball_str = "None";
			} else {
				ball_str = bs.toString();
			}
			area.append("Background subtraction: " + ball_str + "\n");
			area.append("\n");
		}

	}

	public void nextCard() {
		// get file list if cardIndex == 0, that is, at first page.
		// 1 slide: cardIndex == 0
		// 2 slide: cardIndex == 1
		// 3 slide: cardIndex == 2
		if (cardIndex == 0) {
			// ロジックとしては next button を abort に変更し、検索実行中フラグを立てて
			// ファイルリスト取得
			// その間に、abort ボタンを押したらファイル検索threadをinterruptする.
			// で元のnextに戻す. という流れかな?
			// ファイルに保存するかどうか. 付加が軽いので保存しても良いと思う.
			this.storeInitialSettings(false);
			this.startSearchFileList();
		} else if (cardIndex == 1) {
			//this.baseFrame.getCropAreaPanel().setEnabled(false);
			this.baseFrame.getCropAreaPanel().setVisible(false);
			this.showFinalSetting();
			this.getCardLayout().next(baseFrame.getCenterPanel());
			this.incrementCardIndex();
			this.updateWizerdButton();
		}

	}

	public void incrementCardIndex() {
		if (cardIndex < this.getCardSize()) {
			cardIndex++;
		}
	}

	public void decrementCardIndex() {
		if (cardIndex > 0) {
			cardIndex--;
		}
	}

	public void previousCard() {
		if (cardIndex > 0) {
			this.getCardLayout().previous(baseFrame.getCenterPanel());
			if (cardIndex == 2) {
				if (this.convert_swing_worker != null) {
					this.convert_swing_worker.cancel(true);
				}
				this.baseFrame.getCropAreaPanel().setVisible(true);
			}
			if (cardIndex == 1) {
				this.baseFrame.getCropAreaPanel().setVisible(false);
			}
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
	public boolean startSearchFileList() {

		// 多分これはeventdispatchthread になっていると思う.
		//  ファイルを検索する前にfileSearchLogTextArea を消去する.
		this.baseFrame.getFileSearchLogTextArea().setText("");

		String srcPath;
		srcPath = baseFrame.getSourceText().getText(); // can't click next button if sourceText is blank.
		if (this.oldSearchPath == null || !srcPath.equals(oldSearchPath)) {
			FileSearchWorker fsw = new FileSearchWorker(baseFrame.getSourceText().getText(), baseFrame.getRecursiveRadioButton().isSelected());
			fsw.execute();
		}
		return true;
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
			getMessageList().add("\"" + _srcF.getAbsoluteFile() + "\" is not directory.");
			return false;
		}
		if (!_dstF.isDirectory()) {
			getMessageList().add("\"" + _dstF.getAbsoluteFile() + "\" is not directory.");
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
	 * @param saving  変更情報をHashに保存しておくかどうか. Sliderからのように連続して呼ばれる場合にfalseにしておくと無駄がなくてよい.
	 */
	public void setScaleValues(int min, int max, boolean saving) {
		JSpinner minSpinner = baseFrame.getMinSpinner();
		JSpinner maxSpinner = baseFrame.getMaxSpinner();
		this.baseFrame.enableListener(false);
		maxSpinner.setValue(max);
		minSpinner.setValue(min);
		baseFrame.getScaleRangeSlider().setMinAndMax(min, max);
		this.baseFrame.enableListener(true);
		// 毎回ファイルに保存するのはちょっと大変なので、
		// config に保存するだけにしてみる.
		if( saving ){
		     this.storeCurrentFilterSettings(false);
		}
	}
	/**
	 * スケールの最大値と最小値のspinnerの値をセットする.
	 *
	 * @param min
	 * @param max
	 */
	public void setScaleValues(int min, int max) {
		this.setScaleValues(min, max, true);
	}


	public void adjustValues(){
		if (this.getImp() == null) {
			return;
		}
		ImagePlus imp = this.getImp();
		this.adjustValues(imp);
	}

	/**
	 * 自動で設定する.
	 */
	public void adjustValues(ImagePlus imp) {
		String sat_val = (String) baseFrame.getAutoTypeComboBox().getModel().getSelectedItem();
		IJ.run(imp, "Enhance Contrast", "saturated=0.35");
		int min = (int) this.getImp().getDisplayRangeMin();

		try {
			Double.parseDouble(sat_val);
			IJ.run(imp, "Enhance Contrast", "saturated=" + sat_val);
		} catch (NumberFormatException e) {
			logger.fine(e.toString());
		}
		//this.getImp().setDisplayRange(min, max);
		int max = (int) this.getImp().getDisplayRangeMax();
		if (max > this.getMaxDisplayRangeValue()) {
			max = this.getMaxDisplayRangeValue();
		}
		if (min < 0) {
			min = 0;
		}

		this.baseFrame.enableListener(false);
		baseFrame.getMaxSpinner().setValue(max);
		baseFrame.getMinSpinner().setValue(min);
		// max からセッティングしないとダメ. setLowerValue() 内で、
		// 現時点で設定されているmax より大きなminの値を与えた場合、
		// max の値が代わりに使われるため.
		baseFrame.getScaleRangeSlider().setMinAndMax(min, max);
		this.updateDensityPlot();
		this.baseFrame.enableListener(true);
		IJ.setMinAndMax(imp, (int) min, (int) max);
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
			this.getImp().setDisplayRange(min, max);
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

	/**
	 * 初期画面の設定情報を保存する.
	 *
	 * @param save ファイルに保存するかどうか.
	 */
	public void storeInitialSettings(boolean save) {
		// src, dst ディレクトリ保存
		this.storeDirectorySetting(false);

		// recursive button
		this.storeRecursiveSetting(false);

		// remove special char
		this.storeRemoveSpecialCharSetting(false);

		// image format
		this.storeFormatComboBoxSettings(false);

		// display range max 
		this.storeDisplayRangeMaxSetting(false);

		// file pattern
		this.storeFilePatternSettings(false);

		// add param
		this.storeAddParamSetting(false);

		if (save) {
			AutoConverterConfig.save(baseFrame, true);
		}
	}

	/**
	 * ファイルのパターン文字列等の保存
	 *
	 * @param save ファイルに保存するかどうか.
	 */
	public void storeFilePatternSettings(boolean save) {
		String selected_pattern_name = (String) baseFrame.getFilePatternComboBox().getModel().getSelectedItem();
		String regex_string = baseFrame.getFilePatternTextField().getText();
		if (!regex_string.equals("")) {
			AutoConverterConfig.setConfig(AutoConverterConfig.KEY_SELECTED_PATTERN, selected_pattern_name);
			AutoConverterConfig.setConfig(selected_pattern_name, regex_string, AutoConverterConfig.PREFIX_REGEXP);
			if (save) {
				AutoConverterConfig.save(baseFrame, true);
			}
		}

	}

	/**
	 * display range の最大値
	 *
	 * @param save ファイルに保存するかどうか.
	 */
	public void storeDisplayRangeMaxSetting(boolean save) {
		String selected = (String) this.baseFrame.getDisplayRangeComboBox().getModel().getSelectedItem();
		AutoConverterConfig.setConfig(AutoConverterConfig.KEY_SELECTED_DISPLAY_RANGE, selected);
		if (save) {
			AutoConverterConfig.save(baseFrame, true);
		}
	}

	/**
	 * 変換先フォーマット保存
	 *
	 * @param save ファイルに保存するかどうか.
	 */
	public void storeFormatComboBoxSettings(boolean save) {
		String selected = (String) this.baseFrame.getImageFormatComboBox().getModel().getSelectedItem();
		AutoConverterConfig.setConfig(AutoConverterConfig.KEY_IMAGE_FORMAT, selected);
		if (save) {
			AutoConverterConfig.save(baseFrame, true);
		}
	}

	/**
	 * 特殊文字を削除するかどうかの設定を保存.
	 *
	 * @param save ファイルに保存するかどうか.
	 */
	public void storeRemoveSpecialCharSetting(boolean save) {
		boolean _select = this.baseFrame.getRemoveSpecialCharRadioButton().isSelected();
		AutoConverterConfig.setConfig(AutoConverterConfig.KEY_REMOVE_SPECIAL_CHAR, java.text.MessageFormat.format(java.util.ResourceBundle.getBundle("autoconverter/controller/Bundle").getString("{0}"), new Object[]{_select}));
		if (save) {
			AutoConverterConfig.save(baseFrame, true);
		}
	}

	/**
	 * ファイル名に変換設定を加えて保存する.
	 *
	 * @param save ファイルに保存するかどうか.
	 */
	public void storeAddParamSetting(boolean save) {
		boolean selected = baseFrame.getAddParamRadioButton().isSelected();
		AutoConverterConfig.setConfig(AutoConverterConfig.KEY_ADD_PARAM_TO_FILENAME, java.text.MessageFormat.format(java.util.ResourceBundle.getBundle("autoconverter/controller/Bundle").getString("{0}"), new Object[]{selected}));
		if (save) {
			AutoConverterConfig.save(baseFrame, true);
		}
	}

	/**
	 * recursive buttonの状態を保存
	 *
	 * @param save ファイルに保存するかどうか.
	 */
	public void storeRecursiveSetting(boolean save) {
		boolean _select = this.baseFrame.getRecursiveRadioButton().isSelected();
		AutoConverterConfig.setConfig(AutoConverterConfig.KEY_RECURSIVE_ON, java.text.MessageFormat.format(java.util.ResourceBundle.getBundle("autoconverter/controller/Bundle").getString("{0}"), new Object[]{_select}));
		if (save) {
			AutoConverterConfig.save(baseFrame, true);
		}
	}

	/**
	 * 読出元、書き出し先のディレクトリ情報を保存する.
	 *
	 * @param save ファイルに保存するかどうか.
	 */
	public void storeDirectorySetting(boolean save) {
		File _srcDir = new File(this.baseFrame.getSourceText().getText());
		if (_srcDir != null) {
			AutoConverterConfig.setConfig(AutoConverterConfig.KEY_SOURCE_DIRECTORY, _srcDir.getAbsolutePath());
		}
		File _dstDir = new File(this.baseFrame.getDestinationText().getText());
		if (_dstDir != null) {
			AutoConverterConfig.setConfig(AutoConverterConfig.KEY_DESTINATION_DIRECTORY, _dstDir.getAbsolutePath());
		}
		if (save) {
			AutoConverterConfig.save(baseFrame, true);
		}

	}

	/**
	 * ファイルに設定を保存せずにメモリ上(AutoConverterConfig.config)にのみ 保存する. ただし、loading
	 * (起動中フラグ)が立っているときは無視する.
	 */
	public void storeCurrentFilterSettings() {
		this.storeCurrentFilterSettings(true);
	}

	/**
	 * ファイルに設定を保存する. ただし、loading (起動中フラグ)が立っているときは無視する. 書き込む.
	 */
	public void storeCurrentFilterSettings(boolean saving) {
		try {
			if (loading == false || initializing == true) {
				return;
			}
			// running_store_process が立っている間(連続で実行されている)は保存をしない.
			if (running_store_process == true) {
				return;
			}
			running_store_process = true;
			String filter;
			filter = (String) baseFrame.getFilterSelectCBox().getSelectedItem();
			if (filter.equals("filter")) {
				return;
			}

			int max = this.getMaxSpinnerValue();
			this.storedMaxValues.put(filter, max);
			AutoConverterConfig.setConfig(filter, max, AutoConverterConfig.PREFIX_MAX);
			int min = this.getMinSpinnerValue();
			this.storedMinValues.put(filter, min);
			AutoConverterConfig.setConfig(filter, min, AutoConverterConfig.PREFIX_MIN);
			// カラー, モード取得
			String mode;
			String color;
			mode = (String) baseFrame.modeSelecter.getSelectedItem();
			color = (String) baseFrame.colorChannelSelector.getSelectedItem();
			this.storedColor.put(filter, color);
			AutoConverterConfig.setConfig(filter, color, AutoConverterConfig.PREFIX_COLOR);

			this.storedMode.put(filter, mode);

			// ball size
			Integer ball_size = this.getBallSize();
			this.storedBallSizes.put(filter, ball_size);
			AutoConverterConfig.setConfig(filter, ball_size, AutoConverterConfig.PREFIX_BALL);

			// 選択されているものを調べる.
			if (baseFrame.getAutoRadioButton().isSelected()) {
				this.storedAuto.put(filter, Boolean.TRUE);
				AutoConverterConfig.setConfig(filter, "true", AutoConverterConfig.PREFIX_AUTO);
			} else if (baseFrame.getManualRadioButton().isSelected()) {
				this.storedAuto.put(filter, Boolean.FALSE);
				AutoConverterConfig.setConfig(filter, "false", AutoConverterConfig.PREFIX_AUTO);
			}

			String adjust_type = (String) baseFrame.getAutoTypeComboBox().getModel().getSelectedItem();
			this.storedAutoType.put(filter, adjust_type);
			AutoConverterConfig.setConfig(filter, adjust_type, AutoConverterConfig.PREFIX_AUTO_TYPE);

			if (saving) {
				AutoConverterConfig.save(baseFrame, true);
			}
		} finally {
			this.running_store_process = false;
		}

	}

	public void loadCurrentFilterSettings() {
		try {
			this.enableSaveCurrentFilterSettings(false);
			String filter;
			filter = (String) baseFrame.getFilterSelectCBox().getSelectedItem();
			if (filter.equals("filter")) {
				return;
			}
			Boolean auto = this.storedAuto.get(filter);
			Integer min = this.storedMinValues.get(filter);
			Integer max = this.storedMaxValues.get(filter);
			Integer ballSize = this.storedBallSizes.get(filter);
			String auto_type = this.storedAutoType.get(filter);

			this.baseFrame.enableListener(false);
			if (min != null && max != null) {
				this.setScaleValues(min, max);
			}
			if (auto != null) {
				this.setAutoSelected(auto);
			}
			if (auto_type != null) {
				this.baseFrame.getAutoTypeComboBox().getModel().setSelectedItem(auto_type);
			} else {
				this.baseFrame.getAutoTypeComboBox().setSelectedIndex(0);
			}
			if (ballSize != null) {
				this.baseFrame.getBallSizeSpinner().setValue(ballSize);
			}

			String color = this.storedColor.get(filter);
			if (color != null) {
				ComboBoxModel model = this.baseFrame.colorChannelSelector.getModel();
				model.setSelectedItem(color);
			} else {
				this.baseFrame.colorChannelSelector.setSelectedIndex(0);
			}
			this.baseFrame.enableListener(true);
		} finally {
			this.enableSaveCurrentFilterSettings(true);
		}
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

	/**
	 * AutoとManual関連のボタンのON/OFFを設定する.
	 *
	 * @param auto autoの状態を変更する.
	 */
	public void setAutoSelected(boolean auto) {

		this.baseFrame.getAutoRadioButton().setSelected(auto);
		this.baseFrame.getManualRadioButton().setSelected(!auto);
		this.configAutoRelatedComponents(auto);
		if (auto == true) {
			this.adjustValues();
		}
	}

	/**
	 * AutoとManual関連のボタンの有効、無効を設定する.
	 *
	 * @param bool auto が有効の場合
	 */
	public void configAutoRelatedComponents(boolean bool) {
		this.baseFrame.getMinSpinner().setEnabled(!bool);
		this.baseFrame.getMaxSpinner().setEnabled(!bool);
		this.baseFrame.getAdjustButton().setEnabled(!bool);
		this.baseFrame.getScaleRangeSlider().setEnabled(!bool);
		//this.baseFrame.getAutoTypeComboBox().setEnabled(bool);
	}

	/**
	 * 画像の色を設定する.
	 *
	 * @param toString
	 */
	public void setColor(String toString) {
		if (this.getImp() == null) {
			return;
		}
		IJ.run(this.getImp(), toString, "");
		this.getImp().updateImage();
		this.baseFrame.getImageDisplayPanel().repaint();
		this.storeCurrentFilterSettings(false);
	}

	/**
	 * バックグラウンド補正を行う.
	 *
	 * @param radius 直径.
	 */
	public void subtractBackground(int radius) {
		ImagePlus _imp = this.getImp();
		this.subtractBackground(_imp, radius);
	}

	public void subtractBackground(ImagePlus _imp, int radius) {
		if (_imp == null) {
			return;
		}
		// 画像を更新
		if (radius < 20) {
			this.setMessageLabel("Substraction ball is too small. Ignored.", Color.RED);
			return;
		}
		IJ.run(_imp, "Subtract Background...", "rolling=" + radius);
		_imp.updateImage();
		this.baseFrame.getImageDisplayPanel().repaint();
	}

	/**
	 * 現在のcolor channel combobox の色にする.
	 */
	public void setColor() {
		String color = (String) this.baseFrame.colorChannelSelector.getModel().getSelectedItem();
		this.setColor(color);
	}

	/**
	 * @return the imp
	 */
	public ImagePlus getImp() {
		return this.baseFrame.getImageDisplayPanel().getImp();
	}

	public String getDestinationDirectoryPath() {
		String dst = baseFrame.getDestinationText().getText();
		return dst;
	}

	public String getSourceDirectoryPath() {
		String src = baseFrame.getSourceText().getText();
		return src;
	}

	/**
	 * イメージ全部をコンバートする.
	 */
	public void convertImages() {
		if (getImageSet() == null) {
			IJ.showMessage("No images found.");
			return;
		}
		final JTextArea _area = baseFrame.getSummaryDisplayArea();
		baseFrame.getConvertButton().setEnabled(false);

		final String dst = this.getDestinationDirectoryPath();
		final String src = this.getSourceDirectoryPath();

		final boolean remove_char = this.baseFrame.getRemoveSpecialCharRadioButton().isSelected();

		final String type = (String) baseFrame.getImageFormatComboBox().getSelectedItem();

		final boolean addparam = baseFrame.getAddParamRadioButton().isSelected();

		convert_swing_worker = new SwingWorker<Integer, String>() {
			@Override
			protected void process(java.util.List<String> chunks) {
				for (String s : chunks) {
					_area.append(s);
					_area.setCaretPosition(_area.getText().length());
				}
			}

			@Override
			protected Integer doInBackground() throws Exception {
				int number = getImageSet().size();
				int count = 1;
				ImagePanel imgPanel = baseFrame.getImageDisplayPanel();
				int crop_height = imgPanel.getRoiHeight();
				int crop_width = imgPanel.getRoiWidth();
				int crop_x = imgPanel.getLeftTopX();
				int crop_y = imgPanel.getLeftTopY();
				int resize_x = getResizeX();

				for (CaptureImage _cm : getImageSet().getFiles()) {
					if (isCancelled()) {
						return new Integer(22);
					}
					String _path = _cm.getFile().getAbsolutePath();
					String filter = _cm.getFilter();
					Integer bs = storedBallSizes.get(filter);
					int ballsize = 0;
					if (bs == null) {
						ballsize = 0;
					} else {
						ballsize = bs.intValue();
					}
					Integer min = storedMinValues.get(filter);
					Integer max = storedMaxValues.get(filter);
					Boolean auto = storedAuto.get(filter);
					String auto_type = storedAutoType.get(filter);
					String mode = storedMode.get(filter);
					String color = storedColor.get(filter);
					if (color == null) {
						color = "Grays";
					}
					if (mode == null) {
						mode = "Single";
					}

					// directory check
					String fname = _cm.getFile().getName();
					String abssrc = _cm.getFile().getAbsolutePath();
					// src     => /src
					// dst     => /dst
					// rpath   => /path/target/
					// dstpath => /dst/path/target
					// rtop    => /dst/path
					String rpath = _path.replaceFirst(Pattern.quote(src), "");
					String dstpath = dst + rpath;
					File dstdir = new File(dstpath).getParentFile();
					if (!dstdir.exists()) { //ディレクトリが無い!
						dstdir.mkdirs();
					} else if (!dstdir.isDirectory()) {
						// ディレクトリ以外!
						IJ.showMessage(dstdir + " is not directory. stop.");
						return new Integer(1);
					}
					if (remove_char) { // special character 削除
						fname = AutoConverterUtils.tr("()[]{} *?/:;!<>#$%&'\"\\", "______________________", fname).replaceAll("_+", "_").replaceAll("_-_", "-").replaceAll("_+\\.", ".");

					}

					String dstbase = removeExtension(dstdir + File.separator + fname);

					ImagePlus _imp = IJ.openImage(_path);
					if (ballsize != 0) {
						IJ.run(_imp, "Subtract Background...", "rolling=" + ballsize);
						if (addparam) {
							dstbase = dstbase + "_BALL" + ballsize;
						}
					}

					if (auto == true) {
						IJ.run(_imp, "Enhance Contrast", "saturated=0.35");
						min = (int) _imp.getDisplayRangeMin();
						IJ.run(_imp, "Enhance Contrast", "saturated=" + auto_type);
						max = (int) _imp.getDisplayRangeMax();
						IJ.setMinAndMax(_imp, min, max);
						if (addparam) {
							dstbase = dstbase + "_AUTO" + auto_type;
						}
					} else {
						IJ.setMinAndMax(_imp, (int) min, (int) max);
						if (addparam) {
							dstbase = dstbase + "_RANGE" + min + "-" + max;
						}
					}
					if(_imp.getType() == ImagePlus.GRAY8 || _imp.getType() == ImagePlus.COLOR_256 || _imp.getType() == ImagePlus.COLOR_RGB){
					  IJ.run(_imp, "Apply LUT", "");
					}
					// 色設定.
					IJ.run(_imp, color, "");

					if (crop_height != 0 && crop_width != 0) { // crop 領域が設定されている.
						_imp.setRoi(crop_x, crop_y, crop_width, crop_height);
						IJ.run(_imp, "Crop", "");
						//IJ.run(imp, "Select None", "");
						if (addparam) {
							dstbase = dstbase + "_CROPx" + crop_x + "y" + crop_y + "w" + crop_width + "h" + crop_height;
						}
					}
					int width  = _imp.getWidth();
					int height = _imp.getHeight();
					int resize_y = 0;
					if(width != resize_x && resize_x != 0 ){
						resize_y = height * resize_x / width;
					}

					// resize
					if (resize_x > 0 && resize_y > 0) {
						IJ.run(_imp, "Size...", "width=" + resize_x + " height=" + resize_y + "512 constrain average interpolation=Bilinear");
					}

					String fpath = "";
					if (type.equals("jpg")) {
						fpath = dstbase + ".jpg";
						IJ.saveAs(_imp, "jpg", fpath);
					} else if (type.equals("png") || type.equals("ping")) {
						fpath = dstbase + ".png";
						IJ.saveAs(_imp, "png", fpath);
					} else if (type.equals("tif") || type.equals("8bit tiff")) {
						fpath = dstbase + ".tif";
						IJ.run(_imp, "RGB Color", null);
						IJ.saveAsTiff(_imp, fpath);
					}
					publish("(" + count + "/" + number + ") " + abssrc + "  ==>   " + fpath + "\n");
					_imp.close();
					count++;
				}
				return new Integer(0);
			}

			@Override
			public void done() {
				baseFrame.getConvertButton().setEnabled(true);
				if (isCancelled()) {
					return;
				}
				_area.append("Conversion finished.\n");
				Calendar now = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
				String date = sdf.format(now.getTime());

				//String date = "" + now.get(Calendar.YEAR) + now.get(Calendar.MONDAY) + now.get(Calendar.DAY_OF_MONTH) + "_" + now.get(Calendar.HOUR_OF_DAY) + "h" + now.get(Calendar.MINUTE) + "m" + now.get(Calendar.SECOND) + "s";
				String memoPath = dst + File.separator + "conversion_log" + date + ".txt";
				try {
					BufferedWriter bw = new BufferedWriter(new FileWriter(new File(memoPath)));
					bw.write(_area.getText());
					bw.close();

				} catch (IOException ex) {
					_area.append("Fail to write log to " + memoPath);
				}
			}
		};
		convert_swing_worker.execute();
	}

	public int getBallSize() {
		Integer size = (Integer) this.baseFrame.getBallSizeSpinner().getValue();
		if (size != null) {
			return size.intValue();
		} else {
			return 0;
		}
	}

	public BaseFrame getBaseFrame() {
		return baseFrame;
	}

	/**
	 * 0 は選択されていない場合.
	 *
	 * @return
	 */
	public int getResizeX() {
		JSpinner sp = this.baseFrame.getResizeSpinner();
		if (sp.isEnabled()) {
			Integer x_scale = (Integer) sp.getValue();
			return x_scale.intValue();
		} else {
			return 0;
		}
	}

	/**
	 * 現在選択中のImageIDを返す.
	 *
	 * @return
	 */
	public String getCurrentSelectedImageID() {
		String dir = this.baseFrame.getSourceText().getText() + (String) this.baseFrame.getDirSelectCBox().getSelectedItem();
		String wellname = (String) this.baseFrame.getWellSelectCBox().getSelectedItem();
		String position = (String) this.baseFrame.getPositionSelectCBox().getSelectedItem();
		String slice = (String) this.baseFrame.getzSelectCBox().getSelectedItem();
		String time = (String) this.baseFrame.getTimeSelectCBox().getSelectedItem();
		String filter = (String) this.baseFrame.getFilterSelectCBox().getSelectedItem();
		String imageID = createImageID(dir, wellname, position, slice, time, filter);

		return imageID;
	}

	/**
	 * @return the imageSet
	 */
	public ImageSet getImageSet() {
		return imageSet;
	}

	/**
	 * ファイルのパターン文字列を取得する.
	 *
	 * @return
	 */
	public String getFilePatternString() {
		JTextField filePatternTextField = this.baseFrame.getFilePatternTextField();
		return filePatternTextField.getText();
	}

	public Pattern getFilePattern() {
		if (pattern_string == null) {
			pattern_string = this.getFilePatternString();
		}
		if (pattern_string.equals(this.getFilePatternString()) && pattern != null) {
			return pattern;
		} else {
			pattern_string = this.getFilePatternString();
			pattern = Pattern.compile(pattern_string);
			return pattern;
		}
	}

	public int getMaxDisplayRangeValue() {
		String disp = (String) this.baseFrame.getDisplayRangeComboBox().getModel().getSelectedItem();
		int max_value = Integer.parseInt(disp);
		return max_value;
	}

	public void setCropPanel(int x, int y, int w, int h) {
		this.baseFrame.getxTextField().setText("" + x);
		this.baseFrame.getyTextField().setText("" + y);
		this.baseFrame.getwTextField().setText("" + w);
		this.baseFrame.gethTextField().setText("" + h);
	}
}
