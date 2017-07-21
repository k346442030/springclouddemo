package com.platform.server.tvplay;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.platform.server.contents.FeignPath;
import com.platform.server.protocol.tvplay.TvplayFeignClient;
import com.platform.server.protocol.tvplay.entity.Tvplay;
import com.platform.server.protocol.tvplay.request.AddTvplayReq;
import com.platform.service.business.controller.mock.BaseMockController;
import com.platform.service.business.controller.response.BaseResponse;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(FeignPath.TVPLAY_PATH + TvplayFeignClient.PATH)
public class TvplayMockController extends BaseMockController<Tvplay, String> implements TvplayFeignClient{

	@Override
	protected Tvplay getMockEmptyEntity() {
		return new Tvplay();
	}
	
	@Override
	@ApiOperation(value = "新增电视剧", notes = "新增电视剧")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "name", value = "机构名", required = true, paramType = "query", dataType = "String")})
	public BaseResponse addTvplay(AddTvplayReq form) {
		return null;
	}

}
