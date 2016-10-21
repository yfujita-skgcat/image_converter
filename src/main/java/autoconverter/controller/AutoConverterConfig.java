/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package autoconverter.controller;

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
import java.util.regex.Pattern;

/**
 *
 * @author yfujita
 */
public class AutoConverterConfig {

	private static HashMap<String, String> config = new HashMap<String, String>();
	public static final String celaviewRegexpString = "(?<WELL>[^-]+)--W(?<NUM>\\d+)--P(?<POS>\\d+)--Z(?<ZPOS>\\d+)--T(?<TIME>\\d+)--(?<FILTER>.*)\\.(?:tif|TIF|tiff|TIFF)";
	public static final Pattern celaviewPattern = Pattern.compile(celaviewRegexpString);
	public static final String inCell6000RegexpString = "(?<WELL>[A-Q] - \\d+)\\(fld (?<POS>\\d+) wv (?<FILTER>[^\\)]+)\\)\\.(?:tif|TIFF|tiff|TIFF)";
	public static final Pattern inCell6000Pattern = Pattern.compile(inCell6000RegexpString);
	/*A - 02(fld 04 wv Blue - FITC).tif
A - 02(fld 04 wv TL-Brightfield - dsRed).tif
A - 02(fld 04 wv UV - DAPI).tif
	String regrex = "(?<TYPE>GET|POST) (?<IP>\\d+\\.\\d+\\.\\d+\\.\\d+)";
	*/
	public static final String ixRegexpString = "(.*)_w\\d+(BF|NUA|NIBA|WBV|CFP|RFP|WIGA)\\.(?:tif|TIF|TIFF|tiff)";
	public static final Pattern ixPattern = Pattern.compile(ixRegexpString);
	public static String KEY_SOURCE_DIRECTORY = "SOURCE_DIRECTORY";
	public static String KEY_DESTINATION_DIRECTORY = "DESTINATION_DIRECTORY";
	public static String KEY_MAIN_FRAME_SIZE_X = "MAIN_FRAME_SIZE_X";
	public static String KEY_MAIN_FRAME_SIZE_Y = "MAIN_FRAME_SIZE_Y";
	public static String KEY_RECURSIVE_ON = "RECURSIVE_ON";
	public static String KEY_SELECTED_PATTERN = "SELECTED_PATTERN";
	public static String KEY_SELECTED_DISPLAY_RANGE = "SELECTED_DISPLAY_RANGE";
	public static String PREFIX_BALL = "BALL";
	public static String PREFIX_MIN = "MIN";
	public static String PREFIX_MAX = "MAX";
	public static String PREFIX_COLOR = "COLOR";
	public static String PREFIX_AUTO = "AUTO";
	public static String PREFIX_REGEXP = "REGEXP";
	public static String REGEXP_NAME_CUSTOM = "Custom";
	public static String REGEXP_NAME_CELAVIEW = "Celaview";
	public static String REGEXP_NAME_INCELL6000 = "In Cell 6000";
	private static String file_path = System.getProperty("user.home") + File.separator + ".ImageJ" + File.separator + "autoconverter.config";

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
	public static void save(String path) throws FileNotFoundException {
		File dir = new File((new File(path)).getParent());
		dir.mkdirs();
		XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(path)));
		encoder.writeObject(config);
		encoder.flush();
		encoder.close();
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
		pattern.put(AutoConverterConfig.PREFIX_REGEXP + "_" + AutoConverterConfig.REGEXP_NAME_CELAVIEW, AutoConverterConfig.celaviewRegexpString);
		pattern.put(AutoConverterConfig.PREFIX_REGEXP + "_" + AutoConverterConfig.REGEXP_NAME_INCELL6000, AutoConverterConfig.inCell6000RegexpString);
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
