package com.platform.server.webspider;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.platform.server.contents.FeignPath;
import com.platform.server.protocol.webspider.BaiduSpiderFeignClient;
import com.platform.server.protocol.webspider.request.SpiderPageReq;
import com.platform.service.business.controller.response.BaseResponse;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(FeignPath.WEB_SPIDER_PATH + BaiduSpiderMockController.PATH)
public class BaiduSpiderMockController implements BaiduSpiderFeignClient {

	@Override
	@ApiOperation(value = "爬取电视剧", notes = "爬取电视剧")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "start", value = "开始页码", defaultValue = "1", required = false, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "end", value = "结束页码", defaultValue = "30", required = false, paramType = "query", dataType = "String") })
	public BaseResponse spiderTvPlay(SpiderPageReq req) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@ApiOperation(value = "爬取电影", notes = "爬取电影")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "start", value = "开始页码", defaultValue = "1", required = false, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "end", value = "结束页码", defaultValue = "30", required = false, paramType = "query", dataType = "String") })
	public BaseResponse spiderMovie(SpiderPageReq req) {
		// TODO Auto-generated method stub
		return null;
	}

}
