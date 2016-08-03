package com.winso.comm_library;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 不同时区对应的时间处理工具类
 * 
 * @author HuangWenwei
 * 
 * @date 2014年7月31日
 */
public class TimeZoneUtil {

	/**
	 * 判断用户的设备时区是否为东八区（中国） 2014年7月31日
	 * 
	 * @return
	 */
	public static boolean isInEasternEightZones() {
		boolean defaultVaule = true;
		if (TimeZone.getDefault() == TimeZone.getTimeZone("GMT+08"))
			defaultVaule = true;
		else
			defaultVaule = false;
		return defaultVaule;
	}

	/**
	 * 根据不同时区，转换时间 2014年7月31日
	 * 
	 * @param time
	 * @return
	 */
	public static Date transformTime(Date date, TimeZone oldZone,
			TimeZone newZone) {
		Date finalDate = null;
		if (date != null) {
			int timeOffset = oldZone.getOffset(date.getTime())
					- newZone.getOffset(date.getTime());
			finalDate = new Date(date.getTime() - timeOffset);
		}
		return finalDate;

	}

	/*
	 * 转化为时间串
	 */
	public static String toDateString(int year, int month, int day) {
		return String.format("%04d-%02d-%02d", year, month, day);
	}

	public static String getCurDateTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间

		return formatter.format(curDate);

	}
	public static String getCurDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间

		return formatter.format(curDate);

	}
	/**
	 * 从当前日期算起，获取N天前的日期（当前日不算在内），日期格式为yyyy-MM-dd
	 * 
	 * @param daily
	 *            天数
	 * @return
	 */
	public static String getDateByDay(Integer daily) {
		Date date = new Date();
		int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(date));
		int month = Integer.parseInt(new SimpleDateFormat("MM").format(date));
		int day = Integer.parseInt(new SimpleDateFormat("dd").format(date))
				- daily;
		if (day < 1) {
			month -= 1;
			if (month == 0) {
				year -= 1;
				month = 12;
			}
			if (month == 4 || month == 6 || month == 9 || month == 11) {
				day = 30 + day;
			} else if (month == 1 || month == 3 || month == 5 || month == 7
					|| month == 8 || month == 10 || month == 12) {
				day = 31 + day;
			} else if (month == 2) {
				if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
					day = 29 + day;
				} else {
					day = 28 + day;
				}
			}

		}
		String y = year + "";
		String m = "";
		String d = "";
		if (month < 10) {
			m = "0" + month;
		} else {
			m = month + "";
		}
		if (day < 10) {
			d = "0" + day;
		} else {
			d = day + "";
		}
		return y + "-" + m + "-" + d;
	}

	/**
	 * 从当前日期算起，获取N个月前的日期，日期格式为yyyy-MM-dd
	 * 
	 * @param mon
	 *            月份
	 * @return
	 */
	public static String getDateByMonth(Integer mon) {
		Date date = new Date();
		int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(date));
		int month = Integer.parseInt(new SimpleDateFormat("MM").format(date))
				- mon;
		int day = Integer.parseInt(new SimpleDateFormat("dd").format(date));
		if (month == 0) {
			year -= 1;
			month = 12;
		} else if (day > 28) {
			if (month == 2) {
				if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
					day = 29;
				} else {
					day = 28;
				}
			} else if ((month == 4 || month == 6 || month == 9 || month == 11)
					&& day == 31) {
				day = 30;
			}
		}
		String y = year + "";
		String m = "";
		String d = "";
		if (month < 10) {
			m = "00" + month;
		} else {
			m = month + "";
		}
		if (day < 10) {
			d = "0" + day;
		} else {
			d = day + "";
		}
		return y + "-" + m + "-" + d;
	}
}
