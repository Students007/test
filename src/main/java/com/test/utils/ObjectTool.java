package com.test.utils;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ObjectTool {

	/**
	 * 转换bean为map
	 *
	 * @param source 要转换的bean
	 * @param        <T> bean类型
	 * @return 转换结果
	 */
	public static <T> Map<String, String> bean2Map(T source) throws IllegalAccessException {
		Map<String, String> result = new HashMap<>();

		Class<?> sourceClass = source.getClass();
		// 拿到所有的字段,不包括继承的字段
		Field[] sourceFiled = sourceClass.getDeclaredFields();
		for (Field field : sourceFiled) {
			field.setAccessible(true);// 设置可访问,不然拿不到private
			// 配置了注解的话则使用注解名称,作为header字段
			FieldName fieldName = field.getAnnotation(FieldName.class);
			if (fieldName == null) {
				result.put(field.getName(), field.get(source).toString());
			} else {
				if (fieldName.Ignore()) {
					continue;
				}
				result.put(fieldName.value(), field.get(source).toString());
			}
		}
		return result;
	}

	/**
	 * 让 Map按key进行排序
	 */
	public static Map<String, String> sortMapByKey(Map<String, String> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}
		Map<String, String> sortMap = new TreeMap<String, String>(new MapKeyComparator());
		sortMap.putAll(map);
		return sortMap;
	}

}

//实现一个比较器类
class MapKeyComparator implements Comparator<String> {

	@Override
	public int compare(String s1, String s2) {
		return s1.compareTo(s2); // 从小到大排序
	}
}
