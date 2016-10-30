/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package autoconverter.model;

import autoconverter.controller.ApplicationController;
import java.util.ArrayList;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.TreeSet;
import java.util.logging.Logger;
import autoconverter.controller.AutoConverterUtils;
import ij.IJ;

/**
 *
 * @author yfujita
 */
public class ImageSet {

	private static final Logger logger = AutoConverterUtils.getLogger();
	/**
	 * 全てのCaptureImage. imageIDをキーとしたハッシュ.
	 */
	private HashMap<String, CaptureImage> fileHashMap;
	/**
	 * イメージショット中のすべてのファイル
	 */
	private ArrayList<CaptureImage> files;
	/**
	 * 同じImage IDごとに分類されたファイルリスト. 結果的にfilter以外の共通部分.
	 */
	private HashMap<String, ArrayList<CaptureImage>> shotHash;
	/**
	 * イメージの保存ディレクトリ.
	 */
	private TreeSet<String> directories;
	/**
	 * well の名前
	 */
	private TreeSet<String> wellNames;
	/*:
	 * well番号
	 */
	private TreeSet<String> wells;
	/**
	 * ショットのwell内の位置
	 */
	private TreeSet<String> positions;
	/**
	 * スライス. ここではz軸方向.
	 */
	private TreeSet<String> slices;
	/**
	 * 時間
	 */
	private TreeSet<String> times;
	/**
	 * フィルタ
	 */
	private TreeSet<String> filters;
	private LinkedHashSet<String> shotIDs;
	private int shotIndex;
	private ArrayList<String> shotIDArray;
	private ArrayList<Collection> parameter_collection;

	public ImageSet() {
		files = new ArrayList<CaptureImage>();
		fileHashMap = new HashMap<String, CaptureImage>();
		shotHash = new HashMap<String, ArrayList<CaptureImage>>();
		directories = new TreeSet<String>();
		wellNames = new TreeSet<String>(new WellNameComparator());
		wells = new TreeSet<String>();
		positions = new TreeSet<String>();
		slices = new TreeSet<String>();
		times = new TreeSet<String>();
		filters = new TreeSet<String>();
		shotIDs = new LinkedHashSet<String>();
		shotIndex = 0;
		this.wrapParameters();
	}

	/**
	 * parameter_collectionに全てのCollectionを入れておいて、
	 * 共通に何か操作したい時に、iteratorでまとめて操作できるように する.
	 */
	private void wrapParameters() {
		parameter_collection = new ArrayList<Collection>();
		this.parameter_collection.add(files);
		this.parameter_collection.add(directories);
		this.parameter_collection.add(wellNames);
		this.parameter_collection.add(wells);
		this.parameter_collection.add(positions);
		this.parameter_collection.add(slices);
		this.parameter_collection.add(times);
		this.parameter_collection.add(filters);
		this.parameter_collection.add(shotIDs);
	}

	public ImageSet(ImageSet _set) {
		this.files = _set.getFiles();
		this.shotHash = _set.getShotHash();
		this.directories = _set.getDirectories();
		this.wellNames = _set.getWellNames();
		this.wells = _set.getWells();
		this.positions = _set.getPositions();
		this.slices = _set.getSlices();
		this.times = _set.getTimes();
		this.filters = _set.getFilters();
		this.shotIDs = _set.getShotIDs();
		this.shotIndex = 0;
		this.wrapParameters();
	}

	public ImageSet(ArrayList<CaptureImage> _images) {
		this();
		for (CaptureImage _img : _images) {
			this.addFile(_img);
		}
	}

	/**
	 * clear set
	 */
	public void clear() {
		for (Collection col : this.parameter_collection) {
			col.clear();
		}
		this.shotHash.clear();
		this.shotIndex = 0;
	}

	/**
	 * ファイルを追加する.
	 *
	 * @param _img
	 */
	public void addFile(CaptureImage _img) {
		if(_img == null){
			IJ.showMessage("_img == null. This is bug! Report to Yoshihiko Fujita.");
			return;
		}
		this.getFiles().add(_img);
		this.getDirectories().add(_img.getDirectory());
		this.getWellNames().add(_img.getWellName());
		this.getWells().add(_img.getWell());
		this.getPositions().add(_img.getPosition());
		this.getSlices().add(_img.getSlice());
		this.getTimes().add(_img.getTime());
		this.getFilters().add(_img.getFilter());
		if (!this.shotHash.containsKey(_img.getShotID())) {
			this.getShotHash().put(_img.getShotID(), new ArrayList<CaptureImage>());
		}
		this.getShotHash().get(_img.getShotID()).add(_img);
		this.getShotIDs().add(_img.getShotID());
		this.getFileHashMap().put(_img.getImageID(), _img);
	}

	public void logFileInfo() {
		if(this.getFiles().isEmpty() ){
			logger.fine("Files in imageSet == 0.");
		}
		for(CaptureImage s: this.getFiles()){
			logger.fine(s.getInfo());
		}
		
	}

	/**
	 * 画像ファイルの数を返す
	 *
	 * @return
	 */
	public int getSize() {
		return this.getFiles().size();
	}

	/**
	 * ショットの数を返す.
	 *
	 * @return
	 */
	public int getShotSize() {
		return this.getShotHash().size();
	}

	/**
	 * 画像ファイルを返す.
	 *
	 * @param _index
	 * @return
	 */
	public CaptureImage getCaptureImageAt(int _index) {
		return this.getFiles().get(_index);
	}

	/**
	 * 画像ファイルを返す.
	 *
	 * @param _imageID
	 * @return
	 */
	public CaptureImage getCaptureImageAt(String _imageID) {
		return this.getFileHashMap().get(_imageID);
	}

	/**
	 * 画像ファイルを返す.
	 *
	 * @param dir
	 * @param wellname
	 * @param position
	 * @param slice
	 * @param time
	 * @param filter
	 * @return
	 */
	public CaptureImage getCaptureImageAt(String dir, String wellname, String position, String slice, String time, String filter) {
		String _imageID = ApplicationController.createImageID(dir, wellname, position, slice, time, filter);
		return this.getCaptureImageAt(_imageID);
	}

	/**
	 * _index番目のショットを返す.
	 *
	 * @param _index
	 * @return
	 */
	public ArrayList<CaptureImage> getShotAt(int _index) {
		return this.getShotAt(this.getShotIDs().toArray(new String[0])[_index]);
	}

	/**
	 * ファイルを追加する.
	 *
	 * @param _f
	 */
	public void addFile(File _f) {
		CaptureImage _img = new CaptureImage(_f);
		this.addFile(_img);
	}

	/**
	 * ファイルの数(ショットではない)を返す.
	 *
	 * @return
	 */
	public int size() {
		if (this.files == null) {
			return 0;
		}
		return this.files.size();
	}

	/**
	 * ショットの数を返す.
	 *
	 * @return
	 */
	public int shotSize() {
		if (shotIDArray == null || this.shotIDs == null) {
			return 0;
		}
		return this.shotIDs.size();
	}

	/**
	 * 条件に合う画像を集めて、ImageSetとして返す.
	 *
	 * @param _directory
	 * @param _wellname
	 * @param _well
	 * @param _position
	 * @param _slice
	 * @param _time
	 * @param _filter
	 * @return
	 */
	public ImageSet getImageSet(String _directory, String _wellname, String _position, String _slice, String _time, String _filter) {
		/* About search order. If you want to search objects from 5 dimension array
     * (Na, Nb, Nc, Nd, Ne), Which order is best?
     * (Na > Nb > Nc > Nd > Ne)
     * Na*Nb*Nc*Nd*Ne + Nb*Nc*Nd*Ne + Nc*Nd*Ne
		 */
		ImageSet _imageSet = new ImageSet();
		for (CaptureImage _img : this.files) {
			if (_img.isDirectory(_directory)
				&& _img.isFilter(_filter)
				&& _img.isPosition(_position)
				&& _img.isSlice(_slice)
				&& _img.isTime(_time)
				&& _img.isWellName(_wellname)) {
				_imageSet.addFile(_img);
			}
		}
		return _imageSet;
	}

	/**
	 * 画像ファイルをパス指定で加える.
	 *
	 * @param path
	 */
	public void addFile(String path) {
		if (path == null) {
			return;
		}
		this.addFile(new File(path));
	}

	/**
	 * ファイル一覧を取得する.
	 *
	 * @return the files
	 */
	public ArrayList<CaptureImage> getFiles() {
		return files;
	}

	/**
	 * shotIDをキーとした同一ショットの集まりを返す. フィルタをまとめて1セットとしたショット.
	 *
	 * @return the filter2Image
	 */
	public HashMap<String, ArrayList<CaptureImage>> getShotHash() {
		return shotHash;
	}

	/**
	 * shotIDと一致する同一ショットの画像リストを返す.
	 *
	 * @param _shotID
	 * @return
	 */
	public ArrayList<CaptureImage> getShotAt(String _shotID) {
		return this.getShotHash().get(_shotID);
	}

	//public ArrayList<CaptureImage> getShotAt(String _directory, String _wellname, String _position, String _slice, String _time, String _filter) {
	//}
	/**
	 * @return the directories
	 */
	public TreeSet<String> getDirectories() {
		return directories;
	}

	/**
	 * @return the wellNames
	 */
	public TreeSet<String> getWellNames() {
		return wellNames;
	}

	/**
	 * @return the wells
	 */
	public TreeSet<String> getWells() {
		return wells;
	}

	/**
	 * @return the positions
	 */
	public TreeSet<String> getPositions() {
		return positions;
	}

	/**
	 * @return the slices
	 */
	public TreeSet<String> getSlices() {
		return slices;
	}

	/**
	 * @return the times
	 */
	public TreeSet<String> getTimes() {
		return times;
	}

	/**
	 * @return the filters
	 */
	public TreeSet<String> getFilters() {
		return filters;
	}

	/**
	 * @return the shotIDs
	 */
	public LinkedHashSet<String> getShotIDs() {
		return shotIDs;
	}

	/*
	public void postInitialize(){

	}*/
	/**
	 * @return the fileHashMap
	 */
	public HashMap<String, CaptureImage> getFileHashMap() {
		return fileHashMap;
	}


}
