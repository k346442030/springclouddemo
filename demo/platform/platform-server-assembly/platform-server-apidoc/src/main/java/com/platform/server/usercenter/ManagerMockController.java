package com.platform.server.usercenter;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.platform.server.contents.FeignPath;
import com.platform.server.protocol.usercenter.ManagerFeignClient;
import com.platform.server.protocol.usercenter.entity.Manager;
import com.platform.server.protocol.usercenter.request.ManagerLoginForm;
import com.platform.service.business.controller.mock.BaseMockController;
import com.platform.service.business.controller.response.TokenResponse;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(FeignPath.USERCENTER_PATH + ManagerFeignClient.PATH)
public class ManagerMockController extends BaseMockController<Manager, Long> implements ManagerFeignClient {

	@Override
	protected Manager getMockEmptyEntity() {
		return new Manager();
	}

	@Override
	@ApiOperation(value = "管理员登录", notes = "管理员登录")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "account", value = "账号", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "query", dataType = "String") })
	public TokenResponse login(ManagerLoginForm form) {
		return null;
	}

}
