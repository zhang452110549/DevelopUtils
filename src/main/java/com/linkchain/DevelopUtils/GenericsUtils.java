package com.linkchain.DevelopUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides a helper that locates the declarated generics type of a class.
 * 
 * @author sshwsfc@gmail.com
 */
@SuppressWarnings("rawtypes")
public class GenericsUtils {
	/**
	 * Locates the first generic declaration on a class.
	 * 
	 * @param clazz
	 *            The class to introspect
	 * @return the first generic declaration, or <code>null</code> if cannot be
	 *         determined
	 */

	public static Class getGenericClass(Class clazz) {
		return getGenericClass(clazz, 0);
	}

	/**
	 * Locates generic declaration by index on a class.
	 * 
	 * @param clazz
	 *            clazz The class to introspect
	 * @param index
	 *            the Index of the generic ddeclaration,start from 0.
	 */
	public static Class getGenericClass(Class clazz, int index) {
		Type genType = clazz.getGenericSuperclass();

		if (genType instanceof ParameterizedType) {
			Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

			if ((params != null) && (params.length >= (index - 1))) {
				return (Class) params[index];
			}
		}
		return null;
	}

	/**
	 * 通过反射,获得定义Class时声明的父类的范型参数的类型.
	 */
	public static Class getSuperClassGenricType(Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	/**
	 * 通过反射,获得定义Class时声明的父类的范型参数的类型.
	 */
	public static Class getSuperClassGenricType(Class clazz, int index) throws IndexOutOfBoundsException {

		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {

			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}
		return (Class) params[index];
	}

	/**
	 * 将源对象中的值复制到目标对象中值为空的同名字段中。
	 * 
	 * @param source
	 *            源对象
	 * @param target
	 *            目标对象 * @param ignoreList 需忽略的字段名列表
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static void copyValue(Object source, Object target, String... ignoreList)
			throws IllegalArgumentException, IllegalAccessException {
		Map<String, Object> sourceValueMap = new HashMap<String, Object>();
		Field[] fields = source.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (!Modifier.toString(field.getModifiers()).contains("final")) {
				field.setAccessible(true);
				sourceValueMap.put(field.getName(), field.get(source));
			}
		}
		fields = target.getClass().getDeclaredFields();
		// 忽略的字段
		boolean isIgnore = false;
		for (Field field : fields) {
			isIgnore = false;
			field.setAccessible(true);
			// 对 final修饰在属性值不能更改
			if (!Modifier.toString(field.getModifiers()).contains("final")) {
				if (ignoreList.length > 0) {
					for (String ignore : ignoreList) {
						if (ignore.equals(field.getName())) {
							isIgnore = true;
							break;
						}
					}
				}
				if (!isIgnore && sourceValueMap.get(field.getName()) != null) {
					field.set(target, sourceValueMap.get(field.getName()));
				}
			}
		}
	}

	/**
	 * 获取某个class的字段的数据类型
	 * 
	 * @param clazz
	 *            类class
	 * @param fieldName
	 *            字段名称
	 * @return 数据类型名称
	 * @throws Exception
	 */
	public static final String getFieldType(Class clazz, String fieldName) throws Exception {
		String modifiers = "";
		Field field = clazz.getDeclaredField(fieldName);
		modifiers = field.getGenericType().getTypeName();
		return modifiers;
	}

	public static void main(String[] args) throws Exception {

	}

}
