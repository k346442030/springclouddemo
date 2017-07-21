package com.platform.service.business.common.util;

import java.util.List;

public class ConvertUtil {
	public static <T> T getListOne(List<T> list) {
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

}
