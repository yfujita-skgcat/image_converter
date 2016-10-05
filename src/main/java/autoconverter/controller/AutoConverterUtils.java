package autoconverter.controller;

import ij.*;
import ij.io.FileInfo;
import java.awt.Component;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import autoconverter.model.ImageSet;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author yfujita
 */
public class AutoConverterUtils {

  static public final String COLOR_UNKNOWN = "unknown";
  static public final String COLOR_GRAY = "Grays";
  static public final String COLOR_CYAN = "Cyan";
  static public final String COLOR_BLUE = "Blue";
  static public final String COLOR_GREEN = "Green";
  static public final String COLOR_RED = "Red";
  static public final String COLOR_YELLOW = "Yellow";
  static public final String COLOR_MAGENTA = "Magenta";
  static public final String[] COLOR_LIST = {
    AutoConverterUtils.COLOR_GRAY,
    AutoConverterUtils.COLOR_MAGENTA,
    AutoConverterUtils.COLOR_CYAN,
    AutoConverterUtils.COLOR_BLUE,
    AutoConverterUtils.COLOR_GREEN,
    AutoConverterUtils.COLOR_YELLOW,
    AutoConverterUtils.COLOR_RED};
  public static final Pattern cellaviewPattern = Pattern.compile("(.*[A-Z](\\d+)--W(\\d+)--P(\\d+)--Z(\\d+)--T(\\d+))--(.*)\\.(?:tif|TIF|tiff|TIFF)");
  public static final Pattern ixPattern = Pattern.compile("(.*)_w\\d+(BF|NUA|NIBA|WBV|CFP|RFP|WIGA)\\.(?:tif|TIF|TIFF|tiff)");
  private static Logger logger = null;
  private static Logger fulllogger = null;

  /**
   * Display dialog for selecting directory.
   * @param parent
   * @return
   */
  public static File getDirectory(Component parent, String def) {
    JFileChooser jfc = new JFileChooser(".");
    jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    if( def != null){
      jfc.setSelectedFile(new File(def));
    }
    int ret = jfc.showSaveDialog(parent);
    if (ret != JFileChooser.APPROVE_OPTION) {
      return null;
    }
    File directory = jfc.getSelectedFile();
    return directory;
  }

  /**
   * Determine default color from String.
   * BF-> COLOR_GRAY, (CFP|AmCyan|WBV)->COLOR_BLUE, etc.
   * @param cs
   * @return
   */
  public static String getDefaultColor(String cs) {
    if (Pattern.compile("BF", Pattern.CASE_INSENSITIVE).matcher(cs).find()) {
      return AutoConverterUtils.COLOR_GRAY;
    } else if (Pattern.compile("(CFP|AmCyan|WBV)", Pattern.CASE_INSENSITIVE).matcher(cs).find()) {
      return AutoConverterUtils.COLOR_BLUE;
    } else if (Pattern.compile("(Hoechst|NUA)", Pattern.CASE_INSENSITIVE).matcher(cs).find()) {
      return AutoConverterUtils.COLOR_CYAN;
    } else if (Pattern.compile("(GFP|ZsGreen|NIBA)", Pattern.CASE_INSENSITIVE).matcher(cs).find()) {
      return AutoConverterUtils.COLOR_GREEN;
    } else if (Pattern.compile("(WIGA|DsRed|AsRed|RFP|PI|Cy3)", Pattern.CASE_INSENSITIVE).matcher(cs).find()) {
      return AutoConverterUtils.COLOR_RED;
    } else if (Pattern.compile("(YFP|ZsYellow)", Pattern.CASE_INSENSITIVE).matcher(cs).find()) {
      return AutoConverterUtils.COLOR_YELLOW;
    } else {
      return AutoConverterUtils.COLOR_GRAY;
    }
  }

  /**
   * Search TIF files.
   * @param top
   * @param list
   * @param recursive Recursive search mode.
   * @throws java.lang.InterruptedException
   */
  public static void recursiveSearch(File top, ArrayList<File> list, boolean recursive) throws InterruptedException {
    String[] contents = top.list();
    for (int i = 0; i < contents.length; i++) {
      if (Thread.interrupted()) {
        throw new InterruptedException();
      }
      File sdir = new File(top, contents[i]);
      if (sdir.isDirectory() && recursive) {
        recursiveSearch(sdir, list, recursive);
        continue;
      }
      if (sdir.getName().matches(".*\\.(tif|TIF|tiff|TIFF)")) {
        if (!sdir.getName().matches("_thumb_")) {
          list.add(sdir);
        }
      } else {
        logger.fine(sdir.getAbsolutePath() + " is not tiff file");
      }
    }
  }
  /**
   * Search TIF files.
   * @param top
   * @param imgSet
   * @param recursive
   * @throws java.lang.InterruptedException
   */
  public static void recursiveSearch(File top, ImageSet imgSet, boolean recursive) throws InterruptedException {
    ArrayList<File> list = new ArrayList<File>();
    AutoConverterUtils.recursiveSearch(top, list, recursive);
    for(File f: list){
      imgSet.addFile(f);
    }
  }
  /**
   * 
   * @param top
   * @param imgSet
   * @param recursive
   * @throws java.lang.InterruptedException
   */
  public static void recursiveSearch(String top, ImageSet imgSet, boolean recursive) throws InterruptedException {
    AutoConverterUtils.recursiveSearch(new File(top), imgSet, recursive);
  }

  /**
   * Search TIF files.
   * @param top
   * @param list
   * @param recursive Recursive search mode.
   * @throws java.lang.InterruptedException
   */
  public static void recursiveSearch(String top, ArrayList<File> list, boolean recursive) throws InterruptedException {
    AutoConverterUtils.recursiveSearch(new File(top), list, recursive);
  }


  /**
   * Categorize files by file name without filter name and extension.
   * file_name_w3CFP.tif -> file_name
   * @param list
   * @return
   */
  public static ArrayList<ArrayList<File>> getGroupedFile(ArrayList<File> list) {
    Hashtable<String, ArrayList<File>> hash = new Hashtable<String, ArrayList<File>>();
    String hash_key = null;
    //String color_string = null;
    ArrayList<File> ret = null;
    for (File f : list) {
      hash_key = AutoConverterUtils.getBaseName(f);
      ret = hash.get(hash_key);
      if (ret == null) {
        ret = new ArrayList<File>();
        hash.put(hash_key, ret);
      }
      ret.add(f);
    }
    ArrayList<ArrayList<File>> groupedFiles = new ArrayList<ArrayList<File>>();
    Iterator<String> it = hash.keySet().iterator();
    String key;
    ArrayList<File> files;
    while (it.hasNext()) {
      key = it.next();
      files = hash.get(key);
      //for(File f: files){
      //  System.out.println("key = " + key + ", f.getAbusolutePath = " + f.getAbsolutePath());
      //}
      groupedFiles.add(files);
    }

    return groupedFiles;
  }

  /**
   *
   * @param file
   * @return color identified name such as BF, WIGA, and yfujita-BF-10x etc.
   */
  public static String getFilterString(File file) {
    String path = file.getAbsolutePath();
    return AutoConverterUtils.getFilterString(path);
  }

  /**
   * 
   * @param path
   * @return color identified name such as BF, WIGA, and yfujita-BF-10x etc.
   */
  public static String getFilterString(String path) {
    Matcher match = cellaviewPattern.matcher(path);
    if (match.matches()) {
      //System.out.println("Matching cellaview:" + path);
      return match.group(7);
    }
    match = ixPattern.matcher(path);
    if (match.matches()) {
      //System.out.println("Matching IX:" + path);
      return match.group(2);
    } else {
      return "";
    }
  }

  /**
   *
   * @param imp
   * @return color identified name such as BF, WIGA, and yfujita-BF-10x etc.
   */
  public static String getFilterString(ImagePlus imp) {
    String file_path = AutoConverterUtils.getAbusolutePath(imp);
    return AutoConverterUtils.getFilterString(file_path);
  }

  public static HashSet<String> getFilterStrings(ArrayList<File> list) {
    HashSet<String> colorList = new HashSet<String>();
    for (File f : list) {
      colorList.add(AutoConverterUtils.getFilterString(f));
    }
    return colorList;
  }

  public static String getBaseName(File f) {
    String hash_key = "";
    String path = f.getAbsolutePath();
    Matcher cellaviewMatcher = AutoConverterUtils.cellaviewPattern.matcher(path);
    Matcher ixMatcher = AutoConverterUtils.ixPattern.matcher(path);
    if (cellaviewMatcher.matches()) {
      // this is cellaview
      hash_key = cellaviewMatcher.group(1);
    //color_string = cellaviewMatcher.group(7);
    } else if (ixMatcher.matches()) {
      // this is XI
      hash_key = ixMatcher.group(1);
    //color_string = ixMatcher.group(2);
    }
    return hash_key;
  }

  public static boolean isCellaViewFile(File f) {
    return AutoConverterUtils.isCellaViewFile(f.getAbsolutePath());
  }
  public static boolean isCellaViewFile(String path){
    Matcher celaviewMatcher = AutoConverterUtils.cellaviewPattern.matcher(path);
    if(celaviewMatcher.matches()){
      return true;
    }
    return false;
  }
  public static boolean isIXFile(File f) {
    return AutoConverterUtils.isIXFile(f.getAbsolutePath());
  }
  public static boolean isIXFile(String path) {
    Matcher ixMatcher = AutoConverterUtils.ixPattern.matcher(path);
    if(ixMatcher.matches()){
      return true;
    }
    return false;
  }

  public static Logger getLogger() {
    if (logger == null) {
      logger = Logger.getLogger("LogManager");
      ConsoleHandler sh = new ConsoleHandler();
      sh.setFormatter(new IJLoggerFormatter());
      sh.setLevel(Level.FINE);
      logger.addHandler(sh);
      logger.setLevel(Level.FINE);
    }
    return logger;
  }

  public static Logger getFullLogger() {
    if (fulllogger == null) {
      fulllogger = Logger.getLogger("FullLogManager");
      ConsoleHandler sh = new ConsoleHandler();
      sh.setFormatter(new IJFullLoggerFormatter());
      sh.setLevel(Level.ALL);
      fulllogger.addHandler(sh);
      fulllogger.setLevel(Level.ALL);
    }
    return fulllogger;
  }

  public static String getAbusolutePath(ImagePlus imp) {
    int stack_size = imp.getStackSize();
    ImageStack stack;
    String fname;
    FileInfo fi;
    String abusolute_path;
    //fi = imp.getOriginalFileInfo();
    fi = imp.getOriginalFileInfo();
    if (stack_size > 1) {
      stack = imp.getStack();
      fname = stack.getSliceLabel(imp.getCurrentSlice());
      abusolute_path = fi.directory + fname;
    } else {
      abusolute_path = fi.directory + fi.fileName;
    }
    return abusolute_path;
  }

  /**
   * return version
   * @return
   */
  public static String getVersion(){
    return "0.3.2";
  }
}
