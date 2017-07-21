package com.platform.service.business.service;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;

import com.platform.service.business.common.annotation.InputField;
import com.platform.service.business.common.annotation.NoRepeatField;
import com.platform.service.business.common.annotation.NotEmpty;
import com.platform.service.business.common.constant.SearchType;
import com.platform.service.business.common.enums.DeleteFlag;
import com.platform.service.business.common.enums.MethodType;
import com.platform.service.business.common.errors.ErrorInfos.CommonErr;
import com.platform.service.business.common.exception.ErrInfoException;
import com.platform.service.business.common.util.ValidateUtil;
import com.platform.service.business.dao.BaseRepository;
import com.platform.service.business.entity.BaseEntity;
import com.platform.service.business.entity.SoftDelEntity;

public abstract class BaseServiceImpl<T extends BaseEntity<K>, K extends Serializable> implements BaseService<T, K> {

	protected abstract BaseRepository<T, K> getDao();

	@Override
	public T findOneByID(K id) {
		return getDao().findOne(id);
	}

	@Override
	public List<T> findByIds(List<K> ids) {
		return getDao().findAll(ids);
	}

	@Override
	public List<T> getList(T entity) {
		setNotDelete(entity);
		return getDao().findAll(Example.of(entity));
	}

	@Override
	public List<T> getList() {
		return (List<T>) getDao().findAll();
	}

	@Override
	public T save(T entity) {
		setNotDelete(entity);
		return getDao().save(entity);
	}

	@Override
	public void delete(K id) {
		getDao().delete(id);
	}

	@Override
	public Long count() {
		return getDao().count();
	}

	@Override
	public Long count(T entity) {
		return getDao().count(Example.of(entity));
	}

	@Override
	public T getOne(T entity) {
		setNotDelete(entity);
		Example<T> example = Example.of(entity);
		return getDao().findOne(example);
		// Page<T> page = getDao().findAll(example, new PageRequest(0, 1));
		// List<T> list = page.getContent();
		// if (list == null || list.isEmpty()) {
		// return null;
		// }
		// return list.get(0);
	}

	@Override
	public Page<T> findByPage(T entity, Integer page, Integer size, Sort sort) {
		return this.findByPage(entity, page, size, sort, new HashMap<>());
	}

	@Override
	public Page<T> findByPage(T entity, Integer page, Integer size, Sort sort, Map<String, Object> searchMap) {
		setNotDelete(entity);
		Pageable pageable = new PageRequest(page - 1, size, sort);
		Specification<T> spec = createSpec(entity, searchMap);
		return getDao().findAll(spec, pageable);
	}

	@Override
	public Specification<T> createSpec(T entity, Map<String, Object> searchMap) {
		Specification<T> spec = new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Field[] fileds = entity.getClass().getDeclaredFields();
				try {
					List<Predicate> preList = new ArrayList<>();
					for (Field field : fileds) {
						if (!field.isAnnotationPresent(Column.class) && !field.isAnnotationPresent(Id.class)) {
							continue;
						}
						field.setAccessible(true);
						if (field.get(entity) == null || StringUtils.isEmpty(field.get(entity))) {
							continue;
						}
						Object value = field.get(entity);
						String colName = field.getName();
						Predicate pre = cb.equal(root.get(colName), value);
						preList.add(pre);
					}
					for (Entry<String, Object> entry : searchMap.entrySet()) {
						if (!entry.getKey().contains("$")) {
							continue;
						}
						String[] keys = entry.getKey().split("\\$");
						Object value = entry.getValue();
						String colName = keys[0];
						Predicate pre = null;
						Field field = null;
						switch (keys[1]) {
						case SearchType.LT:
							try {
								field = entity.getClass().getDeclaredField(colName);
							} catch (NoSuchFieldException e) {
								e.printStackTrace();
							}
							if (field == null) {
								break;
							}
							if (field.getType().isAssignableFrom(Number.class)) {
								pre = cb.lt(root.get(keys[0]).as(Number.class),
										(NumberUtils.parseNumber(String.valueOf(value), Number.class)));
							} else if (field.getType().isAssignableFrom(Date.class)) {
								// 只能是时间戳
								pre = cb.lessThan(root.get(keys[0]).as(Date.class),
										new Date(Long.valueOf(String.valueOf(value))));
							}
							break;
						case SearchType.GT:
							try {
								field = entity.getClass().getDeclaredField(colName);
							} catch (NoSuchFieldException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if (field == null) {
								break;
							}
							if (field.getType().isAssignableFrom(Number.class)) {
								pre = cb.gt(root.get(keys[0]).as(Number.class),
										(NumberUtils.parseNumber(String.valueOf(value), Number.class)));
							} else if (field.getType().isAssignableFrom(Date.class)) {
								// 只能是时间戳
								pre = cb.greaterThan(root.get(keys[0]).as(Date.class),
										new Date(Long.valueOf(String.valueOf(value))));
							}
							break;
						case SearchType.LIKE:
							pre = cb.like(root.get(colName), String.valueOf("%" + value + "%"));
							break;
						default:
							continue;
						}
						if (pre != null) {
							preList.add(pre);
						}
					}
					query.where(preList.toArray(new Predicate[preList.size()]));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				}
				return null;
			}
		};
		return spec;
	}

	@SuppressWarnings("unchecked")
	protected void setNotDelete(T entity) {
		if (entity instanceof SoftDelEntity) {
			((SoftDelEntity<T>) entity).setDeleteFlag(DeleteFlag.NOT_DELETE.getValue());
		}
	}

	@Override
	public void inputModelValidate(T entity, MethodType inputType) {
		Field[] fileds = entity.getClass().getDeclaredFields();
		List<String> propertiesName = new ArrayList<String>();
		try {
			for (Field field : fileds) {
				field.setAccessible(true);
				String value = field.get(entity) == null ? null : String.valueOf(field.get(entity));
				if (field.isAnnotationPresent(NotEmpty.class)
						&& containsInputType(inputType, field.getAnnotation(NotEmpty.class).inputType())
						&& StringUtils.isEmpty(value)) {
					throw new ErrInfoException(CommonErr.PARAM_MISS, field.getName());
				}
				if (field.isAnnotationPresent(InputField.class)) {
					InputField inputFieldAnno = field.getAnnotation(InputField.class);
					MethodType[] inputTypes = inputFieldAnno.methodType();
					boolean trueType = containsInputType(inputType, inputTypes);
					if (trueType) {
						propertiesName.add(field.getName());
					}
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		String msg = ValidateUtil.validOrReturnViolationsMessage(entity, propertiesName);
		if (!StringUtils.isEmpty(msg)) {
			throw new ErrInfoException(CommonErr.PARAM_ERR, msg);
		}
	}

	private boolean containsInputType(MethodType inputType, MethodType[] inputTypes) {
		boolean trueType = false;
		for (MethodType type : inputTypes) {
			if (type.equals(inputType)) {
				trueType = true;
				break;
			}
		}
		return trueType;
	}

	public void updateRepeatValidate(T entity, T old) {
		Field[] fileds = entity.getClass().getDeclaredFields();
		try {
			for (Field field : fileds) {
				field.setAccessible(true);
				if (field.isAnnotationPresent(NoRepeatField.class)) {
					Object value = field.get(entity);
					if (value == null) {
						continue;
					}
					Object oldValue = field.get(old);
					if (value.equals(oldValue)) {
						continue;
					}
					T example = this.getEmptyEntity();
					field.set(example, value);
					T entityTemp = this.getOne(example);
					if (entityTemp != null) {
						NoRepeatField inputFieldAnno = field.getAnnotation(NoRepeatField.class);
						String msg = inputFieldAnno.message();
						throw new ErrInfoException(CommonErr.PARAM_ERR, msg);
					}
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void addRepeatValidate(T entity) {
		Field[] fileds = entity.getClass().getDeclaredFields();
		try {
			for (Field field : fileds) {
				field.setAccessible(true);
				if (field.isAnnotationPresent(NoRepeatField.class)) {
					Object value = field.get(entity);
					if (value == null || "".equals(value)) {
						continue;
					}
					T example = this.getEmptyEntity();
					field.set(example, value);
					T entityTemp = this.getOne(example);
					if (entityTemp != null) {
						NoRepeatField inputFieldAnno = field.getAnnotation(NoRepeatField.class);
						String msg = inputFieldAnno.message();
						throw new ErrInfoException(CommonErr.PARAM_ERR, msg);
					}
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Class<T> getEntityClass() {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		@SuppressWarnings("unchecked")
		Class<T> entityClass = (Class<T>) params[0];
		return entityClass;
	}

	@Override
	public T getEmptyEntity() {
		try {
			return getEntityClass().newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
