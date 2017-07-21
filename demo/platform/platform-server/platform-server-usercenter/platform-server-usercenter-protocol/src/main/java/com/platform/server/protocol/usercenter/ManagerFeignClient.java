/*
 * Copyright 2012-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.platform.server.protocol.usercenter;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.platform.server.Configuration;
import com.platform.server.annotations.NoLogin;
import com.platform.server.contents.FeignPath;
import com.platform.server.protocol.usercenter.entity.Manager;
import com.platform.server.protocol.usercenter.request.ManagerLoginForm;
import com.platform.service.business.controller.feign.BaseFeignClient;
import com.platform.service.business.controller.response.TokenResponse;

import io.swagger.annotations.Api;

@FeignClient(name = FeignPath.USERCENTER_PATH, path = ManagerFeignClient.PATH, configuration = Configuration.class)
@Api(value = ManagerFeignClient.PATH, description = "管理员")
public interface ManagerFeignClient extends BaseFeignClient<Manager, Long> {

	String PATH = "/manager";

	@NoLogin
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public TokenResponse login(ManagerLoginForm form);

}
