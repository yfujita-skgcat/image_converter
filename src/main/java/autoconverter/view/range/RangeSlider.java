/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package autoconverter.view.range;

import javax.swing.JSlider;

/**
 *
 * @author yfujita
 */
public class RangeSlider extends JSlider {

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
		int newExtent = oldExtent + oldValue - newValue;

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
