package com.platform.server.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.platform.server.util.HttpUtil;

/**
 * 实现自动刷新服务配置的功能
 * 
 * @author my
 */
@RestController
public class ConfigsController {

	@Autowired
	private DiscoveryClient discoveryClient;

	@RequestMapping(value = "/refreshConfigs", method = RequestMethod.GET)
	public String refreshConfigs(@RequestParam(value = "name", required = true) String name) {
		List<ServiceInstance> apps = discoveryClient.getInstances(name);
		if(apps == null || apps.isEmpty()){
			return "服务不存在或未启动！";
		}
		StringBuilder sb = new StringBuilder();
		for (ServiceInstance app : apps) {
			URI uri = app.getUri();
			String serviceUrl = uri.toString();
			String res = HttpUtil.doPost(serviceUrl + "/refresh");
			sb.append(serviceUrl);
			sb.append(':');
			sb.append(res);
			sb.append("\r\n");
		}
		return "更新成功：\r\n" + sb.toString();
	}

}
