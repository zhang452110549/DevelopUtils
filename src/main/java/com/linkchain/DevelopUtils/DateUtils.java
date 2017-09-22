package com.linkchain.DevelopUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期操作类
 * 
 */
public class DateUtils {
	/** 默认时间格式"yyyy-MM-dd HH:mm:ss" */
	public static final String DEFAULT_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	/** 无间隔的年月日时分秒时间格式"yyyyMMddHHmmss" */
	public static final String FULL_TIME_PATTERN = "yyyyMMddHHmmss";
	/** 时分秒时间格式 HH:mm:ss */
	public static final String SINGLE_TIME_PATTERN = "HH:mm:ss";
	/** 默认日期格式"yyyy-MM-dd" */ 
	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
	/** 单个线程里面重复使用，防止同一个线程里面不停的创建新对象 */
	public static ThreadLocal<SimpleDateFormat> DEFAULT_TIME_FORMAT = new ThreadLocal<SimpleDateFormat>() {
		public SimpleDateFormat initialValue() {
			return new SimpleDateFormat(DEFAULT_TIME_PATTERN);
		}
	};

	/** 单个线程里面重复使用，防止同一个线程里面不停的创建新对象 */
	public static ThreadLocal<SimpleDateFormat> FULL_TIME_FORMAT = new ThreadLocal<SimpleDateFormat>() {
		public SimpleDateFormat initialValue() {
			return new SimpleDateFormat(FULL_TIME_PATTERN);
		}
	};

	/** 单个线程里面重复使用，防止同一个线程里面不停的创建新对象 */
	public static ThreadLocal<SimpleDateFormat> SIGLE_TIME_FORMAT = new ThreadLocal<SimpleDateFormat>() {
		public SimpleDateFormat initialValue() {
			return new SimpleDateFormat(SINGLE_TIME_PATTERN);
		}
	};

	/** 单个线程里面重复使用，防止同一个线程里面不停的创建新对象 */
	public static ThreadLocal<SimpleDateFormat> DEFAULT_DATE_FORMAT = new ThreadLocal<SimpleDateFormat>() {
		public SimpleDateFormat initialValue() {
			return new SimpleDateFormat(DEFAULT_DATE_PATTERN);
		}
	};

	/**
	 * 获取默认格式的昨日日期,包括年月日
	 * 
	 * @return yyyy-MM-dd
	 */
	public static final String getYestoday() {
		return getModifyDateStr(Calendar.DAY_OF_YEAR, -1);
	}

	/**
	 * 获取默认格式的当前日期,包括年月日
	 * 
	 * @return yyyy-MM-dd
	 */
	public static final String getCurrentDateStr() {
		return DEFAULT_DATE_FORMAT.get().format(new Date());
	}

	/**
	 * 获取默认格式的当前时间，包括年月日时分秒
	 * 
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static final String getCurrentTimeStr() {
		return DEFAULT_TIME_FORMAT.get().format(new Date());
	}

	/**
	 * 获取默认格式的当前时间，包括年月日时分秒
	 * 
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static final String formatTime(Date date) {
		return DEFAULT_TIME_FORMAT.get().format(date);
	}

	/**
	 * 字符串转换为日期 日期格式： yyyy-MM-dd HH:mm:ss
	 * 
	 * @return riq
	 */
	public static final Date parseTimeStr(String timeStr) throws ParseException {
		return DEFAULT_TIME_FORMAT.get().parse(timeStr);
	}

	/**
	 * 获取指定格式的当前时间，返回取决于格式
	 * 
	 * @param format
	 *            格式
	 * @return
	 */
	public static final String getCurrentTimeFullStr() {
		return FULL_TIME_FORMAT.get().format(new Date());
	}

	/**
	 * 获取指定格式的时间字符串。默认从当前时间开始
	 *
	 * @param field
	 *            增减字段类型， 如 年 ，月，日，时，分，秒。
	 * @param amount
	 *            增减值 ， 为正时增加 为负时减少。
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static final String getModifyTimeStr(int field, int amount) {
		Calendar currentTime = Calendar.getInstance();
		currentTime.add(field, amount);
		return DEFAULT_TIME_FORMAT.get().format(currentTime.getTime());
	}

	/**
	 * 获取指定格式的时间字符串。默认从当前时间开始
	 *
	 * @param field
	 *            增减字段类型， 如 年 ，月，日，时，分，秒。
	 * @param amount
	 *            增减值 ， 为正时增加 为负时减少。
	 * @return yyyyMMddHHmsms
	 */
	public static final String getModifyTimeFullStr(int field, int amount) {
		Calendar currentTime = Calendar.getInstance();
		currentTime.add(field, amount);
		return FULL_TIME_FORMAT.get().format(currentTime.getTime());
	}

	/**
	 * 获取指定格式的时间字符串。默认从当前时间开始
	 *
	 * @param field
	 *            增减字段类型， 如 年 ，月，日，时，分，秒。
	 * @param amount
	 *            增减值 ， 为正时增加 为负时减少。
	 * @return HH:mm:ss
	 */
	public static final String getModifySingleTimeStr(int field, int amount) {
		Calendar currentTime = Calendar.getInstance();
		currentTime.add(field, amount);
		return SIGLE_TIME_FORMAT.get().format(currentTime.getTime());
	}

	public static final String getModifyDateStr(int field, int amount) {
		Calendar currentTime = Calendar.getInstance();
		currentTime.add(field, amount);
		return DEFAULT_DATE_FORMAT.get().format(currentTime.getTime());
	}

	/**
	 * 获取某个日期的改变值，指定格式的时间字符串。默认从当前时间开始
	 *
	 * @param field
	 *            增减字段类型， 如 年 ，月，日，时，分，秒。
	 * @param amount
	 *            增减值 ， 为正时增加 为负时减少。
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static final String getModifyTimeStr(String dateStr, int field, int amount, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Calendar dateTime = Calendar.getInstance();
		dateTime.setTime(date);
		dateTime.add(field, amount);
		return sdf.format(dateTime.getTime());
	}

	/**
	 * 获取结束日期与开始日期之间的间隔天数,日期格式为: yyyy-MM-dd
	 * 
	 * @param endDate
	 *            结束日期
	 * @param startDate
	 *            开始日期
	 * @return
	 */
	public static final int getBetweenDays(String endDate, String startDate) {
		Date d1;
		try {
			d1 = DEFAULT_DATE_FORMAT.get().parse(endDate);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		Date d2;
		try {
			d2 = DEFAULT_DATE_FORMAT.get().parse(startDate);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

		return (int) ((d1.getTime() - d2.getTime()) / (24 * 60 * 60 * 1000));
	}

	/**
	 * 获取当月的第一天，返回格式为 yyyy-MM-dd
	 * 
	 * @return
	 */
	public static final String getCurrentMonthFirstDay() {
		Calendar date = Calendar.getInstance();
		date.set(Calendar.DAY_OF_MONTH, 1);
		return DEFAULT_DATE_FORMAT.get().format(date.getTime());
	}

	public static void main(String[] args) {
		int num = 10000;
		long start = System.currentTimeMillis();
		for (int i = 0; i < num; i++) {
			DateUtils.getCurrentTimeStr();
		}
		long end = System.currentTimeMillis();
		System.out.println((end - start) + "毫秒");

		start = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_TIME_PATTERN);
		Date date = new Date();
		for (int i = 0; i < num; i++) {
			sdf.format(date);
		}
		end = System.currentTimeMillis();
		System.out.println((end - start) + "毫秒");
	}
}