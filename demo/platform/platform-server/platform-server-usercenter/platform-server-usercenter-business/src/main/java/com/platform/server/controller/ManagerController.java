package com.platform.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.platform.server.context.BusiDataContext;
import com.platform.server.context.model.ClientModel;
import com.platform.server.protocol.usercenter.ManagerFeignClient;
import com.platform.server.protocol.usercenter.entity.Manager;
import com.platform.server.protocol.usercenter.enums.UserType;
import com.platform.server.protocol.usercenter.request.ManagerLoginForm;
import com.platform.server.service.ManagerService;
import com.platform.service.business.cache.SessionService;
import com.platform.service.business.cache.model.ClientSession;
import com.platform.service.business.common.enums.DeleteFlag;
import com.platform.service.business.common.errors.ErrorInfos.ClientErr;
import com.platform.service.business.common.model.AccessToken;
import com.platform.service.business.common.util.ResponseUtil;
import com.platform.service.business.controller.base.BaseController;
import com.platform.service.business.controller.request.BaseReqForm;
import com.platform.service.business.controller.response.BaseResponse;
import com.platform.service.business.controller.response.TokenResponse;
import com.platform.service.business.entity.ClientModelBase;
import com.platform.service.core.util.MD5Util;

@RestController
@RequestMapping(ManagerFeignClient.PATH)
public class ManagerController extends BaseController<Manager, Long> implements ManagerFeignClient {
	@Autowired
	private ManagerService managerService;

	@Override
	protected ManagerService getService() {
		return managerService;
	}

	@Autowired
	protected SessionService sessionService;

	public TokenResponse login(ManagerLoginForm form) {
		Manager example = new Manager();
		example.setAccount(form.getAccount());
		example.setPassword(MD5Util.encrypt(form.getPassword()));
		example.setDeleteFlag(DeleteFlag.NOT_DELETE.getValue());
		Manager manager = this.getService().getOne(example);
		if (manager == null) {
			return ResponseUtil.genErrorResult(ClientErr.LOGIN_ERR, TokenResponse.class);
		}
		ClientModel client = new ClientModel();
		client.setUserId(manager.getId());
		client.setUserName(manager.getName());
		client.setUserType(UserType.MANAGER.getVal());
		TokenResponse tokenResponse = saveIntoSession(client);
		return tokenResponse;
	}

	protected TokenResponse saveIntoSession(ClientModelBase client) {
		ClientSession session = new ClientSession();
		session.setClient(JSONObject.parseObject(JSONObject.toJSONString(client)));
		session.setClientId(client.getClientIdStr());
		AccessToken token = sessionService.renewToken(session);
		TokenResponse tokenResponse = new TokenResponse();
		tokenResponse.setAccessToken(token.getToken());
		tokenResponse.setExpire(token.getExpire());
		return tokenResponse;
	}

	protected void refreshSession(ClientModelBase client) {
		ClientSession session = new ClientSession();
		session.setClient(JSONObject.parseObject(JSONObject.toJSONString(client)));
		session.setClientId(client.getClientIdStr());
		sessionService.refreshSession(session);
	}

	@RequestMapping("logout")
	public BaseResponse logout(BaseReqForm form) {
		sessionService.destorySession(BusiDataContext.getClientEntity().getClientIdStr());
		return ResponseUtil.genSuccessResultVo();
	}


}
