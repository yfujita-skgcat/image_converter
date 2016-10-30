/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoconverter.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;

/**
 *
 * @author yfujita
 */
public class IntegerVerifier extends InputVerifier {

	@Override
	public boolean verify(JComponent jc) {
		JFormattedTextField jft = (JFormattedTextField) jc;
		String s = jft.getText();
		int val = -1;
		try{
			val = Integer.parseInt(s);
		} catch (NumberFormatException e){
			jft.setText("0");
			return false;
		}
		if(val < 0){
			jft.setText("0");
			return false;
		}
		return true;
	}

	/*
	@Override
	public void actionPerformed(ActionEvent ae) {
		JFormattedTextField textf = (JFormattedTextField ) ae.getSource();
		verify(textf);
		textf.selectAll();
	}
*/
}
