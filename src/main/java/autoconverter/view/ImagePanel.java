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

import autoconverter.controller.ApplicationController;
import autoconverter.controller.AutoConverterConfig;
import autoconverter.controller.AutoConverterUtils;
import ij.IJ;
import ij.ImagePlus;
import ij.gui.Roi;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;
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
	private Point start;
	private Point end;
	private boolean dragF;
	private Rectangle rect;
	private int left_top_x;
	private int left_top_y;
	private int height;
	private int width;
	private final ApplicationController appCtrl;
	private static final Logger logger = AutoConverterUtils.getLogger();

	/**
	 * 最後に選択した左上のx座標
	 * @return
	 */
	public int getLeftTopX(){
		return left_top_x;
	}
	public void setLeftTopX(int v){
		this.left_top_x = v;
		this.storeCropAreaToHash();
		rect.setBounds(left_top_x, left_top_y, width, height);
	}

	/**
	 * 最後に選択した左上のy座標
	 * @return
	 */
	public int getLeftTopY(){
		return left_top_y;
	}
	public void setLeftTopY(int v){
		this.left_top_y = v;
		this.storeCropAreaToHash();
		rect.setBounds(left_top_x, left_top_y, width, height);
	}

	/**
	 * 最後に選択した四角の高さ
	 * @return
	 */
	public int getRoiHeight(){
		return height;
	}
	public void setRoiHeight(int v){
		this.height= v;
		this.storeCropAreaToHash();
		rect.setBounds(left_top_x, left_top_y, width, height);
	}

	/**
	 * 最後に選択した四角の幅
	 * @return
	 */
	public int getRoiWidth(){
		return width;
	}
	public void setRoiWidth(int v){
		this.width = v;
		this.storeCropAreaToHash();
		rect.setBounds(left_top_x, left_top_y, width, height);
	}

	/**
	 * Creates new form ImagePanel
	 */
	public ImagePanel() {
		initComponents();
		start = new Point();
		end = new Point();
		dragF = false;
		left_top_x = AutoConverterConfig.getConfig(AutoConverterConfig.KEY_CROP_AREA_X, 0);
		left_top_y = AutoConverterConfig.getConfig(AutoConverterConfig.KEY_CROP_AREA_Y, 0);
		height     = AutoConverterConfig.getConfig(AutoConverterConfig.KEY_CROP_AREA_H, 0);
		width      = AutoConverterConfig.getConfig(AutoConverterConfig.KEY_CROP_AREA_W, 0);
		rect = new Rectangle(left_top_x, left_top_y, width, height);
		appCtrl = ApplicationController.getInstance();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (imp != null) {
			//if( appCtrl.getImageMode() == BaseFrame.IMAGE_MODE_THRESHOLD){
			//	IJ.run(imp, "Convert to Mask", "");
			//}
			//imp.draw();
			g.drawImage(imp.getImage(), 0, 0, this);
			imp.close();
			/*
			Roi roi = imp.getRoi();
			if(roi != null){
				logger.fine("drawing roi");
				g.drawPolygon(roi.getPolygon());
			} else {
				logger.fine("roi == null" );
			}
			*/
		}
		if(rect.width > 0 && rect.height > 0){
			g.setColor(Color.YELLOW);
			g.drawRect(rect.x, rect.y, rect.width, rect.height);
		}
	}

	/**
	 * 囲まれた領域があるかどうかを返す.
	 * @return 
	 */
	public boolean isSelected(){
		if(width != 0 && height != 0 ){
			return true;
		} else {
			return false;
		}
	}

	public void setStart(int x, int y) {
		start.setLocation(x, y);
		this.appCtrl.setCropPanel(x, y, 0, 0);
		this.setDragged(true);
	}

	public void setNow(int x, int y) {
		if (start.x > x) {
			left_top_x = x;
			width = start.x - x;
		} else {
			left_top_x = start.x;
			width = x - start.x;
		}
		if (start.y > y) {
			left_top_y = y;
			height = start.y - y;
		} else {
			left_top_y = start.y;
			height = y - start.y;
		}
		rect.setBounds(left_top_x, left_top_y, width, height);
		this.appCtrl.setCropPanel(left_top_x, left_top_y, width, height);
		repaint();
	}

	public void setEnd(int x, int y) {
		end.setLocation(x, y);
		this.setDragged(false);
		if (start.getX() == end.getX() || start.getY() == end.getY()) {
			left_top_x = 0;
			left_top_y = 0;
			height = 0;
			width = 0;
			rect.setBounds(left_top_x, left_top_y, width, height);
		}
		this.appCtrl.setCropPanel(left_top_x, left_top_y, width, height);
		this.storeCropAreaToHash();
		repaint();

	}

	public void storeCropAreaToHash(){
		AutoConverterConfig.setConfig(AutoConverterConfig.KEY_CROP_AREA_H, height);
		AutoConverterConfig.setConfig(AutoConverterConfig.KEY_CROP_AREA_W, width);
		AutoConverterConfig.setConfig(AutoConverterConfig.KEY_CROP_AREA_X, left_top_x);
		AutoConverterConfig.setConfig(AutoConverterConfig.KEY_CROP_AREA_Y, left_top_y);
	}

	/**
	 * 選択領域を初期化.
	 */
	public void clear(){
		left_top_x = 0;
		left_top_y = 0;
		height = 0;
		width = 0;
		rect.setBounds(left_top_x, left_top_y, width, height);
		this.storeCropAreaToHash();
		repaint();
	}

	public void setDragged(boolean drag) {
		dragF = drag;
	}

	public boolean isDragged() {
		return dragF;
	}


	/**
	 * 画像をセットする.
	 *
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

	public ImagePlus getImp(){
		return imp;
	}

	/**
	 * This method is called from within the constructor to initialize the
	 * form. WARNING: Do NOT modify this code. The content of this method is
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
