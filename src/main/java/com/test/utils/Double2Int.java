package com.test.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Double2Int {
	// 四舍五入把double转化int整型，0.5进一，小于0.5不进一
	public static int getInt(double number) {
		BigDecimal bd = new BigDecimal(number).setScale(0, BigDecimal.ROUND_HALF_UP);
		return Integer.parseInt(bd.toString());
	}

	// 四舍五入把double转化为int类型整数,0.5也舍去,0.51进一
	public static int DoubleFormatInt(Double dou) {
		DecimalFormat df = new DecimalFormat("######0"); // 四色五入转换成整数
		return Integer.parseInt(df.format(dou));
	}

	// 去掉小数凑整:不管小数是多少，都进一
	public static int ceilInt(double number) {
		return (int) Math.ceil(number);
	}

	// 生成范围随机数
	public static int getRandom(int min, int max) {
		return (int) (Math.random() * (max - min) + min);
	}

}