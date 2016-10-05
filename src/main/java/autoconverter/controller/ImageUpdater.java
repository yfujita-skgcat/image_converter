/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package autoconverter.controller;

import autoconverter.model.ImageSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * image update thread.
 * @author yfujita
 */
public class ImageUpdater extends Thread {
  private ArrayList<ImageSet> imageList; // imagelist

  public ImageUpdater(ArrayList<ImageSet> _image){
    imageList = _image;
    if(imageList == null){
      imageList = new ArrayList<ImageSet>();
    }
  }

  public void run() {
    while(true){
      try {
        synchronized(this){
          this.wait();
        }
      } catch (InterruptedException ex) {
        JOptionPane.showConfirmDialog(null, ex.toString(), "Unexpected interupted!", JOptionPane.ERROR_MESSAGE);
      }
      // update images
    }
  }

}
