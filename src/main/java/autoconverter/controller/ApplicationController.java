/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package autoconverter.controller;

import autoconverter.model.CaptureImage;
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
import autoconverter.model.ExImagePlus;
import autoconverter.model.ImageSet;
import autoconverter.view.BaseFrame;
import autoconverter.view.ImagePanel;
import ij.CompositeImage;
import ij.ImageStack;
import ij.gui.Overlay;
import ij.gui.Roi;
import ij.gui.ShapeRoi;
import ij.io.FileInfo;
import ij.measure.Measurements;
import ij.plugin.ImageCalculator;
import ij.plugin.LutLoader;
import ij.plugin.RGBStackMerge;
import ij.plugin.filter.ThresholdToSelection;
import ij.plugin.frame.RoiManager;
import ij.plugin.frame.ThresholdAdjuster;
import ij.process.ImageProcessor;
import ij.process.ImageStatistics;
import ij.process.LUT;
import java.awt.Rectangle;
import java.awt.image.IndexColorModel;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.Integer.min;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

/**
 *
 * @author yfujita
 */
public class ApplicationController implements ApplicationMediator, Measurements {

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
	private HashMap<String, Integer> storedMax;
	private HashMap<String, Integer> storedMin;
	private HashMap<String, Boolean> storedAuto;
	private HashMap<String, String> storedColor;
	private HashMap<String, Integer> storedBall;
	private HashMap<String, String> storedAutoType;
	// Threshold モードのときの各種パラメータを保存. 色は白黒でOK.
	private HashMap<String, Integer> storedTMax;
	private HashMap<String, Integer> storedTMin;
	private HashMap<String, Boolean> storedTAuto;
	private HashMap<String, String> storedTColor;
	private HashMap<String, Integer> storedTBall;
	private HashMap<String, String> storedTAutoType;
	// Relatibve モードのときの各種パラメータを保存. 色は白黒でOK.
	private HashMap<String, Integer> storedRMax;
	private HashMap<String, Integer> storedRMin;
	private HashMap<String, Boolean> storedRAuto;
	private HashMap<String, String> storedRColor;
	private HashMap<String, Integer> storedRBall;
	private HashMap<String, String> storedRAutoType;

	// 現在のモードの各種パラメータ
	private HashMap<String, Integer> maxHash = null;
	private HashMap<String, Integer> minHash = null;
	private HashMap<String, Boolean> autoHash = null;
	private HashMap<String, Integer> ballHash = null;
	private HashMap<String, String>  typeHash = null;
	private HashMap<String, String>  colorHash = null;


	private ThresholdToSelection tts;
	private ImageCalculator ic;
	private String lastSelectedFilter;
	// loadCurrentFilterSettings() 実行中かどうか
	private static boolean loading = false;
	// 値が update されたかどうか
	private static boolean ischanged = true;
	private static int loading_stack = 0;
	// updateImage() 実行中かどうか
	private static boolean updating = false;
	// 初期化中かどうか. 基本的に画像設定の画面を表示するとき
	private static boolean initializing = false;
	private static ApplicationController self = null;
	private String pattern_string;
	private Pattern pattern;
	private ImageProcessWorker convert_swing_worker;
	public static final int ADJUST_MIN_MAX = 1;
	public static final int ADJUST_MIN_TWO_THIRD_MAX = 2;
	private boolean running_store_process = false;
	private int current_image_mode = BaseFrame.IMAGE_MODE_SINGLE;
	public static final String[] COLOR_INDEX = {"Red", "Green", "Blue", "Grays", "Cyan", "Magenta", "Yellow"};
	public static double RELATIVE_MULTIPLICITY = 1000.0;
	private static int COLOR_DEPTH = 256;

	private void printSavedParams(String _filter){
		int stack = 1;
		String[] current_stack = AutoConverterUtils.stackTraceString(stack, false);
		TreeSet <String> filters = new TreeSet(this.getStoredMin().keySet());
		if(_filter != null){
			filters.clear();
			filters.add(_filter);
		}
		StringBuffer sb = new StringBuffer();
		sb.append("\nMode: " + this.getImageModeString());
		for(String filter: filters){
			logger.fine("\n======== Paramters: Call from " + current_stack[stack-1] + " ==========");
			sb.append("\n=-=-=-= [" + filter + "] =-=-=-=\n");
			Integer min = this.getMinHash().get(filter);
			Integer max = this.getMaxHash().get(filter);
			String  sat = this.getTypeHash().get(filter);
			String color = this.getColorHash().get(filter);
			Integer ball = this.getBallHash().get(filter);
			Boolean auto = this.getAutoHash().get(filter);
			sb.append("==== Current\n");
			sb.append("min=" + min);
			sb.append(", max=" + max);
			sb.append(", color=" + color);
			sb.append(", ball=" + ball);
			sb.append(", auto=" + auto);
			sb.append(", sat=" + sat + "\n");

			min = this.getStoredMin().get(filter);
			max = this.getStoredMax().get(filter);
			sat = this.getStoredAutoType().get(filter);
			color = this.getStoredColor().get(filter);
			ball = this.getStoredBall().get(filter);
			auto = this.getStoredAuto().get(filter);
			sb.append("==== Single\n");
			sb.append("min=" + min);
			sb.append(", max=" + max);
			sb.append(", color=" + color);
			sb.append(", ball=" + ball);
			sb.append(", auto=" + auto);
			sb.append(", sat=" + sat + "\n");
			
			min = this.getStoredTMin().get(filter);
			max = this.getStoredTMax().get(filter);
			sat = this.getStoredTAutoType().get(filter);
			color = this.getStoredTColor().get(filter);
			ball = this.getStoredTBall().get(filter);
			auto = this.getStoredTAuto().get(filter);
			sb.append("==== Threshold\n");
			sb.append("min=" + min);
			sb.append(", max=" + max);
			sb.append(", color=" + color);
			sb.append(", ball=" + ball);
			sb.append(", auto=" + auto);
			sb.append(", sat=" + sat + "\n");
			
			min = this.getStoredRMin().get(filter);
			max = this.getStoredRMax().get(filter);
			sat = this.getStoredRAutoType().get(filter);
			color = this.getStoredRColor().get(filter);
			ball = this.getStoredRBall().get(filter);
			auto = this.getStoredRAuto().get(filter);
			sb.append("==== Relative\n");
			sb.append("min=" + min);
			sb.append(", max=" + max);
			sb.append(", color=" + color);
			sb.append(", ball=" + ball);
			sb.append(", auto=" + auto);
			sb.append(", sat=" + sat + "\n");
		}
		logger.fine(sb.toString());
	}

	public ApplicationController(BaseFrame _base) {
		cardIndex = 0;
		cardSize = BaseFrame.MAX_CARD_SIZE;
		messageList = new HashSet<String>();
		oldSearchPath = null;
		imageSet = new ImageSet();
		baseFrame = _base;

		storedMax = new HashMap<String, Integer>();
		storedMin = new HashMap<String, Integer>();
		storedAuto = new HashMap<String, Boolean>();
		storedAutoType = new HashMap<String, String>();
		storedColor = new HashMap<String, String>();
		storedBall = new HashMap<String, Integer>();
		
		storedTMax = new HashMap<String, Integer>();
		storedTMin = new HashMap<String, Integer>();
		storedTAuto = new HashMap<String, Boolean>();
		storedTAutoType = new HashMap<String, String>();
		storedTColor = new HashMap<String, String>();
		storedTBall = new HashMap<String, Integer>();

		storedRMax = new HashMap<String, Integer>();
		storedRMin = new HashMap<String, Integer>();
		storedRAuto = new HashMap<String, Boolean>();
		storedRAutoType = new HashMap<String, String>();
		storedRColor = new HashMap<String, String>();
		storedRBall = new HashMap<String, Integer>();

		this.minHash = storedMin;
		this.maxHash = storedMax;
		this.autoHash = storedAuto;
		this.typeHash = storedAutoType;
		this.colorHash = storedColor;
		this.ballHash = storedBall;

		tts = new ThresholdToSelection();
		ic  = new ImageCalculator();

		lastSelectedFilter = "filter";
		running_store_process = false;
		self = this;
	}

	/**
	 * 各種パラメータを集めて store... に保存する
	 */
	public synchronized void collectParams(){
		collectParams(null);
	}

	/**
	 * モードによってNextButtonの有効化、無効化を切り替える
	 * merge mode のときはfiltercheckbox が2つ以上選択されていることがnextbuttonの有効化に必須条件
	 */
	public void updateNextButton(){
		JButton next_button = this.baseFrame.getNextButton();
		int mode = this.getImageMode();
		if(mode == BaseFrame.IMAGE_MODE_SINGLE){
			next_button.setEnabled(true);
		} else if(mode == BaseFrame.IMAGE_MODE_MERGE){
			if( this.getSelectedFilters().size() < 2){
				next_button.setEnabled(false);
			} else {
				next_button.setEnabled(true);
			}
		} else if(mode == BaseFrame.IMAGE_MODE_THRESHOLD){
			next_button.setEnabled(true);
		} else if(mode == BaseFrame.IMAGE_MODE_RELATIVE){
			String tgt = this.getTargetFilter();
			String ref = this.getReferenceFilter();
			if(tgt == null || ref == null || tgt.equals(ref)){
				next_button.setEnabled(false);
			} else {
				next_button.setEnabled(true);
			}
		}
	}

	public synchronized void collectParams(Object obj){
		logger.fine("\n\n================== IN collectParams() ==================");
		//AutoConverterUtils.printStackTrace(15);
		String filter = (String) this.baseFrame.getFilterSelectCBox().getSelectedItem();
		if(filter.equals("<FILTER>")){
			return;
		}
		logger.fine("filter=" + filter);
		logger.fine("mode=" + this.getImageModeString());
		//this.printSavedParams(filter);
		int mode = this.getImageMode();
		if       (obj == this.baseFrame.getMinSpinner()){
			int min = this.getMinSpinnerValue();
			logger.fine("get min="+min);
			if(min != getMinHash().get(filter) || mode == BaseFrame.IMAGE_MODE_RELATIVE){
				getMinHash().put(filter, min);
				ischanged = true;
				if(mode == BaseFrame.IMAGE_MODE_RELATIVE){
					for(String _filter : this.getStoredRMin().keySet()){ // relative モードはフィルタによらず同一
						logger.fine("Rlative mode: filter:" + _filter + " -> min: " + min);
						getStoredRMin().put(_filter, min);
					}
					AutoConverterConfig.setConfig(filter, min, AutoConverterConfig.PREFIX_R_MIN);
				} else if(mode == BaseFrame.IMAGE_MODE_THRESHOLD){
					AutoConverterConfig.setConfig(filter, min, AutoConverterConfig.PREFIX_T_MIN);
				} else {
					AutoConverterConfig.setConfig(filter, min, AutoConverterConfig.PREFIX_MIN);
				}
			}
		}
		if(obj == this.baseFrame.getMaxSpinner()){
			int max = this.getMaxSpinnerValue();
			logger.fine("get max="+max);
			if(max != getMaxHash().get(filter) || mode == BaseFrame.IMAGE_MODE_RELATIVE){
				logger.fine("updating from " + getMaxHash().get(filter) + " to " + max);
				getMaxHash().put(filter, max);
				ischanged = true;
				if(mode == BaseFrame.IMAGE_MODE_RELATIVE){
					for(String _filter : this.getStoredRMax().keySet()){ // relative モードはフィルタによらず同一
						logger.fine("Rlative mode: filter:" + _filter + " -> max: " + max);
						getStoredRMax().put(_filter, max);
					}
					AutoConverterConfig.setConfig(filter, max, AutoConverterConfig.PREFIX_R_MAX);
				} else if(mode == BaseFrame.IMAGE_MODE_THRESHOLD){
					AutoConverterConfig.setConfig(filter, max, AutoConverterConfig.PREFIX_T_MAX);
				} else {
					AutoConverterConfig.setConfig(filter, max, AutoConverterConfig.PREFIX_MAX);
				}
				//this.printSavedParams(filter);
			}
		}
		if(obj == this.baseFrame.getScaleRangeSlider() || obj == null){
			int min = this.baseFrame.getScaleRangeSlider().getLowValue();
			int max = this.baseFrame.getScaleRangeSlider().getUpperValue();
			int c_min = getMinHash().get(filter);
			int c_max = getMaxHash().get(filter);
			if(min != getMinHash().get(filter) || mode == BaseFrame.IMAGE_MODE_RELATIVE){
				logger.fine("min update! " + c_min + " => " + min);
				getMinHash().put(filter, min);
				ischanged = true;
				if(mode == BaseFrame.IMAGE_MODE_RELATIVE){
					for(String _filter : this.getStoredRMin().keySet()){ // relative モードはフィルタによらず同一
						logger.fine("Rlative mode: filter:" + _filter + " -> min: " + min);
						getStoredRMin().put(_filter, min);
					}
					AutoConverterConfig.setConfig(filter, min, AutoConverterConfig.PREFIX_R_MIN);
				} else if(mode == BaseFrame.IMAGE_MODE_THRESHOLD){
					AutoConverterConfig.setConfig(filter, min, AutoConverterConfig.PREFIX_T_MIN);
				} else {
					AutoConverterConfig.setConfig(filter, min, AutoConverterConfig.PREFIX_MIN);
				}
				//this.printSavedParams(filter);
			}
			if(max != getMaxHash().get(filter) || mode == BaseFrame.IMAGE_MODE_RELATIVE){
				logger.fine("max update! " + c_max + " => " + max);
				getMaxHash().put(filter, max);
				ischanged = true;
				if(mode == BaseFrame.IMAGE_MODE_RELATIVE){
					for(String _filter : this.getStoredRMax().keySet()){ // relative モードはフィルタによらず同一
						logger.fine("Rlative mode: filter:" + _filter + " -> max: " + max);
						getStoredRMax().put(_filter, max);
					}
					AutoConverterConfig.setConfig(filter, max, AutoConverterConfig.PREFIX_R_MAX);
				} else if(mode == BaseFrame.IMAGE_MODE_THRESHOLD){
					AutoConverterConfig.setConfig(filter, max, AutoConverterConfig.PREFIX_T_MAX);
				} else {
					AutoConverterConfig.setConfig(filter, max, AutoConverterConfig.PREFIX_MAX);
				}
				//this.printSavedParams(filter);
			}
		}
		if(obj == this.baseFrame.getAutoRadioButton() || obj == this.baseFrame.getManualRadioButton() || obj == null){
			boolean isAuto = this.baseFrame.getAutoRadioButton().isSelected();
			logger.fine("get isAuto="+isAuto);
			if(isAuto != getAutoHash().get(filter) || mode == BaseFrame.IMAGE_MODE_RELATIVE){
			        getAutoHash().put(filter, isAuto);
				ischanged = true;
				String tfstr = "false";
				if(isAuto){
					tfstr = "true";
				} else {
					tfstr = "false";
				}
				if(mode == BaseFrame.IMAGE_MODE_RELATIVE){
					for(String _filter : this.getStoredRAuto().keySet()){ // relative モードはフィルタによらず同一
						logger.fine("Rlative mode: filter:" + _filter + " -> auto: " + isAuto);
						getStoredRAuto().put(_filter, isAuto);
					}
					AutoConverterConfig.setConfig(filter, tfstr, AutoConverterConfig.PREFIX_R_AUTO);
				} else if(mode == BaseFrame.IMAGE_MODE_THRESHOLD){
					AutoConverterConfig.setConfig(filter, tfstr, AutoConverterConfig.PREFIX_T_AUTO);
				} else {
					AutoConverterConfig.setConfig(filter, tfstr, AutoConverterConfig.PREFIX_AUTO);
				}
				//this.printSavedParams(filter);
			}
		}
		if(obj == this.baseFrame.getAutoTypeComboBox() || obj == null){
			String saturation_value = (String) baseFrame.getAutoTypeComboBox().getModel().getSelectedItem();
			logger.fine("get saturation_value="+saturation_value);
			if( ! saturation_value.equals(typeHash.get(filter))  || mode == BaseFrame.IMAGE_MODE_RELATIVE ){
				getTypeHash().put(filter, saturation_value);
				ischanged = true;
				if(this.getImageMode() == BaseFrame.IMAGE_MODE_SINGLE || this.getImageMode() == BaseFrame.IMAGE_MODE_MERGE){
					AutoConverterConfig.setConfig(filter, saturation_value, AutoConverterConfig.PREFIX_AUTO_TYPE);
				}
				if(mode == BaseFrame.IMAGE_MODE_RELATIVE){
					for(String _filter : this.getStoredRAutoType().keySet()){
						logger.fine("Rlative mode: filter:" + _filter + " -> saturation: " + saturation_value);
						getStoredRAutoType().put(_filter, saturation_value);
					}
					AutoConverterConfig.setConfig(filter, saturation_value, AutoConverterConfig.PREFIX_R_AUTO_TYPE);
				} else if(mode == BaseFrame.IMAGE_MODE_THRESHOLD){
					AutoConverterConfig.setConfig(filter, saturation_value, AutoConverterConfig.PREFIX_T_AUTO_TYPE);
				} else {
					AutoConverterConfig.setConfig(filter, saturation_value, AutoConverterConfig.PREFIX_AUTO_TYPE);
				}
				//this.printSavedParams(filter);
			}
		}
		if(obj == this.baseFrame.getColorChannelSelector() || obj == null){
			String color = (String) this.baseFrame.getColorChannelSelector().getSelectedItem();
			logger.fine("get color="+color + "  (filter: " + filter +")");
			if( ! color.equals(colorHash.get(filter))  || mode == BaseFrame.IMAGE_MODE_RELATIVE ){
				getColorHash().put(filter, color);
				ischanged = true;
				if(mode == BaseFrame.IMAGE_MODE_RELATIVE){
					for(String _filter : this.getStoredRColor().keySet()){
						logger.fine("Rlative mode: filter:" + _filter + " -> color: " + color);
						getStoredRColor().put(_filter, color);
					}
					AutoConverterConfig.setConfig(filter, color, AutoConverterConfig.PREFIX_R_COLOR);
				} else if(mode == BaseFrame.IMAGE_MODE_THRESHOLD){
					AutoConverterConfig.setConfig(filter, color, AutoConverterConfig.PREFIX_T_COLOR);
				} else {
					AutoConverterConfig.setConfig(filter, color, AutoConverterConfig.PREFIX_COLOR);
				}
				//this.printSavedParams(filter);
			}
		}
		if(obj == this.baseFrame.getBallSizeSpinner() || obj == null){
			int ballsize = this.getBallSize();
			logger.fine("get ballsize="+ballsize);
			if( ballsize != getBallHash().get(filter)  || mode == BaseFrame.IMAGE_MODE_RELATIVE ){
				getBallHash().put(filter, ballsize);
				ischanged = true;
				if(mode == BaseFrame.IMAGE_MODE_RELATIVE){
					for(String _filter : this.getStoredRBall().keySet()){
						logger.fine("Rlative mode: filter:" + _filter + " -> ball: " + ballsize);
						getStoredRBall().put(_filter, ballsize);
					}
					AutoConverterConfig.setConfig(filter, ballsize, AutoConverterConfig.PREFIX_R_BALL);
				} else if(mode == BaseFrame.IMAGE_MODE_THRESHOLD){
					AutoConverterConfig.setConfig(filter, ballsize, AutoConverterConfig.PREFIX_T_BALL);
				} else {
					AutoConverterConfig.setConfig(filter, ballsize, AutoConverterConfig.PREFIX_BALL);
				}
				//this.printSavedParams(filter);
			}
		}
		if(ischanged){
			AutoConverterConfig.save(baseFrame, true);
		}
		logger.fine("\n-------------END collectParam()-------------");
	}
	public synchronized void applyParams(){
		// cardIndex == 2 すなわち最後の画面の場合は convert だけなのでapplyParamが必要になることは無いはず?
		applyParams(null, false);
	}
	public synchronized void applyParams(boolean is_force_update){
		this.applyParams(null, is_force_update);
	}
	public synchronized void applyParams(Object obj){
		this.applyParams(obj, false);
	}
	/**
	 * 現在保持している情報をGUIに反映する
	 * @param obj 反映先のGUI部品, null のときは全体を反映する
	 * @param is_force_update 強制的に反映する
	 */
	public synchronized void applyParams(Object obj, boolean is_force_update){
		try{
			logger.fine("\n\n========================= applyParam() ====================");
			if(this.getCardIndex() == 2){
				logger.fine("cardIndex==2 なので applyParam()はスキップ.");
				return;
			}
			logger.fine("applyParams() loading_stack=" + loading_stack);
			if(loading){
				logger.fine("applyParams() locked.");
				return;
			}
			this.lockApplyParameter(true);
			try{
				if(obj == null){
					logger.fine("obj==null, apply to all component");
				}
				this.baseFrame.enableListener(false);
				String filter = (String) this.baseFrame.getFilterSelectCBox().getSelectedItem();
				logger.fine("filter=" + filter);
				logger.fine("mode=" + this.getImageModeString());
				if(filter.equals("<FILTER>")){
					return;
				}
				//this.printSavedParams(filter);
				if(obj == this.baseFrame.getMinSpinner() || obj == this.baseFrame.getMaxSpinner() || obj == null){
					int min = getMinHash().get(filter);
					int max = getMaxHash().get(filter);
					logger.fine("apply min, max=("+min+","+max+") [filter:" + filter + "]");
					//this.baseFrame.getMinSpinner().setValue(min);
					this.setScaleValues(min, max);
				}
				if(obj == this.baseFrame.getAutoRadioButton() || obj == this.baseFrame.getManualRadioButton() || obj == null){
					boolean isAuto = getAutoHash().get(filter);
					logger.fine("apply isAuto=" + isAuto);
					this.setAutoSelected(isAuto);
					//this.baseFrame.getAutoRadioButton().setSelected(isAuto);
				}
				if(obj == this.baseFrame.getAutoTypeComboBox() || obj == null){
					String saturation_value = getTypeHash().get(filter);
					logger.fine("apply saturation_value=" + saturation_value);
					if(saturation_value != null){
						this.baseFrame.getAutoTypeComboBox().getModel().setSelectedItem(saturation_value);
					}
				}
				if(obj == this.baseFrame.getColorChannelSelector() || obj == null){
					String color = getColorHash().get(filter);
					logger.fine("apply color=" + color);
					if(color != null){
						this.baseFrame.getColorChannelSelector().setSelectedItem(color);
					}
				}
				if(obj == this.baseFrame.getBallSizeSpinner() || obj == null){
					int ballsize = getBallHash().get(filter);
					logger.fine("apply ballsize=" + ballsize);
					this.baseFrame.getBallSizeSpinner().setValue(ballsize);
				}
				//this.printSavedParams(filter);
			} finally {
				this.baseFrame.enableListener(true);
				if(obj == null){ // 全コンポーネント対象にアプライした時
					ischanged = false;
				}
				this.lockApplyParameter(false);
			}
		} finally {
			logger.fine("\n-------------END applyParam()-------------");
		}
	}

	public void lockApplyParameter(boolean flg){
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
		//String imageID = this.getCurrentSelectedImageID(current_image_mode);
		String imageID = this.getCurrentSelectedImageID();
		this.updateImage(imageID);
	}

	public synchronized ExImagePlus processImage(ExImagePlus imp, String filter, boolean isColor){
		if(imp == null){
			return null;
		}
		if(filter == null){
			filter = imp.getFilter();
		}
		Boolean auto = this.getAutoHash().get(filter);
		String auto_type = this.getTypeHash().get(filter);
		String color = this.getColorHash().get(filter);
		int ballsize = this.getBallHash().get(filter);
		if (ballsize != 0) {
			logger.fine("Subtracting....");
			this.subtractBackground(imp, ballsize);
			//IJ.run(_imp, "Subtract Background...", "rolling=" + ballsize);
		}
		if (auto) {
			this.adjustValues(imp, filter);
		}
		int min = this.getMinHash().get(filter);
		int max = this.getMaxHash().get(filter);
		logger.fine("getImageMode()=" + this.getImageMode());
		imp.setDisplayRange(min, max); // IJ.setMinAndMax(imp, min, max)と何が違う?

		//if(_imp.getType() == ImagePlus.GRAY8 || _imp.getType() == ImagePlus.COLOR_256 || _imp.getType() == ImagePlus.COLOR_RGB){
		//	IJ.run(_imp, "Apply LUT", "");
		//}
		if(isColor){
			if(color==null){
				this.getColorHash().put(filter, "Grays");
				color = "Grays";
			}
			logger.fine("color=" + color);
			if( Arrays.asList(COLOR_INDEX).contains(color) ){
				IJ.run(imp, color, "");
			}
		}
		return imp;
	}

	public synchronized ExImagePlus processImage(ExImagePlus imp){
		return this.processImage(imp, null, true);
	}
	public synchronized ExImagePlus processImage(ExImagePlus imp, boolean isColor){
		return this.processImage(imp, null, isColor);
	}

	/**
	 * Merge モードで選択されているフィルタリストを取得する
	 * @return 
	 */
	public ArrayList<String> getSelectedFilters(){
		ArrayList list = new ArrayList<String>();
		for(JCheckBox cb:  this.baseFrame.getFilterCheckBoxList()){
			if(cb.isSelected()){
				list.add(cb.getText());
			}

		}
		return list;
	}

	public String getTargetFilter(){
		String filter = null;
		ArrayList<JCheckBox> filterBoxes = this.baseFrame.getFilterCheckBoxList();
		ArrayList<JCheckBox> checkBoxes = this.baseFrame.getTargetCheckBoxList();
		for(int i = 0; i < checkBoxes.size(); i++){
			JCheckBox cb = checkBoxes.get(i);
			if(cb.isSelected()){
				return filterBoxes.get(i).getText();
			}
		}
		return filter;
	}
	public String getReferenceFilter(){
		String filter = null;
		ArrayList<JCheckBox> filterBoxes = this.baseFrame.getFilterCheckBoxList();
		ArrayList<JCheckBox> checkBoxes = this.baseFrame.getReferenceCheckBoxList();
		for(int i = 0; i < checkBoxes.size(); i++){
			JCheckBox cb = checkBoxes.get(i);
			if(cb.isSelected()){
				return filterBoxes.get(i).getText();
			}
		}
		return filter;
	}


	/**
	 * 画像を_imageIDのものに差し替える.
	 *
	 * @param _imageID
	 */
	public synchronized void updateImage(String _imageID) {
		/*
		CaptureImage から getShotID() で shotID を撮ったらそれを使って、
		getImageSet().getShotAt(shotID) で同じshotIDのCaptureImageのリストを取得できる
		*/
		if (updating == true) {
			return;
		}
		try {
			updating = true;
			CaptureImage _cimg = this.getImageSet().getCaptureImageAt(_imageID);
			logger.fine("_imgeID=" + _imageID);
			if (_cimg == null) {
				this.setMessageLabel("ID:" + _imageID + " is not found.", Color.RED);
				return;
			} else {
				this.setMessageLabel(_cimg.getFile().getAbsolutePath());
			}
			ExImagePlus imp = _cimg.getImagePlus();
      // デンシティプロットの参照先画像 (merge/relative の場合、表示しているマージ画像やrelative画像とは別の選択している画像についてdensity plotを表示する必要があるため。
			ImagePlus   pimp = null;
			// RELATIVE のときはprocessing をthreshold にするべき
			if(current_image_mode == BaseFrame.IMAGE_MODE_SINGLE){
				pimp = this.updateSingleImage(imp);
			} else if(current_image_mode == BaseFrame.IMAGE_MODE_THRESHOLD){
				pimp = this.updateThresholdImage(imp);
			} else if(current_image_mode == BaseFrame.IMAGE_MODE_RELATIVE){
				ArrayList<CaptureImage> _cimgs = getImageSet().getShotAt(_cimg.getShotID());
				ArrayList<ExImagePlus> imps = new ArrayList<ExImagePlus>();
				for(CaptureImage _ci: _cimgs){
					imps.add(_ci.getImagePlus());
				}
				pimp = this.updateRelativeImage(imps);
			} else { // Merge mode
				ArrayList<CaptureImage> _cimgs = getImageSet().getShotAt(_cimg.getShotID());
				ArrayList<ExImagePlus> imps = new ArrayList<ExImagePlus>();
				for(CaptureImage _ci: _cimgs){
					imps.add(_ci.getImagePlus());
				}
				pimp = this.updateMergeImage(imps);
				if(pimp == null){
					pimp = this.updateSingleImage(imp);
				}
				logger.fine("pimp=" + pimp);
			}
			if(pimp != null){ // デンシティプロットが参照する画像をセットする
				//baseFrame.getPlotPanel().setImp(_cimg.getImagePlus());
				logger.fine("getfilter()=" + imp.getFilter());
				logger.fine("colorHash=" + getColorHash().get(imp.getFilter()));
				baseFrame.getPlotPanel().setImp(pimp);
				baseFrame.getPlotPanel().setColor(getColorHash().get(imp.getFilter()));
			}
		} finally {
			this.updateDensityPlot();
			this.baseFrame.getImageDisplayPanel().repaint();
			updating = false;
		}
	}

	public synchronized ImagePlus updateRelativeImage(ArrayList<ExImagePlus> imps){
		logger.fine("================= IN updateRelativeImage() ===================");
		this.applyParams();
		if(imps == null){
			return null;
		}
		String ref_filter = this.getReferenceFilter();
		String tgt_filter = this.getTargetFilter();
		if(ref_filter == null || tgt_filter == null){
			return null;
		}
		ExImagePlus ref_imp = null;
		ExImagePlus tgt_imp = null;
		logger.fine("ref_filter="+ref_filter);
		logger.fine("tgt_filter="+tgt_filter);
		for(ExImagePlus _imp: imps){
			String _filter = _imp.getFilter();
			logger.fine("_filter="+_filter);
			if(_imp.getFilter().equals(ref_filter)){
				ref_imp = _imp;
			} else if (_imp.getFilter().equals(tgt_filter)){
				tgt_imp = _imp;
			}
		}
		if(ref_imp == null ){
			this.setMessageLabel("No reference image is selected. Skip update of image and plot.", Color.RED);
			return null;
		}
		if(tgt_imp == null){
			this.setMessageLabel("No target image is selected. Skip update of image and plot.", Color.RED);
			return null;
		}
		ImagePlus rel_imp = ic.run("divide create 32-bit", tgt_imp, ref_imp);

		logger.fine("dividing..");
		ImageProcessor ip = rel_imp.getProcessor();
		double calc_max = ip.getMax();
		if(calc_max * ApplicationController.RELATIVE_MULTIPLICITY > Math.pow(2, 16)){
			this.setMessageLabel("Divided Intensity too high. Histgram may be incorrect.", Color.RED);
		}
		ip.resetRoi();
		ip.multiply(ApplicationController.RELATIVE_MULTIPLICITY);

		ShapeRoi cell_roi = null;
		// ここから選択されていない部分については削除する
		//ArrayList<String> filters = new ArrayList<String>();
		ArrayList<String> sel_filters = this.getSelectedFilters();
		logger.fine("sel_filters=" + sel_filters);
		for(ExImagePlus imp: imps){
			String filter = imp.getFilter();
			logger.fine("filter=" + filter);
			if(! sel_filters.contains(filter)){
				continue;
			}
			int min = getStoredTMin().get(filter);
			int max = this.getMaxDisplayRangeValue() - 1;
			Integer ball = getStoredTBall().get(filter);
			logger.fine("ball=" + ball);
			if(ball > 20 ){
				this.subtractBackground(imp, ball);
			}
			ImageProcessor _ip = imp.getProcessor();
			_ip.setThreshold(min, max, ImageProcessor.NO_LUT_UPDATE);
			try{
				logger.fine("finding ROI in " + filter + " by (min,max)=(" + min + "," + max + ")");
				Roi roi = tts.convert(_ip);
				if(roi == null){
					throw new ArrayIndexOutOfBoundsException("Roi==null");
				}
				logger.fine("found ROI in " + filter);
				if(roi.isArea()){
					if(cell_roi == null){
						cell_roi = new ShapeRoi(roi);
					} else {
						cell_roi = cell_roi.and(new ShapeRoi(roi));
					}
					logger.fine("background=" + ip.getBackgroundValue());
					//ip.setValue(0.0);
					//ip.fillOutside(roi); // 選択領域外を埋める
				}
			} catch (IllegalArgumentException e){
				logger.fine(e.toString());
			} catch (ArrayIndexOutOfBoundsException e){
				logger.fine(e.toString());
			}
		}

		if(cell_roi != null){
			ip.setRoi(cell_roi);
			ip.setValue(0.0);
			ip.fillOutside(cell_roi); // 選択領域外を埋める
			//ip.setValue(255.0);
			//logger.fine("getMax()==" + ip.getMax());
			//ip.setColor(java.awt.Color.WHITE);
			//ip.setLineWidth(2);
			//ip.draw(cell_roi);
			//cell_roi.setStrokeColor(java.awt.Color.WHITE);
			//ip.drawRoi(cell_roi);
		}

		ImageStatistics stat = ip.getStatistics();
		logger.fine("stat.area" + stat.area);
		logger.fine("stat.max=" + stat.max);
		logger.fine("stat.min=" + stat.min);
		//Integer min = this.minHash.get(tgt_filter);
		//Integer max = this.maxHash.get(tgt_filter);
		if(this.getCardIndex() != 2){
			int upper_limit = (int) (calc_max * ApplicationController.RELATIVE_MULTIPLICITY);
			int current_upper_limit = this.baseFrame.getScaleRangeSlider().getMaximum();
			if(upper_limit > current_upper_limit){
				this.baseFrame.getScaleRangeSlider().setMaximum(upper_limit);
			}
		}
		int range_min = this.baseFrame.getScaleRangeSlider().getLowValue();
		int range_max = this.baseFrame.getScaleRangeSlider().getUpperValue();

		//URL path = this.getClass().getClassLoader().getResource("thermal.lut");
		String lut_name = (String) this.baseFrame.getColorChannelSelector().getSelectedItem();
		logger.fine("lut_name=" + lut_name);
		try {
			//LUT lut = new LUT(icm, 0, stat.max);
			LUT lut = loadLut(lut_name, 0, stat.max);
			ip.setLut(lut);
		} catch (IOException ex) {
			Logger.getLogger(ApplicationController.class.getName()).log(Level.SEVERE, null, ex);
		}
		//Path path = Paths.get("thermal.lut");
		//LutLoader.openLut(url);
		rel_imp.setDisplayRange(range_min, range_max);

		// flatten するとヒストグラムが作れない...
		//rel_imp = rel_imp.flatten();
		if(cell_roi != null){
			logger.fine("setting roi...");
			//ip.setColor(java.awt.Color.WHITE);
			//ip.setLineWidth(2);
			//ip.draw(cell_roi);
			ip = rel_imp.getProcessor();
			cell_roi.setStrokeColor(java.awt.Color.WHITE);
			ip.drawRoi(cell_roi);
			ip.setRoi(cell_roi);
			rel_imp.setRoi(cell_roi);
		}

		if(this.getCardIndex() != 2){
			baseFrame.getImageDisplayPanel().setImp(rel_imp);
		}

		return rel_imp;
	}

	public synchronized ExImagePlus updateMergeImage(ArrayList<ExImagePlus> imps){
		try{
			logger.fine("================= IN updateMergeImage() ===================");
			//AutoConverterUtils.printStackTrace(20);
			this.applyParams();
			//this.loadCurrentFilterSettings();
			if(imps == null){
				return null;
			}
			// マージに使用するためにチェックされているフィルタ
			// マージに使用するためにチェクしているフィルタ:
			// - 0 の場合は何もせず抜ける
			// - 1 の場合は updateSingleImage と同じ扱い
			// - 2 以上の場合にマージの扱いを行う
			ArrayList<String> filters = this.getSelectedFilters();
			ArrayList<ExImagePlus> targets = new ArrayList<ExImagePlus>();
			String current_filter = (String) this.baseFrame.getFilterSelectCBox().getSelectedItem();
			ExImagePlus cur_imp = null;
			logger.fine("filters=" + filters);
			// 画像セットの中で、マージのためにチェックされているフィルタだけ選ぶ
			for(ExImagePlus _imp: imps){
				logger.fine("_imp.getFilter()="+_imp.getFilter());
				if(filters.contains(_imp.getFilter())){
					targets.add(_imp);
				}
				if(current_filter.equals(_imp.getFilter())){
					cur_imp = _imp;
				}
			}
			if( targets.size() < 1){
				this.setMessageLabel("No image is selected. Skip update of image and plot.", Color.RED);
				return null;
			}
			
			ExImagePlus[] _imps = new ExImagePlus[COLOR_INDEX.length];
			ExImagePlus returnImp = null;
			for(ExImagePlus _imp: targets){
				String filter = _imp.getFilter();
				if( ! filters.contains(filter)){ // チェックボックスで選択されていない場合
					continue;
				}
				// 色の処理は行なっても行わなくても変Merge画像には影響ない
				_imp = this.processImage(_imp, true);
				// 選択中のフィルタのExImagePlus一致したらそれを返却値として保存. plotPanel にセットする為.
				if(_imp == cur_imp){
					returnImp = _imp;
				}
				
				String color_name = this.getColorHash().get(filter);
				// color_name が COLOR_INDEX[i] に一致したらそのインデックスに入れる (imps[i])
				// COLOR_INDEX は ImageJ の merge のchannel色と同じ順番になっている
				for(int i = 0; i < COLOR_INDEX.length; i++){
					//logger.fine("Checking color color_name("+color_name+") ?= " + COLOR_INDEX[i]);
					if(color_name.equals(COLOR_INDEX[i])){
						//logger.fine("filter("+filter+") => " + color_name);
						_imps[i] = _imp;
						break;
					}
				}
			}
			// targets 中に現在選択中のフィルタがない場合、plotPanel用に画像処理してreturnImpにセットする
			if( returnImp == null && this.getCardIndex() != 2){
				returnImp = this.processImage(cur_imp, true);
			}
			
			//merge 出来た場合は
			if(targets.size() == 1){
				ExImagePlus _imp = targets.get(0);
				if( this.getCardIndex() != 2 ){
					baseFrame.getImageDisplayPanel().setImp(_imp);
					String _filter = _imp.getFilter();
					this.setMessageLabel("Select only 1 filter (" + _filter + "): showing " + _imp.getFile().getName());
				} else {
					// convert モードの時. 1つしか選択されていなければそのままかえす.
					return _imp;
				}
			} else {
				//  ImagePlus から ExImagePlus へのキャスト出来ないのでこのまま.
				ImagePlus mergeImg =  RGBStackMerge.mergeChannels(_imps, false);
				if(this.getCardIndex() != 2){
					baseFrame.getImageDisplayPanel().setImp(mergeImg);
				} else {
					return new ExImagePlus("mergeImage", mergeImg.flatten().getImage(), "mergeImage");
				}
			}
			return returnImp;
		} finally {
			for(ExImagePlus _imp: imps){
				_imp.close();
			}
		}
	}
	

	/**
	 * 設定にしたがって画像をアップデートする.
	 * @param imp 
	 */
	public ExImagePlus updateSingleImage(ExImagePlus imp) {
		// 設定をロードする
		logger.fine("updateSingleImage...");
		this.applyParams();
		if (imp == null) {
			return null;
		}
		imp = this.processImage(imp);
		if(this.getCardIndex() != 2){ // 最後の画面の場合はupdateの必要なし(変換のみ)
			baseFrame.getImageDisplayPanel().setImp(imp.flatten());
		}
		return imp;

	}

	
	public ExImagePlus updateThresholdImage(ExImagePlus imp) {
		logger.fine("updateThresholdImage...");
		this.applyParams();
		if (imp == null) {
			return null;
		}
		imp = this.processImage(imp, true);

		String filter = imp.getFilter();
		ImageProcessor ip = imp.getProcessor();
		double image_max = ip.getMax();
		logger.fine("image_max=" + image_max);
		Integer min = getMinHash().get(filter);
		logger.fine("min="+min);
		if(min > image_max){
			min = 0;
			getMinHash().put(filter, min);
		}
		//int max = (int) imp.getStatistics(MIN_MAX).max;
		int max = this.getMaxDisplayRangeValue() - 1;
		logger.fine("Setting threshold (" + min + ", " + max + ")");
		ip.setThreshold(min, max, ImageProcessor.NO_LUT_UPDATE);
		double min_t = ip.getMinThreshold();
		double max_t = ip.getMaxThreshold();
		logger.fine("ip.getMinThreshold()=" + min_t);
		logger.fine("ip.getMaxThreshold()=" + max_t);
		logger.fine("ip.getWidth()=" + ip.getWidth());
		logger.fine("ip.getHeight()=" + ip.getHeight());
		// tts は ThresholdToSelection()
		try {
			Roi roi = tts.convert(ip);
			if(roi == null){
				throw new ArrayIndexOutOfBoundsException("Roi==null");
			}
			if(roi.isArea()){
				logger.fine("setting roi...");
				ImageProcessor _ip = imp.getProcessor();
				_ip.draw(roi);
				_ip.setRoi(roi);
				imp.setRoi(roi);
			}
		} catch (ArrayIndexOutOfBoundsException e){
			logger.fine(e.toString());
			this.setMessageLabel("No region in (min, max) = (" + min + ", " + max + ")", Color.RED);
		}
		//imp.getProcessor().fill(roi)
		logger.fine("background=" + imp.getProcessor().getBackgroundValue());
		// もしMaskにするなら(処理が軽い)
		//IJ.run(t, "Convert to Mask", "");
		if(this.getCardIndex() != 2){ // 最後の画面の場合はupdateの必要なし(変換のみ)
			baseFrame.getImageDisplayPanel().setImp(imp.flatten());
		}
		return imp;
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
			// モードをSingle に戻す
			this.baseFrame.getModeSelector().setSelectedItem("Single");
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

			// jCheckBoxFilter などの文字列を初期化
			this.setImageMode(BaseFrame.IMAGE_MODE_SINGLE);
			


			this.initSelectorComboBoxes(getImageSet());

			// 保存しているfilter情報を初期化
			this.getStoredMax().clear();
			this.getStoredMin().clear();
			this.getStoredAuto().clear();
			this.getStoredColor().clear();
			this.getStoredBall().clear();

			this.getStoredTMax().clear();
			this.getStoredTMin().clear();
			this.getStoredTAuto().clear();
			this.getStoredTColor().clear();
			this.getStoredTBall().clear();

			this.getStoredRMax().clear();
			this.getStoredRMin().clear();
			this.getStoredRAuto().clear();
			this.getStoredRColor().clear();
			this.getStoredRBall().clear();

			this.baseFrame.getBallSizeSpinner().setValue(0);
			this.baseFrame.getImageScrollPane().getVerticalScrollBar().setUnitIncrement(25);
			this.baseFrame.getImageScrollPane().getHorizontalScrollBar().setUnitIncrement(25);

			for (String s : this.getImageSet().getFilters()) {
				this.getStoredAuto().put(s, Boolean.FALSE);
				// color setting
				String _color = null;
				_color = AutoConverterConfig.getConfig(s, null, AutoConverterConfig.PREFIX_COLOR);
				if (_color != null) { this.getStoredColor().put(s, _color); }
				_color = AutoConverterConfig.getConfig(s, null, AutoConverterConfig.PREFIX_R_COLOR);
				if (_color != null) { this.getStoredRColor().put(s, _color); }
				_color = AutoConverterConfig.getConfig(s, null, AutoConverterConfig.PREFIX_T_COLOR);
				if (_color != null) { this.getStoredTColor().put(s, _color); }

				String _max  = null;
				String _min  = null;
				String _ball = null;
				_max = AutoConverterConfig.getConfig(s, "4095", AutoConverterConfig.PREFIX_MAX);
				this.getStoredMax().put(s, Integer.parseInt(_max));
				_max = AutoConverterConfig.getConfig(s, "4095", AutoConverterConfig.PREFIX_R_MAX);
				this.getStoredRMax().put(s, Integer.parseInt(_max));
				_max = AutoConverterConfig.getConfig(s, "4095", AutoConverterConfig.PREFIX_T_MAX);
				this.getStoredTMax().put(s, Integer.parseInt(_max));

				_min = AutoConverterConfig.getConfig(s, "0", AutoConverterConfig.PREFIX_MIN);
				this.getStoredMin().put(s, Integer.parseInt(_min));
				_min = AutoConverterConfig.getConfig(s, "0", AutoConverterConfig.PREFIX_R_MIN);
				this.getStoredRMin().put(s, Integer.parseInt(_min));
				_min = AutoConverterConfig.getConfig(s, "0", AutoConverterConfig.PREFIX_T_MIN);
				this.getStoredTMin().put(s, Integer.parseInt(_min));

				_ball = AutoConverterConfig.getConfig(s, "0", AutoConverterConfig.PREFIX_BALL);
				this.getStoredBall().put(s, Integer.parseInt(_ball));
				_ball = AutoConverterConfig.getConfig(s, "0", AutoConverterConfig.PREFIX_R_BALL);
				this.getStoredRBall().put(s, Integer.parseInt(_ball));
				_ball = AutoConverterConfig.getConfig(s, "0", AutoConverterConfig.PREFIX_T_BALL);
				this.getStoredTBall().put(s, Integer.parseInt(_ball));


				String _auto = AutoConverterConfig.getConfig(s, null, AutoConverterConfig.PREFIX_AUTO);
				if (_auto != null && _auto.equals("true")) {
					this.getStoredAuto().put(s, true);
				} else {
					this.getStoredAuto().put(s, false);
				}
				_auto = AutoConverterConfig.getConfig(s, null, AutoConverterConfig.PREFIX_R_AUTO);
				if (_auto != null && _auto.equals("true")) {
					this.getStoredRAuto().put(s, true);
				} else {
					this.getStoredRAuto().put(s, false);
				}
				_auto = AutoConverterConfig.getConfig(s, null, AutoConverterConfig.PREFIX_T_AUTO);
				if (_auto != null && _auto.equals("true")) {
					this.getStoredTAuto().put(s, true);
				} else {
					this.getStoredTAuto().put(s, false);
				}

				String _auto_type = AutoConverterConfig.getConfig(s, null, AutoConverterConfig.PREFIX_AUTO_TYPE);
				if (_auto_type != null) { this.getStoredAutoType().put(s, _auto_type); }
				_auto_type = AutoConverterConfig.getConfig(s, null, AutoConverterConfig.PREFIX_R_AUTO_TYPE);
				if (_auto_type != null) { this.getStoredRAutoType().put(s, _auto_type); }
				_auto_type = AutoConverterConfig.getConfig(s, null, AutoConverterConfig.PREFIX_T_AUTO_TYPE);
				if (_auto_type != null) { this.getStoredTAutoType().put(s, _auto_type); }

			}
			//ImagePlus imp = new ImagePlus(this.getImageSet().getShotAt(0).get(0).getFile().getAbsolutePath());
			ExImagePlus cimg = this.getImageSet().getShotAt(0).get(0).getImagePlus();

			this.updateSingleImage(cimg);
			this.updateDensityPlot();
			//this.baseFrame.getImageDisplayPanel().repaint();
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
		//this.storeCurrentFilterSettings();
		this.collectParams();
		// summary を表示
		JTextArea area = this.baseFrame.getSummaryDisplayArea();
		area.setText("");
		area.append("================ summary ==============\n");
		area.append("Java: " + System.getProperty("java.version") + "\n");
		area.append("ImageJ: " + IJ.getVersion() + "\n");
		if(IJ.versionLessThan("1.51t")){
			area.append("*Warning: Quantification of relative image may require version 1.51t or later.\n");
		}
		area.append("Image Converter Plugin: " + AutoConverterUtils.getVersion() + "\n\n");

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

		//String mode = this.setim
		String mode = this.getImageModeString();
		if (mode == null) {
			mode = "Undefined (Single)";
		}
		area.append("Mode: " + mode);
		area.append("\n\n");

		area.append("Total file: " + getImageSet().size() + "\n");

		area.append("\n");
		//this.printSavedParams(null);
		if( this.getImageMode() == BaseFrame.IMAGE_MODE_RELATIVE){
			String tgt = this.getTargetFilter();
			String ref = this.getReferenceFilter();
			area.append("Relative image (" + tgt + "/" + ref + ")\n");
			ArrayList<String> _gated_filters = this.getSelectedFilters();
			if(_gated_filters.size() > 0){
				area.append("Region: " + _gated_filters.stream().collect(Collectors.joining(" & ")) + "\n");
			} else {
				area.append("Region: Whole Area\n");
			}
			Collection<String> luts = this.getColorHash().values();
			luts.removeAll(Collections.singleton(null));
			area.append("LUT: " + luts.iterator().next() + "\n");
			Collection<Boolean> methods = this.getAutoHash().values();
			methods.remove(Collections.singleton(null));
			Boolean method = methods.iterator().next();
			if(method == null){
				method = false;
			}
			if (method) {
				Collection<String> types = this.getTypeHash().values();
				types.removeAll(Collections.singleton(null));
				String type = types.iterator().next();
				area.append("Method: auto (saturated=" + type + "%)");
				area.append("\n");
				area.append("Range: variable\n");
			} else {
				area.append("Method: manual\n");
				Collection<Integer> mins = this.getMinHash().values();
				Collection<Integer> maxs = this.getMaxHash().values();
				Integer min = mins.iterator().next();
				Integer max = maxs.iterator().next();
				area.append("Range: " + min + "-" + max + "\n");
			}
			Collection<Integer> balls = this.getBallHash().values();
			balls.removeAll(Collections.singleton(null));
			Integer bs = balls.iterator().next();
			//Integer bs = this.ballHash.get(0);
			String ball_str = "None";
			if (bs == null || bs == 0) {
				ball_str = "None";
			} else {
				ball_str = bs.toString();
			}
			area.append("Background subtraction: " + ball_str + "\n");
			area.append("\n");

			this.colorHash = this.getStoredTColor();
			this.autoHash  = this.getStoredTAuto();
			this.ballHash  = this.getStoredTBall();
			this.minHash   = this.getStoredTMin();
			this.maxHash   = this.getStoredTMax();
			this.typeHash  = this.getStoredTAutoType();
		} else if( this.getImageMode() == BaseFrame.IMAGE_MODE_MERGE){
			ArrayList<String> selected_filters = this.getSelectedFilters();
			if(selected_filters.size() > 0){
				area.append("Merged filters: " + selected_filters.stream().collect(Collectors.joining(" & ")) + "\n\n");
			}
		}
			
		for (String s : this.getImageSet().getFilters()) {
			area.append("Filter name: " + s + "\n");
			area.append("Color: " + this.getColorHash().get(s) + "\n");
			Boolean method = this.getAutoHash().get(s);
			if(method == null){
				method = false;
			}
			if (method) {
				area.append("Method: auto (saturated=" + this.getTypeHash().get(s) + "%)");
				area.append("\n");
				area.append("Range: variable\n");
			} else {
				area.append("Method: manual\n");
				if(this.getImageMode() == BaseFrame.IMAGE_MODE_THRESHOLD || this.getImageMode() == BaseFrame.IMAGE_MODE_RELATIVE){
					// relative mode と threshold mode では max 側は最大値-1 でthreshold をとる
					int _max = this.getMaxDisplayRangeValue() - 1;
					area.append("Range: " + this.getMinHash().get(s) + "-" + _max + "\n");
				} else {
					area.append("Range: " + this.getMinHash().get(s) + "-" + this.getMaxHash().get(s) + "\n");
				}
			}
			Integer bs = this.getBallHash().get(s);
			String ball_str = "None";
			if (bs == null || bs == 0) {
				ball_str = "None";
			} else {
				ball_str = bs.toString();
			}
			area.append("Background subtraction: " + ball_str + "\n");
			area.append("\n");
		}
		if( this.getImageMode() == BaseFrame.IMAGE_MODE_RELATIVE){
			this.colorHash = this.getStoredRColor();
			this.autoHash  = this.getStoredRAuto();
			this.ballHash  = this.getStoredRBall();
			this.minHash   = this.getStoredRMin();
			this.maxHash   = this.getStoredRMax();
			this.typeHash  = this.getStoredRAutoType();
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
			FileSearchWorker fsw = new FileSearchWorker(baseFrame.getSourceText().getText(), baseFrame.getRecursiveRadioButton().isSelected(), baseFrame.getSymlinkRadioButton().isSelected());
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
	public void setScaleValues(int min, int max) {
		JSpinner minSpinner = baseFrame.getMinSpinner();
		JSpinner maxSpinner = baseFrame.getMaxSpinner();
		this.baseFrame.enableListener(false);
		this.setMinSpinnerMax(max);
		this.setMaxSpinnerMin(min);
		maxSpinner.setValue(max);
		minSpinner.setValue(min);
		baseFrame.getScaleRangeSlider().setMinAndMax(min, max);
		this.baseFrame.enableListener(true);
		//this.collectParams(baseFrame.getScaleRangeSlider());
	}

	// ここがバグの温床だとおもう。merge モードでも最後にsingleで指定したものを撮ってきてる?
	// __TODO__ __BUG__
	public void adjustValues(){
		String imageID = this.getCurrentSelectedImageID();
		CaptureImage cimp = this.getImageSet().getCaptureImageAt(imageID);
		if( cimp != null){
			this.adjustValues(cimp.getImagePlus());
		}
	}

	/**
	 * 自動で設定する.
	 * 自動で設定するときに filter のパラメータで設定する。
	 */
	public void adjustValues(ExImagePlus imp) {
		//String filter = (String) this.baseFrame.getFilterSelectCBox().getSelectedItem();
		String filter = imp.getFilter();
		this.adjustValues(imp, filter);
	}

	public void adjustValues(ExImagePlus imp, String filter) {
		logger.fine("===================== Adjust [filter:" + filter + "] =======================");
		// 最初に backgroundsubtraction がある場合は行っておかないと、subtraction前の輝度値で調整してしまう
		int ballsize = this.getBallHash().get(filter);
		if(ballsize != 0){
			this.subtractBackground(imp, ballsize);
		}
		// これいる。なぜなら、min側はsaturatedの値に関わらず一定にしたいから。
		IJ.run(imp, "Enhance Contrast", "saturated=0.35");
		//int min = (int) this.getImp().getDisplayRangeMin();
		int min = (int) imp.getDisplayRangeMin();
		String sat_val = this.getTypeHash().get(filter);
		//logger.fine("sat_val=" + sat_val);
		if(sat_val == null){
			sat_val = "0.35";
		}
		//Double.parseDouble(sat_val);
		IJ.run(imp, "Enhance Contrast", "saturated=" + sat_val);

		//this.getImp().setDisplayRange(min, max);
		//int max = (int) this.getImp().getDisplayRangeMax();
		int max = (int) imp.getDisplayRangeMax();
		if (max > this.getMaxDisplayRangeValue()) {
			max = this.getMaxDisplayRangeValue();
		}
		if (min < 0) {
			min = 0;
		}
		this.getMinHash().put(filter, min);
		this.getMaxHash().put(filter, max);
		this.baseFrame.enableListener(false);
		this.applyParams(true);
		//baseFrame.getMaxSpinner().setValue(max);
		//baseFrame.getMinSpinner().setValue(min);
		// max からセッティングしないとダメ. setLowerValue() 内で、
		// 現時点で設定されているmax より大きなminの値を与えた場合、
		// max の値が代わりに使われるため.
		//baseFrame.getScaleRangeSlider().setMinAndMax(min, max);
		this.baseFrame.getPlotPanel().setColor(this.getColorHash().get(filter));
		this.updateDensityPlot();
		this.baseFrame.enableListener(true);
		//IJ.setMinAndMax(imp, (int) min, (int) max);
		imp.setDisplayRange(min, max);
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
		//JSpinner minSpinner = baseFrame.getMinSpinner();
		//JSpinner maxSpinner = baseFrame.getMaxSpinner();
		//Integer min = (Integer) minSpinner.getValue();
		//Integer max = (Integer) maxSpinner.getValue();
		String filter = (String)this.baseFrame.getFilterSelectCBox().getSelectedItem();
		if(filter.equals("<FILTER>")){
			return;
		}
		int min = this.getMinHash().get(filter);
		int max = this.getMaxHash().get(filter);
		baseFrame.getPlotPanel().setLowLimit(min);
		baseFrame.getPlotPanel().setHighLimit(max);
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

	public void storeResizeWidthSetting(boolean save){
		Integer width = (Integer) this.baseFrame.getResizeSpinner().getValue();
		AutoConverterConfig.setConfig(AutoConverterConfig.KEY_RESIZE_WIDTH, width);
		if (save) {
			AutoConverterConfig.save(baseFrame, true);
		}
	}

	public void storeResizeCheckSetting(boolean save){
		boolean _select = this.baseFrame.getResizeRadioButton().isSelected();
		AutoConverterConfig.setConfig(AutoConverterConfig.KEY_RESIZE_CHECK, java.text.MessageFormat.format(java.util.ResourceBundle.getBundle("autoconverter/controller/Bundle").getString("{0}"), new Object[]{_select}));
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

	public void storeIgnoreSymlink(boolean save){
		boolean selected = baseFrame.getSymlinkRadioButton().isSelected();
		AutoConverterConfig.setConfig(AutoConverterConfig.KEY_IGNORE_SYMLINK, java.text.MessageFormat.format(java.util.ResourceBundle.getBundle("autoconverter/controller/Bundle").getString("{0}"), new Object[]{selected}));
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
		String filter = (String) this.baseFrame.getFilterSelectCBox().getSelectedItem();
		this.baseFrame.getAutoRadioButton().setSelected(auto);
		this.baseFrame.getManualRadioButton().setSelected(!auto);
		this.configAutoRelatedComponents(auto);
		this.getAutoHash().put(filter, auto);

		//this.updateImage();
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


	public void subtractBackground(ExImagePlus _imp, int radius) {
		if (_imp == null) {
			return;
		}
		// 画像を更新
		if (radius < 20) {
			this.setMessageLabel("Substraction ball is too small. Ignored.", Color.RED);
			return;
		}
		IJ.run(_imp, "Subtract Background...", "rolling=" + radius);
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

	public LUT loadLut(String lut_name, double min, double max) throws IOException{
		InputStream is = getClass().getResourceAsStream(lut_name);
		DataInputStream dis = new DataInputStream(is);
		byte[] lut_byte_data = dis.readAllBytes();
		int read_index = lut_byte_data.length - ApplicationController.COLOR_DEPTH * 3;
		logger.fine("lut_byte_data.length=" + lut_byte_data.length);
		logger.fine("read_index=" + read_index);
		
		byte[] reds   = new byte[ApplicationController.COLOR_DEPTH];
		byte[] blues  = new byte[ApplicationController.COLOR_DEPTH];
		byte[] greens = new byte[ApplicationController.COLOR_DEPTH];
		System.arraycopy(lut_byte_data, read_index + ApplicationController.COLOR_DEPTH * 0, reds,   0, ApplicationController.COLOR_DEPTH);
		System.arraycopy(lut_byte_data, read_index + ApplicationController.COLOR_DEPTH * 1, blues,  0, ApplicationController.COLOR_DEPTH);
		System.arraycopy(lut_byte_data, read_index + ApplicationController.COLOR_DEPTH * 2, greens, 0, ApplicationController.COLOR_DEPTH);
		IndexColorModel icm = new IndexColorModel(8, ApplicationController.COLOR_DEPTH, reds, blues, greens);
		LUT lut = new LUT(icm, min, max);
		return lut;
	}

	/**
	 * イメージ全部をコンバートする.
	 */
	public void convertImages() {
		if (getImageSet() == null) {
			IJ.showMessage("No images found.");
			return;
		}

    convert_swing_worker = new ImageProcessWorker();
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
		String imageID = null;
		//logger.fine(""+image_mode);
		imageID = createImageID(dir, wellname, position, slice, time, filter);
		//logger.fine("imageID="+imageID);

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

	public int getImageMode(){
		String mode = (String) this.getImageModeString();
		if(mode.equals("Single")){
			return BaseFrame.IMAGE_MODE_SINGLE;
		} else if(mode.equals("Merge")){
			return BaseFrame.IMAGE_MODE_MERGE;
		} else if(mode.equals("Threshold")){
			return BaseFrame.IMAGE_MODE_THRESHOLD;
		} else if(mode.equals("Relative")){
			return BaseFrame.IMAGE_MODE_RELATIVE;
		} else {
			return 0;
		}
	}
	public String getImageModeString(){
		String mode = (String) this.baseFrame.getModeSelector().getSelectedItem();
		return mode;
	}
	public void setImageMode(int mode){
		logger.fine("IN:  setImageMode()");
		try{
			this.baseFrame.enableListener(false);
			TreeSet<String> filters = this.getImageSet().getFilters();
			String filter = (String) this.baseFrame.getFilterSelectCBox().getSelectedItem();
			int nfilter = filters.size();
			current_image_mode = mode;
			ArrayList<JCheckBox> filterBoxes = this.baseFrame.getFilterCheckBoxList();
			ArrayList<JCheckBox> targetBoxes = this.baseFrame.getTargetCheckBoxList();
			ArrayList<JCheckBox> referenceBoxes = this.baseFrame.getReferenceCheckBoxList();
			JComboBox colorSelector = this.baseFrame.getColorChannelSelector();
			DefaultComboBoxModel color_model = new DefaultComboBoxModel(COLOR_INDEX);
			colorSelector.setModel(color_model);
			this.baseFrame.getScaleRangeSlider().setMaximum(this.getMaxDisplayRangeValue());
			if(mode == BaseFrame.IMAGE_MODE_SINGLE){
				for(int i = 0; i < filterBoxes.size(); i++){
					targetBoxes.get(i).setEnabled(false);
					referenceBoxes.get(i).setEnabled(false);
					filterBoxes.get(i).setEnabled(false);
					filterBoxes.get(i).setText("---");
				}
				this.minHash   = getStoredMin();
				this.maxHash   = getStoredMax();
				this.autoHash  = getStoredAuto();
				this.typeHash  = getStoredAutoType();
				this.colorHash = getStoredColor();
				this.ballHash  = getStoredBall();
				if(this.getColorHash().get(filter) != null){
					colorSelector.setSelectedItem(this.getColorHash().get(filter));
				}
				this.baseFrame.getLabelFilter().setText("Filter");
			} else if(mode == BaseFrame.IMAGE_MODE_THRESHOLD){
				for(int i = 0; i < filterBoxes.size(); i++){
					targetBoxes.get(i).setEnabled(false);
					referenceBoxes.get(i).setEnabled(false);
					filterBoxes.get(i).setEnabled(false);
					filterBoxes.get(i).setText("---");
				}
				this.minHash    = getStoredTMin();
				this.maxHash    = getStoredTMax();
				this.autoHash   = getStoredTAuto();
				this.typeHash   = getStoredTAutoType();
				this.colorHash  = getStoredTColor();
				this.ballHash   = getStoredTBall();
				if(this.getColorHash().get(filter) != null){
					colorSelector.setSelectedItem(this.getColorHash().get(filter));
				}
				this.baseFrame.getLabelFilter().setText("Filter");
			} else if(mode == BaseFrame.IMAGE_MODE_MERGE){
				Iterator<String> it = filters.iterator();
				for(int i = 0; i < filterBoxes.size(); i++){
					if(it.hasNext()){
						filterBoxes.get(i).setText(it.next());
						referenceBoxes.get(i).setEnabled(false);
						targetBoxes.get(i).setEnabled(false);
						filterBoxes.get(i).setEnabled(true);
					} else {
						filterBoxes.get(i).setText("---");
						referenceBoxes.get(i).setEnabled(false);
						targetBoxes.get(i).setEnabled(false);
						filterBoxes.get(i).setEnabled(false);
					}
				}
				this.minHash   = getStoredMin();
				this.maxHash   = getStoredMax();
				this.autoHash  = getStoredAuto();
				this.typeHash  = getStoredAutoType();
				this.colorHash = getStoredColor();
				this.ballHash  = getStoredBall();
				if(this.getColorHash().get(filter) != null){
					colorSelector.setSelectedItem(this.getColorHash().get(filter));
				}
				this.baseFrame.getLabelFilter().setText("Filter");
			} else if(mode == BaseFrame.IMAGE_MODE_RELATIVE){
				Iterator<String> it = filters.iterator();
				for(int i = 0; i < filterBoxes.size(); i++){
					if(it.hasNext()){
						filterBoxes.get(i).setText(it.next());
						referenceBoxes.get(i).setEnabled(true);
						targetBoxes.get(i).setEnabled(true);
						filterBoxes.get(i).setEnabled(true);
					} else {
						filterBoxes.get(i).setText("---");
						referenceBoxes.get(i).setEnabled(false);
						targetBoxes.get(i).setEnabled(false);
						filterBoxes.get(i).setEnabled(false);
					}
				}
				this.minHash   = getStoredRMin();
				this.maxHash   = getStoredRMax();
				this.autoHash  = getStoredRAuto();
				this.typeHash  = getStoredRAutoType();
				this.colorHash = getStoredRColor();
				this.ballHash  = getStoredRBall();
				String[] luts = {"/thermal.lut", "/Blue_Green_Red.lut"};
				color_model = new DefaultComboBoxModel(luts);
				colorSelector.setModel(color_model);
				if(getColorHash().get(filter) != null && Arrays.asList(luts).contains(getColorHash().get(filter))){
					colorSelector.setSelectedItem(getColorHash().get(filter));
				} else {
					colorSelector.setSelectedItem(0);
					this.getColorHash().put(filter, "/thermal.lut");
				}
				this.baseFrame.getLabelFilter().setText("Use for finding Cell");
			} else {
				logger.fine("不明なモード(番号):" + mode);
			}
			this.updateNextButton();
		} finally{
			this.baseFrame.enableListener(true);
		}
	}

	/**
	 * @return the maxHash
	 */
	public HashMap<String, Integer> getMaxHash() {
		return maxHash;
	}

	/**
	 * @return the minHash
	 */
	public HashMap<String, Integer> getMinHash() {
		return minHash;
	}

	/**
	 * @return the autoHash
	 */
	public HashMap<String, Boolean> getAutoHash() {
		return autoHash;
	}

	/**
	 * @return the ballHash
	 */
	public HashMap<String, Integer> getBallHash() {
		return ballHash;
	}

	/**
	 * @return the typeHash
	 */
	public HashMap<String, String> getTypeHash() {
		return typeHash;
	}

	/**
	 * @return the colorHash
	 */
	public HashMap<String, String> getColorHash() {
		return colorHash;
	}

	/**
	 * @return the storedMax
	 */
	public HashMap<String, Integer> getStoredMax() {
		return storedMax;
	}

	/**
	 * @param storedMax the storedMax to set
	 */
	public void setStoredMax(HashMap<String, Integer> storedMax) {
		this.storedMax = storedMax;
	}

	/**
	 * @return the storedMin
	 */
	public HashMap<String, Integer> getStoredMin() {
		return storedMin;
	}

	/**
	 * @return the storedAuto
	 */
	public HashMap<String, Boolean> getStoredAuto() {
		return storedAuto;
	}

	/**
	 * @return the storedColor
	 */
	public HashMap<String, String> getStoredColor() {
		return storedColor;
	}

	/**
	 * @return the storedBall
	 */
	public HashMap<String, Integer> getStoredBall() {
		return storedBall;
	}

	/**
	 * @return the storedAutoType
	 */
	public HashMap<String, String> getStoredAutoType() {
		return storedAutoType;
	}

	/**
	 * @return the storedTMax
	 */
	public HashMap<String, Integer> getStoredTMax() {
		return storedTMax;
	}

	/**
	 * @return the storedTMin
	 */
	public HashMap<String, Integer> getStoredTMin() {
		return storedTMin;
	}

	/**
	 * @return the storedTAuto
	 */
	public HashMap<String, Boolean> getStoredTAuto() {
		return storedTAuto;
	}

	/**
	 * @return the storedTColor
	 */
	public HashMap<String, String> getStoredTColor() {
		return storedTColor;
	}

	/**
	 * @return the storedTBall
	 */
	public HashMap<String, Integer> getStoredTBall() {
		return storedTBall;
	}

	/**
	 * @return the storedTAutoType
	 */
	public HashMap<String, String> getStoredTAutoType() {
		return storedTAutoType;
	}

	/**
	 * @return the storedRMax
	 */
	public HashMap<String, Integer> getStoredRMax() {
		return storedRMax;
	}

	/**
	 * @return the storedRMin
	 */
	public HashMap<String, Integer> getStoredRMin() {
		return storedRMin;
	}

	/**
	 * @return the storedRAuto
	 */
	public HashMap<String, Boolean> getStoredRAuto() {
		return storedRAuto;
	}

	/**
	 * @return the storedRColor
	 */
	public HashMap<String, String> getStoredRColor() {
		return storedRColor;
	}

	/**
	 * @return the storedRBall
	 */
	public HashMap<String, Integer> getStoredRBall() {
		return storedRBall;
	}

	/**
	 * @return the storedRAutoType
	 */
	public HashMap<String, String> getStoredRAutoType() {
		return storedRAutoType;
	}
}
