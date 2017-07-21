package com.platform.service.business.controller.feign;

import java.io.Serializable;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.platform.service.business.controller.request.inputModelBase.BaseAddForm;
import com.platform.service.business.controller.request.inputModelBase.BaseGetOneForm;
import com.platform.service.business.controller.request.inputModelBase.BaseKeyForm;
import com.platform.service.business.controller.request.inputModelBase.BaseSearchForm;
import com.platform.service.business.controller.response.BaseResponse;
import com.platform.service.business.controller.response.EntityListResponse;
import com.platform.service.business.controller.response.EntityResponse;
import com.platform.service.business.entity.BaseEntity;

public interface BaseFeignClient<T extends BaseEntity<K>, K extends Serializable> {

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public EntityListResponse search(@RequestBody BaseSearchForm form);

	@RequestMapping(value = "/getOne", method = RequestMethod.POST)
	public EntityResponse getOne(@RequestBody BaseGetOneForm form);

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public BaseResponse add(@RequestBody BaseAddForm form);

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public BaseResponse update(@RequestBody BaseAddForm form);

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public BaseResponse delete(@RequestBody BaseKeyForm<K> form);

}
