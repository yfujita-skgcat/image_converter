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
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
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
	private final boolean ignore_symlink;
	private final ImageSet imageSet;
        private ArrayList<File> imageList;
	private final File srcPath;
	private final ApplicationController appCtrl;

	public FileSearchWorker(File _srcPath, boolean _recursive, boolean _ignore_symlink){
		super();
		recursive = _recursive;
		ignore_symlink = _ignore_symlink;
		imageSet = new ImageSet();
		srcPath = _srcPath;
		appCtrl = ApplicationController.getInstance();
	}

	public FileSearchWorker(String _srcPath, boolean _recursive, boolean _ignore_symlink){
		this(new File(_srcPath), _recursive, _ignore_symlink);
	}



	@Override
	protected ArrayList<File> doInBackground() {
		try {
			imageList = recursiveSearch(srcPath);
		} catch (InterruptedException ex) {
			logger.info("FIle search canceled");
		}
		Collections.sort(imageList);
		try{
			for (Iterator<File> it = imageList.iterator(); it.hasNext();) {
				File f = it.next();
				logger.fine("Adding: " + f.getName());
				this.imageSet.addFile(f);
			}
		} catch(IllegalArgumentException e){
			logger.warning(AutoConverterUtils.stacktrace(e));
			return null;
		} catch(IllegalStateException e){
			logger.warning(AutoConverterUtils.stacktrace(e));
			return null;
		} catch(NullPointerException e){
			logger.warning(AutoConverterUtils.stacktrace(e));
			return null;
		}
		return imageList;
	}
	
	@Override
	protected void process(List<String> _list) {
		ApplicationController appCont = ApplicationController.getInstance();
		BaseFrame baseFrame = appCont.getBaseFrame();
		JTextArea textArea = baseFrame.getFileSearchLogTextArea();
		for (String s : _list) {
			textArea.append(s + "\n");
		}
	}
	
	@Override
	protected void done() {
		BaseFrame baseFrame = ApplicationController.getInstance().getBaseFrame();
		JTextArea textArea = baseFrame.getFileSearchLogTextArea();
		textArea.append("DONE\n");
		if (imageSet.size() < 1) {
			IJ.showMessage("No shot found");
			return;
		}
		// ApplicationController にimageSet をセットする
		// あとのimageSetの処理は全部ApplicationControllerで行う.
		appCtrl.setImageSet(imageSet);
		
		if (this.isCancelled()) {
			textArea.append("Canceled.\n");
			this.getImageSet().clear();
		} else {
			textArea.append("Proceed to next step.\n");
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
			if (filePattern.matcher(sdir.getName()).matches() ) {
				if (!sdir.getName().matches("_thumb_")) {
					if(Files.isSymbolicLink(sdir.toPath()) && ignore_symlink  ){
						publish("SKIP(symlink): " + sdir.getAbsolutePath());
					} else {
						publish("FOUND: " + sdir.getAbsolutePath());
						list.add(sdir);
					}
				}
			} else {
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
