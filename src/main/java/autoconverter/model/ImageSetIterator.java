/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package autoconverter.model;

/**
 * ImageSet専用のiterator
 * @author yfujita
 */
public class ImageSetIterator implements RandomAccessIterator {
  private final ImageSet imageSet;
  private int shotIndex;
  private int imageIndex;

  public ImageSetIterator(ImageSet _set){
    imageSet = _set;
    shotIndex = 0;
    imageIndex = 0;
  }

  /**
   * 次の画像があるかどうか.
   * @return
   */
  public boolean hasNext() {
    if(imageIndex < imageSet.getSize()){
      return true;
    } else {
      return false;
    }
  }

  /**
   * 現在のindexの画像を返し、indexをインクリメントする.
   * @return
   */
  public CaptureImage next() {
    CaptureImage _image = imageSet.getCaptureImageAt(imageIndex);
    imageIndex++;
    return _image;
  }

  /**
   * 前の画像があるかどうか.
   * @return
   */
  public boolean hasPrevious() {
    if(imageIndex < 0 ){
      return false;
    } else {
      return true;
    }
  }

  /**
   * 現在のindexの画像を返し、indexをデクリメントする.
   * @return
   */
  public CaptureImage previous() {
    CaptureImage _image = imageSet.getCaptureImageAt(imageIndex);
    imageIndex--;
    return _image;
  }

  /**
   * 次に返される画像のindex.
   * @return
   */
  public int nextIndex() {
    return imageIndex;
  }

  /**
   * 前に返される画像のindex.
   * @return
   */
  public int previousIndex() {
    return imageIndex;
  }

  public void remove() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void set(Object e) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void add(Object e) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public Object get() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

}
