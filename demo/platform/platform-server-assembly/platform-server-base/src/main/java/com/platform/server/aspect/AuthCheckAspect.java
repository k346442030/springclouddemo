package com.platform.server.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.platform.server.annotations.NoLogin;
import com.platform.server.context.BusiDataContext;
import com.platform.service.business.aspect.AspectOrders;
import com.platform.service.business.aspect.ControllerAspectBase;
import com.platform.service.business.cache.SessionService;
import com.platform.service.business.cache.model.ClientSession;
import com.platform.service.business.common.errors.ErrorInfos.ClientErr;
import com.platform.service.business.common.exception.ErrInfoException;
import com.platform.service.business.context.ClientDataContext;
import com.platform.service.business.controller.request.BaseReqForm;

@Aspect
@Configuration
@Order(AspectOrders.AUTHCHECK_ORDER)
public class AuthCheckAspect extends ControllerAspectBase {

	@Autowired
	private SessionService sessionService;

	@Override
	@Around(value = "pointCut()")
	protected Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
		// 先清空下线程
		clearBinding();
		// 在Spring的环境里，signature就是MethodSignature
		MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
		// 获取Method
		Method method = methodSignature.getMethod();
		// 方法上没有申明@NoLogin且类上也没有申明@NoLogin，则检查登录状况
		if (method.isAnnotationPresent(NoLogin.class)
				|| method.getDeclaringClass().isAnnotationPresent(NoLogin.class)) {
			return pjp.proceed();
		}
		for (Class<?> feignClasses : method.getDeclaringClass().getInterfaces()) {
			Method superMethod = feignClasses.getMethod(method.getName(), method.getParameterTypes());
			if (superMethod != null && (superMethod.isAnnotationPresent(NoLogin.class)
					|| superMethod.getDeclaringClass().isAnnotationPresent(NoLogin.class))) {
				return pjp.proceed();
			}
		}
		Object form = pjp.getArgs()[0];
		if (form == null || !(form instanceof BaseReqForm)) {
			throw new ErrInfoException(ClientErr.NO_LOGIN);
		}
		// 检查登录及权限
		checkAndBindLoginInfo((BaseReqForm) form);
		// 执行业务
		return pjp.proceed();
	}

	private void checkAndBindLoginInfo(BaseReqForm form) {
		// 获取携带的access_token
		String access_token = form.getAccessToken();
		// 获取会话身份对象
		ClientSession identity = sessionService.fetchClientCacheByUserToken(access_token);
		if (null == identity) {
			// 如果会话身份对象不存在则表示未登录或已超时
			throw new ErrInfoException(ClientErr.NO_LOGIN);
		}
		sessionService.refreshToken(identity);
		BusiDataContext.setClientSession(identity);
	}

	private void clearBinding() {
		ClientDataContext.clear();
	}
}
