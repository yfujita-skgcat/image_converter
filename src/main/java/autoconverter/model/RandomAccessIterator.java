/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package autoconverter.model;

import java.util.ListIterator;

/**
 * ランダムアクセスするためのインターフェースを提供する.
 * @author yfujita
 */
public interface RandomAccessIterator <T> extends ListIterator {
  public T get();
}
