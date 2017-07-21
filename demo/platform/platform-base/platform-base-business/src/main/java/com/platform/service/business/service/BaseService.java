package com.platform.service.business.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.platform.service.business.common.enums.MethodType;
import com.platform.service.business.entity.BaseEntity;

/**
 * 
 * @author shenmy14613
 *
 * @param <T>操作的模型类
 * @param <K>主键类型
 */
public interface BaseService<T extends BaseEntity<K>, K extends Serializable> {

	T findOneByID(K id);

	List<T> findByIds(List<K> ids);

	List<T> getList();

	List<T> getList(T entity);

	T save(T demo);

	void delete(K id);

	Long count();

	Page<T> findByPage(T entity, Integer page, Integer size, Sort sort);

	T getOne(T entity);

	void inputModelValidate(T entity, MethodType inputType);

	T getEmptyEntity();

	void updateRepeatValidate(T entity, T old);

	void addRepeatValidate(T entity);

	Page<T> findByPage(T entity, Integer page, Integer size, Sort sort, Map<String, Object> searchMap);

	Specification<T> createSpec(T entity, Map<String, Object> searchMap);

	Long count(T entity);

	Class<T> getEntityClass();
}
