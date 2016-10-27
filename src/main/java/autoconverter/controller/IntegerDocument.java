/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoconverter.controller;

import java.util.logging.Logger;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author yfujita
 */
public class IntegerDocument extends PlainDocument {
	int currentValue = 0;
	private int maxValue;
	private int minValue;
	private static Logger logger = AutoConverterUtils.getLogger();
	public IntegerDocument(){
		super();
		minValue = 0;
		maxValue = 65535;
	}
	public int getValue(){
		return currentValue;
	}
	public void setMinAndMaxValue(int min, int max){
		minValue = min;
		maxValue = max;
	}

	/**
	 *
	 * @param offset
	 * @param str
	 * @param attributes
	 * @throws BadLocationException
	 */
	@Override
	public void insertString(int offset, String str, AttributeSet attributes) throws BadLocationException {
		logger.fine("input");
		if(str == null){
			return;
		} else {
			String newValue;
			int length = getLength();
			if(length == 0){
				newValue = str;
			} else {
				String currentContent = getText(0, length);
				StringBuffer currentBuffer = new StringBuffer(currentContent);
				currentBuffer.insert(offset, str);
				newValue = currentBuffer.toString();
			}
			currentValue = checkInput(newValue, offset);
			super.insertString(offset, str, attributes);	
		}
		return;
	}

	private int checkInput(String proposedValue, int offset) throws BadLocationException {
		if (proposedValue.length() > 0) {
			try {
				int newValue = Integer.parseInt(proposedValue);
				if(newValue < minValue || newValue > maxValue){
					throw new NumberFormatException("Out of range. (" + minValue + ", " + maxValue + ")");
				}
				return newValue;
			} catch (NumberFormatException e) {
				throw new BadLocationException(proposedValue, offset);
			}
		} else {
			return 0;
		}
	}

	/**
	 *
	 * @param offset
	 * @param length
	 * @throws BadLocationException
	 */
	@Override
	public void remove(int offset, int length) throws BadLocationException {
		int currentLength = getLength();
		String currentContent = getText(0, currentLength);
		String before = currentContent.substring(0, offset);
		String after = currentContent.substring(length + offset, currentLength);
		String newValue = before + after;
		currentValue = checkInput(newValue, offset);
		super.remove(offset, length);
	}
	
}
