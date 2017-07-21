package com.platform.service.business.controller.base;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.platform.service.business.common.annotation.basemethod.MethodBase;
import com.platform.service.business.common.enums.MethodType;
import com.platform.service.business.common.errors.ErrorInfos.CommonErr;
import com.platform.service.business.common.exception.ErrInfoException;
import com.platform.service.business.common.util.ResponseUtil;
import com.platform.service.business.controller.feign.BaseFeignClient;
import com.platform.service.business.controller.request.inputModelBase.BaseAddForm;
import com.platform.service.business.controller.request.inputModelBase.BaseGetOneForm;
import com.platform.service.business.controller.request.inputModelBase.BaseInputJSONForm;
import com.platform.service.business.controller.request.inputModelBase.BaseKeyForm;
import com.platform.service.business.controller.request.inputModelBase.BaseSearchForm;
import com.platform.service.business.controller.response.BaseResponse;
import com.platform.service.business.controller.response.EntityListResponse;
import com.platform.service.business.controller.response.EntityResponse;
import com.platform.service.business.entity.BaseEntity;
import com.platform.service.business.service.BaseService;

public abstract class BaseController<T extends BaseEntity<K>, K extends Serializable> implements BaseFeignClient<T, K> {

	protected abstract BaseService<T, K> getService();

	@MethodBase(methodType = MethodType.SEARCH)
	public EntityListResponse search(BaseSearchForm form) {
		T entity = beforeInputExecute(form, MethodType.SEARCH);
		beforeSearch(entity);
		Page<T> pageRes = getService().findByPage(entity, form.getPage(), form.getSize(),
				form.getSort() == null ? null : new Sort(form.getSort()), form.getForm());
		return afterSearch(new EntityListResponse(pageRes));
	}

	protected void beforeSearch(T entity) {

	}

	protected EntityListResponse afterSearch(EntityListResponse res) {
		return res;
	}

	@MethodBase(methodType = MethodType.GETONE)
	public EntityResponse getOne(BaseGetOneForm form) {
		T entity = beforeInputExecute(form, MethodType.GETONE);
		beforeGetOne(entity);
		T res = getService().getOne(entity);
		return afterGetOne(new EntityResponse(res));
	}

	protected void beforeGetOne(T entity) {

	}

	protected EntityResponse afterGetOne(EntityResponse res) {
		return res;
	}

	@MethodBase(methodType = MethodType.INSERT)
	public BaseResponse add(BaseAddForm form) {
		T entity = beforeInputExecute(form, MethodType.INSERT);
		beforeAdd(entity);
		getService().addRepeatValidate(entity);
		getService().save(entity);
		return ResponseUtil.genSuccessResultVo();
	}

	protected void beforeAdd(T entity) {

	}

	@MethodBase(methodType = MethodType.UPDATE)
	public BaseResponse update(BaseAddForm form) {
		T entity = beforeInputExecute(form, MethodType.UPDATE);
		T old = exist(entity.keyValue());
		getService().updateRepeatValidate(entity, old);
		beforeUpdate(entity);
		getService().save(entity);
		return ResponseUtil.genSuccessResultVo();
	}

	protected void beforeUpdate(T entity) {

	}

	@MethodBase(methodType = MethodType.DELETE)
	public BaseResponse delete(BaseKeyForm<K> form) {
		K key = form.getKey();
		exist(key);
		getService().delete(key);
		return ResponseUtil.genSuccessResultVo();
	}

	protected T beforeInputExecute(BaseInputJSONForm form, MethodType inputType) {
		JSONObject map = form.getForm();
		T entity = getInputEntity(map);
		getService().inputModelValidate(entity, inputType);
		return entity;
	}

	protected T exist(K key) {
		if (StringUtils.isEmpty(key)) {
			throw new ErrInfoException(CommonErr.PARAM_ERR);
		}
		T old = getService().findOneByID(key);
		if (old == null) {
			throw new ErrInfoException(CommonErr.DATA_NOT_EXIST);
		}
		return old;
	}

	protected T getInputEntity(JSONObject map) {
		T entity = JSONObject.toJavaObject(map, getService().getEntityClass());
		if (entity == null) {
			entity = getService().getEmptyEntity();
		}
		return entity;
	}

}
