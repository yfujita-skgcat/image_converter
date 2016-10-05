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
import java.util.HashMap;

/**
 *
 * @author yfujita
 */
public class AutoConverterConfig {
  private static HashMap<String, String> config = new HashMap<String, String>();
  public static String KEY_SOURCE_DIRECTORY = "SOURCE_DIRECTORY";
  public static String KEY_DESTINATION_DIRECTORY = "DESTINATION_DIRECTORY";
  public static String KEY_MAIN_FRAME_SIZE_X = "MAIN_FRAME_SIZE_X";
  public static String KEY_MAIN_FRAME_SIZE_Y = "MAIN_FRAME_SIZE_Y";
  public static String KEY_RECURSIVE_ON = "RECURSIVE_ON";
  private static String file_path = System.getProperty("user.home") + File.separator + ".ImageJ" + File.separator + "autoconverter.config";

  /**
   * @return the config
   */
  public static String getConfig(String key) {
    return config.get(key);
  }

  /**
   *
   * @param key
   * @param def
   * @return
   */
  public static String getConfig(String key, String def){
    String ret = config.get(key);
    if(ret == null){
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
   * @param key of config
   * @param val of config
   */
  public static void setConfig(String key, String val) {
    config.put(key, val);
  }

  /**
   * set config file path.
   * @param path
   */
  public static void setFilePath(String path){
    file_path = path;
  }

  /**
   * save config to path
   * @param path
   * @throws java.io.FileNotFoundException
   */
  public static void save(String path) throws FileNotFoundException{
    File dir = new File((new File(path)).getParent());
    dir.mkdirs();
    XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(path)));
    encoder.writeObject(config);
    encoder.flush();
    encoder.close();
  }

  /**
   * save config to file.
   * @throws java.io.FileNotFoundException
   */
  public static void save() throws FileNotFoundException{
    AutoConverterConfig.save(file_path);
  }

  /**
   * load config from path
   * @param path
   * @throws java.io.FileNotFoundException
   */
  public static void load(String path) throws FileNotFoundException{
    XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(path)));
    File dir = new File((new File(path)).getParent());
    dir.mkdirs();
    config = (HashMap) decoder.readObject();
    decoder.close();
  }

  /**
   * load config from path
   * @throws java.io.FileNotFoundException
   */
  public static void load() throws FileNotFoundException{
    AutoConverterConfig.load(file_path);
  }
}
