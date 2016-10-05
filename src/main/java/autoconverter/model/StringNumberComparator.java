/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package autoconverter.model;

import java.util.Comparator;

/**
 * Well (X??) (X: alphabet, ??:数値)形式の文字列をソートする.
 * 要するにWell名のソート.
 * @author yfujita
 */
public class StringNumberComparator implements Comparator {

	public int compare(Object well1, Object well2) {
		String w1 = (String)well1;
		String w2 = (String)well2;
		int v1 = Integer.parseInt(w1.replaceAll("[^0-9]", ""));
		int v2 = Integer.parseInt(w2.replaceAll("[^0-9]", ""));
		int ret;
		ret = v1 - v2;
		return ret;

		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
