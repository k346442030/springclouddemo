package com.platform.server.context;

import com.alibaba.fastjson.JSONObject;
import com.platform.server.context.model.ClientModel;
import com.platform.service.business.cache.model.ClientSession;
import com.platform.service.business.context.ClientDataContext;

public class BusiDataContext {

	public static ClientModel getClientEntity() {
		return JSONObject.toJavaObject(ClientDataContext.getClient(), ClientModel.class);
	}
	
	/**
	 * 设置用户信息
	 * 
	 * @param identity
	 */
	public static void setClientSession(ClientSession identity) {
		ClientDataContext.setClient(identity.getClient());
	}

}
