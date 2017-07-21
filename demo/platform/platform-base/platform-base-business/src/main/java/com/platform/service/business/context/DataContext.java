package com.platform.service.business.context;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 数据上下文，用于在一个线程上携带环境信息
 *
 */
public class DataContext {

	public static final String KEY_NAMESPACE = DataContext.class.getName();

	private static ThreadLocal<DataContext> dataContext = new ThreadLocal<DataContext>() {
		protected DataContext initialValue() {
			return new DataContext();
		}
	};

	private Map<String, Object> store = new HashMap<String, Object>();

	private LinkedList<String> appliedAspectAdviceList = new LinkedList<String>();

	private static DataContext getDataContext() {
		return dataContext.get();
	}

	public static Map<String, Object> getStore() {
		return getDataContext().store;
	}

	public static Object get(String key) {
		return getStore().get(key);
	}

	public static Object set(String key, Object value) {
		return getStore().put(key, value);
	}

	public static Object remove(String key) {
		return getStore().remove(key);
	}

	public static void clear() {
		getStore().clear();
	}

	public static LinkedList<String> getAppliedAspectAdviceList() {
		return getDataContext().appliedAspectAdviceList;
	}

	public static void addAppliedAspectAdvice(String aspectAdviceName) {
		getAppliedAspectAdviceList().add(aspectAdviceName);
	}

	public static void removeAppliedAspectAdvice(String aspectAdviceName) {
		getAppliedAspectAdviceList().removeLastOccurrence(aspectAdviceName);
	}

	public static boolean isAspectAdviceApplied(String aspectAdviceName) {
		return getAppliedAspectAdviceList().contains(aspectAdviceName);
	}

}
