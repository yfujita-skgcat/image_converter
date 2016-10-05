/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ImagePanel.java
 *
 * Created on 2009/12/13, 19:38:12
 */
package autoconverter.view;

import ij.ImagePlus;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Scrollable;

/**
 *
 * @author yfujita
 */
public class ImagePanel extends javax.swing.JPanel {
  private ImagePlus imp;
  private Image img;

  /** Creates new form ImagePanel */
  public ImagePanel() {
    initComponents();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if(imp != null){
      g.drawImage(imp.getImage(), 0, 0, this);
    }
  }

  /**
   * 画像をセットする.
   * @param _imp
   */
  public synchronized void setImp(ImagePlus _imp) {
    imp = _imp;
    this.setPreferredSize(new Dimension(imp.getWidth(), imp.getHeight()));
    //this.invalidate();
    this.validate();

    //img.getWidth();
    //ImageIcon icon = new ImageIcon(imp.getImage());
    //this.add(new JLabel(icon), BorderLayout.CENTER);
    this.repaint();
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                setLayout(new java.awt.BorderLayout());
        }// </editor-fold>//GEN-END:initComponents

        // Variables declaration - do not modify//GEN-BEGIN:variables
        // End of variables declaration//GEN-END:variables
}
