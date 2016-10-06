/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package autoconverter.model;

import autoconverter.controller.AutoConverterUtils;
import java.io.File;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * キャプチャ画像. 画像のshotIDやその他の情報をカプセル化する.
 * @author yfujita
 */
public class CaptureImage {
  public static final Pattern celaviewPattern = Pattern.compile("([^-]+)--W(\\d+)--P(\\d+)--Z(\\d+)--T(\\d+)--(.*)\\.(?:tif|TIF|tiff|TIFF)");
  public static final Pattern ixPattern = Pattern.compile("(.*)_w\\d+(BF|NUA|NIBA|WBV|CFP|RFP|WIGA)\\.(?:tif|TIF|TIFF|tiff)");
  private static final Logger logger = AutoConverterUtils.getLogger();
  public static final int TYPE_CELAVIEW = 1;
  public static final int TYPE_IX= 2;
  public static final int TYPE_UNKNOWN = 3;
  public static final String NO_SPECIFIED = "";
  /**
   * イメージの保存先ディレクトリ
   */
  private String directory;
  /**
   * Wellの名前. A1, A2など.
   */
  private String wellName;
  /**
   * Well番号. A1 => 1, A2 =>2 など.
   */
  private String well;
  /**
   * Well内の位置番号.
   */
  private String position;
  /**
   * Z方向のスライス番号.
   */
  private String slice;
  /**
   * 時間番号.
   */
  private String time;
  /**
   * フィルター名.
   */
  private String filter;
  /**
   * 画像をキャプチャーした機器の種類.
   * TYPE_UNKNOWN, TYPE_CELAVIEW, TYPE_IX の3つが予約されている.
   */
  private int type;
  /**
   * shotID = [directory, wellName, well, position, slice, time].join("-")
   */
  private String shotID;
	/**
	 * imageID = [directory, wellName, well, position, slice, time, filter].join("-")
	 */
	private String imageID;
  /**
   * 実態のファイル.
   */
  private File file;

  /**
   * インスタンスを作製する.
   */
  public CaptureImage(){
    this.directory = "";
    this.wellName = "";
    this.well = CaptureImage.NO_SPECIFIED;
    this.position = CaptureImage.NO_SPECIFIED;
    this.slice = CaptureImage.NO_SPECIFIED;
    this.time = CaptureImage.NO_SPECIFIED;
    this.filter = "";
    this.type = CaptureImage.TYPE_UNKNOWN;
    this.shotID = "";
		this.imageID = "";
    this.file = null;
  }

  /**
   * ファイルを指定してインスタンスを作製する.
   * @param _f
   */
  public CaptureImage(File _f){
    this.setFile(_f);
  }

  /**
   * パスを指定してインスタンスを作製する.
   * @param _f
   */
  public CaptureImage(String _f){
    if(_f == null){
      return;
    }
    this.setFile(new File(_f));
  }

  /**
   * ファイルをセットする. ファイル名からwellName, well, position, filterなどを決定する.
   * @param _f
   */
  public void setFile(File _f){
    if (_f == null || !_f.canRead()) {
      return;
    }
    //Matcher m1 = CaptureImage.celaviewPattern.matcher(_f.getAbsolutePath());
    Matcher m1 = CaptureImage.celaviewPattern.matcher(_f.getName());
    Matcher m2 = CaptureImage.ixPattern.matcher(_f.getName());
    this.directory = _f.getParent();
    if (m1.matches()) {
      this.wellName = m1.group(1);
      this.well = m1.group(2);
      this.position = m1.group(3);
      this.slice = m1.group(4);
      this.time = m1.group(5);
      this.filter = m1.group(6);
      this.type = CaptureImage.TYPE_CELAVIEW;
    //"([^-]+)--W(\\d+)--P(\\d+)--Z(\\d+)--T(\\d+))--(.*)\\.(?:tif|TIF|tiff|TIFF)"
    } else if (m2.matches()) {
      //"(.*)_w\\d+(BF|NUA|NIBA|WBV|CFP|RFP|WIGA)\\.(?:tif|TIF|TIFF|tiff)"
      this.wellName = m2.group(1);
      this.filter = m2.group(2);
      this.type = CaptureImage.TYPE_IX;
    } else {
      logger.warning(_f.getAbsolutePath() + " is not supported.");
      this.wellName = _f.getAbsolutePath();
      this.type = CaptureImage.TYPE_UNKNOWN;
    }
    this.shotID = this.directory + "-" + this.wellName + "-" + this.position + "-" + this.slice + "-" + this.time;
    this.imageID = this.directory + "-" + this.wellName + "-" + this.position + "-" + this.slice + "-" + this.time + "-" + this.filter;
    this.file = _f;
  }

	/**
	 * imageIDが一致するか調べる. nullが与えられると、trueを返す.
	 * @param _imageID
	 * @return 
	 */
	public boolean isImageID(String _imageID){
		if(_imageID == null){
			return true;
		}
		return this.getImageID().equals(_imageID);
	}

  /**
   * shotIDが一致するか調べる. nullが与えられると、trueを返す.
   * @param _shotID
   * @return
   */
  public boolean isShotID(String _shotID){
    if(_shotID == null){
      return true;
    }
    return this.getShotID().equals(_shotID);
  }
  /**
   * directory が一致するか調べる. nullが与えられると、trueを返す.
   * @param _directory
   * @return
   */
  public boolean isDirectory(String _directory){
    if(_directory == null){
      return true;
    }
    return this.getDirectory().equals(_directory);
  }
  /**
   * well が一致するか調べる. nullが与えられると、trueを返す.
   * @param _well
   * @return
   */
  public boolean isWell(String _well){
    if(_well == CaptureImage.NO_SPECIFIED){ return true; }
    return this.getWell() == _well;
  }
  /**
   * wellName が一致するか調べる. nullが与えられると、trueを返す.
   * @param _wellName
   * @return
   */
  public boolean isWellName(String _wellName){
    if(_wellName == null){ return true; }
    return this.getWellName().equals(_wellName);
  }
  /**
   * position が一致するか調べる. NO_SPECIFIEDが与えられると、trueを返す.
   * @param _position
   * @return
   */
  public boolean isPosition(String _position){
    if(_position == CaptureImage.NO_SPECIFIED){ return true; }
    return this.getPosition() == _position;
  }
  /**
   * slice が一致するか調べる. NO_SPECIFIEDが与えられると、trueを返す.
   * @param _slice
   * @return
   */
  public boolean isSlice(String _slice){
    if(_slice == CaptureImage.NO_SPECIFIED){ return true; }
    return this.getSlice() == _slice;
  }
  /**
   * time が一致するか調べる. NO_SPECIFIEDが与えられると、trueを返す.
   * @param _time
   * @return
   */
  public boolean isTime(String _time){
    if(_time == CaptureImage.NO_SPECIFIED){ return true; }
    return this.getTime() == _time;
  }
  /**
   * filter が一致するか調べる. NO_SPECIFIEDが与えられると、trueを返す.
   * @param _filter
   * @return
   */
  public boolean isFilter(String _filter){
    if(_filter == null){return true;}
    return this.getFilter().equals(_filter);
  }
  /**
   * type が一致するか調べる.
   * @param _type
   * @return
   */
  public boolean isType(int _type){
    return this.getType() == _type;
  }

  /**
   * @return the directory
   */
  public String getDirectory() {
    return directory;
  }

  /**
   * @return the wellName
   */
  public String getWellName() {
    return wellName;
  }

  /**
   * @return the well
   */
  public String getWell() {
    return well;
  }

  /**
   * @return the position
   */
  public String getPosition() {
    return position;
  }

  /**
   * @return the slice
   */
  public String getSlice() {
    return slice;
  }

  /**
   * @return the time
   */
  public String getTime() {
    return time;
  }

  /**
   * @return the filter
   */
  public String getFilter() {
    return filter;
  }

  /**
   * @return the type
   */
  public int getType() {
    return type;
  }

  /**
   * @return the shotID
   */
  public String getShotID() {
    return shotID;
  }

  /**
   * @return the file
   */
  public File getFile() {
    return file;
  }

	/**
	 * @return the imageID
	 */
	public String getImageID() {
		return imageID;
	}

	String getInfo() {
		StringBuilder sb;
		sb = new StringBuilder("");
		sb.append("getImageID() = ").append(this.getImageID()).append("\n");
		sb.append("getDirectory() = ").append(this.getDirectory()).append("\n");
		sb.append("getFilter() = ").append(this.getFilter()).append("\n");
		sb.append("getPosition() = ").append(this.getPosition()).append("\n");
		sb.append("getShotID() = ").append(this.getShotID()).append("\n");
		sb.append("getTime() = ").append(this.getTime()).append("\n");
		sb.append("getWell() = ").append(this.getWell()).append("\n");
		sb.append("getWellName() = ").append(this.getWellName()).append("\n");
		return sb.toString();


	}
}
