package com.platform.server.movie;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.platform.server.contents.FeignPath;
import com.platform.server.protocol.movie.MovieFeignClient;
import com.platform.server.protocol.movie.entity.Movie;
import com.platform.server.protocol.movie.request.MovieAddReq;
import com.platform.server.protocol.movie.request.MovieSearchReq;
import com.platform.server.protocol.movie.response.MovieSearchRes;
import com.platform.service.business.controller.mock.BaseMockController;
import com.platform.service.business.controller.request.inputModelBase.BaseGetOneForm;
import com.platform.service.business.controller.response.BaseResponse;
import com.platform.service.business.controller.response.EntityResponse;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(FeignPath.MOVIE_PATH + MovieFeignClient.PATH)
public class MovieMockController extends BaseMockController<Movie, String> implements MovieFeignClient{

	@Override
	public BaseResponse addMovie(MovieAddReq req) {
		return null;
	}

	@Override
	@ApiOperation(value = "搜索电影", notes = "搜索电影")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "key_word", value = "关键字", required = true, paramType = "query", dataType = "String")})
	public MovieSearchRes searchMovie(MovieSearchReq req) {
		return null;
	}
	
	@Override
	@ApiOperation(value = "搜索电影", notes = "搜索电影")
	public EntityResponse getOne(BaseGetOneForm form) {
		return super.getOne(form);
	}
}
