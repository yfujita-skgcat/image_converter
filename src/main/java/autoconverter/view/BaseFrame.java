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

import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import autoconverter.controller.AutoConverterConfig;
import autoconverter.controller.AutoConverterUtils;
import autoconverter.controller.ApplicationController;
import autoconverter.view.range.RangeSlider;
import java.awt.event.ItemEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSpinner;

/**
 *
 * @author yfujita
 */
public class BaseFrame extends javax.swing.JFrame {

	private static Logger logger = AutoConverterUtils.getLogger();
	public static final int MAX_CARD_SIZE = 3;

	/**
	 * @return the logger
	 */
	public static Logger getLogger() {
		return logger;
	}
	//private ImageSet imageSet;
	//private String oldSearchPath;
	//private int rangeSliderHighValue;
	//private int rangeSliderLowValue;
	private final ApplicationController appController;

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
		Integer _height = Integer.valueOf(AutoConverterConfig.getConfig(AutoConverterConfig.KEY_MAIN_FRAME_SIZE_Y, "300", null ));

		this.appController = new ApplicationController(this);

		initComponents();

		String selected_item = AutoConverterConfig.getConfig(AutoConverterConfig.KEY_SELECTED_PATTERN, AutoConverterConfig.REGEXP_NAME_CELAVIEW, null);
		this.initFilePatternComboBox(selected_item);

		String _srcDir = AutoConverterConfig.getConfig(AutoConverterConfig.KEY_SOURCE_DIRECTORY, null, null);
		String _dstDir = AutoConverterConfig.getConfig(AutoConverterConfig.KEY_DESTINATION_DIRECTORY, null, null);
		//logger.fine(_srcDir);
		//logger.fine(_dstDir);
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
		if(_remove_spec_char.equals("true")){
			this.removeSpecialCharRadioButton.setSelected(true);
		} else {
			this.removeSpecialCharRadioButton.setSelected(false);
		}

		// add param load
		String _add_param = AutoConverterConfig.getConfig(AutoConverterConfig.KEY_ADD_PARAM_TO_FILENAME, "true",null);
		if(_add_param.equals("true")){
			this.addParamRadioButton.setSelected(true);
		} else {
			this.addParamRadioButton.setSelected(false);
		}

		String disp_range = AutoConverterConfig.getConfig(AutoConverterConfig.KEY_SELECTED_DISPLAY_RANGE, "4095", null);
		this.displayRangeComboBox.getModel().setSelectedItem(disp_range);

		this.appController.updateWizerdButton();
		//logger.fine(java.text.MessageFormat.format(java.util.ResourceBundle.getBundle("autoconverter/controller/Bundle").getString("SETTING TO ({0}, {1})"), new Object[] {_width, _height}));
		this.setSize(new Dimension(_width, _height));
	}


	/**
	 * ファイルパターンを表示するcomboBox 設定.
	 * 
	 * @param selected_item 選択するitemを指定する. null の場合は指定しない
	 */
	public void initFilePatternComboBox(String selected_item){
		ArrayList<String> filePatterns = AutoConverterConfig.getFilePatternNames();
		if(filePatterns.size() > 0){
			DefaultComboBoxModel itemList = new DefaultComboBoxModel(filePatterns.toArray());
			if(selected_item != null){
			  itemList.setSelectedItem(selected_item);
			}
			//logger.fine(selected_item);
			String regexString = AutoConverterConfig.getConfig(selected_item, "", AutoConverterConfig.PREFIX_REGEXP);
			if(selected_item.equals(AutoConverterConfig.REGEXP_NAME_CELAVIEW)){
				regexString = AutoConverterConfig.celaviewRegexpString;
				this.getFilePatternTextField().setEditable(false);
				this.filePatternComboBox.setEditable(false);
			} else if(selected_item.equals(AutoConverterConfig.REGEXP_NAME_INCELL6000)){
				regexString = AutoConverterConfig.inCell6000RegexpString;
				this.getFilePatternTextField().setEditable(false);
				this.filePatternComboBox.setEditable(false);
			} else if(selected_item.equals(AutoConverterConfig.REGEXP_NAME_IX81)){
				regexString = AutoConverterConfig.IX81RegexpString;
				this.getFilePatternTextField().setEditable(false);
				this.filePatternComboBox.setEditable(false);
			} else if(selected_item.equals(AutoConverterConfig.REGEXP_NAME_CUSTOM)){
				regexString = "";
			}
			this.filePatternComboBox.setModel(itemList);
			this.getFilePatternTextField().setText(regexString);
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {
                java.awt.GridBagConstraints gridBagConstraints;

                brightnessAutoGroup = new javax.swing.ButtonGroup();
                centerPanel = new javax.swing.JPanel();
                slideScrollPane1 = new javax.swing.JScrollPane();
                slide1 = new javax.swing.JPanel();
                recursiveRadioButton = new javax.swing.JRadioButton();
                sourceText = new javax.swing.JTextField();
                destinationText = new javax.swing.JTextField();
                sourceButton = new javax.swing.JButton();
                destinationButton = new javax.swing.JButton();
                imageFormatComboBox = new javax.swing.JComboBox();
                convertLabel = new javax.swing.JLabel();
                resizeRadioButton = new javax.swing.JCheckBox();
                resizeSpinner = new javax.swing.JSpinner();
                jLabel1 = new javax.swing.JLabel();
                tif_checkbox = new javax.swing.JCheckBox();
                jpg_checkbox = new javax.swing.JCheckBox();
                png_checkbox = new javax.swing.JCheckBox();
                jScrollPane1 = new javax.swing.JScrollPane();
                fileSearchLogTextArea = new javax.swing.JTextArea();
                filePatternComboBox = new javax.swing.JComboBox<>();
                filePatternTextField = new javax.swing.JTextField();
                displayRangeLabel = new javax.swing.JLabel();
                displayRangeComboBox = new javax.swing.JComboBox<>();
                removeSpecialCharRadioButton = new javax.swing.JRadioButton();
                addParamRadioButton = new javax.swing.JRadioButton();
                slideScrollPane2 = new javax.swing.JScrollPane();
                slide2 = new javax.swing.JPanel();
                imageScrollPane = new javax.swing.JScrollPane();
                imagePanel = new autoconverter.view.ImagePanel();
                imageChangePanel = new javax.swing.JPanel();
                dirSelectCBox = new javax.swing.JComboBox();
                wellSelectCBox = new javax.swing.JComboBox();
                positionSelectCBox = new javax.swing.JComboBox();
                timeSelectCBox = new javax.swing.JComboBox();
                filterSelectCBox = new javax.swing.JComboBox();
                zSelectCBox = new javax.swing.JComboBox();
                imagePropertyPanel = new javax.swing.JPanel();
                colorSelectScrollPane = new javax.swing.JScrollPane();
                colorSelectPanel = new javax.swing.JPanel();
                modeSelecter = new javax.swing.JComboBox();
                colorChannelSelector = new javax.swing.JComboBox();
                channelLabel = new java.awt.Label();
                jCheckBox1 = new javax.swing.JCheckBox();
                jCheckBox2 = new javax.swing.JCheckBox();
                jCheckBox3 = new javax.swing.JCheckBox();
                jCheckBox4 = new javax.swing.JCheckBox();
                jCheckBox5 = new javax.swing.JCheckBox();
                jCheckBox6 = new javax.swing.JCheckBox();
                jCheckBox7 = new javax.swing.JCheckBox();
                visibleLabel = new java.awt.Label();
                jCheckBox8 = new javax.swing.JCheckBox();
                jCheckBox9 = new javax.swing.JCheckBox();
                jCheckBox10 = new javax.swing.JCheckBox();
                jCheckBox11 = new javax.swing.JCheckBox();
                jCheckBox12 = new javax.swing.JCheckBox();
                jCheckBox13 = new javax.swing.JCheckBox();
                jCheckBox14 = new javax.swing.JCheckBox();
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
                messageLabel = new javax.swing.JLabel();
                proceedPanel = new javax.swing.JPanel();
                cancelButton = new javax.swing.JButton();
                convertButton = new javax.swing.JButton();
                cardMovePanel = new javax.swing.JPanel();
                backButton = new javax.swing.JButton();
                nextButton = new javax.swing.JButton();
                jMenuBar1 = new javax.swing.JMenuBar();
                jMenu1 = new javax.swing.JMenu();
                jMenu2 = new javax.swing.JMenu();

                setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
                setMinimumSize(new java.awt.Dimension(630, 400));
                addComponentListener(new java.awt.event.ComponentAdapter() {
                        public void componentResized(java.awt.event.ComponentEvent evt) {
                                formComponentResized(evt);
                        }
                });

                centerPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
                centerPanel.setLayout(new java.awt.CardLayout());

                slide1.setPreferredSize(new java.awt.Dimension(450, 300));

                java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("autoconverter/controller/Bundle"); // NOI18N
                recursiveRadioButton.setText(bundle.getString("BaseFrame.recursiveRadioButton.text")); // NOI18N
                recursiveRadioButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                recursiveRadioButtonActionPerformed(evt);
                        }
                });

                sourceText.setEditable(false);

                destinationText.setEditable(false);

                sourceButton.setText(bundle.getString("BaseFrame.sourceButton.text")); // NOI18N
                sourceButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                sourceButtonActionPerformed(evt);
                        }
                });

                destinationButton.setText(bundle.getString("BaseFrame.destinationButton.text")); // NOI18N
                destinationButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                destinationButtonActionPerformed(evt);
                        }
                });

                imageFormatComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "jpg", "8bit tiff", "png" }));

                convertLabel.setText(bundle.getString("AutoConverter.convertLabel.text")); // NOI18N

                resizeRadioButton.setText(bundle.getString("BaseFrame.resizeRadioButton.text")); // NOI18N

                jLabel1.setText(bundle.getString("BaseFrame.jLabel1.text")); // NOI18N

                tif_checkbox.setSelected(true);
                tif_checkbox.setText(bundle.getString("BaseFrame.tif_checkbox.text")); // NOI18N

                jpg_checkbox.setText(bundle.getString("BaseFrame.jpg_checkbox.text")); // NOI18N
                jpg_checkbox.setEnabled(false);

                png_checkbox.setText(bundle.getString("BaseFrame.png_checkbox.text")); // NOI18N
                png_checkbox.setEnabled(false);

                fileSearchLogTextArea.setColumns(20);
                fileSearchLogTextArea.setRows(5);
                fileSearchLogTextArea.setBorder(javax.swing.BorderFactory.createTitledBorder(null, bundle.getString("file_search_log"), javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION)); // NOI18N
                jScrollPane1.setViewportView(fileSearchLogTextArea);

                filePatternComboBox.setEditable(true);
                filePatternComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Celaview", "In Cell 6000", "Custom" }));
                filePatternComboBox.addItemListener(new java.awt.event.ItemListener() {
                        public void itemStateChanged(java.awt.event.ItemEvent evt) {
                                filePatternComboBoxItemStateChanged(evt);
                        }
                });

                filePatternTextField.setText(bundle.getString("BaseFrame.filePatternTextField.text")); // NOI18N

                displayRangeLabel.setText(bundle.getString("BaseFrame.displayRangeLabel.text")); // NOI18N

                displayRangeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4095", "65535" }));
                displayRangeComboBox.setActionCommand(bundle.getString("BaseFrame.displayRangeComboBox.actionCommand")); // NOI18N
                displayRangeComboBox.addItemListener(new java.awt.event.ItemListener() {
                        public void itemStateChanged(java.awt.event.ItemEvent evt) {
                                displayRangeComboBoxItemStateChanged(evt);
                        }
                });

                removeSpecialCharRadioButton.setText(bundle.getString("BaseFrame.removeSpecialCharRadioButton.text")); // NOI18N
                removeSpecialCharRadioButton.addChangeListener(new javax.swing.event.ChangeListener() {
                        public void stateChanged(javax.swing.event.ChangeEvent evt) {
                                removeSpecialCharRadioButtonStateChanged(evt);
                        }
                });

                addParamRadioButton.setText(bundle.getString("BaseFrame.addParamRadioButton.text")); // NOI18N
                addParamRadioButton.addChangeListener(new javax.swing.event.ChangeListener() {
                        public void stateChanged(javax.swing.event.ChangeEvent evt) {
                                addParamRadioButtonStateChanged(evt);
                        }
                });

                javax.swing.GroupLayout slide1Layout = new javax.swing.GroupLayout(slide1);
                slide1.setLayout(slide1Layout);
                slide1Layout.setHorizontalGroup(
                        slide1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(slide1Layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addGroup(slide1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1)
                                        .addGroup(slide1Layout.createSequentialGroup()
                                                .addGroup(slide1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(convertLabel)
                                                        .addGroup(slide1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                .addComponent(sourceButton, javax.swing.GroupLayout.Alignment.LEADING, 0, 1, Short.MAX_VALUE)
                                                                .addComponent(destinationButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(slide1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(destinationText)
                                                        .addComponent(sourceText)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, slide1Layout.createSequentialGroup()
                                                                .addGap(24, 24, 24)
                                                                .addComponent(imageFormatComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(119, 119, 119)
                                                                .addComponent(resizeRadioButton)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(resizeSpinner, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE))))
                                        .addGroup(slide1Layout.createSequentialGroup()
                                                .addComponent(filePatternComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(filePatternTextField))
                                        .addGroup(slide1Layout.createSequentialGroup()
                                                .addGroup(slide1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(slide1Layout.createSequentialGroup()
                                                                .addComponent(jLabel1)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(tif_checkbox)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jpg_checkbox)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(png_checkbox)
                                                                .addGap(111, 111, 111)
                                                                .addComponent(displayRangeLabel)
                                                                .addGap(4, 4, 4)
                                                                .addComponent(displayRangeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(slide1Layout.createSequentialGroup()
                                                                .addComponent(recursiveRadioButton)
                                                                .addGap(65, 65, 65)
                                                                .addComponent(removeSpecialCharRadioButton)
                                                                .addGap(71, 71, 71)
                                                                .addComponent(addParamRadioButton)))
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
                );
                slide1Layout.setVerticalGroup(
                        slide1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(slide1Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(slide1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(recursiveRadioButton)
                                        .addComponent(removeSpecialCharRadioButton)
                                        .addComponent(addParamRadioButton))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(slide1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(sourceText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(sourceButton))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(slide1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(destinationText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(destinationButton))
                                .addGap(18, 18, 18)
                                .addGroup(slide1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(imageFormatComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(convertLabel)
                                        .addComponent(resizeRadioButton)
                                        .addComponent(resizeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(slide1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(tif_checkbox)
                                        .addComponent(jpg_checkbox)
                                        .addComponent(png_checkbox)
                                        .addComponent(displayRangeLabel)
                                        .addComponent(displayRangeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(slide1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(filePatternComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(filePatternTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
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

                dirSelectCBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "folder" }));
                dirSelectCBox.addItemListener(new java.awt.event.ItemListener() {
                        public void itemStateChanged(java.awt.event.ItemEvent evt) {
                                imageSelectorChanged(evt);
                        }
                });
                imageChangePanel.add(dirSelectCBox);

                wellSelectCBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "well" }));
                wellSelectCBox.addItemListener(new java.awt.event.ItemListener() {
                        public void itemStateChanged(java.awt.event.ItemEvent evt) {
                                imageSelectorChanged(evt);
                        }
                });
                imageChangePanel.add(wellSelectCBox);

                positionSelectCBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "position" }));
                positionSelectCBox.addItemListener(new java.awt.event.ItemListener() {
                        public void itemStateChanged(java.awt.event.ItemEvent evt) {
                                imageSelectorChanged(evt);
                        }
                });
                imageChangePanel.add(positionSelectCBox);

                timeSelectCBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "time" }));
                timeSelectCBox.addItemListener(new java.awt.event.ItemListener() {
                        public void itemStateChanged(java.awt.event.ItemEvent evt) {
                                imageSelectorChanged(evt);
                        }
                });
                imageChangePanel.add(timeSelectCBox);

                filterSelectCBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "filter" }));
                filterSelectCBox.addItemListener(new java.awt.event.ItemListener() {
                        public void itemStateChanged(java.awt.event.ItemEvent evt) {
                                imageSelectorChanged(evt);
                        }
                });
                imageChangePanel.add(filterSelectCBox);

                zSelectCBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "z" }));
                imageChangePanel.add(zSelectCBox);

                slide2.add(imageChangePanel, java.awt.BorderLayout.SOUTH);

                imagePropertyPanel.setMinimumSize(new java.awt.Dimension(500, 200));
                imagePropertyPanel.setPreferredSize(new java.awt.Dimension(550, 300));
                imagePropertyPanel.setLayout(new java.awt.BorderLayout());

                colorSelectScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                colorSelectScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

                colorSelectPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, bundle.getString("BaseFrame.colorSelectPanel.border.title"), javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION)); // NOI18N
                colorSelectPanel.setLayout(new java.awt.GridBagLayout());

                modeSelecter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Single", "Merge" }));
                modeSelecter.setEnabled(false);
                modeSelecter.setPreferredSize(new java.awt.Dimension(150, 30));
                modeSelecter.addItemListener(new java.awt.event.ItemListener() {
                        public void itemStateChanged(java.awt.event.ItemEvent evt) {
                                modeSelecterItemStateChanged(evt);
                        }
                });
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 0;
                gridBagConstraints.gridwidth = 2;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
                colorSelectPanel.add(modeSelecter, gridBagConstraints);

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
                gridBagConstraints.gridwidth = 2;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
                colorSelectPanel.add(colorChannelSelector, gridBagConstraints);

                channelLabel.setAlignment(java.awt.Label.CENTER);
                channelLabel.setText(bundle.getString("BaseFrame.channelLabel.text")); // NOI18N
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 1;
                gridBagConstraints.gridy = 2;
                colorSelectPanel.add(channelLabel, gridBagConstraints);

                jCheckBox1.setText(bundle.getString("BaseFrame.jCheckBox1.text")); // NOI18N
                jCheckBox1.setEnabled(false);
                jCheckBox1.setMaximumSize(new java.awt.Dimension(200, 22));
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 1;
                gridBagConstraints.gridy = 3;
                colorSelectPanel.add(jCheckBox1, gridBagConstraints);

                jCheckBox2.setText(bundle.getString("BaseFrame.jCheckBox2.text")); // NOI18N
                jCheckBox2.setEnabled(false);
                jCheckBox2.setMaximumSize(new java.awt.Dimension(200, 22));
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 1;
                gridBagConstraints.gridy = 4;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
                colorSelectPanel.add(jCheckBox2, gridBagConstraints);

                jCheckBox3.setText(bundle.getString("BaseFrame.jCheckBox3.text")); // NOI18N
                jCheckBox3.setEnabled(false);
                jCheckBox3.setMaximumSize(new java.awt.Dimension(200, 22));
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 1;
                gridBagConstraints.gridy = 5;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
                colorSelectPanel.add(jCheckBox3, gridBagConstraints);

                jCheckBox4.setText(bundle.getString("BaseFrame.jCheckBox4.text")); // NOI18N
                jCheckBox4.setEnabled(false);
                jCheckBox4.setMaximumSize(new java.awt.Dimension(200, 22));
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 1;
                gridBagConstraints.gridy = 6;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
                colorSelectPanel.add(jCheckBox4, gridBagConstraints);

                jCheckBox5.setText(bundle.getString("BaseFrame.jCheckBox5.text")); // NOI18N
                jCheckBox5.setEnabled(false);
                jCheckBox5.setMaximumSize(new java.awt.Dimension(200, 22));
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 1;
                gridBagConstraints.gridy = 7;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
                colorSelectPanel.add(jCheckBox5, gridBagConstraints);

                jCheckBox6.setText(bundle.getString("BaseFrame.jCheckBox6.text")); // NOI18N
                jCheckBox6.setEnabled(false);
                jCheckBox6.setMaximumSize(new java.awt.Dimension(200, 22));
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 1;
                gridBagConstraints.gridy = 8;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
                colorSelectPanel.add(jCheckBox6, gridBagConstraints);

                jCheckBox7.setText(bundle.getString("BaseFrame.jCheckBox7.text")); // NOI18N
                jCheckBox7.setEnabled(false);
                jCheckBox7.setMaximumSize(new java.awt.Dimension(200, 22));
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 1;
                gridBagConstraints.gridy = 9;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
                colorSelectPanel.add(jCheckBox7, gridBagConstraints);

                visibleLabel.setAlignment(java.awt.Label.RIGHT);
                visibleLabel.setText(bundle.getString("BaseFrame.visibleLabel.text")); // NOI18N
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 2;
                colorSelectPanel.add(visibleLabel, gridBagConstraints);

                jCheckBox8.setEnabled(false);
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 3;
                colorSelectPanel.add(jCheckBox8, gridBagConstraints);

                jCheckBox9.setEnabled(false);
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 4;
                colorSelectPanel.add(jCheckBox9, gridBagConstraints);

                jCheckBox10.setEnabled(false);
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 5;
                colorSelectPanel.add(jCheckBox10, gridBagConstraints);

                jCheckBox11.setEnabled(false);
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 6;
                colorSelectPanel.add(jCheckBox11, gridBagConstraints);

                jCheckBox12.setEnabled(false);
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 7;
                colorSelectPanel.add(jCheckBox12, gridBagConstraints);

                jCheckBox13.setEnabled(false);
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 8;
                colorSelectPanel.add(jCheckBox13, gridBagConstraints);

                jCheckBox14.setEnabled(false);
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 9;
                colorSelectPanel.add(jCheckBox14, gridBagConstraints);

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
                scalePanel.add(scaleRangeSlider);

                brightnessPanel.add(scalePanel, java.awt.BorderLayout.SOUTH);

                plotPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, bundle.getString("BaseFrame.brightnessPanel.border.title"), javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION)); // NOI18N

                javax.swing.GroupLayout plotPanelLayout = new javax.swing.GroupLayout(plotPanel);
                plotPanel.setLayout(plotPanelLayout);
                plotPanelLayout.setHorizontalGroup(
                        plotPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 588, Short.MAX_VALUE)
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

                messageLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
                messageLabel.setPreferredSize(new java.awt.Dimension(40, 30));
                southPanel.add(messageLabel, java.awt.BorderLayout.SOUTH);

                proceedPanel.setPreferredSize(new java.awt.Dimension(450, 100));
                proceedPanel.setLayout(new java.awt.BorderLayout());

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
                proceedPanel.add(cancelButton, java.awt.BorderLayout.WEST);

                convertButton.setText(bundle.getString("BaseFrame.convertButton.text")); // NOI18N
                convertButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                convertButtonActionPerformed(evt);
                        }
                });
                proceedPanel.add(convertButton, java.awt.BorderLayout.EAST);

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

                southPanel.add(proceedPanel, java.awt.BorderLayout.CENTER);

                getContentPane().add(southPanel, java.awt.BorderLayout.SOUTH);

                jMenu1.setText(bundle.getString("BaseFrame.jMenu1.text")); // NOI18N
                jMenuBar1.add(jMenu1);

                jMenu2.setText(bundle.getString("BaseFrame.jMenu1.text")); // NOI18N
                jMenuBar1.add(jMenu2);

                setJMenuBar(jMenuBar1);

                pack();
        }// </editor-fold>//GEN-END:initComponents

  private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed

		this.appController.nextCard();
		// TODO add your handling code here:
  }//GEN-LAST:event_nextButtonActionPerformed

  private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
		this.appController.previousCard();
		// TODO add your handling code here:
  }//GEN-LAST:event_backButtonActionPerformed

  private void sourceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sourceButtonActionPerformed
		File _srcDir = AutoConverterUtils.getDirectory(this, AutoConverterConfig.getConfig(AutoConverterConfig.KEY_SOURCE_DIRECTORY, null, null));
		if (_srcDir == null) {
			return;
		}
		this.getSourceText().setText(_srcDir.getAbsolutePath());
		appController.storeDirectorySetting(true);
		this.appController.updateWizerdButton();
  }//GEN-LAST:event_sourceButtonActionPerformed

  private void destinationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_destinationButtonActionPerformed
		File _dstDir = AutoConverterUtils.getDirectory(this, AutoConverterConfig.getConfig(AutoConverterConfig.KEY_DESTINATION_DIRECTORY, null, null));
		if (_dstDir == null) {
			return;
		}
		this.getDestinationText().setText(_dstDir.getAbsolutePath());
		appController.storeDirectorySetting(true);
  }//GEN-LAST:event_destinationButtonActionPerformed

	/**
	 * Store main window size to property file everytime the window size is
	 * changed.
	 *
	 * @param evt
	 */
  private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
		Component _c = evt.getComponent();
		Dimension _d = _c.getSize();
		AutoConverterConfig.setConfig(AutoConverterConfig.KEY_MAIN_FRAME_SIZE_X, _d.width);
		AutoConverterConfig.setConfig(AutoConverterConfig.KEY_MAIN_FRAME_SIZE_Y, _d.height);
		AutoConverterConfig.save(this, true);
  }//GEN-LAST:event_formComponentResized

	/**
	 * Close window if cancel button is clicked.
	 *
	 * @param evt
	 */
  private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
	  // とりあえず今のところexit buttonとして使う.
	  System.exit(0);
  }//GEN-LAST:event_cancelButtonActionPerformed

  private void recursiveRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recursiveRadioButtonActionPerformed
		if (evt.getSource() != this.getRecursiveRadioButton()) {
			return;
		}
		appController.storeRecursiveSetting(true);
  }//GEN-LAST:event_recursiveRadioButtonActionPerformed

  private void rangeSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rangeSliderStateChanged
		//logger.fine("State Changed! lowValue=" + this.rangeSlider.getLowValue() +", highValue=" + this.rangeSlider.getHighValue());
		// I can't understand how to notify that property of rangeSlider changed.
		this.appController.updateRangeSlider();
		// TODO add your handling code here:
  }//GEN-LAST:event_rangeSliderStateChanged

  private void modeSelecterItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_modeSelecterItemStateChanged
		// TODO add your handling code here:
		getLogger().fine(evt.getItem().toString());
  }//GEN-LAST:event_modeSelecterItemStateChanged

        private void colorChannelSelectorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_colorChannelSelectorItemStateChanged
					// TODO add your handling code here:
					//getLogger().fine(evt.getItem().toString());
					//this.appController.setColor(evt.getItem().toString());
					this.appController.setColor(evt.getItem().toString());
        }//GEN-LAST:event_colorChannelSelectorItemStateChanged

	/**
	 * 選択された画像を再描画する.
	 * 主に、画像洗濯用のComboBoxが変更された時に呼ばれる.
	 * @param evt 
	 */
  private void imageSelectorChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_imageSelectorChanged

		/*
		 * 画像選択用のComboBoxが変更された時に呼ぶ
		 */
		//getLogger().fine(evt.getItem().toString());
		ArrayList<JComboBox> boxes = this.getSelectCBoxes();
		for (Iterator<JComboBox> it = boxes.iterator(); it.hasNext();) {
			JComboBox box;
			box = it.next();
			String item = (String) box.getSelectedItem();
      //this.shotID = this.directory + "_" + this.wellName + "-" + this.well + "-" + this.position + "-" + this.slice + "-" + this.time;
			//getLogger().fine("selected:" + item);
		}
		String dir = this.getSourceText().getText() +  (String) this.getDirSelectCBox().getSelectedItem();
		String wellname = (String) this.getWellSelectCBox().getSelectedItem();
		String position = (String) this.getPositionSelectCBox().getSelectedItem();
		String slice = (String) this.getzSelectCBox().getSelectedItem();
		String time = (String) this.getTimeSelectCBox().getSelectedItem();
		String filter = (String) this.getFilterSelectCBox().getSelectedItem();
		String imageID = dir + java.text.MessageFormat.format(java.util.ResourceBundle.getBundle("autoconverter/controller/Bundle").getString("-{0}-{1}-{2}-{3}-{4}"), new Object[] {wellname, position, slice, time, filter});
		this.getAppController().updateImage(imageID);
		this.getAppController().updateDensityPlot();

  }//GEN-LAST:event_imageSelectorChanged



        private void scaleRangeSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_scaleRangeSliderStateChanged
                // TODO add your handling code here:
                if(evt.getSource() == this.getScaleRangeSlider()){
                        int lower = this.scaleRangeSlider.getValue();
                        int upper = this.getScaleRangeSlider().getUpperValue();
                        this.getAppController().setScaleValues(lower, upper);
                        this.getAppController().setScaleMaxValues(upper);
                        this.getAppController().updateDensityPlot();
                }
        }//GEN-LAST:event_scaleRangeSliderStateChanged

	/**
	 * minSpinner の最大値をmaxSpinnerの値-1に設定する.
	 * @param evt 
	 */
  private void maxSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_maxSpinnerStateChanged
		if(evt.getSource() == this.maxSpinner){
			Integer max = (Integer) this.maxSpinner.getValue();
		  this.getAppController().setMinSpinnerMax(max.intValue()-1);
		  this.getScaleRangeSlider().setUpperValue(max);
		  this.appController.storeCurrentFilterSettings();
		}
  }//GEN-LAST:event_maxSpinnerStateChanged

	/**
	 * maxSpinner の最小値をminSpinnerの値+1に設定する.
	 * @param evt 
	 */
  private void minSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_minSpinnerStateChanged
		if(evt.getSource() == this.minSpinner){
			Integer min = (Integer) this.minSpinner.getValue();
		  this.getAppController().setMaxSpinnerMin(min.intValue()+1);
		  this.getScaleRangeSlider().setLowerValue(min);
		  this.appController.storeCurrentFilterSettings();
		}
  }//GEN-LAST:event_minSpinnerStateChanged

  private void autoRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autoRadioButtonActionPerformed
		if(evt.getSource() == this.getAutoRadioButton() &&  this.getAutoRadioButton().isSelected() ){
			this.appController.configAutoRelatedComponents(true);
			this.appController.adjustValues();
			this.appController.storeCurrentFilterSettings();
		}
  }//GEN-LAST:event_autoRadioButtonActionPerformed

  private void manualRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manualRadioButtonActionPerformed
		if(evt.getSource() == this.getManualRadioButton() &&  this.getManualRadioButton().isSelected() ){
			this.appController.configAutoRelatedComponents(false);
			this.appController.storeCurrentFilterSettings();
		}
  }//GEN-LAST:event_manualRadioButtonActionPerformed

  /**
   * adjust button が押されたら画像の輝度値を自動調整.
   * @param evt 
   */
        private void adjustButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adjustButtonActionPerformed
                // TODO add your handling code here:
		this.appController.adjustValues();
        }//GEN-LAST:event_adjustButtonActionPerformed

        private void convertButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_convertButtonActionPerformed
                // TODO add your handling code here:
		this.appController.convertImages();
        }//GEN-LAST:event_convertButtonActionPerformed

        private void ballSizeSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ballSizeSpinnerStateChanged
                if( evt.getSource() == this.ballSizeSpinner){
                        JSpinner spinner = (JSpinner) evt.getSource();
                        Integer val = (Integer) spinner.getValue();
                        logger.fine(java.util.ResourceBundle.getBundle("autoconverter/controller/Bundle").getString("SUBTRACT BACKGROUND FROM LISTNER."));
                        //this.appController.subtractBackground(val.intValue());
                        this.appController.storeCurrentFilterSettings();
                        this.appController.updateImage();
                }
                // TODO add your handling code here:
        }//GEN-LAST:event_ballSizeSpinnerStateChanged

        private void filePatternComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_filePatternComboBoxItemStateChanged
		if(this.getFilePatternComboBox() != evt.getSource()){
			return;
		}
		String selectedPatternName = (String) evt.getItem();
		if(evt.getStateChange() == ItemEvent.SELECTED ){
			if(selectedPatternName.equals(AutoConverterConfig.REGEXP_NAME_CELAVIEW) ||
				selectedPatternName.equals(AutoConverterConfig.REGEXP_NAME_INCELL6000) ){
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
			AutoConverterConfig.save(this, true);
		} else if (evt.getStateChange() == ItemEvent.DESELECTED){
			// 1つ前の選択のpatternを取ってきてconfigに保存
			String regexPattern = this.getFilePatternTextField().getText();
			if(regexPattern.equals("")){
				AutoConverterConfig.removeConfig(selectedPatternName, AutoConverterConfig.PREFIX_REGEXP);
			} else {
				AutoConverterConfig.setConfig(selectedPatternName, regexPattern, AutoConverterConfig.PREFIX_REGEXP);
			}
		}
        }//GEN-LAST:event_filePatternComboBoxItemStateChanged

        private void displayRangeComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_displayRangeComboBoxItemStateChanged
		if( evt.getSource() != this.displayRangeComboBox){
			return;
		}
		if(evt.getStateChange() == ItemEvent.SELECTED){
			appController.storeDisplayRangeMaxSetting(true);
		}
        }//GEN-LAST:event_displayRangeComboBoxItemStateChanged

        private void removeSpecialCharRadioButtonStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_removeSpecialCharRadioButtonStateChanged
         	if (evt.getSource() != this.getRemoveSpecialCharRadioButton()) {
			return;
		}
		appController.storeRemoveSpecialCharSetting(true);
        }//GEN-LAST:event_removeSpecialCharRadioButtonStateChanged

        private void autoTypeComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_autoTypeComboBoxItemStateChanged
		if(evt.getSource() != this.autoTypeComboBox){
			return;
		}

		if(this.autoRadioButton.isSelected()){
			appController.adjustValues();
		}
		appController.storeCurrentFilterSettings();
        }//GEN-LAST:event_autoTypeComboBoxItemStateChanged

        private void addParamRadioButtonStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_addParamRadioButtonStateChanged
		if(evt.getSource() != this.getAddParamRadioButton()){
			return;
		}
		appController.storeAddParamSetting(true);

        }//GEN-LAST:event_addParamRadioButtonStateChanged

        private void imagePanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imagePanelMousePressed
		this.imagePanel.setStart(evt.getX(), evt.getY());
        }//GEN-LAST:event_imagePanelMousePressed

        private void imagePanelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imagePanelMouseReleased
		this.imagePanel.setEnd(evt.getX(), evt.getY());
        }//GEN-LAST:event_imagePanelMouseReleased

        private void imagePanelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imagePanelMouseDragged
		this.imagePanel.setNow(evt.getX(), evt.getY());
        }//GEN-LAST:event_imagePanelMouseDragged

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
        private java.awt.Label channelLabel;
        public javax.swing.JComboBox colorChannelSelector;
        private javax.swing.JPanel colorSelectPanel;
        private javax.swing.JScrollPane colorSelectScrollPane;
        private javax.swing.JButton convertButton;
        private javax.swing.JLabel convertLabel;
        private javax.swing.JButton destinationButton;
        private javax.swing.JTextField destinationText;
        private javax.swing.JComboBox dirSelectCBox;
        private javax.swing.JComboBox<String> displayRangeComboBox;
        private javax.swing.JLabel displayRangeLabel;
        private javax.swing.JComboBox<String> filePatternComboBox;
        private javax.swing.JTextField filePatternTextField;
        private javax.swing.JTextArea fileSearchLogTextArea;
        private javax.swing.JComboBox filterSelectCBox;
        private javax.swing.JPanel imageChangePanel;
        private javax.swing.JComboBox imageFormatComboBox;
        private autoconverter.view.ImagePanel imagePanel;
        private javax.swing.JPanel imagePropertyPanel;
        private javax.swing.JScrollPane imageScrollPane;
        private javax.swing.JCheckBox jCheckBox1;
        private javax.swing.JCheckBox jCheckBox10;
        private javax.swing.JCheckBox jCheckBox11;
        private javax.swing.JCheckBox jCheckBox12;
        private javax.swing.JCheckBox jCheckBox13;
        private javax.swing.JCheckBox jCheckBox14;
        private javax.swing.JCheckBox jCheckBox2;
        private javax.swing.JCheckBox jCheckBox3;
        private javax.swing.JCheckBox jCheckBox4;
        private javax.swing.JCheckBox jCheckBox5;
        private javax.swing.JCheckBox jCheckBox6;
        private javax.swing.JCheckBox jCheckBox7;
        private javax.swing.JCheckBox jCheckBox8;
        private javax.swing.JCheckBox jCheckBox9;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JMenu jMenu1;
        private javax.swing.JMenu jMenu2;
        private javax.swing.JMenuBar jMenuBar1;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JCheckBox jpg_checkbox;
        private javax.swing.JRadioButton manualRadioButton;
        private javax.swing.JSpinner maxSpinner;
        private javax.swing.JLabel messageLabel;
        private javax.swing.JSpinner minSpinner;
        public javax.swing.JComboBox modeSelecter;
        private javax.swing.JButton nextButton;
        private autoconverter.view.PlotPanel plotPanel;
        private javax.swing.JCheckBox png_checkbox;
        private javax.swing.JComboBox positionSelectCBox;
        private javax.swing.JPanel proceedPanel;
        private javax.swing.JRadioButton recursiveRadioButton;
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
        private javax.swing.JTextField sourceText;
        private javax.swing.JPanel southPanel;
        private javax.swing.JPanel spinnerPanel;
        private javax.swing.JLabel subtractLabel;
        private javax.swing.JPanel subtractionPanel;
        private javax.swing.JTextArea summaryDisplayArea;
        private javax.swing.JLabel summaryLabel;
        private javax.swing.JScrollPane summaryScrollPane;
        private javax.swing.JCheckBox tif_checkbox;
        private javax.swing.JComboBox timeSelectCBox;
        private java.awt.Label visibleLabel;
        private javax.swing.JComboBox wellSelectCBox;
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
	 * @return 
	 */
	public ArrayList<JComboBox> getSelectCBoxes (){
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
	 * @return the channelLabel
	 */
	public java.awt.Label getChannelLabel() {
		return channelLabel;
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
	 * @return the jCheckBox1
	 */
	public javax.swing.JCheckBox getjCheckBox1() {
		return jCheckBox1;
	}

	/**
	 * @return the jCheckBox10
	 */
	public javax.swing.JCheckBox getjCheckBox10() {
		return jCheckBox10;
	}

	/**
	 * @return the jCheckBox11
	 */
	public javax.swing.JCheckBox getjCheckBox11() {
		return jCheckBox11;
	}

	/**
	 * @return the jCheckBox12
	 */
	public javax.swing.JCheckBox getjCheckBox12() {
		return jCheckBox12;
	}

	/**
	 * @return the jCheckBox13
	 */
	public javax.swing.JCheckBox getjCheckBox13() {
		return jCheckBox13;
	}

	/**
	 * @return the jCheckBox14
	 */
	public javax.swing.JCheckBox getjCheckBox14() {
		return jCheckBox14;
	}

	/**
	 * @return the jCheckBox2
	 */
	public javax.swing.JCheckBox getjCheckBox2() {
		return jCheckBox2;
	}

	/**
	 * @return the jCheckBox3
	 */
	public javax.swing.JCheckBox getjCheckBox3() {
		return jCheckBox3;
	}

	/**
	 * @return the jCheckBox4
	 */
	public javax.swing.JCheckBox getjCheckBox4() {
		return jCheckBox4;
	}

	/**
	 * @return the jCheckBox5
	 */
	public javax.swing.JCheckBox getjCheckBox5() {
		return jCheckBox5;
	}

	/**
	 * @return the jCheckBox6
	 */
	public javax.swing.JCheckBox getjCheckBox6() {
		return jCheckBox6;
	}

	/**
	 * @return the jCheckBox7
	 */
	public javax.swing.JCheckBox getjCheckBox7() {
		return jCheckBox7;
	}

	/**
	 * @return the jCheckBox8
	 */
	public javax.swing.JCheckBox getjCheckBox8() {
		return jCheckBox8;
	}

	/**
	 * @return the jCheckBox9
	 */
	public javax.swing.JCheckBox getjCheckBox9() {
		return jCheckBox9;
	}

	/**
	 * @return the jComboBox1
	 */
	public javax.swing.JComboBox getjComboBox1() {
		return modeSelecter;
	}

	/**
	 * @return the jComboBox2
	 */
	public javax.swing.JComboBox getjComboBox2() {
		return colorChannelSelector;
	}


	/**
	 * @return the jMenu1
	 */
	public javax.swing.JMenu getjMenu1() {
		return jMenu1;
	}

	/**
	 * @return the jMenu2
	 */
	public javax.swing.JMenu getjMenu2() {
		return jMenu2;
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
	 * @return the visibleLabel
	 */
	public java.awt.Label getVisibleLabel() {
		return visibleLabel;
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
	public javax.swing.JTextArea getSummaryDisplayArea(){
		return summaryDisplayArea;
	}

	public javax.swing.JButton getConvertButton(){
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
	 * @return the jpg_checkbox
	 */
	public javax.swing.JCheckBox getJpg_checkbox() {
		return jpg_checkbox;
	}

	/**
	 * @return the png_checkbox
	 */
	public javax.swing.JCheckBox getPng_checkbox() {
		return png_checkbox;
	}

	/**
	 * @return the tif_checkbox
	 */
	public javax.swing.JCheckBox getTif_checkbox() {
		return tif_checkbox;
	}

	/**
	 * @param jpg_checkbox the jpg_checkbox to set
	 */
	public void setJpg_checkbox(javax.swing.JCheckBox jpg_checkbox) {
		this.jpg_checkbox = jpg_checkbox;
	}

	/**
	 * @param png_checkbox the png_checkbox to set
	 */
	public void setPng_checkbox(javax.swing.JCheckBox png_checkbox) {
		this.png_checkbox = png_checkbox;
	}

	/**
	 * @param tif_checkbox the tif_checkbox to set
	 */
	public void setTif_checkbox(javax.swing.JCheckBox tif_checkbox) {
		this.tif_checkbox = tif_checkbox;
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
	 * @param removeSpecialCharRadioButton the removeSpecialCharRadioButton to set
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

}
