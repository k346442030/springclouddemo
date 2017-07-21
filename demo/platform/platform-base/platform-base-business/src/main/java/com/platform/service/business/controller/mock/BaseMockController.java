package com.platform.service.business.controller.mock;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.platform.service.business.common.enums.MethodType;
import com.platform.service.business.common.util.ResponseUtil;
import com.platform.service.business.controller.base.BaseController;
import com.platform.service.business.controller.request.inputModelBase.BaseAddForm;
import com.platform.service.business.controller.request.inputModelBase.BaseGetOneForm;
import com.platform.service.business.controller.request.inputModelBase.BaseKeyForm;
import com.platform.service.business.controller.request.inputModelBase.BaseSearchForm;
import com.platform.service.business.controller.response.BaseResponse;
import com.platform.service.business.controller.response.EntityListResponse;
import com.platform.service.business.controller.response.EntityResponse;
import com.platform.service.business.dao.BaseRepository;
import com.platform.service.business.entity.BaseEntity;
import com.platform.service.business.service.BaseService;
import com.platform.service.business.service.BaseServiceImpl;

import io.swagger.annotations.ApiOperation;

public abstract class BaseMockController<T extends BaseEntity<K>, K extends Serializable> extends BaseController<T, K> {

	protected BaseService<T, K> getService() {
		return new BaseServiceImpl<T, K>() {

			@Override
			public T getEmptyEntity() {
				return getMockEmptyEntity();
			}

			@Override
			protected BaseRepository<T, K> getDao() {
				return null;
			}
		};
	}

	@ApiOperation(value = "列表查询", notes = "", hidden = true)
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public EntityListResponse search(@RequestBody BaseSearchForm form) {
		beforeInputExecute(form, MethodType.SEARCH);
		Page<T> pageRes = new PageImpl<T>(mockList(form.getSize()),
				new PageRequest(form.getPage() - 1, form.getSize(), new Sort(form.getSort())), 500);
		return new EntityListResponse(pageRes);
	}

	@ApiOperation(value = "查询单个", notes = "", hidden = true)
	@RequestMapping(value = "/getOne", method = RequestMethod.POST)
	public EntityResponse getOne(@RequestBody BaseGetOneForm form) {
		beforeInputExecute(form, MethodType.GETONE);
		T res = mockEntity(1);
		return new EntityResponse(res);
	}

	@ApiOperation(value = "新增", notes = "", hidden = true)
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public BaseResponse add(@RequestBody BaseAddForm form) {
		beforeInputExecute(form, MethodType.INSERT);
		return ResponseUtil.genSuccessResultVo();
	}

	@ApiOperation(value = "修改", notes = "", hidden = true)
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public BaseResponse update(@RequestBody BaseAddForm form) {
		beforeInputExecute(form, MethodType.UPDATE);
		return ResponseUtil.genSuccessResultVo();
	}

	@ApiOperation(value = "删除", notes = "", hidden = true)
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public BaseResponse delete(@RequestBody BaseKeyForm<K> form) {
		return ResponseUtil.genSuccessResultVo();
	}

	protected List<T> mockList(int size) {
		List<T> list = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			T entity = mockEntity(i);
			list.add(entity);
		}
		return list;
	}

	protected T mockEntity(int i) {
		T entity = this.getService().getEmptyEntity();
		Field[] fileds = entity.getClass().getDeclaredFields();
		try {
			for (Field field : fileds) {
				field.setAccessible(true);
				if (String.class.equals(field.getType())) {
					field.set(entity, "test" + field.getName() + i);
				} else if ("id".equals(field.getName())) {
					field.set(entity, i);
				} else if (Long.class.equals(field.getType()) || Integer.class.equals(field.getType())) {
					field.set(entity, Math.random() * 1000);
				} else if (BigDecimal.class.equals(field.getType())) {
					field.set(entity, new BigDecimal(Math.random() * 1000));
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return entity;
	}

	protected T getMockEmptyEntity() {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		@SuppressWarnings("unchecked")
		Class<T> entityClass = (Class<T>) params[0];
		try {
			return (T) entityClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

}
