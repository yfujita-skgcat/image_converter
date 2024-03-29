/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package autoconverter.controller;

import autoconverter.view.BaseFrame;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author yfujita
 */
public class AutoConverterConfig {

	private static HashMap<String, String> config = new HashMap<String, String>();
	public static final String celaviewRegexpString = "(?<WELL>[^-]+)--W(?<NUM>\\d+)--P(?<POS>\\d+)--Z(?<ZPOS>\\d+)--T(?<TIME>\\d+)--(?<FILTER>.*)\\.(?:tif|TIF|tiff|TIFF)";
	public static final Pattern celaviewPattern = Pattern.compile(celaviewRegexpString);
	//public static final String inCell6000RegexpString = "(?<WELL>[A-Q] - \\d+)\\(fld (?<POS>\\d+) wv (?<FILTER>[^\\)]+)\\)\\.(?:tif|TIFF|tiff|TIFF)";
	public static final String inCell6000RegexpString = "(?<WELL>[A-Q] - \\d+)\\((fld (?<POS>\\d+))? ?(z (?<ZPOS>\\d+))? ?(wv (?<FILTER>[^\\)]+))?\\)\\.(?:tif|TIF|tiff|TIFF)";
	public static final Pattern inCell6000Pattern = Pattern.compile(inCell6000RegexpString);
	//public static final String CytellRegexpString =     "(?<WELL>[A-Q] - \\d+)\\((fld (?<POS>\\d+))? ?(z (?<ZPOS>\\d+))? ?(wv (?<FILTER>[^\\)]+))?\\)\\.(?:tif|TIF|tiff|TIFF)";
	//public static final Pattern CytellPattern     = Pattern.compile("");
	public static final String IX81RegexpString = "(?!.*_thumb_)(?!.*_univ).*-(?<POS>\\d{3})_w(?<FILTER>[^.]+)\\.(?:TIF|tif|TIFF|tiff)";
	public static final Pattern ixPattern = Pattern.compile(IX81RegexpString);
	public static final String CQ1RegexpString = "(?<WELL>W\\d+)F(?<POS>\\d+)T(?<TIME>\\d+)Z(?<ZPOS>...)(?<FILTER>C\\d+)\\.(?:tif|TIF|tiff|TIFF)";
	public static final Pattern CQ1Pattern = Pattern.compile(CQ1RegexpString);
	public static final String IX81RegexString_A = "(?!.*_thumb_)(?!.*_univ)(?<WELL>\\d+)_w(?<FILTER>[^.]+)\\.(?:TIF|tif|TIFF|tiff)";
	public static final Pattern ix81Pattern_A = Pattern.compile(IX81RegexString_A);
	public static final String IX81RegexString_B = "(?!.*_thumb_)(?!.*_univ)(?<WELL>.*)_w(?<FILTER>[^.]+)\\.(?:TIF|tif|TIFF|tiff)";
	public static final Pattern ix81Pattern_B = Pattern.compile(IX81RegexString_B);
	public static final String IX81RegexString_C = "(?!.*_thumb_)(?!.*_univ)(?<WELL>.+[^\\d])(?<POS>\\d+)_w(?<FILTER>[^.]+)\\.(?:TIF|tif|TIFF|tiff)";
	public static final Pattern ix81Pattern_C = Pattern.compile(IX81RegexString_C);
	public static final String IX81RegexString_D = "(?!.*_thumb_)(?!.*_univ)(?<WELL>.*)[ -](?<POS>\\d+)_w(?<FILTER>[^.]+)\\.(?:TIF|tif|TIFF|tiff)";
	public static final Pattern ix81Pattern_D = Pattern.compile(IX81RegexString_D);
	public static final String CQ1ChannelRegexString = "Ch(\\d+)\\s+([^\\s]+)\\s+([^\\s]+)\\s+([^/ ]+).*";
	public static final Pattern CQ1ChannelPattern = Pattern.compile(CQ1ChannelRegexString);
	public static final String KEY_CROP_AREA_X = "CROP_AREA_X";
	public static final String KEY_CROP_AREA_Y = "CROP_AREA_Y";
	public static final String KEY_CROP_AREA_W = "CROP_AREA_W";
	public static final String KEY_CROP_AREA_H = "CROP_AREA_H";
	public static final String KEY_SOURCE_DIRECTORY = "SOURCE_DIRECTORY";
	public static final String KEY_DESTINATION_DIRECTORY = "DESTINATION_DIRECTORY";
	public static final String KEY_MAIN_FRAME_SIZE_X = "MAIN_FRAME_SIZE_X";
	public static final String KEY_MAIN_FRAME_SIZE_Y = "MAIN_FRAME_SIZE_Y";
	public static final String KEY_IMAGE_FORMAT = "IMAGE_FORMAT";
	public static final String KEY_RECURSIVE_ON = "RECURSIVE_ON";
	public static final String KEY_SELECTED_PATTERN = "SELECTED_PATTERN";
	public static final String KEY_SELECTED_DISPLAY_RANGE = "SELECTED_DISPLAY_RANGE";
	public static final String KEY_REMOVE_SPECIAL_CHAR = "REMOVE_SPECIAL_CHAR";
	public static final String KEY_ADD_PARAM_TO_FILENAME = "ADD_PARAM_TO_FILENAME";
	public static final String KEY_IGNORE_SYMLINK = "IGNORE_SYMLINK";
	public static final String KEY_CONVERT_WELL_NAME = "CONVERT_WELL_NAME";
	public static final String KEY_PLATE_TYPE = "PLATE_TYPE";
	public static final String KEY_IMAGE_MODE = "IMAGE_MODE";
	public static final String KEY_RESIZE_CHECK = "RESIZE_CHECK";
	public static final String KEY_RESIZE_WIDTH = "RESIZE_WIDTH";
	public static final String KEY_READ_CQ1CONFIG = "READCQ1CONFIG";
	public static final String KEY_INCLUDE_REGEXP_STRING = "INCLUDE_REGEXP_STRING";
	public static final String KEY_EXCLUDE_REGEXP_STRING = "EXCLUDE_REGEXP_STRING";

	public static final String PREFIX_BALL = "BALL";
	public static final String PREFIX_MIN = "MIN";
	public static final String PREFIX_MAX = "MAX";
	public static final String PREFIX_COLOR = "COLOR";
	public static final String PREFIX_AUTO = "AUTO";
	public static final String PREFIX_REGEXP = "REGEXP";
	public static final String PREFIX_AUTO_TYPE = "AUTO_SCALE_TYPE";

	public static final String PREFIX_T_BALL = "T_BALL";
	public static final String PREFIX_T_MIN = "T_MIN";
	public static final String PREFIX_T_MAX = "T_MAX";
	public static final String PREFIX_T_COLOR = "T_COLOR";
	public static final String PREFIX_T_AUTO = "T_AUTO";
	public static final String PREFIX_T_AUTO_TYPE = "T_AUTO_SCALE_TYPE";

	public static final String PREFIX_R_BALL = "R_BALL";
	public static final String PREFIX_R_MIN = "R_MIN";
	public static final String PREFIX_R_MAX = "R_MAX";
	public static final String PREFIX_R_COLOR = "R_COLOR";
	public static final String PREFIX_R_AUTO = "R_AUTO";
	public static final String PREFIX_R_AUTO_TYPE = "R_AUTO_SCALE_TYPE";


	public static final String REGEXP_NAME_CUSTOM = "Custom";
	public static final String REGEXP_NAME_CELAVIEW = "Celaview";
	public static final String REGEXP_NAME_CQ1 = "CQ1";
	public static final String REGEXP_NAME_INCELL6000 = "In Cell 6000";
	public static final String REGEXP_NAME_IX81 = "IX81";
	public static final String REGEXP_NAME_IX81A = "IX81 (e.g. 13_w1BF.tif)";
	public static final String REGEXP_NAME_IX81B = "IX81 (e.g. iPS_wNIBA.tif)";
	public static final String REGEXP_NAME_IX81C = "IX81 (e.g. 2016310_293FT13_wNIBA.tif)";
	public static final String REGEXP_NAME_IX81D = "IX81 (e.g. 293FT 10_wNIBA.tif)";
	private static String file_path = System.getProperty("user.home") + File.separator + ".ImageJ" + File.separator + "autoconverter.config";
	private static Logger logger = AutoConverterUtils.getLogger();

	/**
	 * @return the config
	 */
	public static String getConfig(String key, String prefix) {
		if (prefix == null) {
			return config.get(key);
		} else {
			return config.get(prefix + "_" + key);
		}
	}

	/**
	 *
	 * @param key
	 * @param def
	 * @return
	 */
	public static String getConfig(String key, String def, String prefix) {
		String ret = AutoConverterConfig.getConfig(key, prefix);
		if (ret == null) {
			return def;
		}
		return ret;
	}

	public static int getConfig(String key, int def){
		int ret = AutoConverterConfig.getConfig(key, def, null);
		return ret;
	}

	public static int getConfig(String key, int def, String prefix) {
		String ret = AutoConverterConfig.getConfig(key, prefix);
		if (ret == null) {
			return def;
		}
		return Integer.parseInt(ret);
	}

	/**
	 *
	 * @param key of config
	 * @param val of config
	 */
	public static void setConfig(String key, int val) {
		AutoConverterConfig.setConfig(key, Integer.toString(val));
	}

	/**
	 *
	 * @param key
	 * @param val
	 * @param prefix
	 */
	public static void setConfig(String key, int val, String prefix) {
		AutoConverterConfig.setConfig(key, Integer.toString(val), prefix);
	}

	/**
	 * @param key of config
	 * @param val of config
	 */
	public static void setConfig(String key, String val) {
		config.put(key, val);
	}

	/**
	 *
	 * @param key
	 * @param val
	 * @param prefix
	 */
	public static void setConfig(String key, String val, String prefix) {
		config.put(prefix + "_" + key, val);
	}

	/**
	 * 
	 * @param key 
	 */
	public static void removeConfig(String key){
		config.remove(key);
	}

	/**
	 * 
	 * @param key
	 * @param prefix 
	 */
	public static void removeConfig(String key, String prefix){
		if (prefix == null) {
			AutoConverterConfig.removeConfig(key);
		} else {
			AutoConverterConfig.removeConfig(prefix + "_" + key);
		}
	}

	/**
	 * set config file path.
	 *
	 * @param path
	 */
	public static void setFilePath(String path) {
		file_path = path;
	}

	/**
	 * save config to path
	 *
	 * @param path
	 * @throws java.io.FileNotFoundException
	 */
	public static synchronized void save(String path) throws FileNotFoundException {
		File dir = new File((new File(path)).getParent());
		dir.mkdirs();
		logger.fine(config.get(AutoConverterConfig.KEY_IGNORE_SYMLINK));
		logger.fine(config.get(AutoConverterConfig.KEY_ADD_PARAM_TO_FILENAME));
		logger.log(Level.INFO, "Writing config to {0}...", path);
		try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(path)))) {
			encoder.writeObject(config);
			encoder.flush();
		}
	}

	/**
	 * save config to file.
	 *
	 * @throws java.io.FileNotFoundException
	 */
	public static void save() throws FileNotFoundException {
		AutoConverterConfig.save(file_path);
	}

	/**
	 * save config to file.
	 * throws をこの関数内で解決する.
	 * @param ignore エラーが起きても終了しない.
	 * @param baseFrame lockをかけるwindow
	 */
	public static void save(BaseFrame baseFrame, boolean ignore) {
		try {
			AutoConverterConfig.save(file_path);
		} catch (FileNotFoundException ex) {
			if(ignore){
				JOptionPane.showConfirmDialog(baseFrame, ex.toString(), java.util.ResourceBundle.getBundle("autoconverter/controller/Bundle").getString("CONFIG SAVE ERROR"), JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showConfirmDialog(baseFrame, ex.toString() + "\nShutdown program... Error Code: 45", java.util.ResourceBundle.getBundle("autoconverter/controller/Bundle").getString("CONFIG SAVE ERROR"), JOptionPane.ERROR_MESSAGE);
				System.exit(45);
			}
		}
	}

	/**
	 * load config from path
	 *
	 * @param path
	 * @throws java.io.FileNotFoundException
	 */
	public static void load(String path) throws FileNotFoundException {
		XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(path)));
		File dir = new File((new File(path)).getParent());
		dir.mkdirs();
		config = (HashMap) decoder.readObject();
		decoder.close();

	}


	/**
	 * load config from path
	 *
	 * @throws java.io.FileNotFoundException
	 */
	public static void load() throws FileNotFoundException {
		AutoConverterConfig.load(file_path);
	}

	/**
	 * config 内のREGEX関連の設定を抜き出し、Customなどの必須項目を
	 * 追加したリストを返す.
	 * @return 
	 */
	public static LinkedHashMap<String, String> getPatternHashMap(){
		LinkedHashMap<String, String> pattern = new LinkedHashMap<>();

		for(String key : new TreeSet<>(config.keySet())){
			if(key.startsWith(AutoConverterConfig.PREFIX_REGEXP)){
				pattern.put(key, config.get(key));
			}
		}
		pattern.put(AutoConverterConfig.PREFIX_REGEXP + "_" + AutoConverterConfig.REGEXP_NAME_CQ1, AutoConverterConfig.CQ1RegexpString);
		pattern.put(AutoConverterConfig.PREFIX_REGEXP + "_" + AutoConverterConfig.REGEXP_NAME_CELAVIEW, AutoConverterConfig.celaviewRegexpString);
		pattern.put(AutoConverterConfig.PREFIX_REGEXP + "_" + AutoConverterConfig.REGEXP_NAME_INCELL6000, AutoConverterConfig.inCell6000RegexpString);
		pattern.put(AutoConverterConfig.PREFIX_REGEXP + "_" + AutoConverterConfig.REGEXP_NAME_IX81, AutoConverterConfig.IX81RegexpString);
		pattern.put(AutoConverterConfig.PREFIX_REGEXP + "_" + AutoConverterConfig.REGEXP_NAME_IX81A, AutoConverterConfig.IX81RegexString_A);
		pattern.put(AutoConverterConfig.PREFIX_REGEXP + "_" + AutoConverterConfig.REGEXP_NAME_IX81B, AutoConverterConfig.IX81RegexString_B);
		pattern.put(AutoConverterConfig.PREFIX_REGEXP + "_" + AutoConverterConfig.REGEXP_NAME_IX81C, AutoConverterConfig.IX81RegexString_C);
		pattern.put(AutoConverterConfig.PREFIX_REGEXP + "_" + AutoConverterConfig.REGEXP_NAME_IX81D, AutoConverterConfig.IX81RegexString_D);
		pattern.put(AutoConverterConfig.PREFIX_REGEXP + "_" + AutoConverterConfig.REGEXP_NAME_CUSTOM, "");
		return pattern;
	}

	/**
	 * regex_name で指定された正規表現がconfigファイル内に見つかれば
	 * その正規表現を返す.
	 * @param regex_name Celaview, incell などの文字列
	 * @return 正規表現の文字列
	 */
	public static String getRegexp(String regex_name){
		LinkedHashMap<String, String> pattern = AutoConverterConfig.getPatternHashMap();
		if(pattern.containsKey(regex_name)){
		   return pattern.get(regex_name);
		} else if (pattern.containsKey(AutoConverterConfig.PREFIX_REGEXP + "_" + regex_name)){
			return pattern.get(AutoConverterConfig.PREFIX_REGEXP + "_" + regex_name);
		}
		return "";
	}

	/**
	 * ファイル名パターンの名前一覧を返す.
	 * @return 
	 */
	public static ArrayList <String> getFilePatternNames(){
		LinkedHashMap<String, String> pattern = AutoConverterConfig.getPatternHashMap();
		ArrayList <String> list = new ArrayList();
		for(String s: pattern.keySet()){
			list.add(s.replaceFirst(AutoConverterConfig.PREFIX_REGEXP+"_", ""));
		}
		return list;
	}
}
