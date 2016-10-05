/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package autoconverter.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JProgressBar;

/**
 *
 * @author yfujita
 */
public class ProgressListener implements PropertyChangeListener {
  private final JProgressBar bar;

  public ProgressListener(JProgressBar _bar){
    bar = _bar;
  }

  public void propertyChange(PropertyChangeEvent evt) {
    String propertyName = evt.getPropertyName();
    if("progress".equals(propertyName)){
      //bar.setStringPainted(true);
      //bar.setIndeterminate(true);
      int progress = (Integer) evt.getNewValue();
      bar.setValue(progress);
    }
  }
}
