package com.platform.server.music;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.platform.server.contents.FeignPath;
import com.platform.server.protocol.music.MusicFeignClient;
import com.platform.server.protocol.music.request.MusicSourceReq;
import com.platform.server.protocol.music.request.SearchMusicReq;
import com.platform.server.protocol.music.response.MusicList;
import com.platform.service.business.controller.response.BaseResponse;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(FeignPath.MUSIC_PATH + MusicFeignClient.PATH)
public class MusicMockController implements MusicFeignClient{

	@Override
	@ApiOperation(value = "搜索音乐", notes = "搜索音乐")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "keyWord", value = "关键字", required = true, paramType = "query", dataType = "String")})
	public MusicList searchMusic(SearchMusicReq req) {
		return null;
	}

	@Override
	@ApiOperation(value = "设置音乐来源", notes = "设置音乐来源")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "musicSource", value = "来源：1 qq,2 baidu", required = true, paramType = "query", dataType = "Integer")})
	public BaseResponse setMusicSource(MusicSourceReq req) {
		return null;
	}


}
