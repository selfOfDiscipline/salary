package com.tyzq.salary.utils;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期工具类
 *
 * @author admin
 *
 */
public class DateUtils {

	/**
	 * 格式化日期
	 *
	 * @param date
	 *            日期字符串
	 * @param pattern
	 *            转换模式
	 * @return 不带时间的日期
	 */
	public static LocalDate formatDate(String date, String pattern) {
		if (StringUtils.isNotBlank(date) && StringUtils.isNotBlank(pattern)) {
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
			return LocalDate.parse(date, dateTimeFormatter);
		}
		return null;
	}

	/**
	 * 获得日期间的月份数
	 *
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return 月份数
	 */
	public static Integer getMonthsBetween(LocalDate startDate, LocalDate endDate) {
		if (startDate != null && endDate != null) {
			Period period = startDate.isAfter(endDate) ? Period.between(endDate, startDate)
					: Period.between(startDate, endDate);
			return Integer.valueOf(period.getMonths());
		}
		return null;
	}

	/**
	 * 获得日期间的天数
	 *
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return 天数
	 */
	public static Integer getDaysBetween(LocalDate startDate, LocalDate endDate) {
		if (startDate != null && endDate != null) {
			Period period = startDate.isAfter(endDate) ? Period.between(endDate, startDate)
					: Period.between(startDate, endDate);
			return Integer.valueOf(period.getDays());
		}
		return null;
	}

	/**
	 * 得到时间字符串
	 *
	 * @param startDate
	 *            时间
	 * @param pattern
	 *            转换模式
	 * @return
	 */
	public static String getDateString(Date startDate, String pattern) {
		String sReturn = null;

		if (startDate != null && StringUtils.isNotBlank(pattern)) {
			SimpleDateFormat formatter = new SimpleDateFormat(pattern);
			sReturn = formatter.format(startDate);
		}

		return sReturn;
	}

	/**
	 * 得到当前时间字符串
	 *
	 * @return
	 */
	public static String getNowDateString() {
		String sReturn = null;

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sReturn = formatter.format(new Date());

		return sReturn;
	}

	/**
	 * 得到时间
	 *
	 * @param dateStr
	 *            时间字符串
	 * @param pattern
	 *            转换格式
	 * @return
	 */
	public static Date getDate(String dateStr, String pattern) {
		Date dtReturn = null;

		if (StringUtils.isNotBlank(dateStr)) {
			if (StringUtils.isBlank(pattern)) {
				pattern = "yyyy-MM-dd HH:mm:ss";
			}

			try {
				SimpleDateFormat formatter = new SimpleDateFormat(pattern);
				dtReturn = formatter.parse(dateStr);
			} catch (Exception e) {
			}
		}

		return dtReturn;
	}

	// 获取本月的开始时间
	public static Date getBeginDayOfMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(getNowYear(), getNowMonth() - 1, 1);
		return getDayStartTime(calendar.getTime());
	}

	// 获取本月的结束时间
	public static Date getEndDayOfMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(getNowYear(), getNowMonth() - 1, 1);
		int day = calendar.getActualMaximum(5);
		calendar.set(getNowYear(), getNowMonth() - 1, day);
		return getDayEndTime(calendar.getTime());
	}

	// 获取本年的开始时间
	public static Date getBeginDayOfYear() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, getNowYear());
		// cal.set
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DATE, 1);

		return getDayStartTime(cal.getTime());
	}

	// 获取本年的结束时间
	public static Date getEndDayOfYear() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, getNowYear());
		cal.set(Calendar.MONTH, Calendar.DECEMBER);
		cal.set(Calendar.DATE, 31);
		return getDayEndTime(cal.getTime());
	}

	// 获取今年是哪一年
	public static Integer getNowYear() {
		Date date = new Date();
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		return Integer.valueOf(gc.get(1)) - 1;
	}

	// 获取本月是哪一月
	public static int getNowMonth() {
		Date date = new Date();
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		return gc.get(2) + 1;
	}

	// 获取某个日期的开始时间
	public static Timestamp getDayStartTime(Date d) {
		Calendar calendar = Calendar.getInstance();
		if (null != d)
			calendar.setTime(d);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0,
				0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return new Timestamp(calendar.getTimeInMillis());
	}

	// 获取某个日期的结束时间
	public static Timestamp getDayEndTime(Date d) {
		Calendar calendar = Calendar.getInstance();
		if (null != d)
			calendar.setTime(d);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23,
				59, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return new Timestamp(calendar.getTimeInMillis());
	}


	public static String getFirstDayOfMonth(int year,int month){
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最小天数
        int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String firstDayOfMonth = sdf.format(cal.getTime());
        return firstDayOfMonth;
    }

	/**
	* 获得该月最后一天
	* @param year
	* @param month
	* @return
	*/
	public static String getLastDayOfMonth(int year,int month){
	        Calendar cal = Calendar.getInstance();
	        //设置年份
	        cal.set(Calendar.YEAR,year);
	        //设置月份
	        cal.set(Calendar.MONTH, month-1);
	        //获取某月最大天数
	        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	        //设置日历中月份的最大天数
	        cal.set(Calendar.DAY_OF_MONTH, lastDay);
	        //格式化日期
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        String lastDayOfMonth = sdf.format(cal.getTime());
	        return lastDayOfMonth;

	}

	/*
	 * @Author zwc   zwc_503@163.com
	 * @Date 15:14 2020/9/21
	 * @Param
	 * @return
	 * @Version 1.0
	 * @Description //TODO 获取当前月份
	 **/
	public static Date getThisDateMonth() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
		String format = simpleDateFormat.format(new Date());
		try {
			return simpleDateFormat.parse(format);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * @Author zwc   zwc_503@163.com
	 * @Date 15:14 2020/9/21
	 * @Param
	 * @return
	 * @Version 1.0
	 * @Description //TODO 获取当前月份的上个月
	 **/
	public static Date getThisDateLastMonth() {
		// 计算月份
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date date = null;
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(sdf.format(new Date())));
			cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
			date = cal.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/*
	 * @Author: 郑稳超先生 zwc_503@163.com
	 * @Date: 11:01 2021/1/8
	 * @Param:
	 * @return:
	 * @Description: //TODO 给日期追加number个月
	 **/
	public static Date stepMonthWithDate(Date date, int number) {
		// 定义 格式
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, number);
		return c.getTime();
	}
}
