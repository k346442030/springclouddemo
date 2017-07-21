package com.platform.service.business.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class RandomUtil {

	private static final String NUMERIC = "0123456789";

	private static final String LOWERCASE_ALPHA = "abcdefghijklmnopqrstuvwxyz";

	private static final String UPPERCASE_ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXWZ";

	private static final String ALPHA = LOWERCASE_ALPHA + UPPERCASE_ALPHA;

	private static final String NUMERIC_ALPHA = NUMERIC + ALPHA;

	public static String genRandomString(int length) {
		return genNumericAlphaRandomString(length);
	}

	public static String genNumericRandomString(int length) {
		return genRandomString(NUMERIC, length);
	}

	public static String genLowerAlphaRandomString(int length) {
		return genRandomString(LOWERCASE_ALPHA, length);
	}

	public static String genUpperAlphaRandomString(int length) {
		return genRandomString(UPPERCASE_ALPHA, length);
	}

	public static String genAlphaRandomString(int length) {
		return genRandomString(ALPHA, length);
	}

	public static String genNumericAlphaRandomString(int length) {
		return genRandomString(NUMERIC_ALPHA, length);
	}

	public static String genRandomString(String dict, int length) {
		return genRandomString(dict, length, null);
	}

	public static String genRandomString(String dict, int length, Random random) {
		int dictLength = dict.length();
		if (null == random) {
			random = new Random();
		}
		StringBuilder sBuilder = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			char c = dict.charAt(random.nextInt(dictLength));
			sBuilder.append(c);
		}
		return sBuilder.toString();
	}

	public static String genSecureRandomString(String dict, int length) {
		return genRandomString(dict, length, new SecureRandom());
	}

	public static String genNumericAlphaSecureRandomString(int length) {
		return genSecureRandomString(NUMERIC_ALPHA, length);
	}

	public static <T> void setRandom(T obj, Field f) throws InstantiationException {
		try {
			if (f.getType() == String.class) {
				f.set(obj, genNumericAlphaSecureRandomString(16));
			} else if (f.getType() == int.class) {
				f.setInt(obj, new SecureRandom().nextInt());
			} else if (f.getType() == double.class) {
				f.setDouble(obj, new SecureRandom().nextDouble());
			} else if (f.getType() == float.class) {
				f.setFloat(obj, new SecureRandom().nextFloat());
			} else if (f.getType() == long.class) {
				f.setLong(obj, new SecureRandom().nextLong());
			} else if (f.getType() == boolean.class) {
				f.setBoolean(obj, new SecureRandom().nextBoolean());
			} else if (f.getType() == List.class) {

				Type fc = f.getGenericType(); // 关键的地方，如果是List类型，得到其Generic的类型
				// Map<?, ?> m = new HashMap<>();
				if (fc != null)
					if (fc instanceof ParameterizedType) // 【3】如果是泛型参数的类型
					{

						// ParameterizedType p = (ParameterizedType)fc;
						// Class<?> g = (Class<?>)p.getActualTypeArguments()[0];
						// Object p_cls =p.getClass().newInstance();
						// List list = new ArrayList();
						// for (Field _f:p_cls.getClass().getDeclaredFields()
						// ) {
						// _f.setAccessible(true);
						// setRandom(p_cls,_f);
						// }
						// list.add(p_cls);
					}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public static Type[] getParameterizedTypes(Object object) {
		Type superclassType = object.getClass().getGenericSuperclass();
		if (!ParameterizedType.class.isAssignableFrom(superclassType.getClass())) {
			return null;
		}
		return ((ParameterizedType) superclassType).getActualTypeArguments();
	}

	// public static <T> T createObject(Class<T> tClass){
	public static <T> T createObject(Class<T> tClass) {
		try {
			T t = tClass.newInstance();
			for (Field f : t.getClass().getDeclaredFields()) {
				if (f.getName().equals("serialVersionUID")) {
					continue;
				}
				f.setAccessible(true);
				setRandom(t, f);
			}

			return t;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {

	}
}
