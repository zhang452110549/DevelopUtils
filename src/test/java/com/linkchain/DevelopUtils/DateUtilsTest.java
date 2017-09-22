package com.linkchain.DevelopUtils;

import java.util.Calendar;

public class DateUtilsTest {
	public static void main(String[] args) {
		System.out.println(DateUtils.getModifyTimeStr("2017-09-20 17:05:06", Calendar.DAY_OF_MONTH, 7, DateUtils.DEFAULT_TIME_PATTERN));
	}
}
