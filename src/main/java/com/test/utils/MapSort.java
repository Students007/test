package com.test.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class MapSort {

	public static List<Map.Entry<String, Integer>> sortUseValue(Map<String, Integer> map) {

		if (CheckTool.isNullOrEmpty(map)) {
			return null;
		}

		// 注意 ArrayList<>() 括号里要传入map.entrySet()
		List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				// 按照value值，重小到大排序
				// return o1.getValue() - o2.getValue();

				// 按照value值，从大到小排序
				// return o2.getValue() - o1.getValue();

				// 按照value值，用compareTo()方法默认是从小到大排序
				return o1.getValue().compareTo(o2.getValue());
			}
		});

		return list;

	}
}