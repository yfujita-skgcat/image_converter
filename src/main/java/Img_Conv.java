
import autoconverter.controller.ApplicationController;
import autoconverter.controller.AutoConverterUtils;
import autoconverter.controller.ShutdownHook;
import autoconverter.view.BaseFrame;
import ij.IJ;
import ij.plugin.PlugIn;
import java.util.Locale;
import java.util.ResourceBundle;

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
                //java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("autoconverter/controller/Bundle"); // NOI18N
		//IJ.showMessage("This is a test imagej plugin.");
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		Locale.setDefault(Locale.ENGLISH);
		BaseFrame ac = new BaseFrame();
		ac.setTitle(AutoConverterUtils.getVersion());
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				//new BaseFrame().setVisible(true);
				ac.setVisible(true);
			}
		});
		// 終了時に保存する場合はこれを有効にする.
		//Thread th = new Thread(new ShutdownHook());
		//Runtime.getRuntime().addShutdownHook(th);
		
	}

	public static void main(final String... args){
		new ij.ImageJ();
		new Img_Conv().run("");


	}
	
}
