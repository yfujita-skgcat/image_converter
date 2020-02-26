/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package autoconverter.controller;

import ij.ImagePlus;
import java.awt.CardLayout;
import java.util.HashSet;
import autoconverter.model.ImageSet;

/**
 * GUIのMediator
 *
 * @author yfujita
 */
public interface ApplicationMediator {

	/**
	 * 1 shotを選択する.
	 *
	 * @param _imp
	 */
	public abstract void setImageSet(ImageSet _imp);

	/**
	 * ImageSetの中から次の画像を表示する.
	 */
	public abstract void nextImage();

	/**
	 * ImageSetの中から前の画像を表示する.
	 */
	public abstract void previousImage();

	/**
	 * 現在のカードオブジェクトを取得する
	 */
	public abstract CardLayout getCardLayout();

	/**
	 * 次のカードを表示する
	 */
	public abstract void nextCard();

	/**
	 * 前のカードを表示する
	 */
	public abstract void previousCard();

	/**
	 * カードindexを返す
	 *
	 * @return
	 */
	public abstract int getCardIndex();

	/**
	 * カードの枚数を返す
	 *
	 * @return
	 */
	public abstract int getCardSize();

	/**
	 * 画像ファイルのリストを取得する
	 *
	 * @return
	 */
	public abstract boolean startSearchFileList();


	/**
	 * WizerdのNext, Back, Cancelのボタンの有効、無効をアップデートする.
	 */
	public abstract void updateWizerdButton();

	/**
	 * メッセージのリストを取得する
	 *
	 * @return
	 */
	public abstract HashSet<String> getMessageList();

	/**
	 * スケールの最大値と最小値のspinnerの値をセットする.
	 *
	 * @param min
	 * @param max
	 */
	public abstract void setScaleValues(int min, int max);

	/**
	 * スケールの最大値のspinnerの値をセットする.
	 *
	 * @param max
	 */
	public abstract void setScaleMaxValues(int max);

	/**
	 * maxSpinner の最小値を変更する.
	 *
	 * @param min
	 */
	public abstract void setMaxSpinnerMin(int min);

	/**
	 * minSpinner の最大値を変更する.
	 *
	 * @param max
	 */
	public abstract void setMinSpinnerMax(int max);

	/**
	 * 輝度分布の描画を更新する.
	 */
	public abstract void updateDensityPlot();

	/**
	 *  minSpinner の現在値を取得する.
	 */
	public abstract int getMinSpinnerValue();
	/**
	 * maxSpinner の現在値を取得する.
	 * @return 
	 */
	public abstract int getMaxSpinnerValue();

	/**
	 * 現在のフィルタ設定を保存する.
	 */
	public abstract void collectParams();
	//public abstract void storeCurrentFilterSettings();

	public abstract void setAutoSelected(boolean auto);

}
