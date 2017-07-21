package com.platform.server.aspect;

import com.platform.service.business.aspect.AspectOrders;
import com.platform.service.business.aspect.ControllerAspectBase;
import com.platform.service.business.common.errors.ErrorInfos.CommonErr;
import com.platform.service.business.common.exception.ErrInfoException;
import com.platform.service.business.common.util.ValidateUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.validation.ConstraintViolation;
import java.util.Set;

@Aspect
@Configuration
@Order(AspectOrders.VALIDATE_ORDER)
public class ValidateAspect extends ControllerAspectBase {

	@Around(value = "pointCut()")
	public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
		for (Object arg : pjp.getArgs()) {
			Set<ConstraintViolation<Object>> violations = ValidateUtil.validOrReturnViolations(arg);
			if (!violations.isEmpty()) {
				throw new ErrInfoException(CommonErr.PARAM_ERR, buildMessageFromViolations(violations));
			}
		}
		return pjp.proceed();
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

}
