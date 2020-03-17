/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoconverter.controller;

import java.util.logging.Logger;

/**
 *
 * @author yfujita
 */
public class ThreadExceptionHandler implements Thread.UncaughtExceptionHandler{
	private static final Logger logger = AutoConverterUtils.getLogger();

	@Override
	public void uncaughtException(Thread thread, Throwable e) {
		logger.fine("Uncaught Exception:");
		logger.fine(thread.getName());   // Thread ID 1
		logger.fine(e.toString());  // java.lang.IllegalArgumentException
		logger.fine(""+e.getStackTrace()[0].getLineNumber());  // 28
		logger.fine(e.getStackTrace()[0].getFileName());    
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
