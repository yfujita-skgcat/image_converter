
import autoconverter.view.BaseFrame;
import ij.IJ;
import ij.plugin.PlugIn;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author yfujita
 */
public class Img_Conv implements PlugIn {

	@Override
	public void run(String string) {
		IJ.showMessage("This is a test imagej plugin.");
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		BaseFrame ac = new BaseFrame();
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new BaseFrame().setVisible(true);
			}
		});
		
	}

	public static void main(final String... args){
		new ij.ImageJ();
		new Img_Conv().run("");


	}
	
}
