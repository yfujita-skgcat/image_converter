/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoconverter.controller;

import autoconverter.model.ImageSet;
import autoconverter.view.BaseFrame;
import ij.IJ;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

/**
 * File を取得するWorker. ファイル検索をバックグラウンドで動かして、途中結果を
 * BaseFrame のfileSearchLogTextArea に書き出す.
 * 検索後は getImageList, getImages で検索したファイル一覧を取得することができる.
 * @author yfujita
 */
public class FileSearchWorker extends SwingWorker <ArrayList<File>, String>{

	private static Logger logger = AutoConverterUtils.getLogger();
	private final boolean recursive;
	private final ImageSet imageSet;
        private ArrayList<File> imageList;
	private final File srcPath;
	private final ApplicationController appCtrl;

	public FileSearchWorker(File _srcPath, boolean _recursive){
		super();
		recursive = _recursive;
		imageSet = new ImageSet();
		srcPath = _srcPath;
		appCtrl = ApplicationController.getInstance();
	}

	public FileSearchWorker(String _srcPath, boolean _recursive){
		this(new File(_srcPath), _recursive);
	}



	@Override
	protected ArrayList<File> doInBackground() {
		try {
			imageList = recursiveSearch(srcPath);
		} catch (InterruptedException ex) {
			logger.info("FIle search canceled");
		}
		logger.fine("imageList.size() == " + imageList.size());
		Collections.sort(imageList);
		logger.fine("imageList.size() == " + imageList.size());
		logger.fine("imageSet.size() == " + this.imageSet.size());
		try{
			for (Iterator<File> it = imageList.iterator(); it.hasNext();) {
				File f = it.next();
				logger.fine(f.getName());
				this.imageSet.addFile(f);
				logger.fine(f.getName());
			}
		} catch(IllegalArgumentException e){
			logger.fine(e.toString());
			IJ.showMessage(e.toString());
			return null;
		} catch(IllegalStateException e){
			logger.fine(e.toString());
			IJ.showMessage(e.toString());
			return null;
		} catch(NullPointerException e){
			logger.fine(e.toString());
			IJ.showMessage(e.toString());
			return null;
		}
		logger.fine("imageSet.size() == " + this.imageSet.size());
		
		//return "DONE";
		return imageList;
	}
	
	@Override
	protected void process(List<String> _list) {
		ApplicationController appCont = ApplicationController.getInstance();
		BaseFrame baseFrame = appCont.getBaseFrame();
		JTextArea textArea = baseFrame.getFileSearchLogTextArea();
		for (String s : _list) {
			textArea.append(s + "\n");
			//waitDialogInformationArea.append(s + "\n");
			//waitDialogInformationLabel.setText(s + " files were found.");
		}
	}
	
	@Override
	protected void done() {
		BaseFrame baseFrame = ApplicationController.getInstance().getBaseFrame();
		JTextArea textArea = baseFrame.getFileSearchLogTextArea();
		textArea.append("DONE");
		//waitDialogInformationArea.append("DONE");
		// imageSet が空だったらエラーだして終了.
		if (imageSet.size() < 1) {
			logger.fine("No shot found.");
			IJ.showMessage("No shot found");
			return;
		}
		// ApplicationController にimageSet をセットする
		// あとのimageSetの処理は全部ApplicationControllerで行う.
		appCtrl.setImageSet(imageSet);
		
		if (this.isCancelled()) {
			logger.fine("Canceled.");
			this.getImageSet().clear();
		} else {
			logger.fine("DONE");
			
			textArea.append("Next Card");
			// ここでページをすすめる
			appCtrl.initializeImageConfigurationPane();
			appCtrl.getCardLayout().next(baseFrame.getCenterPanel());
			appCtrl.incrementCardIndex();
			appCtrl.updateWizerdButton();
		}
	}
	


	/**
	 * WaitDialog.recursive によって再帰検索か単純検索かを決定してFileのリストを返す.
	 */
	public ArrayList<File> recursiveSearch(File top) throws InterruptedException {
		ArrayList<File> list = new ArrayList();
		Pattern filePattern = appCtrl.getFilePattern();

		int counter = 0;
		String[] contents = top.list();
		for (int i = 0; i < contents.length; i++) {
			if (Thread.interrupted()) {
				throw new InterruptedException();
			}
			File sdir = new File(top, contents[i]);
			if (sdir.isDirectory() && recursive) {
				list.addAll(recursiveSearch(sdir));
				continue;
			}
			// ここを正規表現で最初からマッチさせる
			//if (filePattern.matcher(sdir.getName()).find() ) {
			if (filePattern.matcher(sdir.getName()).matches() ) {
				if (!sdir.getName().matches("_thumb_")) {
					publish("FOUND: " + sdir.getAbsolutePath());
					list.add(sdir);
				}
			} else {
				//logger.fine(sdir.getAbsolutePath() + " is not tiff file");
				publish("SKIP: " + sdir.getAbsolutePath());
			}
		}
		return list;
	}

	/**
	 * @return the imageSet
	 */
	public ImageSet getImageSet() {
		return imageSet;
	}
	
}
