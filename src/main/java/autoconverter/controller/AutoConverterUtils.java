package autoconverter.controller;

import ij.*;
import ij.io.FileInfo;
import java.awt.Component;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import autoconverter.model.ImageSet;
import autoconverter.view.DirectoryChooserDialog;
import java.awt.Frame;
import java.io.PrintWriter;
import java.io.StringWriter;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author yfujita
 */
public class AutoConverterUtils {

	static public final String VERSION = "0.3.9";
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
	private static Logger logger = null;
	private static Logger fulllogger = null;

	/**
	 * Display dialog for selecting directory.
	 *
	 * @param parent
	 * @return
	 */
	public static File getDirectory(Component parent, String def) {
		int ret = 0;
		DirectoryChooserDialog dcd = null;
		JFileChooser jfc = null;
		File def_dir = null;
		if(def != null){
			def_dir = new File(def);
		}

		if (!AutoConverterUtils.isWindows()) {
			dcd = new DirectoryChooserDialog((Frame) parent, true);
			if( def_dir != null && def_dir.isDirectory()){
				dcd.setFilePath(def);
			}
			ret = dcd.showDialog();
		} else {

			jfc = new JFileChooser(".");
			jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			if( def_dir != null && def_dir.isDirectory()){
				jfc.setSelectedFile(def_dir);
			}
			ret = jfc.showSaveDialog(parent);
		}

		if (ret != JFileChooser.APPROVE_OPTION) {
			return null;
		}
		File directory;
		if (!AutoConverterUtils.isWindows()) {
			directory = dcd.getFile();
		} else {
			directory = jfc.getSelectedFile();
		}
		if(directory == null){
			return null;
		}
		if (!directory.isDirectory()) {
			File parent_dir = directory.getParentFile();
			return parent_dir;
		}
		return directory;
	}

	public static boolean isWindows(){
		boolean ret = "\\".equals(System.getProperty("file.separator"));
		return ret;
	}

	/**
	 * Determine default color from String. BF-> COLOR_GRAY,
	 * (CFP|AmCyan|WBV)->COLOR_BLUE, etc.
	 *
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
	 *
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
	 *
	 * @param top
	 * @param imgSet
	 * @param recursive
	 * @throws java.lang.InterruptedException
	 */
	public static void recursiveSearch(File top, ImageSet imgSet, boolean recursive) throws InterruptedException {
		ArrayList<File> list = new ArrayList<File>();
		AutoConverterUtils.recursiveSearch(top, list, recursive);
		for (File f : list) {
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
	 *
	 * @param top
	 * @param list
	 * @param recursive Recursive search mode.
	 * @throws java.lang.InterruptedException
	 */
	public static void recursiveSearch(String top, ArrayList<File> list, boolean recursive) throws InterruptedException {
		AutoConverterUtils.recursiveSearch(new File(top), list, recursive);
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
	 *
	 * @return
	 */
	public static String getVersion() {
		return AutoConverterUtils.VERSION;
	}

	public static String tr(String from, String to, String str) {
		if (from.length() != to.length()) {
			throw new IllegalArgumentException("Mismatch lengthes of from and to.");
		}
		char[] tmp = new char[str.length()];
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			tmp[i] = c;
			for (int j = 0; j < from.length(); j++) {
				if (c == from.charAt(j)) {
					tmp[i] = to.charAt(j);
					break;
				}
			}
		}
		str = new String(tmp);
		return str;
	}

	public static String stacktrace(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}
	public static String stacktrace(String msg){
		Exception e = new Exception(msg);
		return AutoConverterUtils.stacktrace(e);
	}
	public static void showStacktrace(String msg){
		if(logger != null){
			logger.fine(AutoConverterUtils.stacktrace(msg));
		}
	}
}
