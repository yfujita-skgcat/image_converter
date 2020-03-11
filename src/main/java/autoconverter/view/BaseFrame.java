/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */

 /*
* baseFrame.java
*
* Created on 2009/08/22, 15:33:31
 */
package autoconverter.view;

import autoconverter.controller.IntegerVerifier;
import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import autoconverter.controller.AutoConverterConfig;
import autoconverter.controller.AutoConverterUtils;
import autoconverter.controller.ApplicationController;
import autoconverter.view.range.RangeSlider;
import java.awt.event.ItemEvent;
import java.time.Instant;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JSpinner;

/**
 *
 * @author yfujita
 */
public class BaseFrame extends javax.swing.JFrame {

	/**
	 * @return the jCheckBoxFilter1
	 */
	public javax.swing.JCheckBox getjCheckBoxFilter1() {
		return jCheckBoxFilter1;
	}

	/**
	 * @param jCheckBoxFilter1 the jCheckBoxFilter1 to set
	 */
	public void setjCheckBoxFilter1(javax.swing.JCheckBox jCheckBoxFilter1) {
		this.jCheckBoxFilter1 = jCheckBoxFilter1;
	}

	/**
	 * @return the jCheckBoxFilter2
	 */
	public javax.swing.JCheckBox getjCheckBoxFilter2() {
		return jCheckBoxFilter2;
	}

	/**
	 * @param jCheckBoxFilter2 the jCheckBoxFilter2 to set
	 */
	public void setjCheckBoxFilter2(javax.swing.JCheckBox jCheckBoxFilter2) {
		this.jCheckBoxFilter2 = jCheckBoxFilter2;
	}

	/**
	 * @return the jCheckBoxFilter3
	 */
	public javax.swing.JCheckBox getjCheckBoxFilter3() {
		return jCheckBoxFilter3;
	}

	/**
	 * @param jCheckBoxFilter3 the jCheckBoxFilter3 to set
	 */
	public void setjCheckBoxFilter3(javax.swing.JCheckBox jCheckBoxFilter3) {
		this.jCheckBoxFilter3 = jCheckBoxFilter3;
	}

	/**
	 * @return the jCheckBoxFilter4
	 */
	public javax.swing.JCheckBox getjCheckBoxFilter4() {
		return jCheckBoxFilter4;
	}

	/**
	 * @param jCheckBoxFilter4 the jCheckBoxFilter4 to set
	 */
	public void setjCheckBoxFilter4(javax.swing.JCheckBox jCheckBoxFilter4) {
		this.jCheckBoxFilter4 = jCheckBoxFilter4;
	}

	/**
	 * @return the jCheckBoxFilter5
	 */
	public javax.swing.JCheckBox getjCheckBoxFilter5() {
		return jCheckBoxFilter5;
	}

	/**
	 * @param jCheckBoxFilter5 the jCheckBoxFilter5 to set
	 */
	public void setjCheckBoxFilter5(javax.swing.JCheckBox jCheckBoxFilter5) {
		this.jCheckBoxFilter5 = jCheckBoxFilter5;
	}

	/**
	 * @return the jCheckBoxFilter6
	 */
	public javax.swing.JCheckBox getjCheckBoxFilter6() {
		return jCheckBoxFilter6;
	}

	/**
	 * @param jCheckBoxFilter6 the jCheckBoxFilter6 to set
	 */
	public void setjCheckBoxFilter6(javax.swing.JCheckBox jCheckBoxFilter6) {
		this.jCheckBoxFilter6 = jCheckBoxFilter6;
	}

	/**
	 * @return the jCheckBoxFilter7
	 */
	public javax.swing.JCheckBox getjCheckBoxFilter7() {
		return jCheckBoxFilter7;
	}

	/**
	 * @param jCheckBoxFilter7 the jCheckBoxFilter7 to set
	 */
	public void setjCheckBoxFilter7(javax.swing.JCheckBox jCheckBoxFilter7) {
		this.jCheckBoxFilter7 = jCheckBoxFilter7;
	}

	/**
	 * @return the jCheckBoxVisible1
	 */
	public javax.swing.JCheckBox getjCheckBoxVisible1() {
		return jCheckBoxTarget1;
	}

	/**
	 * @param jCheckBoxVisible1 the jCheckBoxVisible1 to set
	 */
	public void setjCheckBoxVisible1(javax.swing.JCheckBox jCheckBoxVisible1) {
		this.jCheckBoxTarget1 = jCheckBoxVisible1;
	}

	/**
	 * @return the jCheckBoxVisible2
	 */
	public javax.swing.JCheckBox getjCheckBoxVisible2() {
		return jCheckBoxTarget2;
	}

	/**
	 * @param jCheckBoxVisible2 the jCheckBoxVisible2 to set
	 */
	public void setjCheckBoxVisible2(javax.swing.JCheckBox jCheckBoxVisible2) {
		this.jCheckBoxTarget2 = jCheckBoxVisible2;
	}

	/**
	 * @return the jCheckBoxVisible3
	 */
	public javax.swing.JCheckBox getjCheckBoxVisible3() {
		return jCheckBoxTarget3;
	}

	/**
	 * @param jCheckBoxVisible3 the jCheckBoxVisible3 to set
	 */
	public void setjCheckBoxVisible3(javax.swing.JCheckBox jCheckBoxVisible3) {
		this.jCheckBoxTarget3 = jCheckBoxVisible3;
	}

	/**
	 * @return the jCheckBoxVisible4
	 */
	public javax.swing.JCheckBox getjCheckBoxVisible4() {
		return jCheckBoxTarget4;
	}

	/**
	 * @param jCheckBoxVisible4 the jCheckBoxVisible4 to set
	 */
	public void setjCheckBoxVisible4(javax.swing.JCheckBox jCheckBoxVisible4) {
		this.jCheckBoxTarget4 = jCheckBoxVisible4;
	}

	/**
	 * @return the jCheckBoxVisible5
	 */
	public javax.swing.JCheckBox getjCheckBoxVisible5() {
		return jCheckBoxTarget5;
	}

	/**
	 * @param jCheckBoxVisible5 the jCheckBoxVisible5 to set
	 */
	public void setjCheckBoxVisible5(javax.swing.JCheckBox jCheckBoxVisible5) {
		this.jCheckBoxTarget5 = jCheckBoxVisible5;
	}

	/**
	 * @return the jCheckBoxVisible6
	 */
	public javax.swing.JCheckBox getjCheckBoxVisible6() {
		return jCheckBoxTarget6;
	}

	/**
	 * @param jCheckBoxVisible6 the jCheckBoxVisible6 to set
	 */
	public void setjCheckBoxVisible6(javax.swing.JCheckBox jCheckBoxVisible6) {
		this.jCheckBoxTarget6 = jCheckBoxVisible6;
	}

	/**
	 * @return the jCheckBoxVisible7
	 */
	public javax.swing.JCheckBox getjCheckBoxVisible7() {
		return jCheckBoxTarget7;
	}

	/**
	 * @param jCheckBoxVisible7 the jCheckBoxVisible7 to set
	 */
	public void setjCheckBoxVisible7(javax.swing.JCheckBox jCheckBoxVisible7) {
		this.jCheckBoxTarget7 = jCheckBoxVisible7;
	}

	private static Logger logger = AutoConverterUtils.getLogger();
	public static final int MAX_CARD_SIZE = 3;
	private boolean active = true;
	private int active_stack = 1;
	private final IntegerVerifier inputverifier;
	private final ApplicationController appController;
	public static final int IMAGE_MODE_SINGLE = 1;
	public static final int IMAGE_MODE_MERGE  = 2;
	public static final int IMAGE_MODE_THRESHOLD = 3;
	public static final int IMAGE_MODE_RELATIVE = 4;
	private ArrayList<JCheckBox> filterCheckBoxList;
	private ArrayList<JCheckBox> targetCheckBoxList;
	private ArrayList<JCheckBox> referenceCheckBoxList;
	private Object lastObj = null; // 最後にイベントから呼ばれたオブジェクト
	//private Instant timer; // 最後にイベントから呼ばれた時間
	private long timer = 0; // 最後にイベントから呼ばれた時間
	private static final long inactive_time = 500; // 連続で同じイベントが来たときに無視する時間

	public void enableListener(boolean flag) {
	// enable するとactive状態になる. < 1 の時はListenerが動かない
		if (flag) {
			active_stack++;
		} else {
			active_stack--;
		}
		if (active_stack > 0) {
			active = true;
		} else {
			active = false;
		}
	//logger.fine("active_stack = " + active_stack);
	}

	/**
	 * @return the logger
	 */
	public static Logger getLogger() {
		return logger;
	}

	/**
	 * Creates new form baseFrame
	 */
	public BaseFrame() {
		try {
			AutoConverterConfig.load();
		} catch (FileNotFoundException ex) {
			Logger.getLogger(BaseFrame.class.getName()).log(Level.SEVERE, null, ex);
		}
		Integer _width = Integer.valueOf(AutoConverterConfig.getConfig(AutoConverterConfig.KEY_MAIN_FRAME_SIZE_X, "400", null));
		Integer _height = Integer.valueOf(AutoConverterConfig.getConfig(AutoConverterConfig.KEY_MAIN_FRAME_SIZE_Y, "300", null));

		this.appController = new ApplicationController(this);
// input verifier
		inputverifier = new IntegerVerifier();

		initComponents();

		// checkbox をまとめておく
		this.filterCheckBoxList = new ArrayList();
		this.targetCheckBoxList = new ArrayList();
		this.referenceCheckBoxList = new ArrayList();
		this.filterCheckBoxList.add(jCheckBoxFilter1);
		this.filterCheckBoxList.add(jCheckBoxFilter2);
		this.filterCheckBoxList.add(jCheckBoxFilter3);
		this.filterCheckBoxList.add(jCheckBoxFilter4);
		this.filterCheckBoxList.add(jCheckBoxFilter5);
		this.filterCheckBoxList.add(jCheckBoxFilter6);
		this.filterCheckBoxList.add(jCheckBoxFilter7);
		this.targetCheckBoxList.add(jCheckBoxTarget1);
		this.targetCheckBoxList.add(jCheckBoxTarget2);
		this.targetCheckBoxList.add(jCheckBoxTarget3);
		this.targetCheckBoxList.add(jCheckBoxTarget4);
		this.targetCheckBoxList.add(jCheckBoxTarget5);
		this.targetCheckBoxList.add(jCheckBoxTarget6);
		this.targetCheckBoxList.add(jCheckBoxTarget7);
		this.referenceCheckBoxList.add(jCheckBoxRef1);
		this.referenceCheckBoxList.add(jCheckBoxRef2);
		this.referenceCheckBoxList.add(jCheckBoxRef3);
		this.referenceCheckBoxList.add(jCheckBoxRef4);
		this.referenceCheckBoxList.add(jCheckBoxRef5);
		this.referenceCheckBoxList.add(jCheckBoxRef6);
		this.referenceCheckBoxList.add(jCheckBoxRef7);


// crop 位置のロード
		this.xTextField.setText(AutoConverterConfig.getConfig(AutoConverterConfig.KEY_CROP_AREA_X, "0", null));
		this.yTextField.setText(AutoConverterConfig.getConfig(AutoConverterConfig.KEY_CROP_AREA_Y, "0", null));
		this.hTextField.setText(AutoConverterConfig.getConfig(AutoConverterConfig.KEY_CROP_AREA_H, "0", null));
		this.wTextField.setText(AutoConverterConfig.getConfig(AutoConverterConfig.KEY_CROP_AREA_W, "0", null));

// crop are text panel 無効化
		this.cropAreaPanel.setVisible(false);

		String selected_item = AutoConverterConfig.getConfig(AutoConverterConfig.KEY_SELECTED_PATTERN, AutoConverterConfig.REGEXP_NAME_CELAVIEW, null);
		this.initFilePatternComboBox(selected_item);

		String _srcDir = AutoConverterConfig.getConfig(AutoConverterConfig.KEY_SOURCE_DIRECTORY, null, null);
		String _dstDir = AutoConverterConfig.getConfig(AutoConverterConfig.KEY_DESTINATION_DIRECTORY, null, null);
		this.sourceText.setText(_srcDir);
		this.destinationText.setText(_dstDir);

		String _recursive = AutoConverterConfig.getConfig(AutoConverterConfig.KEY_RECURSIVE_ON, "true", null);
		if (_recursive.equals("true")) {
			this.recursiveRadioButton.setSelected(true);
		} else {
			this.recursiveRadioButton.setSelected(false);
		}

// special char load
		String _remove_spec_char = AutoConverterConfig.getConfig(AutoConverterConfig.KEY_REMOVE_SPECIAL_CHAR, "true", null);
		if (_remove_spec_char.equals("true")) {
			this.removeSpecialCharRadioButton.setSelected(true);
		} else {
			this.removeSpecialCharRadioButton.setSelected(false);
		}

// add param load
		String _add_param = AutoConverterConfig.getConfig(AutoConverterConfig.KEY_ADD_PARAM_TO_FILENAME, "true", null);
		if (_add_param.equals("true")) {
			this.addParamRadioButton.setSelected(true);
		} else {
			this.addParamRadioButton.setSelected(false);
		}

		String disp_range = AutoConverterConfig.getConfig(AutoConverterConfig.KEY_SELECTED_DISPLAY_RANGE, "4095", null);
		this.displayRangeComboBox.getModel().setSelectedItem(disp_range);

		this.appController.updateWizerdButton();

		this.setSize(new Dimension(_width, _height));
	}

	/**
	 * ファイルパターンを表示するcomboBox 設定.
	 *
	 * @param selected_item 選択するitemを指定する. null の場合は指定しない
	 */
	public void initFilePatternComboBox(String selected_item) {
		ArrayList<String> filePatterns = AutoConverterConfig.getFilePatternNames();
		if (filePatterns.size() > 0) {
			DefaultComboBoxModel itemList = new DefaultComboBoxModel(filePatterns.toArray());
			if (selected_item != null) {
				itemList.setSelectedItem(selected_item);
			}
			String regexString = AutoConverterConfig.getConfig(selected_item, "", AutoConverterConfig.PREFIX_REGEXP);
			if (selected_item.equals(AutoConverterConfig.REGEXP_NAME_CELAVIEW)) {
				regexString = AutoConverterConfig.celaviewRegexpString;
				this.getFilePatternTextField().setEditable(false);
				this.filePatternComboBox.setEditable(false);
			} else if (selected_item.equals(AutoConverterConfig.REGEXP_NAME_INCELL6000)) {
				regexString = AutoConverterConfig.inCell6000RegexpString;
				this.getFilePatternTextField().setEditable(false);
				this.filePatternComboBox.setEditable(false);
			} else if (selected_item.equals(AutoConverterConfig.REGEXP_NAME_IX81)) {
				regexString = AutoConverterConfig.IX81RegexpString;
				this.getFilePatternTextField().setEditable(false);
				this.filePatternComboBox.setEditable(false);
			} else if (selected_item.equals(AutoConverterConfig.REGEXP_NAME_IX81A)) {
				regexString = AutoConverterConfig.IX81RegexString_A;
				this.getFilePatternTextField().setEditable(false);
				this.filePatternComboBox.setEditable(false);
			} else if (selected_item.equals(AutoConverterConfig.REGEXP_NAME_IX81B)) {
				regexString = AutoConverterConfig.IX81RegexString_B;
				this.getFilePatternTextField().setEditable(false);
				this.filePatternComboBox.setEditable(false);
			} else if (selected_item.equals(AutoConverterConfig.REGEXP_NAME_IX81C)) {
				regexString = AutoConverterConfig.IX81RegexString_C;
				this.getFilePatternTextField().setEditable(false);
				this.filePatternComboBox.setEditable(false);
			} else if (selected_item.equals(AutoConverterConfig.REGEXP_NAME_IX81D)) {
				regexString = AutoConverterConfig.IX81RegexString_D;
				this.getFilePatternTextField().setEditable(false);
				this.filePatternComboBox.setEditable(false);
			} else if (selected_item.equals(AutoConverterConfig.REGEXP_NAME_CUSTOM)) {
				regexString = "";
			}
			this.filePatternComboBox.setModel(itemList);
			this.getFilePatternTextField().setText(regexString);
		}
	}

	/**
	 * This method is called from within the constructor to initialize the
	 * form. WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {
                java.awt.GridBagConstraints gridBagConstraints;

                brightnessAutoGroup = new javax.swing.ButtonGroup();
                targetButtonGroup = new javax.swing.ButtonGroup();
                referenceButtonGroup = new javax.swing.ButtonGroup();
                centerPanel = new javax.swing.JPanel();
                slideScrollPane1 = new javax.swing.JScrollPane();
                slide1 = new javax.swing.JPanel();
                jScrollPane1 = new javax.swing.JScrollPane();
                fileSearchLogTextArea = new javax.swing.JTextArea();
                sourcePanel = new javax.swing.JPanel();
                sourceText = new javax.swing.JTextField();
                sourceButton = new javax.swing.JButton();
                destinationPanel = new javax.swing.JPanel();
                destinationText = new javax.swing.JTextField();
                destinationButton = new javax.swing.JButton();
                checkBoxPanel = new javax.swing.JPanel();
                recursiveRadioButton = new javax.swing.JRadioButton();
                removeSpecialCharRadioButton = new javax.swing.JRadioButton();
                addParamRadioButton = new javax.swing.JRadioButton();
                regexPanel = new javax.swing.JPanel();
                filePatternComboBox = new javax.swing.JComboBox<>();
                filePatternTextField = new javax.swing.JTextField();
                paramPanel = new javax.swing.JPanel();
                convertLabel = new javax.swing.JLabel();
                imageFormatComboBox = new javax.swing.JComboBox();
                displayRangeLabel = new javax.swing.JLabel();
                displayRangeComboBox = new javax.swing.JComboBox<>();
                resizeRadioButton = new javax.swing.JCheckBox();
                resizeSpinner = new javax.swing.JSpinner();
                slideScrollPane2 = new javax.swing.JScrollPane();
                slide2 = new javax.swing.JPanel();
                imageScrollPane = new javax.swing.JScrollPane();
                imagePanel = new autoconverter.view.ImagePanel();
                imageChangePanel = new javax.swing.JPanel();
                dirSelectCBox = new javax.swing.JComboBox();
                wellSelectCBox = new javax.swing.JComboBox();
                positionSelectCBox = new javax.swing.JComboBox();
                timeSelectCBox = new javax.swing.JComboBox();
                zSelectCBox = new javax.swing.JComboBox();
                imagePropertyPanel = new javax.swing.JPanel();
                colorSelectScrollPane = new javax.swing.JScrollPane();
                colorSelectPanel = new javax.swing.JPanel();
                modeSelector = new javax.swing.JComboBox();
                colorChannelSelector = new javax.swing.JComboBox();
                jCheckBoxFilter1 = new javax.swing.JCheckBox();
                jCheckBoxFilter2 = new javax.swing.JCheckBox();
                jCheckBoxFilter3 = new javax.swing.JCheckBox();
                jCheckBoxFilter4 = new javax.swing.JCheckBox();
                jCheckBoxFilter5 = new javax.swing.JCheckBox();
                jCheckBoxFilter6 = new javax.swing.JCheckBox();
                jCheckBoxFilter7 = new javax.swing.JCheckBox();
                jCheckBoxTarget1 = new javax.swing.JCheckBox();
                jCheckBoxTarget2 = new javax.swing.JCheckBox();
                jCheckBoxTarget3 = new javax.swing.JCheckBox();
                jCheckBoxTarget4 = new javax.swing.JCheckBox();
                jCheckBoxTarget5 = new javax.swing.JCheckBox();
                jCheckBoxTarget6 = new javax.swing.JCheckBox();
                jCheckBoxTarget7 = new javax.swing.JCheckBox();
                jLabelVisible = new javax.swing.JLabel();
                jLabelFilter = new javax.swing.JLabel();
                filterSelectCBox = new javax.swing.JComboBox();
                jCheckBoxRef1 = new javax.swing.JCheckBox();
                jCheckBoxRef2 = new javax.swing.JCheckBox();
                jCheckBoxRef3 = new javax.swing.JCheckBox();
                jCheckBoxRef4 = new javax.swing.JCheckBox();
                jCheckBoxRef5 = new javax.swing.JCheckBox();
                jCheckBoxRef6 = new javax.swing.JCheckBox();
                jCheckBoxRef7 = new javax.swing.JCheckBox();
                jLabel1 = new javax.swing.JLabel();
                brightnessPanel = new javax.swing.JPanel();
                scalePanel = new javax.swing.JPanel();
                spinnerPanel = new javax.swing.JPanel();
                minSpinner = new javax.swing.JSpinner();
                autoRadioButton = new javax.swing.JRadioButton();
                manualRadioButton = new javax.swing.JRadioButton();
                adjustButton = new javax.swing.JButton();
                autoTypeComboBox = new javax.swing.JComboBox<>();
                maxSpinner = new javax.swing.JSpinner();
                subtractionPanel = new javax.swing.JPanel();
                subtractLabel = new javax.swing.JLabel();
                ballSizeSpinner = new javax.swing.JSpinner();
                scaleRangeSlider = new autoconverter.view.range.RangeSlider();
                plotPanel = new autoconverter.view.PlotPanel();
                slideScrollPane3 = new javax.swing.JScrollPane();
                slide3 = new javax.swing.JPanel();
                summaryLabel = new javax.swing.JLabel();
                summaryScrollPane = new javax.swing.JScrollPane();
                summaryDisplayArea = new javax.swing.JTextArea();
                southPanel = new javax.swing.JPanel();
                jPanel1 = new javax.swing.JPanel();
                messageLabel = new javax.swing.JLabel();
                cropAreaPanel = new javax.swing.JPanel();
                xTextField = new javax.swing.JFormattedTextField();
                xLabel = new javax.swing.JLabel();
                yLabel = new javax.swing.JLabel();
                wLabel = new javax.swing.JLabel();
                hLabel = new javax.swing.JLabel();
                yTextField = new javax.swing.JFormattedTextField();
                wTextField = new javax.swing.JFormattedTextField();
                hTextField = new javax.swing.JFormattedTextField();
                proceedPanel = new javax.swing.JPanel();
                jPanel2 = new javax.swing.JPanel();
                cancelButton = new javax.swing.JButton();
                exitButton = new javax.swing.JButton();
                cardMovePanel = new javax.swing.JPanel();
                backButton = new javax.swing.JButton();
                nextButton = new javax.swing.JButton();
                convertPanel = new javax.swing.JPanel();
                convertButton = new javax.swing.JButton();
                jMenuBar1 = new javax.swing.JMenuBar();
                fileMenu = new javax.swing.JMenu();

                setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
                setMinimumSize(new java.awt.Dimension(630, 400));
                addComponentListener(new java.awt.event.ComponentAdapter() {
                        public void componentResized(java.awt.event.ComponentEvent evt) {
                                formComponentResized(evt);
                        }
                });
                addWindowListener(new java.awt.event.WindowAdapter() {
                        public void windowClosed(java.awt.event.WindowEvent evt) {
                                formWindowClosed(evt);
                        }
                        public void windowClosing(java.awt.event.WindowEvent evt) {
                                formWindowClosing(evt);
                        }
                });

                centerPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
                centerPanel.setLayout(new java.awt.CardLayout());

                slide1.setPreferredSize(new java.awt.Dimension(450, 300));

                fileSearchLogTextArea.setColumns(20);
                fileSearchLogTextArea.setRows(5);
                java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("autoconverter/controller/Bundle"); // NOI18N
                fileSearchLogTextArea.setBorder(javax.swing.BorderFactory.createTitledBorder(null, bundle.getString("file_search_log"), javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION)); // NOI18N
                jScrollPane1.setViewportView(fileSearchLogTextArea);

                sourcePanel.setLayout(new java.awt.BorderLayout());

                sourceText.setEditable(false);
                sourcePanel.add(sourceText, java.awt.BorderLayout.CENTER);

                sourceButton.setText(bundle.getString("BaseFrame.sourceButton.text")); // NOI18N
                sourceButton.setMaximumSize(new java.awt.Dimension(180, 30));
                sourceButton.setMinimumSize(new java.awt.Dimension(180, 30));
                sourceButton.setPreferredSize(new java.awt.Dimension(180, 30));
                sourceButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                sourceButtonActionPerformed(evt);
                        }
                });
                sourcePanel.add(sourceButton, java.awt.BorderLayout.WEST);

                destinationPanel.setLayout(new java.awt.BorderLayout());

                destinationText.setEditable(false);
                destinationPanel.add(destinationText, java.awt.BorderLayout.CENTER);

                destinationButton.setText(bundle.getString("BaseFrame.text")); // NOI18N
                destinationButton.setMaximumSize(new java.awt.Dimension(180, 30));
                destinationButton.setMinimumSize(new java.awt.Dimension(180, 30));
                destinationButton.setName(""); // NOI18N
                destinationButton.setPreferredSize(new java.awt.Dimension(180, 30));
                destinationButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                destinationButtonActionPerformed(evt);
                        }
                });
                destinationPanel.add(destinationButton, java.awt.BorderLayout.WEST);

                recursiveRadioButton.setText(bundle.getString("BaseFrame.recursiveRadioButton.text")); // NOI18N
                recursiveRadioButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                recursiveRadioButtonActionPerformed(evt);
                        }
                });
                checkBoxPanel.add(recursiveRadioButton);

                removeSpecialCharRadioButton.setText(bundle.getString("BaseFrame.removeSpecialCharRadioButton.text")); // NOI18N
                removeSpecialCharRadioButton.addChangeListener(new javax.swing.event.ChangeListener() {
                        public void stateChanged(javax.swing.event.ChangeEvent evt) {
                                removeSpecialCharRadioButtonStateChanged(evt);
                        }
                });
                checkBoxPanel.add(removeSpecialCharRadioButton);

                addParamRadioButton.setText(bundle.getString("BaseFrame.addParamRadioButton.text")); // NOI18N
                addParamRadioButton.addChangeListener(new javax.swing.event.ChangeListener() {
                        public void stateChanged(javax.swing.event.ChangeEvent evt) {
                                addParamRadioButtonStateChanged(evt);
                        }
                });
                checkBoxPanel.add(addParamRadioButton);

                regexPanel.setLayout(new java.awt.BorderLayout());

                filePatternComboBox.setEditable(true);
                filePatternComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Celaview", "In Cell 6000", "Custom" }));
                filePatternComboBox.addItemListener(new java.awt.event.ItemListener() {
                        public void itemStateChanged(java.awt.event.ItemEvent evt) {
                                filePatternComboBoxItemStateChanged(evt);
                        }
                });
                regexPanel.add(filePatternComboBox, java.awt.BorderLayout.WEST);

                filePatternTextField.setText(bundle.getString("BaseFrame.filePatternTextField.text")); // NOI18N
                regexPanel.add(filePatternTextField, java.awt.BorderLayout.CENTER);

                paramPanel.setMinimumSize(new java.awt.Dimension(639, 35));
                paramPanel.setPreferredSize(new java.awt.Dimension(363, 35));

                convertLabel.setText(bundle.getString("AutoConverter.convertLabel.text")); // NOI18N

                imageFormatComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "jpg", "8bit tiff", "png" }));

                displayRangeLabel.setText(bundle.getString("BaseFrame.displayRangeLabel.text")); // NOI18N

                displayRangeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "255", "4095", "65535" }));
                displayRangeComboBox.setActionCommand(bundle.getString("BaseFrame.displayRangeComboBox.actionCommand")); // NOI18N
                displayRangeComboBox.addItemListener(new java.awt.event.ItemListener() {
                        public void itemStateChanged(java.awt.event.ItemEvent evt) {
                                displayRangeComboBoxItemStateChanged(evt);
                        }
                });

                resizeRadioButton.setText(bundle.getString("BaseFrame.resizeRadioButton.text")); // NOI18N
                resizeRadioButton.setMaximumSize(new java.awt.Dimension(120, 30));
                resizeRadioButton.setMinimumSize(new java.awt.Dimension(120, 30));
                resizeRadioButton.setPreferredSize(new java.awt.Dimension(120, 30));
                resizeRadioButton.addChangeListener(new javax.swing.event.ChangeListener() {
                        public void stateChanged(javax.swing.event.ChangeEvent evt) {
                                resizeRadioButtonStateChanged(evt);
                        }
                });

                resizeSpinner.setEnabled(false);
                resizeSpinner.setMinimumSize(new java.awt.Dimension(88, 25));
                resizeSpinner.setPreferredSize(new java.awt.Dimension(88, 25));

                javax.swing.GroupLayout paramPanelLayout = new javax.swing.GroupLayout(paramPanel);
                paramPanel.setLayout(paramPanelLayout);
                paramPanelLayout.setHorizontalGroup(
                        paramPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(paramPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(convertLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(imageFormatComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(displayRangeLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(displayRangeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(59, 59, 59)
                                .addComponent(resizeRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(resizeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                );
                paramPanelLayout.setVerticalGroup(
                        paramPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(paramPanelLayout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addGroup(paramPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(imageFormatComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(convertLabel)
                                        .addComponent(displayRangeLabel)
                                        .addComponent(displayRangeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(resizeRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(resizeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(5, 5, 5))
                );

                javax.swing.GroupLayout slide1Layout = new javax.swing.GroupLayout(slide1);
                slide1.setLayout(slide1Layout);
                slide1Layout.setHorizontalGroup(
                        slide1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(slide1Layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addGroup(slide1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(sourcePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jScrollPane1)
                                        .addComponent(checkBoxPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(regexPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(destinationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(paramPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1237, Short.MAX_VALUE))
                                .addContainerGap())
                );
                slide1Layout.setVerticalGroup(
                        slide1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(slide1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(checkBoxPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sourcePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(destinationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(paramPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(regexPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
                                .addContainerGap())
                );

                slideScrollPane1.setViewportView(slide1);

                centerPanel.add(slideScrollPane1, "slide1");

                slide2.setPreferredSize(new java.awt.Dimension(450, 300));
                slide2.setLayout(new java.awt.BorderLayout());

                imageScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                imageScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

                imagePanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
                        public void mouseDragged(java.awt.event.MouseEvent evt) {
                                imagePanelMouseDragged(evt);
                        }
                });
                imagePanel.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mousePressed(java.awt.event.MouseEvent evt) {
                                imagePanelMousePressed(evt);
                        }
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                imagePanelMouseReleased(evt);
                        }
                });
                imageScrollPane.setViewportView(imagePanel);

                slide2.add(imageScrollPane, java.awt.BorderLayout.CENTER);

                imageChangePanel.setMaximumSize(new java.awt.Dimension(32767, 40));
                imageChangePanel.setMinimumSize(new java.awt.Dimension(100, 40));
                imageChangePanel.setPreferredSize(new java.awt.Dimension(511, 40));

                dirSelectCBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "FOLDER" }));
                dirSelectCBox.addItemListener(new java.awt.event.ItemListener() {
                        public void itemStateChanged(java.awt.event.ItemEvent evt) {
                                imageSelectorChanged(evt);
                        }
                });
                imageChangePanel.add(dirSelectCBox);

                wellSelectCBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<WELL>" }));
                wellSelectCBox.addItemListener(new java.awt.event.ItemListener() {
                        public void itemStateChanged(java.awt.event.ItemEvent evt) {
                                imageSelectorChanged(evt);
                        }
                });
                imageChangePanel.add(wellSelectCBox);

                positionSelectCBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<POS>" }));
                positionSelectCBox.addItemListener(new java.awt.event.ItemListener() {
                        public void itemStateChanged(java.awt.event.ItemEvent evt) {
                                imageSelectorChanged(evt);
                        }
                });
                imageChangePanel.add(positionSelectCBox);

                timeSelectCBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<TIME>" }));
                timeSelectCBox.addItemListener(new java.awt.event.ItemListener() {
                        public void itemStateChanged(java.awt.event.ItemEvent evt) {
                                imageSelectorChanged(evt);
                        }
                });
                imageChangePanel.add(timeSelectCBox);

                zSelectCBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<ZPOS>" }));
                zSelectCBox.addItemListener(new java.awt.event.ItemListener() {
                        public void itemStateChanged(java.awt.event.ItemEvent evt) {
                                imageSelectorChanged(evt);
                        }
                });
                imageChangePanel.add(zSelectCBox);

                slide2.add(imageChangePanel, java.awt.BorderLayout.SOUTH);

                imagePropertyPanel.setMinimumSize(new java.awt.Dimension(500, 200));
                imagePropertyPanel.setPreferredSize(new java.awt.Dimension(550, 300));
                imagePropertyPanel.setLayout(new java.awt.BorderLayout());

                colorSelectScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                colorSelectScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

                colorSelectPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, bundle.getString("BaseFrame.colorSelectPanel.border.title"), javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION)); // NOI18N
                colorSelectPanel.setLayout(new java.awt.GridBagLayout());

                modeSelector.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Single", "Merge", "Threshold", "Relative" }));
                modeSelector.setPreferredSize(new java.awt.Dimension(150, 30));
                modeSelector.addItemListener(new java.awt.event.ItemListener() {
                        public void itemStateChanged(java.awt.event.ItemEvent evt) {
                                modeSelectorItemStateChanged(evt);
                        }
                });
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 0;
                gridBagConstraints.gridwidth = 3;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
                colorSelectPanel.add(modeSelector, gridBagConstraints);

                colorChannelSelector.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Grays", "Magenta", "Blue", "Cyan", "Green", "Yellow", "Red" }));
                colorChannelSelector.setPreferredSize(new java.awt.Dimension(150, 30));
                colorChannelSelector.addItemListener(new java.awt.event.ItemListener() {
                        public void itemStateChanged(java.awt.event.ItemEvent evt) {
                                colorChannelSelectorItemStateChanged(evt);
                        }
                });
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 1;
                gridBagConstraints.gridwidth = 3;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
                colorSelectPanel.add(colorChannelSelector, gridBagConstraints);

                jCheckBoxFilter1.setText(bundle.getString("BaseFrame.jCheckBoxFilter1.text")); // NOI18N
                jCheckBoxFilter1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
                jCheckBoxFilter1.setBorderPainted(true);
                jCheckBoxFilter1.setEnabled(false);
                jCheckBoxFilter1.setMaximumSize(new java.awt.Dimension(200, 22));
                jCheckBoxFilter1.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jCheckBoxFilter1ActionPerformed(evt);
                        }
                });
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 2;
                gridBagConstraints.gridy = 4;
                gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
                colorSelectPanel.add(jCheckBoxFilter1, gridBagConstraints);
                jCheckBoxFilter1.getAccessibleContext().setAccessibleDescription(bundle.getString("BaseFrame.jCheckBoxFilter1.AccessibleContext.accessibleDescription")); // NOI18N

                jCheckBoxFilter2.setText(bundle.getString("BaseFrame.jCheckBoxFilter2.text")); // NOI18N
                jCheckBoxFilter2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
                jCheckBoxFilter2.setBorderPainted(true);
                jCheckBoxFilter2.setEnabled(false);
                jCheckBoxFilter2.setMaximumSize(new java.awt.Dimension(200, 22));
                jCheckBoxFilter2.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jCheckBoxFilter2ActionPerformed(evt);
                        }
                });
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 2;
                gridBagConstraints.gridy = 5;
                gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
                colorSelectPanel.add(jCheckBoxFilter2, gridBagConstraints);

                jCheckBoxFilter3.setText(bundle.getString("BaseFrame.jCheckBoxFilter3.text")); // NOI18N
                jCheckBoxFilter3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
                jCheckBoxFilter3.setBorderPainted(true);
                jCheckBoxFilter3.setEnabled(false);
                jCheckBoxFilter3.setMaximumSize(new java.awt.Dimension(200, 22));
                jCheckBoxFilter3.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jCheckBoxFilter3ActionPerformed(evt);
                        }
                });
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 2;
                gridBagConstraints.gridy = 6;
                gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
                colorSelectPanel.add(jCheckBoxFilter3, gridBagConstraints);

                jCheckBoxFilter4.setText(bundle.getString("BaseFrame.jCheckBoxFilter4.text")); // NOI18N
                jCheckBoxFilter4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
                jCheckBoxFilter4.setBorderPainted(true);
                jCheckBoxFilter4.setEnabled(false);
                jCheckBoxFilter4.setMaximumSize(new java.awt.Dimension(200, 22));
                jCheckBoxFilter4.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jCheckBoxFilter4ActionPerformed(evt);
                        }
                });
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 2;
                gridBagConstraints.gridy = 7;
                gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
                colorSelectPanel.add(jCheckBoxFilter4, gridBagConstraints);

                jCheckBoxFilter5.setText(bundle.getString("BaseFrame.jCheckBoxFilter5.text")); // NOI18N
                jCheckBoxFilter5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
                jCheckBoxFilter5.setBorderPainted(true);
                jCheckBoxFilter5.setEnabled(false);
                jCheckBoxFilter5.setMaximumSize(new java.awt.Dimension(200, 22));
                jCheckBoxFilter5.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jCheckBoxFilter5ActionPerformed(evt);
                        }
                });
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 2;
                gridBagConstraints.gridy = 8;
                gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
                colorSelectPanel.add(jCheckBoxFilter5, gridBagConstraints);

                jCheckBoxFilter6.setText(bundle.getString("BaseFrame.jCheckBoxFilter6.text")); // NOI18N
                jCheckBoxFilter6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
                jCheckBoxFilter6.setBorderPainted(true);
                jCheckBoxFilter6.setEnabled(false);
                jCheckBoxFilter6.setMaximumSize(new java.awt.Dimension(200, 22));
                jCheckBoxFilter6.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jCheckBoxFilter6ActionPerformed(evt);
                        }
                });
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 2;
                gridBagConstraints.gridy = 9;
                gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
                colorSelectPanel.add(jCheckBoxFilter6, gridBagConstraints);

                jCheckBoxFilter7.setText(bundle.getString("BaseFrame.jCheckBoxFilter7.text")); // NOI18N
                jCheckBoxFilter7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
                jCheckBoxFilter7.setBorderPainted(true);
                jCheckBoxFilter7.setEnabled(false);
                jCheckBoxFilter7.setMaximumSize(new java.awt.Dimension(200, 22));
                jCheckBoxFilter7.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jCheckBoxFilter7ActionPerformed(evt);
                        }
                });
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 2;
                gridBagConstraints.gridy = 10;
                gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
                colorSelectPanel.add(jCheckBoxFilter7, gridBagConstraints);

                targetButtonGroup.add(jCheckBoxTarget1);
                jCheckBoxTarget1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
                jCheckBoxTarget1.setBorderPainted(true);
                jCheckBoxTarget1.setEnabled(false);
                jCheckBoxTarget1.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jCheckBoxTarget1ActionPerformed(evt);
                        }
                });
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 4;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                colorSelectPanel.add(jCheckBoxTarget1, gridBagConstraints);

                targetButtonGroup.add(jCheckBoxTarget2);
                jCheckBoxTarget2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
                jCheckBoxTarget2.setBorderPainted(true);
                jCheckBoxTarget2.setEnabled(false);
                jCheckBoxTarget2.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jCheckBoxTarget2ActionPerformed(evt);
                        }
                });
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 5;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                colorSelectPanel.add(jCheckBoxTarget2, gridBagConstraints);

                targetButtonGroup.add(jCheckBoxTarget3);
                jCheckBoxTarget3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
                jCheckBoxTarget3.setBorderPainted(true);
                jCheckBoxTarget3.setEnabled(false);
                jCheckBoxTarget3.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jCheckBoxTarget3ActionPerformed(evt);
                        }
                });
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 6;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                colorSelectPanel.add(jCheckBoxTarget3, gridBagConstraints);

                targetButtonGroup.add(jCheckBoxTarget4);
                jCheckBoxTarget4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
                jCheckBoxTarget4.setBorderPainted(true);
                jCheckBoxTarget4.setEnabled(false);
                jCheckBoxTarget4.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jCheckBoxTarget4ActionPerformed(evt);
                        }
                });
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 7;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                colorSelectPanel.add(jCheckBoxTarget4, gridBagConstraints);

                targetButtonGroup.add(jCheckBoxTarget5);
                jCheckBoxTarget5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
                jCheckBoxTarget5.setBorderPainted(true);
                jCheckBoxTarget5.setEnabled(false);
                jCheckBoxTarget5.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jCheckBoxTarget5ActionPerformed(evt);
                        }
                });
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 8;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                colorSelectPanel.add(jCheckBoxTarget5, gridBagConstraints);

                targetButtonGroup.add(jCheckBoxTarget6);
                jCheckBoxTarget6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
                jCheckBoxTarget6.setBorderPainted(true);
                jCheckBoxTarget6.setEnabled(false);
                jCheckBoxTarget6.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jCheckBoxTarget6ActionPerformed(evt);
                        }
                });
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 9;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                colorSelectPanel.add(jCheckBoxTarget6, gridBagConstraints);

                targetButtonGroup.add(jCheckBoxTarget7);
                jCheckBoxTarget7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
                jCheckBoxTarget7.setBorderPainted(true);
                jCheckBoxTarget7.setEnabled(false);
                jCheckBoxTarget7.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jCheckBoxTarget7ActionPerformed(evt);
                        }
                });
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 10;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                colorSelectPanel.add(jCheckBoxTarget7, gridBagConstraints);

                jLabelVisible.setText(bundle.getString("BaseFrame.jLabelVisible.text")); // NOI18N
                jLabelVisible.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 3;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                colorSelectPanel.add(jLabelVisible, gridBagConstraints);

                jLabelFilter.setText(bundle.getString("BaseFrame.jLabelFilter.text")); // NOI18N
                jLabelFilter.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 2;
                gridBagConstraints.gridy = 3;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
                colorSelectPanel.add(jLabelFilter, gridBagConstraints);

                filterSelectCBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<FILTER>" }));
                filterSelectCBox.addItemListener(new java.awt.event.ItemListener() {
                        public void itemStateChanged(java.awt.event.ItemEvent evt) {
                                imageSelectorChanged(evt);
                        }
                });
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 2;
                gridBagConstraints.gridwidth = 3;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
                colorSelectPanel.add(filterSelectCBox, gridBagConstraints);

                referenceButtonGroup.add(jCheckBoxRef1);
                jCheckBoxRef1.setText(bundle.getString("BaseFrame.jCheckBoxRef1.text")); // NOI18N
                jCheckBoxRef1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
                jCheckBoxRef1.setBorderPainted(true);
                jCheckBoxRef1.setEnabled(false);
                jCheckBoxRef1.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jCheckBoxRef1ActionPerformed(evt);
                        }
                });
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 1;
                gridBagConstraints.gridy = 4;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                colorSelectPanel.add(jCheckBoxRef1, gridBagConstraints);

                referenceButtonGroup.add(jCheckBoxRef2);
                jCheckBoxRef2.setText(bundle.getString("BaseFrame.jCheckBoxRef2.text")); // NOI18N
                jCheckBoxRef2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
                jCheckBoxRef2.setBorderPainted(true);
                jCheckBoxRef2.setEnabled(false);
                jCheckBoxRef2.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jCheckBoxRef2ActionPerformed(evt);
                        }
                });
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 1;
                gridBagConstraints.gridy = 5;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                colorSelectPanel.add(jCheckBoxRef2, gridBagConstraints);

                referenceButtonGroup.add(jCheckBoxRef3);
                jCheckBoxRef3.setText(bundle.getString("BaseFrame.jCheckBoxRef3.text")); // NOI18N
                jCheckBoxRef3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
                jCheckBoxRef3.setBorderPainted(true);
                jCheckBoxRef3.setEnabled(false);
                jCheckBoxRef3.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jCheckBoxRef3ActionPerformed(evt);
                        }
                });
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 1;
                gridBagConstraints.gridy = 6;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                colorSelectPanel.add(jCheckBoxRef3, gridBagConstraints);

                referenceButtonGroup.add(jCheckBoxRef4);
                jCheckBoxRef4.setText(bundle.getString("BaseFrame.jCheckBoxRef4.text")); // NOI18N
                jCheckBoxRef4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
                jCheckBoxRef4.setBorderPainted(true);
                jCheckBoxRef4.setEnabled(false);
                jCheckBoxRef4.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jCheckBoxRef4ActionPerformed(evt);
                        }
                });
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 1;
                gridBagConstraints.gridy = 7;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                colorSelectPanel.add(jCheckBoxRef4, gridBagConstraints);

                referenceButtonGroup.add(jCheckBoxRef5);
                jCheckBoxRef5.setText(bundle.getString("BaseFrame.jCheckBoxRef5.text")); // NOI18N
                jCheckBoxRef5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
                jCheckBoxRef5.setBorderPainted(true);
                jCheckBoxRef5.setEnabled(false);
                jCheckBoxRef5.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jCheckBoxRef5ActionPerformed(evt);
                        }
                });
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 1;
                gridBagConstraints.gridy = 8;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                colorSelectPanel.add(jCheckBoxRef5, gridBagConstraints);

                referenceButtonGroup.add(jCheckBoxRef6);
                jCheckBoxRef6.setText(bundle.getString("BaseFrame.jCheckBoxRef6.text")); // NOI18N
                jCheckBoxRef6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
                jCheckBoxRef6.setBorderPainted(true);
                jCheckBoxRef6.setEnabled(false);
                jCheckBoxRef6.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jCheckBoxRef6ActionPerformed(evt);
                        }
                });
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 1;
                gridBagConstraints.gridy = 9;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                colorSelectPanel.add(jCheckBoxRef6, gridBagConstraints);

                referenceButtonGroup.add(jCheckBoxRef7);
                jCheckBoxRef7.setText(bundle.getString("BaseFrame.jCheckBoxRef7.text")); // NOI18N
                jCheckBoxRef7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
                jCheckBoxRef7.setBorderPainted(true);
                jCheckBoxRef7.setEnabled(false);
                jCheckBoxRef7.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jCheckBoxRef7ActionPerformed(evt);
                        }
                });
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 1;
                gridBagConstraints.gridy = 10;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                colorSelectPanel.add(jCheckBoxRef7, gridBagConstraints);

                jLabel1.setText(bundle.getString("BaseFrame.jLabel1.text")); // NOI18N
                jLabel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 1;
                gridBagConstraints.gridy = 3;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                colorSelectPanel.add(jLabel1, gridBagConstraints);

                colorSelectScrollPane.setViewportView(colorSelectPanel);

                imagePropertyPanel.add(colorSelectScrollPane, java.awt.BorderLayout.WEST);

                brightnessPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
                brightnessPanel.setMinimumSize(new java.awt.Dimension(260, 368));
                brightnessPanel.setPreferredSize(new java.awt.Dimension(270, 300));
                brightnessPanel.setLayout(new java.awt.BorderLayout());

                scalePanel.setPreferredSize(new java.awt.Dimension(400, 62));
                scalePanel.setLayout(new java.awt.GridLayout(3, 0));

                spinnerPanel.setLayout(new java.awt.GridBagLayout());

                minSpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, 4094, 1));
                minSpinner.setMinimumSize(new java.awt.Dimension(100, 28));
                minSpinner.setPreferredSize(new java.awt.Dimension(100, 28));
                minSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
                        public void stateChanged(javax.swing.event.ChangeEvent evt) {
                                minSpinnerStateChanged(evt);
                        }
                });
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 0;
                spinnerPanel.add(minSpinner, gridBagConstraints);

                brightnessAutoGroup.add(autoRadioButton);
                autoRadioButton.setText(bundle.getString("BaseFrame.autoRadioButton.text")); // NOI18N
                autoRadioButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                autoRadioButtonActionPerformed(evt);
                        }
                });
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridwidth = 2;
                spinnerPanel.add(autoRadioButton, gridBagConstraints);

                brightnessAutoGroup.add(manualRadioButton);
                manualRadioButton.setSelected(true);
                manualRadioButton.setText(bundle.getString("BaseFrame.manualRadioButton.text")); // NOI18N
                manualRadioButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                manualRadioButtonActionPerformed(evt);
                        }
                });
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridwidth = 2;
                spinnerPanel.add(manualRadioButton, gridBagConstraints);

                adjustButton.setText(bundle.getString("BaseFrame.adjustButton.text")); // NOI18N
                adjustButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                adjustButtonActionPerformed(evt);
                        }
                });
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridwidth = 3;
                spinnerPanel.add(adjustButton, gridBagConstraints);

                autoTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0.35", "0.1", "0.2", "0.7", "1.0", "2.0", "5.0", "10.0", "15.0", "20.0", "25.0", "30.0", "40.0", "50.0" }));
                autoTypeComboBox.setMinimumSize(new java.awt.Dimension(160, 28));
                autoTypeComboBox.setPreferredSize(new java.awt.Dimension(160, 28));
                autoTypeComboBox.addItemListener(new java.awt.event.ItemListener() {
                        public void itemStateChanged(java.awt.event.ItemEvent evt) {
                                autoTypeComboBoxItemStateChanged(evt);
                        }
                });
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 8;
                gridBagConstraints.gridy = 0;
                spinnerPanel.add(autoTypeComboBox, gridBagConstraints);

                maxSpinner.setModel(new javax.swing.SpinnerNumberModel(4095, 1, 4095, 1));
                maxSpinner.setMinimumSize(new java.awt.Dimension(100, 28));
                maxSpinner.setPreferredSize(new java.awt.Dimension(100, 28));
                maxSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
                        public void stateChanged(javax.swing.event.ChangeEvent evt) {
                                maxSpinnerStateChanged(evt);
                        }
                });
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 9;
                gridBagConstraints.gridy = 0;
                spinnerPanel.add(maxSpinner, gridBagConstraints);

                scalePanel.add(spinnerPanel);

                subtractionPanel.setPreferredSize(new java.awt.Dimension(282, 50));
                subtractionPanel.setLayout(new javax.swing.BoxLayout(subtractionPanel, javax.swing.BoxLayout.LINE_AXIS));

                subtractLabel.setText(bundle.getString("BaseFrame.subtractLabel.text")); // NOI18N
                subtractionPanel.add(subtractLabel);

                ballSizeSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
                        public void stateChanged(javax.swing.event.ChangeEvent evt) {
                                ballSizeSpinnerStateChanged(evt);
                        }
                });
                subtractionPanel.add(ballSizeSpinner);

                scalePanel.add(subtractionPanel);

                scaleRangeSlider.setMaximum(4095);
                scaleRangeSlider.setExtent(4095);
                scaleRangeSlider.addChangeListener(new javax.swing.event.ChangeListener() {
                        public void stateChanged(javax.swing.event.ChangeEvent evt) {
                                scaleRangeSliderStateChanged(evt);
                        }
                });
                scaleRangeSlider.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                scaleRangeSliderMouseReleased(evt);
                        }
                });
                scalePanel.add(scaleRangeSlider);

                brightnessPanel.add(scalePanel, java.awt.BorderLayout.SOUTH);

                plotPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, bundle.getString("BaseFrame.brightnessPanel.border.title"), javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION)); // NOI18N

                javax.swing.GroupLayout plotPanelLayout = new javax.swing.GroupLayout(plotPanel);
                plotPanel.setLayout(plotPanelLayout);
                plotPanelLayout.setHorizontalGroup(
                        plotPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 1083, Short.MAX_VALUE)
                );
                plotPanelLayout.setVerticalGroup(
                        plotPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 213, Short.MAX_VALUE)
                );

                brightnessPanel.add(plotPanel, java.awt.BorderLayout.CENTER);

                imagePropertyPanel.add(brightnessPanel, java.awt.BorderLayout.CENTER);

                slide2.add(imagePropertyPanel, java.awt.BorderLayout.NORTH);

                slideScrollPane2.setViewportView(slide2);

                centerPanel.add(slideScrollPane2, "slide2");

                slide3.setPreferredSize(new java.awt.Dimension(450, 300));
                slide3.setLayout(new java.awt.BorderLayout());

                summaryLabel.setText(bundle.getString("BaseFrame.summaryLabel.text")); // NOI18N
                slide3.add(summaryLabel, java.awt.BorderLayout.PAGE_START);

                summaryDisplayArea.setEditable(false);
                summaryDisplayArea.setColumns(20);
                summaryDisplayArea.setRows(5);
                summaryScrollPane.setViewportView(summaryDisplayArea);

                slide3.add(summaryScrollPane, java.awt.BorderLayout.CENTER);

                slideScrollPane3.setViewportView(slide3);

                centerPanel.add(slideScrollPane3, "slide3");

                getContentPane().add(centerPanel, java.awt.BorderLayout.CENTER);

                southPanel.setMaximumSize(new java.awt.Dimension(200, 36));
                southPanel.setMinimumSize(new java.awt.Dimension(200, 36));
                southPanel.setPreferredSize(new java.awt.Dimension(200, 70));
                southPanel.setLayout(new java.awt.BorderLayout());

                jPanel1.setLayout(new java.awt.BorderLayout());

                messageLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
                messageLabel.setPreferredSize(new java.awt.Dimension(40, 30));
                jPanel1.add(messageLabel, java.awt.BorderLayout.CENTER);

                cropAreaPanel.setMinimumSize(new java.awt.Dimension(200, 100));
                cropAreaPanel.setPreferredSize(new java.awt.Dimension(200, 30));
                cropAreaPanel.setLayout(new java.awt.GridBagLayout());

                xTextField.setText(bundle.getString("BaseFrame.xTextField.text")); // NOI18N
                xTextField.setInputVerifier(inputverifier);
                xTextField.setMinimumSize(new java.awt.Dimension(40, 30));
                xTextField.setPreferredSize(new java.awt.Dimension(40, 30));
                xTextField.addFocusListener(new java.awt.event.FocusAdapter() {
                        public void focusLost(java.awt.event.FocusEvent evt) {
                                textFieldFocusLost(evt);
                        }
                });
                xTextField.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                textFieldActionPerformed(evt);
                        }
                });
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 1;
                gridBagConstraints.gridy = 0;
                gridBagConstraints.gridwidth = 2;
                gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
                gridBagConstraints.weightx = 1.0;
                gridBagConstraints.weighty = 1.0;
                cropAreaPanel.add(xTextField, gridBagConstraints);

                xLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
                xLabel.setText(bundle.getString("BaseFrame.xLabel.text")); // NOI18N
                xLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
                xLabel.setMaximumSize(new java.awt.Dimension(15, 30));
                xLabel.setMinimumSize(new java.awt.Dimension(15, 30));
                xLabel.setName(""); // NOI18N
                xLabel.setPreferredSize(new java.awt.Dimension(15, 20));
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 0;
                gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
                gridBagConstraints.weightx = 1.0;
                gridBagConstraints.weighty = 1.0;
                cropAreaPanel.add(xLabel, gridBagConstraints);

                yLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
                yLabel.setText(bundle.getString("BaseFrame.yLabel.text")); // NOI18N
                yLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
                yLabel.setMaximumSize(new java.awt.Dimension(15, 20));
                yLabel.setMinimumSize(new java.awt.Dimension(15, 20));
                yLabel.setPreferredSize(new java.awt.Dimension(15, 20));
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 3;
                gridBagConstraints.gridy = 0;
                gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
                gridBagConstraints.weightx = 1.0;
                gridBagConstraints.weighty = 1.0;
                cropAreaPanel.add(yLabel, gridBagConstraints);

                wLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
                wLabel.setText(bundle.getString("BaseFrame.wLabel.text")); // NOI18N
                wLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
                wLabel.setMaximumSize(new java.awt.Dimension(15, 20));
                wLabel.setMinimumSize(new java.awt.Dimension(15, 20));
                wLabel.setName(""); // NOI18N
                wLabel.setPreferredSize(new java.awt.Dimension(15, 20));
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 6;
                gridBagConstraints.gridy = 0;
                gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
                gridBagConstraints.weightx = 1.0;
                gridBagConstraints.weighty = 1.0;
                cropAreaPanel.add(wLabel, gridBagConstraints);

                hLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
                hLabel.setText(bundle.getString("BaseFrame.hLabel.text")); // NOI18N
                hLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
                hLabel.setMaximumSize(new java.awt.Dimension(15, 20));
                hLabel.setMinimumSize(new java.awt.Dimension(15, 20));
                hLabel.setPreferredSize(new java.awt.Dimension(15, 20));
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 9;
                gridBagConstraints.gridy = 0;
                gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
                gridBagConstraints.weightx = 1.0;
                gridBagConstraints.weighty = 1.0;
                cropAreaPanel.add(hLabel, gridBagConstraints);

                yTextField.setText(bundle.getString("BaseFrame.yTextField.text")); // NOI18N
                yTextField.setInputVerifier(inputverifier);
                yTextField.setMinimumSize(new java.awt.Dimension(40, 30));
                yTextField.setPreferredSize(new java.awt.Dimension(40, 30));
                yTextField.addFocusListener(new java.awt.event.FocusAdapter() {
                        public void focusLost(java.awt.event.FocusEvent evt) {
                                textFieldFocusLost(evt);
                        }
                });
                yTextField.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                textFieldActionPerformed(evt);
                        }
                });
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 4;
                gridBagConstraints.gridy = 0;
                gridBagConstraints.gridwidth = 2;
                gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
                gridBagConstraints.weightx = 1.0;
                gridBagConstraints.weighty = 1.0;
                cropAreaPanel.add(yTextField, gridBagConstraints);

                wTextField.setText(bundle.getString("BaseFrame.wTextField.text")); // NOI18N
                wTextField.setInputVerifier(inputverifier);
                wTextField.setMinimumSize(new java.awt.Dimension(40, 30));
                wTextField.setPreferredSize(new java.awt.Dimension(40, 30));
                wTextField.addFocusListener(new java.awt.event.FocusAdapter() {
                        public void focusLost(java.awt.event.FocusEvent evt) {
                                textFieldFocusLost(evt);
                        }
                });
                wTextField.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                textFieldActionPerformed(evt);
                        }
                });
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 7;
                gridBagConstraints.gridy = 0;
                gridBagConstraints.gridwidth = 2;
                gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
                gridBagConstraints.weightx = 1.0;
                gridBagConstraints.weighty = 1.0;
                cropAreaPanel.add(wTextField, gridBagConstraints);

                hTextField.setText(bundle.getString("BaseFrame.hTextField.text")); // NOI18N
                hTextField.setInputVerifier(inputverifier);
                hTextField.setMinimumSize(new java.awt.Dimension(40, 30));
                hTextField.setPreferredSize(new java.awt.Dimension(40, 30));
                hTextField.addFocusListener(new java.awt.event.FocusAdapter() {
                        public void focusLost(java.awt.event.FocusEvent evt) {
                                textFieldFocusLost(evt);
                        }
                });
                hTextField.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                textFieldActionPerformed(evt);
                        }
                });
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 10;
                gridBagConstraints.gridy = 0;
                gridBagConstraints.gridwidth = 2;
                gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
                gridBagConstraints.weightx = 1.0;
                gridBagConstraints.weighty = 1.0;
                cropAreaPanel.add(hTextField, gridBagConstraints);

                jPanel1.add(cropAreaPanel, java.awt.BorderLayout.EAST);

                southPanel.add(jPanel1, java.awt.BorderLayout.PAGE_START);

                proceedPanel.setPreferredSize(new java.awt.Dimension(450, 100));
                proceedPanel.setLayout(new java.awt.BorderLayout());

                jPanel2.setPreferredSize(new java.awt.Dimension(200, 40));

                cancelButton.setText(bundle.getString("BaseFrame.cancelButton.text")); // NOI18N
                cancelButton.setActionCommand(bundle.getString("BaseFrame.cancelButton.actionCommand")); // NOI18N
                cancelButton.setMaximumSize(new java.awt.Dimension(80, 30));
                cancelButton.setMinimumSize(new java.awt.Dimension(80, 30));
                cancelButton.setPreferredSize(new java.awt.Dimension(80, 30));
                cancelButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                cancelButtonActionPerformed(evt);
                        }
                });
                jPanel2.add(cancelButton);

                exitButton.setText("exit");
                exitButton.setActionCommand(bundle.getString("BaseFrame.exitButton.actionCommand")); // NOI18N
                exitButton.setMaximumSize(new java.awt.Dimension(80, 30));
                exitButton.setMinimumSize(new java.awt.Dimension(80, 30));
                exitButton.setPreferredSize(new java.awt.Dimension(80, 30));
                exitButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                exitButtonActionPerformed(evt);
                        }
                });
                jPanel2.add(exitButton);

                proceedPanel.add(jPanel2, java.awt.BorderLayout.WEST);

                backButton.setText(bundle.getString("BaseFrame.backButton.text")); // NOI18N
                backButton.setMaximumSize(new java.awt.Dimension(80, 30));
                backButton.setMinimumSize(new java.awt.Dimension(80, 30));
                backButton.setPreferredSize(new java.awt.Dimension(80, 30));
                backButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                backButtonActionPerformed(evt);
                        }
                });
                cardMovePanel.add(backButton);

                nextButton.setText(bundle.getString("BaseFrame.nextButton.text")); // NOI18N
                nextButton.setMaximumSize(new java.awt.Dimension(80, 30));
                nextButton.setMinimumSize(new java.awt.Dimension(80, 30));
                nextButton.setPreferredSize(new java.awt.Dimension(80, 30));
                nextButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                nextButtonActionPerformed(evt);
                        }
                });
                cardMovePanel.add(nextButton);

                proceedPanel.add(cardMovePanel, java.awt.BorderLayout.CENTER);

                convertPanel.setPreferredSize(new java.awt.Dimension(100, 40));

                convertButton.setText(bundle.getString("BaseFrame.convertButton.text")); // NOI18N
                convertButton.setMaximumSize(new java.awt.Dimension(80, 30));
                convertButton.setMinimumSize(new java.awt.Dimension(80, 30));
                convertButton.setPreferredSize(new java.awt.Dimension(80, 30));
                convertButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                convertButtonActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout convertPanelLayout = new javax.swing.GroupLayout(convertPanel);
                convertPanel.setLayout(convertPanelLayout);
                convertPanelLayout.setHorizontalGroup(
                        convertPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 100, Short.MAX_VALUE)
                        .addGroup(convertPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(convertPanelLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(convertButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))
                );
                convertPanelLayout.setVerticalGroup(
                        convertPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 40, Short.MAX_VALUE)
                        .addGroup(convertPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(convertPanelLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(convertButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))
                );

                proceedPanel.add(convertPanel, java.awt.BorderLayout.EAST);

                southPanel.add(proceedPanel, java.awt.BorderLayout.CENTER);

                getContentPane().add(southPanel, java.awt.BorderLayout.SOUTH);

                fileMenu.setText(bundle.getString("BaseFrame.fileMenu.text")); // NOI18N
                fileMenu.setEnabled(false);
                jMenuBar1.add(fileMenu);

                setJMenuBar(jMenuBar1);

                pack();
        }// </editor-fold>//GEN-END:initComponents

private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
	if (!active) {
		return;
	}

	this.appController.nextCard();
}//GEN-LAST:event_nextButtonActionPerformed

private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
	if (!active) {
		return;
	}
	this.appController.previousCard();
}//GEN-LAST:event_backButtonActionPerformed

private void sourceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sourceButtonActionPerformed
	if (!active) {
		return;
	}
	File _srcDir = AutoConverterUtils.getDirectory(this, AutoConverterConfig.getConfig(AutoConverterConfig.KEY_SOURCE_DIRECTORY, null, null));
	if (_srcDir == null) {
		return;
	}
	this.getSourceText().setText(_srcDir.getAbsolutePath());
	appController.storeDirectorySetting(false);
	this.appController.updateWizerdButton();
}//GEN-LAST:event_sourceButtonActionPerformed

private void destinationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_destinationButtonActionPerformed
	if (!active) {
		return;
	}
	File _dstDir = AutoConverterUtils.getDirectory(this, AutoConverterConfig.getConfig(AutoConverterConfig.KEY_DESTINATION_DIRECTORY, null, null));
	if (_dstDir == null) {
		return;
	}
	this.getDestinationText().setText(_dstDir.getAbsolutePath());
	appController.storeDirectorySetting(false);
	this.appController.updateWizerdButton();
}//GEN-LAST:event_destinationButtonActionPerformed

	/**
	 * Store main window size to property file everytime the window size is
	 * changed.
	 *
	 * @param evt
	 */
private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
	if (!active) {
		return;
	}
	Component _c = evt.getComponent();
	Dimension _d = _c.getSize();
	AutoConverterConfig.setConfig(AutoConverterConfig.KEY_MAIN_FRAME_SIZE_X, _d.width);
	AutoConverterConfig.setConfig(AutoConverterConfig.KEY_MAIN_FRAME_SIZE_Y, _d.height);
//AutoConverterConfig.save(this, true);
}//GEN-LAST:event_formComponentResized

	/**
	 * Close window if cancel button is clicked.
	 *
	 * @param evt
	 */
private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
	if (!active) {
		return;
	}
	if (this.cancelButton == evt.getSource()) {
		this.dispose();
	}
//System.exit(0);
}//GEN-LAST:event_cancelButtonActionPerformed

private void recursiveRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recursiveRadioButtonActionPerformed
	if (!active) {
		return;
	}
	if (evt.getSource() != this.getRecursiveRadioButton()) {
		return;
	}
	appController.storeRecursiveSetting(false);
}//GEN-LAST:event_recursiveRadioButtonActionPerformed

private void modeSelectorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_modeSelectorItemStateChanged
	if (!active) {
		return;
	}
	String mode = evt.getItem().toString();
	this.enableListener(false);
	if(mode.equals("Single")){
		appController.setImageMode(BaseFrame.IMAGE_MODE_SINGLE);
	} else if(mode.equals("Merge")){
		appController.setImageMode(BaseFrame.IMAGE_MODE_MERGE);
	} else if(mode.equals("Threshold")){
		appController.setImageMode(BaseFrame.IMAGE_MODE_THRESHOLD);
	} else if(mode.equals("Relative")){
		appController.setImageMode(BaseFrame.IMAGE_MODE_RELATIVE);
	} else {
		getLogger().warning("不明なモード:" + mode);
	}
	appController.applyParams(true);
	this.enableListener(true);
	//appController.collectParams();
	appController.updateImage();
	//getLogger().fine(evt.getItem().toString());
	logger.fine("\n------------------ state changed: change mode to " + mode + " -----------------");
}//GEN-LAST:event_modeSelectorItemStateChanged

private void colorChannelSelectorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_colorChannelSelectorItemStateChanged
	if (!active) {
		return;
	}
	this.appController.collectParams(evt.getSource());
	logger.fine("colorChannelSelector changed to " + evt.getItem().toString());
	//this.appController.setColor(evt.getItem().toString());
	this.appController.collectParams(evt.getSource());
	this.enableListener(false);
	appController.updateImage();
	//this.appController.setColor();
	this.enableListener(true);
}//GEN-LAST:event_colorChannelSelectorItemStateChanged

	/**
	 * 選択された画像を再描画する. 主に、画像洗濯用のComboBoxが変更された時に呼ばれる.
	 *
	 * @param evt
	 */
private void imageSelectorChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_imageSelectorChanged
	if (!active) {
		return;
	}
	if (evt.getStateChange() != ItemEvent.SELECTED) {
		return;
	}
	String dir = this.getSourceText().getText() + (String) this.getDirSelectCBox().getSelectedItem();
	String wellname = (String) this.getWellSelectCBox().getSelectedItem();
	String position = (String) this.getPositionSelectCBox().getSelectedItem();
	String slice = (String) this.getzSelectCBox().getSelectedItem();
	String time = (String) this.getTimeSelectCBox().getSelectedItem();
	String filter = (String) this.getFilterSelectCBox().getSelectedItem();
	// filter が変更されたときは、現行のComponentのパラメータではなく、stored... 保存のパラメータにComponentの状態を復帰する
	if(evt.getSource() == this.filterSelectCBox){
		logger.fine("================ filter => " + filter + " ===================");
		// フィルタが変更された場合はパラメータを設定し直さなければならないので、強制的にcomponentの状態をupdateする
		// ただし、merge モードだったらmin, max
		if(this.appController.getImageMode() != BaseFrame.IMAGE_MODE_RELATIVE){
			this.getAppController().applyParams(true);
		}
	}
	//this.getAppController().collectParams();
	String imageID = ApplicationController.createImageID(dir, wellname, position, slice, time, filter);
	this.getAppController().updateImage(imageID);
	//this.getAppController().updateDensityPlot();

}//GEN-LAST:event_imageSelectorChanged


private void scaleRangeSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_scaleRangeSliderStateChanged
	if (!active) {
		return;
	}
// TODO add your handling code here:
	if (evt.getSource() == this.getScaleRangeSlider()) {
		logger.fine("Slider Changed");
		this.getAppController().collectParams(evt.getSource());
		this.getAppController().applyParams();
		//this.getAppController().updateImage();
	}
}//GEN-LAST:event_scaleRangeSliderStateChanged

	/**
	 * minSpinner の最大値をmaxSpinnerの値-1に設定する.
	 *
	 * @param evt
	 */
private void maxSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_maxSpinnerStateChanged
	if (!active) {
		return;
	}
	if (evt.getSource() == this.maxSpinner) {
		Integer max = (Integer) this.maxSpinner.getValue();
		Integer min = (Integer) this.minSpinner.getValue();
		this.enableListener(false);
		if (max < min) {
			max = min + 1;
			this.maxSpinner.setValue(max);
		}
		this.getAppController().setMinSpinnerMax(max - 1);
		this.getScaleRangeSlider().setUpperValue(max);
		this.appController.collectParams(evt.getSource());
		this.getAppController().updateImage();
		this.enableListener(true);
	}
}//GEN-LAST:event_maxSpinnerStateChanged

	/**
	 * maxSpinner の最小値をminSpinnerの値+1に設定する.
	 *
	 * @param evt
	 */
private void minSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_minSpinnerStateChanged
	if (!active) {
		return;
	}
	if (evt.getSource() == this.minSpinner) {
		Integer max = (Integer) this.maxSpinner.getValue();
		Integer min = (Integer) this.minSpinner.getValue();
		this.enableListener(false);
		if (max < min) {
			min = max - 1;
			this.minSpinner.setValue(min);
		}
		this.getAppController().setMaxSpinnerMin(min + 1);
		this.getScaleRangeSlider().setLowerValue(min);
		this.appController.collectParams(evt.getSource());
		this.getAppController().updateImage();
		this.enableListener(true);
	}
}//GEN-LAST:event_minSpinnerStateChanged

private void autoRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autoRadioButtonActionPerformed
	if (!active) {
		return;
	}
	if (evt.getSource() == this.getAutoRadioButton() && this.getAutoRadioButton().isSelected()) {
		this.appController.configAutoRelatedComponents(true);
		this.getAppController().collectParams();
		this.appController.adjustValues();
		this.getAppController().applyParams(evt.getSource());
		this.appController.updateImage();
		//this.appController.storeCurrentFilterSettings(false);
	}
}//GEN-LAST:event_autoRadioButtonActionPerformed

private void manualRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manualRadioButtonActionPerformed
	if (!active) {
		return;
	}
	if (evt.getSource() == this.getManualRadioButton() && this.getManualRadioButton().isSelected()) {
		this.appController.configAutoRelatedComponents(false);
		this.getAppController().collectParams(evt.getSource());
		this.getAppController().applyParams(evt.getSource());
		//this.appController.storeCurrentFilterSettings(false);
		this.appController.updateImage();
	}
}//GEN-LAST:event_manualRadioButtonActionPerformed

	/**
	 * adjust button が押されたら画像の輝度値を自動調整.
	 *
	 * @param evt
	 */
private void adjustButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adjustButtonActionPerformed
	if (!active) {
		return;
	}
	this.appController.adjustValues();
	//this.appController.storeCurrentFilterSettings(false);
	this.appController.updateImage();
}//GEN-LAST:event_adjustButtonActionPerformed

private void convertButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_convertButtonActionPerformed
	if (!active) {
		return;
	}
	this.appController.convertImages();
}//GEN-LAST:event_convertButtonActionPerformed

private void ballSizeSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ballSizeSpinnerStateChanged
	if (!active) {
		return;
	}
	if (evt.getSource() == this.getBallSizeSpinner()) {
		JSpinner spinner = (JSpinner) evt.getSource();
		//this.appController.storeCurrentFilterSettings(false);
		this.appController.collectParams(evt.getSource());
		this.appController.updateImage();
	}
}//GEN-LAST:event_ballSizeSpinnerStateChanged

private void filePatternComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_filePatternComboBoxItemStateChanged
	if (!active) {
		return;
	}
	if (this.getFilePatternComboBox() != evt.getSource()) {
		return;
	}
	String selectedPatternName = (String) evt.getItem();
	if (evt.getStateChange() == ItemEvent.SELECTED) {
		if (selectedPatternName.equals(AutoConverterConfig.REGEXP_NAME_CELAVIEW)
			|| selectedPatternName.equals(AutoConverterConfig.REGEXP_NAME_INCELL6000)) {
			this.getFilePatternTextField().setEditable(false);
			this.filePatternComboBox.setEditable(false);
		} else {
			this.getFilePatternTextField().setEditable(true);
			this.filePatternComboBox.setEditable(true);
		}
		String regexPattern = AutoConverterConfig.getRegexp(selectedPatternName);
		this.getFilePatternTextField().setText(regexPattern);
		AutoConverterConfig.setConfig(AutoConverterConfig.KEY_SELECTED_PATTERN, selectedPatternName);

// 1つ前の選択のpatternの名前(selectedPatternName)が新しい場合に、comboBoxに登録したい
		this.initFilePatternComboBox(selectedPatternName);
	} else if (evt.getStateChange() == ItemEvent.DESELECTED) {
// 1つ前の選択のpatternを取ってきてconfigに保存
		String regexPattern = this.getFilePatternTextField().getText();
		if (regexPattern.equals("")) {
			AutoConverterConfig.removeConfig(selectedPatternName, AutoConverterConfig.PREFIX_REGEXP);
		} else {
			AutoConverterConfig.setConfig(selectedPatternName, regexPattern, AutoConverterConfig.PREFIX_REGEXP);
		}
	}
}//GEN-LAST:event_filePatternComboBoxItemStateChanged

private void displayRangeComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_displayRangeComboBoxItemStateChanged
	if (!active) {
		return;
	}
	if (evt.getSource() != this.displayRangeComboBox) {
		return;
	}
	if (evt.getStateChange() == ItemEvent.SELECTED) {
		appController.storeDisplayRangeMaxSetting(false);
	}
}//GEN-LAST:event_displayRangeComboBoxItemStateChanged

private void removeSpecialCharRadioButtonStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_removeSpecialCharRadioButtonStateChanged
	if (!active) {
		return;
	}
	if (evt.getSource() != this.getRemoveSpecialCharRadioButton()) {
		return;
	}
	appController.storeRemoveSpecialCharSetting(false);
}//GEN-LAST:event_removeSpecialCharRadioButtonStateChanged

private void autoTypeComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_autoTypeComboBoxItemStateChanged
	if (!active) {
		return;
	}
	if (evt.getSource() != this.autoTypeComboBox) {
		return;
	}
	if (evt.getStateChange() != ItemEvent.SELECTED) {
		return;
	}
	appController.collectParams(evt.getSource());

	if (this.autoRadioButton.isSelected()) {
		appController.adjustValues();
	}
	//appController.storeCurrentFilterSettings(false);
}//GEN-LAST:event_autoTypeComboBoxItemStateChanged

private void addParamRadioButtonStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_addParamRadioButtonStateChanged
	if (!active) {
		return;
	}
	if (evt.getSource() != this.getAddParamRadioButton()) {
		return;
	}
	appController.storeAddParamSetting(false);

}//GEN-LAST:event_addParamRadioButtonStateChanged

private void imagePanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imagePanelMousePressed
	if (!active) {
		return;
	}
	this.imagePanel.setStart(evt.getX(), evt.getY());
}//GEN-LAST:event_imagePanelMousePressed

private void imagePanelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imagePanelMouseReleased
	if (!active) {
		return;
	}
	this.imagePanel.setEnd(evt.getX(), evt.getY());
}//GEN-LAST:event_imagePanelMouseReleased

private void imagePanelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imagePanelMouseDragged
	if (!active) {
		return;
	}
	this.imagePanel.setNow(evt.getX(), evt.getY());
}//GEN-LAST:event_imagePanelMouseDragged

private void textFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldActionPerformed
	JFormattedTextField tf = (javax.swing.JFormattedTextField) evt.getSource();
	tf.getInputVerifier().verify(tf);
	int val = Integer.parseInt(tf.getText());
	if (evt.getSource() == this.xTextField) {
		this.imagePanel.setLeftTopX(val);
	} else if (evt.getSource() == this.yTextField) {
		this.imagePanel.setLeftTopY(val);
	} else if (evt.getSource() == this.hTextField) {
		this.imagePanel.setRoiHeight(val);
	} else if (evt.getSource() == this.wTextField) {
		this.imagePanel.setRoiWidth(val);
	}
	this.imagePanel.repaint();
}//GEN-LAST:event_textFieldActionPerformed

private void textFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textFieldFocusLost
	JFormattedTextField tf = (javax.swing.JFormattedTextField) evt.getSource();
	tf.getInputVerifier().verify(tf);
	int val = Integer.parseInt(tf.getText());
	if (evt.getSource() == this.xTextField) {
		this.imagePanel.setLeftTopX(val);
	} else if (evt.getSource() == this.yTextField) {
		this.imagePanel.setLeftTopY(val);
	} else if (evt.getSource() == this.hTextField) {
		this.imagePanel.setRoiHeight(val);
	} else if (evt.getSource() == this.wTextField) {
		this.imagePanel.setRoiWidth(val);
	}
	this.imagePanel.repaint();
}//GEN-LAST:event_textFieldFocusLost

private void resizeRadioButtonStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_resizeRadioButtonStateChanged
	if (evt.getSource() == this.resizeRadioButton) {
		this.resizeSpinner.setEnabled(this.resizeRadioButton.isSelected());
	}
}//GEN-LAST:event_resizeRadioButtonStateChanged

private void scaleRangeSliderMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scaleRangeSliderMouseReleased
	/*
* rangeSlider をドラッグ中に毎回Hashに保存しているとちょっと付加が高い & どうなるかわからないので
* マウスを離した時にだけ保存するようにしてみた.
	 */
	//appController.storeCurrentFilterSettings(false);
	appController.collectParams(evt.getSource());
}//GEN-LAST:event_scaleRangeSliderMouseReleased

private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
	this.dispose();
}//GEN-LAST:event_formWindowClosing

private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
	try {
		AutoConverterConfig.save();
	} catch (FileNotFoundException ex) {
		logger.warning(AutoConverterUtils.stacktrace(ex));
	}
}//GEN-LAST:event_formWindowClosed

private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
	try {
		AutoConverterConfig.save();
	} catch (FileNotFoundException ex) {
		logger.warning(AutoConverterUtils.stacktrace(ex));
	}
	System.exit(0);
}//GEN-LAST:event_exitButtonActionPerformed


        private void jCheckBoxFilter1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxFilter1ActionPerformed
                // TODO add your handling code here:
		this.enableListener(false);
		this.appController.updateNextButton();
		this.appController.updateImage();
		this.enableListener(true);
        }//GEN-LAST:event_jCheckBoxFilter1ActionPerformed

        private void jCheckBoxFilter2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxFilter2ActionPerformed
                // TODO add your handling code here:
		this.enableListener(false);
		this.appController.updateNextButton();
		this.appController.updateImage();
		this.enableListener(true);
        }//GEN-LAST:event_jCheckBoxFilter2ActionPerformed

        private void jCheckBoxFilter3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxFilter3ActionPerformed
                // TODO add your handling code here:
		this.enableListener(false);
		this.appController.updateNextButton();
		this.appController.updateImage();
		this.enableListener(true);
        }//GEN-LAST:event_jCheckBoxFilter3ActionPerformed

        private void jCheckBoxFilter4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxFilter4ActionPerformed
                // TODO add your handling code here:
		this.enableListener(false);
		this.appController.updateNextButton();
		this.appController.updateImage();
		this.enableListener(true);
        }//GEN-LAST:event_jCheckBoxFilter4ActionPerformed

        private void jCheckBoxFilter5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxFilter5ActionPerformed
                // TODO add your handling code here:
		this.enableListener(false);
		this.appController.updateNextButton();
		this.appController.updateImage();
		this.enableListener(true);
        }//GEN-LAST:event_jCheckBoxFilter5ActionPerformed

        private void jCheckBoxFilter6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxFilter6ActionPerformed
                // TODO add your handling code here:
		this.enableListener(false);
		this.appController.updateNextButton();
		this.appController.updateImage();
		this.enableListener(true);
        }//GEN-LAST:event_jCheckBoxFilter6ActionPerformed

        private void jCheckBoxFilter7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxFilter7ActionPerformed
                // TODO add your handling code here:
		this.enableListener(false);
		this.appController.updateNextButton();
		this.appController.updateImage();
		this.enableListener(true);
        }//GEN-LAST:event_jCheckBoxFilter7ActionPerformed

        private void jCheckBoxRef1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxRef1ActionPerformed
		this.enableListener(false);
		this.appController.updateNextButton();
		this.appController.updateImage();
		this.enableListener(true);
                // TODO add your handling code here:
        }//GEN-LAST:event_jCheckBoxRef1ActionPerformed

        private void jCheckBoxTarget1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxTarget1ActionPerformed
                // TODO add your handling code here:
		this.enableListener(false);
		this.appController.updateNextButton();
		this.appController.updateImage();
		this.enableListener(true);
        }//GEN-LAST:event_jCheckBoxTarget1ActionPerformed

        private void jCheckBoxTarget2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxTarget2ActionPerformed
                // TODO add your handling code here:
		this.enableListener(false);
		this.appController.updateNextButton();
		this.appController.updateImage();
		this.enableListener(true);
        }//GEN-LAST:event_jCheckBoxTarget2ActionPerformed

        private void jCheckBoxTarget3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxTarget3ActionPerformed
                // TODO add your handling code here:
		this.enableListener(false);
		this.appController.updateNextButton();
		this.appController.updateImage();
		this.enableListener(true);
        }//GEN-LAST:event_jCheckBoxTarget3ActionPerformed

        private void jCheckBoxTarget4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxTarget4ActionPerformed
                // TODO add your handling code here:
		this.enableListener(false);
		this.appController.updateNextButton();
		this.appController.updateImage();
		this.enableListener(true);
        }//GEN-LAST:event_jCheckBoxTarget4ActionPerformed

        private void jCheckBoxTarget5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxTarget5ActionPerformed
                // TODO add your handling code here:
		this.enableListener(false);
		this.appController.updateNextButton();
		this.appController.updateImage();
		this.enableListener(true);
        }//GEN-LAST:event_jCheckBoxTarget5ActionPerformed

        private void jCheckBoxTarget6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxTarget6ActionPerformed
                // TODO add your handling code here:
		this.enableListener(false);
		this.appController.updateNextButton();
		this.appController.updateImage();
		this.enableListener(true);
        }//GEN-LAST:event_jCheckBoxTarget6ActionPerformed

        private void jCheckBoxTarget7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxTarget7ActionPerformed
                // TODO add your handling code here:
		this.enableListener(false);
		this.appController.updateNextButton();
		this.appController.updateImage();
		this.enableListener(true);
        }//GEN-LAST:event_jCheckBoxTarget7ActionPerformed

        private void jCheckBoxRef2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxRef2ActionPerformed
                // TODO add your handling code here:
		this.enableListener(false);
		this.appController.updateNextButton();
		this.appController.updateImage();
		this.enableListener(true);
        }//GEN-LAST:event_jCheckBoxRef2ActionPerformed

        private void jCheckBoxRef3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxRef3ActionPerformed
                // TODO add your handling code here:
		this.enableListener(false);
		this.appController.updateNextButton();
		this.appController.updateImage();
		this.enableListener(true);
        }//GEN-LAST:event_jCheckBoxRef3ActionPerformed

        private void jCheckBoxRef4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxRef4ActionPerformed
                // TODO add your handling code here:
		this.enableListener(false);
		this.appController.updateNextButton();
		this.appController.updateImage();
		this.enableListener(true);
        }//GEN-LAST:event_jCheckBoxRef4ActionPerformed

        private void jCheckBoxRef5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxRef5ActionPerformed
                // TODO add your handling code here:
		this.enableListener(false);
		this.appController.updateNextButton();
		this.appController.updateImage();
		this.enableListener(true);
        }//GEN-LAST:event_jCheckBoxRef5ActionPerformed

        private void jCheckBoxRef6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxRef6ActionPerformed
                // TODO add your handling code here:
		this.enableListener(false);
		this.appController.updateNextButton();
		this.appController.updateImage();
		this.enableListener(true);
        }//GEN-LAST:event_jCheckBoxRef6ActionPerformed

        private void jCheckBoxRef7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxRef7ActionPerformed
                // TODO add your handling code here:
		this.enableListener(false);
		this.appController.updateNextButton();
		this.appController.updateImage();
		this.enableListener(true);
        }//GEN-LAST:event_jCheckBoxRef7ActionPerformed

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JRadioButton addParamRadioButton;
        private javax.swing.JButton adjustButton;
        private javax.swing.JRadioButton autoRadioButton;
        private javax.swing.JComboBox<String> autoTypeComboBox;
        private javax.swing.JButton backButton;
        public javax.swing.JSpinner ballSizeSpinner;
        private javax.swing.ButtonGroup brightnessAutoGroup;
        private javax.swing.JPanel brightnessPanel;
        private javax.swing.JButton cancelButton;
        private javax.swing.JPanel cardMovePanel;
        private javax.swing.JPanel centerPanel;
        private javax.swing.JPanel checkBoxPanel;
        public javax.swing.JComboBox colorChannelSelector;
        private javax.swing.JPanel colorSelectPanel;
        private javax.swing.JScrollPane colorSelectScrollPane;
        private javax.swing.JButton convertButton;
        private javax.swing.JLabel convertLabel;
        private javax.swing.JPanel convertPanel;
        private javax.swing.JPanel cropAreaPanel;
        private javax.swing.JButton destinationButton;
        private javax.swing.JPanel destinationPanel;
        private javax.swing.JTextField destinationText;
        private javax.swing.JComboBox dirSelectCBox;
        private javax.swing.JComboBox<String> displayRangeComboBox;
        private javax.swing.JLabel displayRangeLabel;
        private javax.swing.JButton exitButton;
        private javax.swing.JMenu fileMenu;
        private javax.swing.JComboBox<String> filePatternComboBox;
        private javax.swing.JTextField filePatternTextField;
        private javax.swing.JTextArea fileSearchLogTextArea;
        private javax.swing.JComboBox filterSelectCBox;
        private javax.swing.JLabel hLabel;
        private javax.swing.JFormattedTextField hTextField;
        private javax.swing.JPanel imageChangePanel;
        private javax.swing.JComboBox imageFormatComboBox;
        private autoconverter.view.ImagePanel imagePanel;
        private javax.swing.JPanel imagePropertyPanel;
        private javax.swing.JScrollPane imageScrollPane;
        private javax.swing.JCheckBox jCheckBoxFilter1;
        private javax.swing.JCheckBox jCheckBoxFilter2;
        private javax.swing.JCheckBox jCheckBoxFilter3;
        private javax.swing.JCheckBox jCheckBoxFilter4;
        private javax.swing.JCheckBox jCheckBoxFilter5;
        private javax.swing.JCheckBox jCheckBoxFilter6;
        private javax.swing.JCheckBox jCheckBoxFilter7;
        private javax.swing.JCheckBox jCheckBoxRef1;
        private javax.swing.JCheckBox jCheckBoxRef2;
        private javax.swing.JCheckBox jCheckBoxRef3;
        private javax.swing.JCheckBox jCheckBoxRef4;
        private javax.swing.JCheckBox jCheckBoxRef5;
        private javax.swing.JCheckBox jCheckBoxRef6;
        private javax.swing.JCheckBox jCheckBoxRef7;
        private javax.swing.JCheckBox jCheckBoxTarget1;
        private javax.swing.JCheckBox jCheckBoxTarget2;
        private javax.swing.JCheckBox jCheckBoxTarget3;
        private javax.swing.JCheckBox jCheckBoxTarget4;
        private javax.swing.JCheckBox jCheckBoxTarget5;
        private javax.swing.JCheckBox jCheckBoxTarget6;
        private javax.swing.JCheckBox jCheckBoxTarget7;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabelFilter;
        private javax.swing.JLabel jLabelVisible;
        private javax.swing.JMenuBar jMenuBar1;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JPanel jPanel2;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JRadioButton manualRadioButton;
        private javax.swing.JSpinner maxSpinner;
        private javax.swing.JLabel messageLabel;
        private javax.swing.JSpinner minSpinner;
        private javax.swing.JComboBox modeSelector;
        private javax.swing.JButton nextButton;
        private javax.swing.JPanel paramPanel;
        private autoconverter.view.PlotPanel plotPanel;
        private javax.swing.JComboBox positionSelectCBox;
        private javax.swing.JPanel proceedPanel;
        private javax.swing.JRadioButton recursiveRadioButton;
        private javax.swing.ButtonGroup referenceButtonGroup;
        private javax.swing.JPanel regexPanel;
        private javax.swing.JRadioButton removeSpecialCharRadioButton;
        private javax.swing.JCheckBox resizeRadioButton;
        private javax.swing.JSpinner resizeSpinner;
        private javax.swing.JPanel scalePanel;
        private autoconverter.view.range.RangeSlider scaleRangeSlider;
        private javax.swing.JPanel slide1;
        private javax.swing.JPanel slide2;
        private javax.swing.JPanel slide3;
        private javax.swing.JScrollPane slideScrollPane1;
        private javax.swing.JScrollPane slideScrollPane2;
        private javax.swing.JScrollPane slideScrollPane3;
        private javax.swing.JButton sourceButton;
        private javax.swing.JPanel sourcePanel;
        private javax.swing.JTextField sourceText;
        private javax.swing.JPanel southPanel;
        private javax.swing.JPanel spinnerPanel;
        private javax.swing.JLabel subtractLabel;
        private javax.swing.JPanel subtractionPanel;
        private javax.swing.JTextArea summaryDisplayArea;
        private javax.swing.JLabel summaryLabel;
        private javax.swing.JScrollPane summaryScrollPane;
        private javax.swing.ButtonGroup targetButtonGroup;
        private javax.swing.JComboBox timeSelectCBox;
        private javax.swing.JLabel wLabel;
        private javax.swing.JFormattedTextField wTextField;
        private javax.swing.JComboBox wellSelectCBox;
        private javax.swing.JLabel xLabel;
        private javax.swing.JFormattedTextField xTextField;
        private javax.swing.JLabel yLabel;
        private javax.swing.JFormattedTextField yTextField;
        private javax.swing.JComboBox zSelectCBox;
        // End of variables declaration//GEN-END:variables

	/**
	 * @return the appController
	 */
	public ApplicationController getAppController() {
		return this.appController;
	}

	public JComboBox getDirSelectCBox() {
		return dirSelectCBox;
	}

	public void setDirSelectCBox(JComboBox dirSelectCBox) {
		this.dirSelectCBox = dirSelectCBox;
	}

	public JComboBox getFilterSelectCBox() {
		return filterSelectCBox;
	}

	public void setFilterSelectCBox(JComboBox filterSelectCBox) {
		this.filterSelectCBox = filterSelectCBox;
	}

	public JComboBox getPositionSelectCBox() {
		return positionSelectCBox;
	}

	public void setPositionSelectCBox(JComboBox positionSelectCBox) {
		this.positionSelectCBox = positionSelectCBox;
	}

	public JComboBox getTimeSelectCBox() {
		return timeSelectCBox;
	}

	public void setTimeSelectCBox(JComboBox timeSelectCBox) {
		this.timeSelectCBox = timeSelectCBox;
	}

	public JComboBox getWellSelectCBox() {
		return wellSelectCBox;
	}

	public void setWellSelectCBox(JComboBox wellSelectCBox) {
		this.wellSelectCBox = wellSelectCBox;
	}

	public JComboBox getzSelectCBox() {
		return zSelectCBox;
	}

	public void setzSelectCBox(JComboBox zSelectCBox) {
		this.zSelectCBox = zSelectCBox;
	}

	/**
	 * イメージを選択するComboBoxのリスト
	 *
	 * @return
	 */
	public ArrayList<JComboBox> getSelectCBoxes() {
		ArrayList<JComboBox> list;
		list = new ArrayList();
		list.add(this.dirSelectCBox);
		list.add(this.filterSelectCBox);
		list.add(this.positionSelectCBox);
		list.add(this.wellSelectCBox);
		list.add(this.zSelectCBox);
		list.add(this.timeSelectCBox);
		return list;
	}

	/**
	 * @return the backButton
	 */
	public javax.swing.JButton getBackButton() {
		return backButton;
	}

	/**
	 * @return the brightnessPanel
	 */
	public javax.swing.JPanel getBrightnessPanel() {
		return brightnessPanel;
	}

	/**
	 * @return the cancelButton
	 */
	public javax.swing.JButton getCancelButton() {
		return cancelButton;
	}

	/**
	 * @return the centerPanel
	 */
	public javax.swing.JPanel getCenterPanel() {
		return centerPanel;
	}


	/**
	 * @return the colorSelectPanel
	 */
	public javax.swing.JPanel getColorSelectPanel() {
		return colorSelectPanel;
	}

	/**
	 * @return the colorSelectScrollPane
	 */
	public javax.swing.JScrollPane getColorSelectScrollPane() {
		return colorSelectScrollPane;
	}

	/**
	 * @return the convertLabel
	 */
	public javax.swing.JLabel getConvertLabel() {
		return convertLabel;
	}

	/**
	 * @return the destinationButton
	 */
	public javax.swing.JButton getDestinationButton() {
		return destinationButton;
	}

	/**
	 * @return the destinationText
	 */
	public javax.swing.JTextField getDestinationText() {
		return destinationText;
	}

	/**
	 * @return the imageChangePanel
	 */
	public javax.swing.JPanel getImageChangePanel() {
		return imageChangePanel;
	}

	/**
	 * @return the imageFormatComboBox
	 */
	public javax.swing.JComboBox getImageFormatComboBox() {
		return imageFormatComboBox;
	}

	/**
	 * @return the imagePropertyPanel
	 */
	public javax.swing.JPanel getImagePropertyPanel() {
		return imagePropertyPanel;
	}

	/**
	 * @return the imageScrollPane
	 */
	public javax.swing.JScrollPane getImageScrollPane() {
		return imageScrollPane;
	}


	/**
	 * @return the jComboBox1
	 */
	public javax.swing.JComboBox getjComboBox1() {
		return getModeSelector();
	}

	/**
	 * @return the jComboBox2
	 */
	public javax.swing.JComboBox getColorChannelSelector() {
		return colorChannelSelector;
	}

	/**
	 * @return the jMenu1
	 */
	public javax.swing.JMenu getjMenu1() {
		return fileMenu;
	}

	/**
	 * @return the jMenuBar1
	 */
	public javax.swing.JMenuBar getjMenuBar1() {
		return jMenuBar1;
	}

	/**
	 * @return the maxSpinner
	 */
	public javax.swing.JSpinner getMaxSpinner() {
		return maxSpinner;
	}

	/**
	 * @return the messageLabel
	 */
	public javax.swing.JLabel getMessageLabel() {
		return messageLabel;
	}

	/**
	 * @return the minSpinner
	 */
	public javax.swing.JSpinner getMinSpinner() {
		return minSpinner;
	}

	/**
	 * @return the nextButton
	 */
	public javax.swing.JButton getNextButton() {
		return nextButton;
	}

	/**
	 * @return the proceedPanel
	 */
	public javax.swing.JPanel getProceedPanel() {
		return proceedPanel;
	}

	/**
	 * @return the recursiveRadioButton
	 */
	public javax.swing.JRadioButton getRecursiveRadioButton() {
		return recursiveRadioButton;
	}

	/**
	 * @return the slide1
	 */
	public javax.swing.JPanel getSlide1() {
		return slide1;
	}

	/**
	 * @return the slide2
	 */
	public javax.swing.JPanel getSlide2() {
		return slide2;
	}

	/**
	 * @return the slide3
	 */
	public javax.swing.JPanel getSlide3() {
		return slide3;
	}

	/**
	 * @return the slideScrollPane1
	 */
	public javax.swing.JScrollPane getSlideScrollPane1() {
		return slideScrollPane1;
	}

	/**
	 * @return the slideScrollPane2
	 */
	public javax.swing.JScrollPane getSlideScrollPane2() {
		return slideScrollPane2;
	}

	/**
	 * @return the slideScrollPane3
	 */
	public javax.swing.JScrollPane getSlideScrollPane3() {
		return slideScrollPane3;
	}

	/**
	 * @return the sourceButton
	 */
	public javax.swing.JButton getSourceButton() {
		return sourceButton;
	}

	/**
	 * @return the sourceText
	 */
	public javax.swing.JTextField getSourceText() {
		return sourceText;
	}

	/**
	 * @return the southPanel
	 */
	public javax.swing.JPanel getSouthPanel() {
		return southPanel;
	}


	/**
	 * @return the brightnessAutoGroup
	 */
	public javax.swing.ButtonGroup getBrightnessAutoGroup() {
		return brightnessAutoGroup;
	}

	/**
	 * @return the autoRadioButton
	 */
	public javax.swing.JRadioButton getAutoRadioButton() {
		return autoRadioButton;
	}

	/**
	 * @return the manualRadioButton
	 */
	public javax.swing.JRadioButton getManualRadioButton() {
		return manualRadioButton;
	}

	/**
	 * @return the adjustButton
	 */
	public javax.swing.JButton getAdjustButton() {
		return adjustButton;
	}

	/**
	 *
	 * @return
	 */
	public javax.swing.JTextArea getSummaryDisplayArea() {
		return summaryDisplayArea;
	}

	public javax.swing.JButton getConvertButton() {
		return convertButton;
	}

	public ImagePanel getImageDisplayPanel() {
		return imagePanel;
	}

	public PlotPanel getPlotPanel() {
		return plotPanel;
	}

	public RangeSlider getScaleRangeSlider() {
		return scaleRangeSlider;
	}

	/**
	 * @return the resizeRadioButton
	 */
	public javax.swing.JCheckBox getResizeRadioButton() {
		return resizeRadioButton;
	}

	/**
	 * @param resizeRadioButton the resizeRadioButton to set
	 */
	public void setResizeRadioButton(javax.swing.JCheckBox resizeRadioButton) {
		this.resizeRadioButton = resizeRadioButton;
	}

	/**
	 * @return the resizeSpinner
	 */
	public javax.swing.JSpinner getResizeSpinner() {
		return resizeSpinner;
	}

	/**
	 * @param resizeSpinner the resizeSpinner to set
	 */
	public void setResizeSpinner(javax.swing.JSpinner resizeSpinner) {
		this.resizeSpinner = resizeSpinner;
	}

	/**
	 * @return the fileSearchLogTextArea
	 */
	public javax.swing.JTextArea getFileSearchLogTextArea() {
		return fileSearchLogTextArea;
	}

	/**
	 * @param fileSearchLogTextArea the fileSearchLogTextArea to set
	 */
	public void setFileSearchLogTextArea(javax.swing.JTextArea fileSearchLogTextArea) {
		this.fileSearchLogTextArea = fileSearchLogTextArea;
	}

	/**
	 * @return the regexpTextField
	 */
	public javax.swing.JTextField getRegexpTextField() {
		return getFilePatternTextField();
	}

	/**
	 * @param regexpTextField the regexpTextField to set
	 */
	public void setRegexpTextField(javax.swing.JTextField regexpTextField) {
		this.setFilePatternTextField(regexpTextField);
	}

	/**
	 * @return the filePatternComboBox
	 */
	public javax.swing.JComboBox<String> getFilePatternComboBox() {
		return filePatternComboBox;
	}

	/**
	 * @param filePatternComboBox the filePatternComboBox to set
	 */
	public void setFilePatternComboBox(javax.swing.JComboBox<String> filePatternComboBox) {
		this.filePatternComboBox = filePatternComboBox;
	}

	/**
	 * @return the filePatternTextField
	 */
	public javax.swing.JTextField getFilePatternTextField() {
		return filePatternTextField;
	}

	/**
	 * @param filePatternTextField the filePatternTextField to set
	 */
	public void setFilePatternTextField(javax.swing.JTextField filePatternTextField) {
		this.filePatternTextField = filePatternTextField;
	}

	/**
	 * @return the displayRangeComboBox
	 */
	public javax.swing.JComboBox<String> getDisplayRangeComboBox() {
		return displayRangeComboBox;
	}

	/**
	 * @param displayRangeComboBox the displayRangeComboBox to set
	 */
	public void setDisplayRangeComboBox(javax.swing.JComboBox<String> displayRangeComboBox) {
		this.displayRangeComboBox = displayRangeComboBox;
	}

	/**
	 * @return the removeSpecialCharRadioButton
	 */
	public javax.swing.JRadioButton getRemoveSpecialCharRadioButton() {
		return removeSpecialCharRadioButton;
	}

	/**
	 * @param removeSpecialCharRadioButton the removeSpecialCharRadioButton
	 * to set
	 */
	public void setRemoveSpecialCharRadioButton(javax.swing.JRadioButton removeSpecialCharRadioButton) {
		this.removeSpecialCharRadioButton = removeSpecialCharRadioButton;
	}

	/**
	 * @return the autoTypeComboBox
	 */
	public javax.swing.JComboBox<String> getAutoTypeComboBox() {
		return autoTypeComboBox;
	}

	/**
	 * @param autoTypeComboBox the autoTypeComboBox to set
	 */
	public void setAutoTypeComboBox(javax.swing.JComboBox<String> autoTypeComboBox) {
		this.autoTypeComboBox = autoTypeComboBox;
	}

	/**
	 * @return the addParamRadioButton
	 */
	public javax.swing.JRadioButton getAddParamRadioButton() {
		return addParamRadioButton;
	}

	/**
	 * @param addParamRadioButton the addParamRadioButton to set
	 */
	public void setAddParamRadioButton(javax.swing.JRadioButton addParamRadioButton) {
		this.addParamRadioButton = addParamRadioButton;
	}

	/**
	 * @return the ballSizeSpinner
	 */
	public javax.swing.JSpinner getBallSizeSpinner() {
		return ballSizeSpinner;
	}

	/**
	 * @param ballSizeSpinner the ballSizeSpinner to set
	 */
	public void setBallSizeSpinner(javax.swing.JSpinner ballSizeSpinner) {
		this.ballSizeSpinner = ballSizeSpinner;
	}

	/**
	 * @return the cropAreaPanel
	 */
	public javax.swing.JPanel getCropAreaPanel() {
		return cropAreaPanel;
	}

	/**
	 * @param cropAreaPanel the cropAreaPanel to set
	 */
	public void setCropAreaPanel(javax.swing.JPanel cropAreaPanel) {
		this.cropAreaPanel = cropAreaPanel;
	}

	/**
	 * @return the xTextField
	 */
	public javax.swing.JTextField getxTextField() {
		return xTextField;
	}

	/**
	 * @param xTextField the xTextField to set
	 */
	public void setxTextField(javax.swing.JFormattedTextField xTextField) {
		this.xTextField = xTextField;
	}

	/**
	 * @return the yTextField
	 */
	public javax.swing.JFormattedTextField getyTextField() {
		return yTextField;
	}

	/**
	 * @param yTextField the yTextField to set
	 */
	public void setyTextField(javax.swing.JFormattedTextField yTextField) {
		this.yTextField = yTextField;
	}

	/**
	 * @return the wTextField
	 */
	public javax.swing.JTextField getwTextField() {
		return wTextField;
	}

	/**
	 * @param wTextField the wTextField to set
	 */
	public void setwTextField(javax.swing.JFormattedTextField wTextField) {
		this.wTextField = wTextField;
	}

	/**
	 * @return the hTextField
	 */
	public javax.swing.JTextField gethTextField() {
		return hTextField;
	}

	/**
	 * @param hTextField the hTextField to set
	 */
	public void sethTextField(javax.swing.JFormattedTextField hTextField) {
		this.hTextField = hTextField;
	}

	/**
	 * @return the filterCheckBoxList
	 */
	public ArrayList<JCheckBox> getFilterCheckBoxList() {
		return filterCheckBoxList;
	}

	/**
	 * @return the targetCheckBoxList
	 */
	public ArrayList<JCheckBox> getTargetCheckBoxList() {
		return targetCheckBoxList;
	}
	public ArrayList<JCheckBox> getReferenceCheckBoxList() {
		return referenceCheckBoxList;
	}

	/**
	 * @return the modeSelector
	 */
	public javax.swing.JComboBox getModeSelector() {
		return modeSelector;
	}

	/**
	 * @param modeSelector the modeSelector to set
	 */
	public void setModeSelector(javax.swing.JComboBox modeSelector) {
		this.modeSelector = modeSelector;
	}

	/**
	 * @param filterCheckBoxList the filterCheckBoxList to set
	 */
	public void setFilterCheckBoxList(ArrayList<JCheckBox> filterCheckBoxList) {
		this.filterCheckBoxList = filterCheckBoxList;
	}

	/**
	 * @param targetCheckBoxList the targetCheckBoxList to set
	 */
	public void setTargetCheckBoxList(ArrayList<JCheckBox> targetCheckBoxList) {
		this.targetCheckBoxList = targetCheckBoxList;
	}

	/**
	 * @param referenceCheckBoxList the referenceCheckBoxList to set
	 */
	public void setReferenceCheckBoxList(ArrayList<JCheckBox> referenceCheckBoxList) {
		this.referenceCheckBoxList = referenceCheckBoxList;
	}

	public JLabel getLabelFilter(){
		return this.jLabelFilter;
	}


}
