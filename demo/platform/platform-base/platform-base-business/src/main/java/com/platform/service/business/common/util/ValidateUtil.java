package com.platform.service.business.common.util;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ValidateUtil {

	private static Validator validator;

	static {
		init();
	}

	private static void init() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	public static <T> Set<ConstraintViolation<T>> validOrReturnViolations(T bean) {
		return validator.validate(bean);
	}

	public static <T> String validOrReturnViolationsMessage(T bean, List<String> propertiesName) {

		Set<ConstraintViolation<T>> violations = new HashSet<ConstraintViolation<T>>();
		for (String propertyName : propertiesName) {
			violations.addAll(validator.validateProperty(bean, propertyName));
		}
		if (!violations.isEmpty()) {
			return buildMessageFromViolations(violations);
		}
		return null;
	}

	public static <T> String validOrReturnViolationsMessage(T bean) {
		Set<ConstraintViolation<T>> violations = validator.validate(bean);
		if (!violations.isEmpty()) {
			return buildMessageFromViolations(violations);
		}
		return null;
	}

	private static <T> String buildMessageFromViolations(Set<ConstraintViolation<T>> violations) {
		// !violations.isEmpty()
		StringBuilder sBuilder = new StringBuilder(violations.size() * 32);
		for (ConstraintViolation<T> violation : violations) {
			sBuilder.append('[').append(violation.getPropertyPath()).append(']').append(violation.getMessage())
					.append(',');
		}
		sBuilder.deleteCharAt(sBuilder.length() - 1);
		return sBuilder.toString();
	}

	public static <T> boolean hasInvalidOf(Set<ConstraintViolation<T>> violations, Class<?>... constraintAnnClasses) {
		if (null == violations || violations.isEmpty() || null == constraintAnnClasses
				|| 0 == constraintAnnClasses.length) {
			return false;
		}
		for (ConstraintViolation<T> violation : violations) {
			Class<?> violationConsAnnClass = violation.getConstraintDescriptor().getAnnotation().getClass();
			for (Class<?> consAnnClass : constraintAnnClasses) {
				if (consAnnClass.isAssignableFrom(violationConsAnnClass)) {
					return true;
				}
			}
		}
		return false;
	}

	public static <T> boolean hasInvalidAllOf(Set<ConstraintViolation<T>> violations,
			Class<?>... constraintAnnClasses) {
		if (null == violations || violations.isEmpty() || null == constraintAnnClasses
				|| 0 == constraintAnnClasses.length) {
			return false;
		}
		Set<Class<?>> checkConsAnnClasses = new HashSet<Class<?>>(constraintAnnClasses.length);
		for (Class<?> consAnnClass : constraintAnnClasses) {
			checkConsAnnClasses.add(consAnnClass);
		}
		Set<Class<?>> foundConsAnnClasses = new HashSet<Class<?>>(checkConsAnnClasses.size());
		for (ConstraintViolation<T> violation : violations) {
			Class<?> violationConsAnnClass = violation.getConstraintDescriptor().getAnnotation().getClass();
			for (Class<?> consAnnClass : constraintAnnClasses) {
				if (consAnnClass.isAssignableFrom(violationConsAnnClass)) {
					foundConsAnnClasses.add(consAnnClass);
				}
			}
		}
		return checkConsAnnClasses.size() == foundConsAnnClasses.size();
	}

}
