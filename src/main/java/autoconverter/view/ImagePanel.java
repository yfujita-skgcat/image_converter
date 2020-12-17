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
	private Image cache_img;
	private double cache_scale = 0;
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
	private boolean changeF;

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
	 * 選択範囲の四角の位置を保存しているrectを更新する。数値は描画の絶対位置
	 * @param x
	 * @param y
	 * @param w
	 * @param h 
	 */
	public void setCropRectangleParams(int x, int y, int w, int h){
		if(rect != null){
			rect.setBounds(x, y, w, h);
		} else {
			logger.warning("rect == null");
		}
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
		//AutoConverterUtils.printStackTrace(20, true);
		double scale = 0;
		if ( appCtrl != null) {
			scale = appCtrl.getScaleDouble();
		}
		if (imp != null) {
			if(scale == 1.0){
				//logger.fine("draw normal image");
				g.drawImage(imp.getImage(), 0, 0, this);
			} else {
				//logger.fine("cache_scale=" + cache_scale);
				//logger.fine("scale=" + cache_scale);
				//logger.fine("cache_img=" + cache_img);
				//logger.fine("isDragged()" + this.isDragged());
				if( cache_scale == scale && cache_img != null && ( this.isDragged() || !this.isScaleChanged()) ){ // drag 中は cache を使う
					g.drawImage(cache_img, 0, 0, this);
				} else {
					if( this.isScaleChanged() ){
						double scaled_width = imp.getWidth() * scale;
						logger.fine("scaled_width=" + scaled_width);
						Image scaled_image = imp.getImage().getScaledInstance((int) scaled_width, -1, Image.SCALE_SMOOTH);
						g.drawImage(scaled_image, 0, 0, this);
						cache_img = scaled_image;
						cache_scale = scale;
						this.setScaleChanged(false);
					}
				}
			}
			imp.close();
		}
		//this.getParent().validate();
		if(rect.width > 0 && rect.height > 0){
			g.setColor(Color.YELLOW);
			g.drawRect(rect.x, rect.y, rect.width, rect.height);
		}
	}

	@Override
	public Dimension getPreferredSize(){
		Dimension d = super.getPreferredSize();
		if( imp == null ){
			return d;
		}
		double scale = appCtrl.getScaleDouble();
		int w = (int) (imp.getWidth() * scale);
		int h = (int) (imp.getHeight() * scale);
		int bfw = appCtrl.getBaseFrame().getWidth();
		int bfh = appCtrl.getBaseFrame().getHeight();
		if( w < bfw + 200 ){
			w = bfw + 200;
		}
		if( h < bfh + 200){
			h = bfh + 200;
		}
		//logger.fine("pref size = " + w + ", " + h) ;
		return new Dimension(w, h);
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
		this.setDragged(true);
		start.setLocation(x, y);
		//this.appCtrl.setCropPanel(x, y, 0, 0);
		double scale = appCtrl.getScaleDouble();
		this.appCtrl.setCropPanel((int)(x/scale), (int)(y/scale), 0, 0);
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
		double scale = appCtrl.getScaleDouble();
		logger.fine("scale=" + scale);
		logger.fine("left_top_x=" + left_top_x);
		logger.fine("left_top_x/scale=" + left_top_x/scale);
		logger.fine("(int) left_top_x/scale=" + (int)(left_top_x/scale));
		//this.appCtrl.setCropPanel(left_top_x, left_top_y, width, height);
		this.appCtrl.setCropPanel((int) (left_top_x/scale), (int) (left_top_y/scale), (int) (width/scale), (int) (height/scale));
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
		//this.appCtrl.setCropPanel(left_top_x, left_top_y, width, height);
		double scale = appCtrl.getScaleDouble();
		this.appCtrl.setCropPanel((int)(left_top_x/scale), (int)(left_top_y/scale), (int) (width/scale), (int) (height/scale));
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

	public void setScaleChanged(boolean change){
		changeF = change;
	}

	public boolean isScaleChanged(){
		return changeF;
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

	public void resetCache(){
		this.cache_img = null;
		this.cache_scale = 0;
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
