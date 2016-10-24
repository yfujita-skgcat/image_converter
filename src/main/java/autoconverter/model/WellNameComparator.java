/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package autoconverter.model;

import autoconverter.controller.ApplicationController;
import autoconverter.controller.AutoConverterUtils;
import java.util.Comparator;
import java.util.logging.Logger;

/**
 * Well (X??) (X: alphabet, ??:数値)形式の文字列をソートする.
 * 要するにWell名のソート.
 * @author yfujita
 */
public class WellNameComparator implements Comparator {

	private static final Logger logger = AutoConverterUtils.getLogger();
	public int compare(Object well1, Object well2) {
		String w1 = (String)well1;
		String w2 = (String)well2;
		int v1 = 0;
		int v2 = 0;
		try {
		v1 = Integer.parseInt(w1.replaceAll("[^0-9]", ""));
		v2 = Integer.parseInt(w2.replaceAll("[^0-9]", ""));
		} catch (NumberFormatException e){
			// 特に何もしない.
			//logger.fine(ApplicationController.stacktrace(e));
		}

		String wc1 = w1.replaceAll("[0-9]","");
		String wc2 = w2.replaceAll("[0-9]","");
		int ret;
		if ((ret = wc1.compareTo(wc2)) == 0){
			ret = v1 - v2;
		}
		return ret;

		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
