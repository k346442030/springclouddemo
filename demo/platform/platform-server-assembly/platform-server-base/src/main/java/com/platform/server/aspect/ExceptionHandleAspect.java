package com.platform.server.aspect;

import com.platform.service.business.aspect.AspectOrders;
import com.platform.service.business.aspect.ControllerAspectBase;
import com.platform.service.business.common.errors.ErrorInfos.CommonErr;
import com.platform.service.business.common.exception.ErrInfoException;
import com.platform.service.business.common.util.ResponseUtil;
import com.platform.service.business.controller.response.BaseResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;

@Aspect
@Configuration
@Order(AspectOrders.EXCEPTION_HANDLE_ORDER)
public class ExceptionHandleAspect extends ControllerAspectBase {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandleAspect.class);

	@SuppressWarnings("unchecked")
	@Override
	@Around(value = "pointCut()")
	protected Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
		Object result = null;
		MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
		try {
			result = pjp.proceed();
		} catch (ErrInfoException e) {
			LOGGER.error("execute failed", e);
			result = ResponseUtil.genErrorResult(e.getErrorNo(), e.getErrorInfo(), methodSignature.getReturnType());
		} catch (Exception e) {
			LOGGER.error("execute failed", e);
			result = ResponseUtil.genErrorResult(CommonErr.ERROR, methodSignature.getReturnType());
		}
		if (result instanceof BaseResponse) {
			BaseResponse res = (BaseResponse) result;
			if (StringUtils.isEmpty(res.getError_info())) {
				res = ResponseUtil.setSuccessResult(res);
			}
		}
		return result;
	}

}
