/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package autoconverter.view.range;

import autoconverter.controller.AutoConverterUtils;
import java.util.logging.Logger;
import javax.swing.JSlider;

/**
 *
 * @author yfujita
 */
public class RangeSlider extends JSlider {

	private static final Logger logger = AutoConverterUtils.getLogger();

	/**
	 * Constructs a RangeSlider with default minimum and maximum values of 0 and
	 * 100.
	 */
	public RangeSlider() {
		initSlider();
	}

	/**
	 * Constructs a RangeSlider with the specified default minimum and maximum
	 * values.
	 */
	public RangeSlider(int min, int max) {
		super(min, max);
		initSlider();
	}

	/**
	 * Initializes the slider by setting default properties.
	 */
	private void initSlider() {
		setOrientation(HORIZONTAL);
	}

	/**
	 * Overrides the superclass method to install the UI delegate to draw two
	 * thumbs.
	 */
	@Override
	public void updateUI() {
		setUI(new RangeSliderUI(this));
		// Update UI for slider labels.  This must be called after updating the
		// UI of the slider.  Refer to JSlider.updateUI().
		updateLabelUIs();
	}

	/**
	 * Returns the lower value in the range.
	 */
	@Override
	public int getValue() {
		return super.getValue();
	}


	/**
	 * 最大値と最小値を同時に設定する.
	 * @param min
	 * @param max 
	 */
	public void setMinAndMax(int min, int max){
		// 基本 getMinimum() < min < max < getMaximum()であればOK.
		if( min > max){
			logger.warning("min > max");
			int tmp;
			tmp = max;
			max = min;
			min = tmp;
		}
		int oldMin    = getValue();
		int oldExtent = getExtent();
		int oldMax    = oldMin + oldExtent;
		if( oldMin == min && oldMax == max){
			return;
		}
		min = Math.max(getMinimum(), min);
		max = Math.min(getMaximum(), max);

		getModel().setRangeProperties(min, max - min, getMinimum(),
						getMaximum(), getValueIsAdjusting());
	}

	/**
	 * Sets the lower value in the range.
	 */
	@Override
	public void setValue(int value) {
		int oldValue = getValue();
		if (oldValue == value) {
			return;
		}

		// Compute new value and extent to maintain upper value.
		int oldExtent = getExtent();
		//int newValue = Math.min(Math.max(getMinimum(), value), oldValue + oldExtent);
		int newValue = Math.min(Math.max(getMinimum(), value), oldValue + oldExtent - 1);
		int newExtent = oldExtent + oldValue - newValue; // max - min ということ

		// Set new value and extent, and fire a single change event.
		getModel().setRangeProperties(newValue, newExtent, getMinimum(),
						getMaximum(), getValueIsAdjusting());
	}
	/**
	 * setValue(int value)の別名
	 */
	public void setLowerValue(int value){
		setValue(value);
	}

	/**
	 * Returns the upper value in the range.
	 */
	public int getUpperValue() {
		return getValue() + getExtent();
	}

	/**
	 * Sets the upper value in the range.
	 */
	public void setUpperValue(int value) {
		// Compute new extent.
		int lowerValue = getValue();
		int newExtent = Math.min(Math.max(0, value - lowerValue), getMaximum() - lowerValue);

		/*
		 * Add for non-zero newExtent
		 */
		if( newExtent == 0){
			newExtent = 1;
		}

		// Set extent to set upper value.
		setExtent(newExtent);
	}

	/**
	 * getUpperValue()の別名. 高い方の値を返す.
	 * @return 
	 */
	public int getHighValue() {
		return getUpperValue();
	}

	/**
	 * getValue() の別名. 低い方の値を返す.
	 * @return 
	 */
	public int getLowValue() {
		return getValue();
	}
}
