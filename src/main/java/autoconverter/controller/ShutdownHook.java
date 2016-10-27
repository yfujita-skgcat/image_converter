/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoconverter.controller;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yfujita
 */
public class ShutdownHook implements Runnable {

	private static Logger logger = AutoConverterUtils.getLogger();

	@Override
	public void run() {
		logger.fine("Shutdown..");
		try {
			AutoConverterConfig.save();
		} catch (FileNotFoundException ex) {
			logger.warning(AutoConverterUtils.stacktrace(ex));
		}
		return;
	}
	
}
