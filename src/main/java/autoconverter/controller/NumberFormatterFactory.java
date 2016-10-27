/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoconverter.controller;

import java.text.NumberFormat;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

/**
 *
 * @author yfujita
 */
public class NumberFormatterFactory extends DefaultFormatterFactory {
	private static NumberFormatter numberFormatter = new NumberFormatter();
	static {
		numberFormatter.setValueClass(Integer.class);
		((NumberFormat) numberFormatter.getFormat()).setGroupingUsed(false);
	}
	public NumberFormatterFactory() {
		super(numberFormatter, numberFormatter, numberFormatter);
	}
}

