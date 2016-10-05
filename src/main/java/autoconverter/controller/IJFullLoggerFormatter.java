package autoconverter.controller;


import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author yfujita
 */
public class IJFullLoggerFormatter extends Formatter{

  @Override
  public String format(LogRecord record) {
    //throw new UnsupportedOperationException("Not supported yet.");
		//DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, Locale.JAPAN);
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.LONG, Locale.JAPAN);
		StringBuffer buf = new StringBuffer();
		//buf.append(df.format(new Date()) + " ");
		buf.append(record.getLevel());
		buf.append(":");
    buf.append(this.getCalledElements());
    buf.append(record.getMessage());
		//buf.append(record.getSourceClassName());
		//buf.append(".");
		//buf.append(record.getSourceMethodName());
		//buf.append(":");
		//buf.append(formatMessage(record));
		buf.append("\n");
		return buf.toString();
	}

  private String getCalledElements(){
    Throwable t = new Throwable();
    StackTraceElement[] st = t.getStackTrace();
    StringBuffer stack_str = new StringBuffer("");
    for(StackTraceElement ste: st){
      stack_str.append(ste.toString() + "\n");
    }
    return stack_str.toString();
    //return st[st.length-1];
  }

}
