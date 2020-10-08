package com.test.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTool {

	// 今天
	public static String getToday() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		return dateString;

	}

	public static String date2String(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");
		return formatter.format(date);
	}

	/**
	 * 获取精确到秒的时间戳
	 *
	 * @return
	 */
	public static int getSecondTimestamp() {
		String timestamp = String.valueOf(new Date().getTime());
		int length = timestamp.length();
		if (length > 3) {
			return Integer.valueOf(timestamp.substring(0, length - 3));
		} else {
			return 0;
		}
	}

	/**
	 * 得到几天前的时间
	 *
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateBefore(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();
	}

	/**
	 * 得到几天后的时间
	 *
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}

	// 本月第一天
	public static Date getFirstDate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();

	}

	// 任务是否过期
	public static boolean isTaskDataOk(Date createData, int needDays) {

		Date finishDate = getDateAfter(createData, needDays);

		if (createData.compareTo(finishDate) < 0) {
			// beginTime.compareTo(endTime)<0时，开始时间小于结束时间
			return true;
		} else {
			return false;
		}

	}
}
