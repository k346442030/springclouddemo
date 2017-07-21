package com.platform.service.business.aspect;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Map.Entry;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;
import com.platform.service.business.common.annotation.basemethod.MethodBase;
import com.platform.service.business.common.annotation.basemethod.MethodBaseAllow;
import com.platform.service.business.common.enums.MethodType;
import com.platform.service.business.common.errors.ErrorInfos.ClientErr;
import com.platform.service.business.common.exception.ErrInfoException;
import com.platform.service.business.controller.request.BaseReqForm;
import com.platform.service.business.controller.request.inputModelBase.BaseInputJSONForm;
import com.platform.service.business.controller.request.inputModelBase.BaseSearchForm;

@Aspect
@Configuration
@Order(AspectOrders.INPUTMODEL_ORDER)
public class InputModelAspect extends ControllerAspectBase {

	// @Pointcut("execution(public * " + ConfigValues.OPENAPI_CONTROLLER_PACKEG
	// + ".*.*(..)) ")
	// public void pointCut() {
	// }

	@Around(value = "pointCut()")
	protected Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
		// 获取Method
		Method method = methodSignature.getMethod();
		if (method.isAnnotationPresent(MethodBase.class)) {
			if (!method.getDeclaringClass().isAnnotationPresent(MethodBaseAllow.class)) {
				throw new ErrInfoException(ClientErr.NO_PERMISSION);
			}
			MethodType methodType = method.getAnnotation(MethodBase.class).methodType();
			MethodBaseAllow allow = method.getDeclaringClass().getAnnotation(MethodBaseAllow.class);
			boolean allowflag = false;
			for (MethodType type : allow.methodType()) {
				if (type.equals(methodType)) {
					allowflag = true;
					break;
				}
			}
			if (!allowflag) {
				throw new ErrInfoException(ClientErr.NO_PERMISSION);
			}
		}
		for (Object arg : pjp.getArgs()) {
			if (arg instanceof BaseReqForm) {
				try {
					HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
							.getRequestAttributes()).getRequest();
					BufferedReader br = new BufferedReader(
							new InputStreamReader((ServletInputStream) request.getInputStream()));
					String line = null;
					StringBuilder sb = new StringBuilder();
					while ((line = br.readLine()) != null) {
						sb.append(line);
					}
					JSONObject json = JSONObject.parseObject(sb.toString());
					if (json != null && !json.isEmpty()) {
						Object argTemp = JSONObject.parseObject(JSONObject.toJSONString(json), arg.getClass());
						BeanUtils.copyProperties(argTemp, arg);
					}
					if (arg instanceof BaseInputJSONForm) {
						BaseInputJSONForm form = ((BaseInputJSONForm) arg);
						JSONObject jsonForm = form.getForm() == null ? new JSONObject() : form.getForm();
						for (Entry<String, String[]> e : request.getParameterMap().entrySet()) {
							jsonForm.put(e.getKey(), e.getValue()[0]);
						}
						form.setForm(jsonForm);
						if (jsonForm.isEmpty() && json != null && !json.isEmpty()) {
							form.setForm(json);
						}
					}
				} catch (Exception e) {
				}
			}
			if (arg instanceof BaseSearchForm) {
				BaseSearchForm pageForm = (BaseSearchForm) arg;
				if (pageForm.getPage() == null) {
					pageForm.setPage(1);
					pageForm.setSize(100);
				}
			}
		}
		return pjp.proceed();
	}

}
