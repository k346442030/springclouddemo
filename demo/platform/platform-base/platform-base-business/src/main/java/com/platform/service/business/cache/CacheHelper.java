package com.platform.service.business.cache;

import com.platform.service.business.util.SpringUtil;

public class CacheHelper {

	private static CacheHelper cacheHelper;

	private SessionService service;

	public static CacheHelper getInstance() {
		if (cacheHelper == null) {
			cacheHelper = new CacheHelper();
		}
		return cacheHelper;
	}
	
	public static SessionService getService(){
		return getInstance().service();
	}

	private CacheHelper() {

	}

	public SessionService service() {
		if (service == null) {
			service = SpringUtil.getBean(SessionService.class);
		}
		return service;
	}

}
