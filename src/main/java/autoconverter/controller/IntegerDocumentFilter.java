/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoconverter.controller;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author yfujita
 */
public class IntegerDocumentFilter extends DocumentFilter {
	//int currentValue = 0;

	@Override
	public void insertString(DocumentFilter.FilterBypass fb,
		int offset, String string, AttributeSet attr) throws BadLocationException {
		if (string == null) {
			return;
		} else {
			replace(fb, offset, 0, string, attr);
		}
	}

	@Override
	public void remove(DocumentFilter.FilterBypass fb, int offset, int length)
		throws BadLocationException {
		replace(fb, offset, length, "", null);
	}

	@Override
	public void replace(DocumentFilter.FilterBypass fb, int offset, int length,
		String text, AttributeSet attrs) throws BadLocationException {
		Document doc = fb.getDocument();
		int currentLength = doc.getLength();
		String currentContent = doc.getText(0, currentLength);
		String before = currentContent.substring(0, offset);
		String after = currentContent.substring(length + offset, currentLength);
		String newValue = before + (text == null ? "" : text) + after;
		//currentValue =
		checkInput(newValue, offset);
		fb.replace(offset, length, text, attrs);
	}

	private static int checkInput(String proposedValue, int offset)
		throws BadLocationException {
		int newValue = 0;
		if (proposedValue.length() > 0) {
			try {
				newValue = Integer.parseInt(proposedValue);
			} catch (NumberFormatException e) {
				throw new BadLocationException(proposedValue, offset);
			}
		}
		return newValue;
	}
}
