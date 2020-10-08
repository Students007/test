package com.test.utils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class ListTool {

	public static List<String> removeDuplicate(List<String> list) {
		HashSet h = new HashSet(list);
		list.clear();
		list.addAll(h);
		return list;
	}

	public static List<String> removeElement(List<String> list, String element) {
		Iterator<String> it = list.iterator();
		while (it.hasNext()) {
			String str = it.next();
			if ("element".equals(str)) {
				it.remove();
			}
		}
		return list;
	}

}
