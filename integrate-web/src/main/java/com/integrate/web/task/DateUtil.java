package com.integrate.web.task;
import static java.util.Calendar.DATE;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.MONDAY;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.SECOND;
import static java.util.Calendar.WEEK_OF_YEAR;
import static java.util.Calendar.YEAR;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日期工具类
 */
public class DateUtil {
	public static final long MILLIS_PER_SECOND = 1000L;
	private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final Logger log = LoggerFactory.getLogger(DateUtil.class);
	public static final long MILLIS_PER_DAY = 24*60*60*1000L;
	/**
	 * 将时间转换成‘yyyy-MM-dd HH24:mm ’格式的字符串
	 * @param date
	 * @return
	 */
	public static String YMDHMToString(Date date){
		if (date == null) {
			return "";
		}
		DateFormat dateformat = new SimpleDateFormat(DEFAULT_FORMAT);
		return dateformat.format(date);
	}
	/**
	 * 将时间转化成 "yyyy-MM-dd"格式的字符串
	 * @param date
	 * @return
	 */
	public static String YMDToString(Date date){
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		return dateformat.format(date);
	}
	
	/**
	 * 将时间转化成 "yyyy-MM-dd HH"格式的字符串
	 * @param date
	 * @return
	 */
	public static String YMDHToString(Date date){
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH");
		return dateformat.format(date);
	}
	
	/**
	 * 检查当前时间和指定时间是否同一周
	 * 
	 * @param  year 			年
	 * @param  week 			周
	 * @param  firstDayOfWeek 	周的第一天设置值，{@link Calendar#DAY_OF_WEEK}
	 * @return {@link Boolean}	true-同一周, false-不同周
	 */
	public static boolean isSameWeek(int year, int week, int firstDayOfWeek) {
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(firstDayOfWeek);
		return year == cal.get(YEAR) && week == cal.get(WEEK_OF_YEAR);
	}
	
	/**
	 * 检查当前时间和指定时间是否同一周
	 * 
	 * @param  time 			被检查的时间
	 * @param  firstDayOfWeek 	周的第一天设置值，{@link Calendar#DAY_OF_WEEK}
	 * @return {@link Boolean}	true-同一周, false-不同周
	 */
	public static boolean isSameWeek(Date time, int firstDayOfWeek) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		cal.setFirstDayOfWeek(firstDayOfWeek);
		return isSameWeek(cal.get(YEAR), cal.get(WEEK_OF_YEAR), firstDayOfWeek);
	}
	
	/**
	 * 获取周的第一天
	 * @param  firstDayOfWeek 	周的第一天设置值，{@link Calendar#DAY_OF_WEEK}
	 * @param  time 			指定时间，为 null 代表当前时间
	 * @return {@link Date}		周的第一天
	 */
	public static Date firstTimeOfWeek(int firstDayOfWeek, Date time) {
		Calendar cal = Calendar.getInstance();
		if (time != null) {
			cal.setTime(time);
		}
		cal.setFirstDayOfWeek(firstDayOfWeek);
		int day = cal.get(DAY_OF_WEEK);
		
		if (day == firstDayOfWeek) {
			day = 0;
		} else if (day < firstDayOfWeek) {
			day = day + (7 - firstDayOfWeek);
		} else if (day > firstDayOfWeek) {
			day = day - firstDayOfWeek;
		}
		
		cal.set(HOUR_OF_DAY, 0);
		cal.set(MINUTE, 0);
		cal.set(SECOND, 0);
		cal.set(MILLISECOND, 0);
		
		cal.add(DATE, -day);
		return cal.getTime();
	}
	
	/**
	 * 检查指定日期是否今天(使用系统时间)
	 * @param date 被检查的日期
	 * @return
	 */
	public static boolean isToday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.add(DATE, 1);
		cal.set(HOUR_OF_DAY, 0);
		cal.set(MINUTE, 0);
		cal.set(SECOND, 0);
		cal.set(MILLISECOND, 0);
		Date end = cal.getTime(); // 明天的开始
		cal.add(MILLISECOND, -1);
		cal.add(DATE, -1);
		Date start = cal.getTime(); // 昨天的结束
		return date.after(start) && date.before(end);
	}

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
	 * 字符串转换成日期格式
	 * 
	 * @param  dateString 		待转换的日期字符串
	 * @param  datePattern 		日期格式
	 * @return {@link Date}		转换后的日期
	 */
	public static Date string2Date(String dateString, String datePattern) {
		if (dateString == null || dateString.trim().isEmpty()) {
			return null;
		}

		DateFormat format = new SimpleDateFormat(datePattern);
		try {
			return format.parse(dateString);
		} catch (ParseException e) {
			log.error("ParseException in Converting String to date: " + e.getMessage());
		}

		return null;

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
	 * 字符串转时间戳
	 * 
	 * @param str
	 *            字符串
	 * @param format
	 *            时间格式
	 * @param isMicrotime
	 *            是否输出毫秒
	 */
	public static final long str2time(String str, String format, boolean isMicrotime) {
		if (str == null || "".equals(str)) {
			return -1;
		}
		try {
			if (isMicrotime) {
				return new SimpleDateFormat(format).parse(str).getTime();
			}
			return new SimpleDateFormat(format).parse(str).getTime() / 1000;
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * 字符串转时间戳
	 * 
	 * @param str
	 *            字符串
	 * @param format
	 *            时间格式
	 */
	public static final int str2time(String str) {
		return (int) str2time(str, DEFAULT_FORMAT, false);
	}
	
	/**
	 * 字符串转时间戳
	 * 
	 * @param str
	 *            字符串
	 * @param isMicrotime
	 *            是否输出毫秒
	 */
	public static final long str2time(String str, boolean isMicrotime) {
		return str2time(str, DEFAULT_FORMAT, isMicrotime);
	}

	/**
	 * 字符串转时间戳
	 * 
	 * @param str
	 *            字符串
	 * @param format
	 *            时间格式
	 */
	public static final int str2time(String str, String format) {
		return (int) str2time(str, format, false);
	}

	/**
	 * 把秒数转换成把毫秒数
	 * 
	 * @param  seconds		秒数的数组
	 * @return {@link Long} 毫秒数
	 */
	public static long toMillisSecond(long...seconds) {
		long millis = 0L;
		if(seconds != null && seconds.length > 0) {
			for (long time : seconds) {
				millis += (time * 1000);
			}
		}
		return millis;
	}
	
	

	/**
	 * 距离现在的月数，
	 * @param len 0：当前月，-1 上一个月，1下一个月 其它类推
	 * @param datePattern 格式化样式
	 * @return 计算后的格式化串
	 */
	
	public static String changeDateToString( int len,String datePattern ){
		Date month=new Date();
		if(len!=0){
			month=changeDateOfMonth(month, len);
		} 
		return DateUtil.date2String(month, datePattern);
	}
	
	public static String getYYMM(int len){//距离现在的月数，0：当前月，-1 上一个月，1下一个月 其它类推
		return DateUtil.changeDateToString(len, "yyyyMM");
	}
	/**
	 * 修改日期
	 * @param theDate 待修改的日期
	 * @param addYeas 加减的年数
	 * @return 修改后的日期
	 */
	
	public static Date changeDateOfYear(Date theDate,int addYeas){
		return changeDateOfMonth(theDate,addYeas*12);
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

	/**
	 * 获得某一时间的0点
	 * @param theDate 需要计算的时间
	 */
	public static Date getDate0AM(Date theDate) {
		if (theDate == null) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(theDate);
		calendar.set(HOUR_OF_DAY, 0);
		calendar.set(MINUTE, 0);
		calendar.set(SECOND, 0);
		calendar.set(MILLISECOND, 0);
		return calendar.getTime();
	}
	
	/**
	 * 获得某一时间的6点
	 * @param theDate 需要计算的时间
	 */
	public static Date getDate6AM(Date theDate) {
		if (theDate == null) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(theDate);
		calendar.set(HOUR_OF_DAY, 6);
		calendar.set(MINUTE, 0);
		calendar.set(SECOND, 0);
		calendar.set(MILLISECOND, 0);
		return calendar.getTime();
	}
	
	/**
	 * 获得某一时间的23点
	 * @param theDate 需要计算的时间
	 */
	public static Date getDate23AM(Date theDate) {
		if (theDate == null) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(theDate);
		calendar.set(HOUR_OF_DAY, 23);
		calendar.set(MINUTE, 0);
		calendar.set(SECOND, 0);
		calendar.set(MILLISECOND, 0);
		return calendar.getTime();
	}

}
