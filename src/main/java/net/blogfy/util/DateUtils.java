package net.blogfy.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 日期常用方法类
 */
@Slf4j
public class DateUtils extends DateFormatUtils {
	
	public static final String PAT_YEAR = "yyyy";
	
	public static final String PAT_MONTH = "yyyy-MM";
	
	public static final String PAT_MONTH_UNINTERRUPTED = "yyyyMM";
	
	public static final String PAT_DATE = "yyyy-MM-dd";
	
	public static final String PAT_DATE_UNINTERRUPTED = "yyyyMMdd";
	
	public static final String PAT_DATE_TIME = "yyyy-MM-dd HH:mm";
	
	public static final String PAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
	
	public static final String PAT_DATETIME_UNINTERRUPTED = "yyyyMMddHHmmss";
	
	public static final String PAT_DATETIME_MILLISECOND = "yyyy-MM-dd HH:mm:ss SSS";
	
	public static final String PAT_DATETIME_MILLISECOND_UNINTERRUPTED = "yyyyMMddHHmmssSSS";
	
	public static Date parse(String date, String pattern) {
		if (date == null) {
			return null;
		}
		try {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			return format.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException("DateFormat exception, data: " + date + ", pattern: " + pattern, e);
		}
	}
	
	/**
	 * 获取当前时间
	 * @return
	 */
	public static final Date now() {
		return new Date(System.currentTimeMillis());
	}
	
	/**
	 * 获取当前时间
	 * @param pattern 格式化
	 * @return
	 */
	public static final Date now(String pattern) {
		return DateUtils.parse(DateUtils.format(now(), pattern), pattern);
	}
	
}