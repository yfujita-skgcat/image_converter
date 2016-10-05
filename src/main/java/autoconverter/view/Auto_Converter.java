package autoconverter.view;




import autoconverter.view.BaseFrame;
import autoconverter.*;
import ij.plugin.PlugIn;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author yfujita
 */
public class Auto_Converter implements PlugIn {
  //private AutoConverter ac;
  private BaseFrame ac;

  public void run(String arg) {
    //ac = new AutoConverter();
    ac = new BaseFrame();
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        new BaseFrame().setVisible(true);
      }
    });
  }

  public static void main(String[] args){
    Auto_Converter ac2 = new Auto_Converter();
    ac2.run("");
  }
}
