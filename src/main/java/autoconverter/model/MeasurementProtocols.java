/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoconverter.model;

import autoconverter.controller.ApplicationController;
import autoconverter.controller.AutoConverterConfig;
import autoconverter.controller.AutoConverterUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author yfujita
 */
public class MeasurementProtocols {
	public static Hashtable <String, Hashtable<String, String>> channel2names = new Hashtable<String, Hashtable<String, String>>();
	public static Hashtable <String, Hashtable<String, String>> wellNumber2names = new Hashtable<String, Hashtable<String, String>>();
	public static char[] row_alphabet_char = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	//private static ArraySet <File> measurementProtocols;
	// フィルタの別名のHashtableをもつ. measurementProtocols.xml のファイルパスをキーとするハッシュ
	private Hashtable<String, Hashtable<String,String>> filterAliases;
	private static final Logger logger = AutoConverterUtils.getLogger();

	// 画像ファイルを指定するとパスからMeasurementProtocol.xml を探せる
	//public String convertFilterName(File image_file, String original_filter){
	public static String getChannelFilterName(File image_file, String original_filter){
		String new_filter = original_filter;
		//logger.fine("original_filter==" + original_filter);
		File mp = MeasurementProtocols.getMeasurementProtocolFile(image_file);
		if(mp == null){
			return new_filter;
		}
		try {
			MeasurementProtocols.readMeasurementProtocol(mp);
		} catch (Exception ex) {
			Logger.getLogger(MeasurementProtocols.class.getName()).log(Level.SEVERE, null, ex);
			return original_filter;
		}
		Hashtable<String, String> c2n = MeasurementProtocols.channel2names.get(mp.getAbsolutePath());
		if(c2n==null){
			logger.fine("c2n==null");
			return original_filter;
		}
		//Hashtable<String, String> w2n = MeasurementProtocols.wellNumber2names.get(image_file.getAbsolutePath());
		new_filter = c2n.get(original_filter);
		if(new_filter == null){
			logger.fine("new_filter==null");
			return original_filter;
		}
		//logger.fine("new_filter==" + new_filter);
		
		return new_filter;
	}
	public static String getWellName(File image_file, String original_name){
		String new_name = original_name;
		File mp = MeasurementProtocols.getMeasurementProtocolFile(image_file);
		if(mp==null){
			return new_name;
		}
		try {
			MeasurementProtocols.readMeasurementProtocol(mp);
		} catch (Exception ex) {
			Logger.getLogger(MeasurementProtocols.class.getName()).log(Level.SEVERE, null, ex);
			return original_name;
		}
		Hashtable<String, String> w2n = MeasurementProtocols.wellNumber2names.get(mp.getAbsolutePath());
		if(w2n == null){
			return original_name;
		}
		new_name = w2n.get(original_name);
		if(new_name==null){
			return original_name;
		}
		return new_name;
	}


	public static void readMeasurementProtocol(File mp) throws ParserConfigurationException, SAXException, IOException {
		String keyPath = mp.getAbsolutePath();
		if(MeasurementProtocols.channel2names.containsKey(keyPath) && MeasurementProtocols.wellNumber2names.containsKey(keyPath)){
			return;
		}
		Hashtable <String, String> c2f = new Hashtable<String,String>();
		Hashtable <String, String> w2n = new Hashtable<String,String>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		org.w3c.dom.Document doc =  builder.parse(mp);
		NodeList channels = doc.getElementsByTagName("icm:Channel");
		NodeList wells    = doc.getElementsByTagName("icm:Well");
		logger.fine("channels.getLength() = " + channels.getLength());
		if(channels.getLength() == 0){
			return;
		}
		for(int i=0; i < channels.getLength(); i++){ // Channel 対応を作製
			Node channel = channels.item(i);
			if( channel.getNodeType() != Node.ELEMENT_NODE){
				logger.fine("node: " + channel.toString() + " is not ELEMENT");
				continue;
			}
			logger.fine("NodeName  = " + channel.getNodeName());
			NamedNodeMap attributes = channel.getAttributes();
			Node channel_name_node = attributes.getNamedItem("icm:Name");
			if(channel_name_node == null){
				logger.fine("icm:Name is not found.");
				continue;
			}
			String channel_name = channel_name_node.getNodeValue();
			logger.fine("channel_name=" + channel_name);
			Matcher matcher = AutoConverterConfig.CQ1ChannelPattern.matcher(channel_name);
			if( matcher.matches() ){
				//logger.fine("group(0)=="+matcher.group(0)); // マッチ全体
				//logger.fine("group(1)=="+matcher.group(1)); // Ch(1), Ch(2), Ch(3),...
				//logger.fine("group(2)=="+matcher.group(2)); // Conforcal, PhaseContrast, ...
				//logger.fine("group(3)=="+matcher.group(3)); // 405nm, 488nm, ... 
				//logger.fine("group(4)=="+matcher.group(4)); // BP447, BP525, ...
				String ch_index = matcher.group(1);
				String ex_wave = matcher.group(4);
				String bright_field = matcher.group(2);
				if(bright_field != null && bright_field.equals("PhaseContrast")){
					ex_wave = bright_field;
				}
				logger.fine("C" + ch_index + " => " + ex_wave);
				c2f.put("C" + ch_index, ex_wave);
			}

		}
		logger.fine(keyPath + ": " + c2f);
		MeasurementProtocols.channel2names.put(keyPath, c2f);

		if(wells.getLength() == 0){
			return;
		}
		for(int i=0; i < wells.getLength(); i++){
			Node well = wells.item(i);
			if( well.getNodeType() != Node.ELEMENT_NODE){
				logger.fine("node: " + well.toString() + " is not ELEMENT");
				continue;
			}
			//logger.fine("NodeName  = " + well.getNodeName());
			NamedNodeMap attributes = well.getAttributes();
			Node node_wn   = attributes.getNamedItem("icm:Number");
			Node node_wcol = attributes.getNamedItem("icm:Column");
			Node node_wrow = attributes.getNamedItem("icm:Row");
			if(node_wn == null || node_wcol == null || node_wrow == null){
				logger.fine("icm:Name, icm:Column or icm:Row is not found.");
				continue;
			}
			int wellNumber = Integer.parseInt(node_wn.getNodeValue());
			int row_i = Integer.parseInt(node_wrow.getNodeValue());
			String row_alphabet = Character.toString(MeasurementProtocols.row_alphabet_char[row_i-1]);
			String coln = node_wcol.getNodeValue();
			// Wxxxx の形式(CQ1)にする.
			//System.out.println(String.format("%06d", 12));
			String cq1_well_number = "W" + String.format("%04d", wellNumber);
			logger.fine(cq1_well_number + " => " + row_alphabet + coln);
			w2n.put(cq1_well_number, row_alphabet + coln);
		}
		logger.fine(keyPath + ": " + w2n);
		MeasurementProtocols.wellNumber2names.put(keyPath, w2n);
	}

	private static File getMeasurementProtocolFile(File image_file){
		File image_directory = image_file.getParentFile();
		while(image_directory != null){
			String image_directory_path = image_directory.getAbsolutePath();
                        // image ファイルを上に辿っていって最初の MeasurementProtocol.xml を用いる
			String candidate = image_directory_path + File.separator + "MeasurementProtocol.xml";
			if(new File(candidate).canRead()){
				// 見つかったらそのMeasurementProtocol.xmlを返す
				return new File(candidate);
			}
			image_directory = image_directory.getParentFile(); // 上にたどる
		}
		return null;
	}

}