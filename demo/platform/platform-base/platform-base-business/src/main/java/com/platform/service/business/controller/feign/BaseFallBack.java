package com.platform.service.business.controller.feign;

import java.io.Serializable;

import com.platform.service.business.common.errors.ErrorInfos.CommonErr;
import com.platform.service.business.common.util.ResponseUtil;
import com.platform.service.business.controller.request.inputModelBase.BaseAddForm;
import com.platform.service.business.controller.request.inputModelBase.BaseKeyForm;
import com.platform.service.business.controller.request.inputModelBase.BaseGetOneForm;
import com.platform.service.business.controller.request.inputModelBase.BaseSearchForm;
import com.platform.service.business.controller.response.BaseResponse;
import com.platform.service.business.controller.response.EntityListResponse;
import com.platform.service.business.controller.response.EntityResponse;
import com.platform.service.business.entity.BaseEntity;

public class BaseFallBack<T extends BaseEntity<K>, K extends Serializable> implements BaseFeignClient<T, K> {

	@Override
	public EntityListResponse search(BaseSearchForm form) {
		return fallBack(EntityListResponse.class);
	}

	@Override
	public EntityResponse getOne(BaseGetOneForm form) {
		return fallBack(EntityResponse.class);
	}

	@Override
	public BaseResponse add(BaseAddForm form) {
		return fallBack();
	}

	@Override
	public BaseResponse update(BaseAddForm form) {
		return fallBack();
	}

	@Override
	public BaseResponse delete(BaseKeyForm<K> form) {
		return fallBack();
	}

	protected BaseResponse fallBack() {
		return ResponseUtil.genErrorResultVo(CommonErr.SERVER_NOT_EXIST);
	}

	protected <S extends BaseResponse> S fallBack(Class<S> back) {
		return ResponseUtil.genErrorResult(CommonErr.SERVER_NOT_EXIST, back);
	}

}
