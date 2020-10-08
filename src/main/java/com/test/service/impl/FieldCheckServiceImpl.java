package com.test.service.impl;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.test.service.IFieldCheckService;
import com.test.utils.CheckTool;

@Service("fieldCheckService")
public class FieldCheckServiceImpl implements IFieldCheckService {

	// 检查实体字段
	@Override
	public String check(Object object, String[] checkFields) {

		List<String> checkFieldsArr = Arrays.asList(checkFields);

		StringBuilder stringBuilder = new StringBuilder();

		Class<?> clazz = object.getClass();
		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
			Field[] fields = clazz.getDeclaredFields();

			for (Field f : fields) {
				f.setAccessible(true);

				boolean checkField = checkFieldsArr.contains(f.getName());
				boolean checkFieldValue = false;
				try {
					checkFieldValue = CheckTool.isNullOrEmpty(f.get(object));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}

				if (checkField && checkFieldValue) {
					// 需要检查
					stringBuilder.append(f.getName() + "= null,");
				}
			}
		}

		if (stringBuilder.length() == 0) {
			return null;
		} else {
			return stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1);
		}

	}
}
