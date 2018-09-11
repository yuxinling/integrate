package com.integrate.common.util;
import static java.util.Calendar.MONTH;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtil {
	public static final long MILLIS_PER_SECOND = 1000L;
	public static final long MILLIS_PER_DAY = 24*60*60*1000L;

	/**
	 * 日期转换成字符串格式
	 * @param theDate 待转换的日期
	 * @param datePattern 日期格式
	 * @return 日期字符串
	 */
	public static String date2String(Date theDate, String datePattern) {
		if (theDate == null) {
			return "";
		}

		DateFormat format = new SimpleDateFormat(datePattern);
		try {
			return format.format(theDate).trim();
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 把当前时间戳转字符串
	 * 
	 * @param format
	 *            时间格式
	 */
	public static final String time2str(String format) {
		return time2str(System.currentTimeMillis(), format);
	}

	/**
	 * 时间戳转字符串
	 * 
	 * @param timestamp
	 *            时间戳
	 * @param format
	 *            时间格式
	 */
	public static final String time2str(long timestamp, String format) {
		return new SimpleDateFormat(format).format(timestamp);
	}
	
	
	/**
	 * 修改日期
	 * @param theDate 待修改的日期
	 * @param addMonth 加减的月数
	 * @return 修改后的日期
	 */
	public static Date changeDateOfMonth(Date theDate,int addMonth){
		if (theDate == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(theDate);
		
		cal.add(MONTH, addMonth);
		return cal.getTime();
	}
	
	public static String getTimeLatser(long past) {

	    long now = System.currentTimeMillis();
	    long time = (now - past) / 1000;

	    StringBuffer sb = new StringBuffer();
	    if (time > 0 && time < 60) { // 1小时内
	        return sb.append(time + "秒前").toString();
	    } else if (time > 60 && time < 3600) {
	        return sb.append(time / 60 + "分钟前").toString();
	    } else if (time >= 3600 && time < 3600 * 24) {
	        return sb.append(time / 3600 + "小时前").toString();
	    } else if (time >= 3600 * 24 && time < 3600 * 48) {
	        return sb.append("昨天").toString();
	    } else if (time >= 3600 * 48 && time < 3600 * 72) {
	        return sb.append("前天").toString();
	    } else if (time >= 3600 * 72) {
	        return sb.append((int) time / (3600 * 24) + "天前").toString();
	    }
	    return "近期";
	}
	
}
