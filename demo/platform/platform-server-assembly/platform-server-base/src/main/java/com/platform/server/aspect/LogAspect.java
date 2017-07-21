package com.platform.server.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.alibaba.fastjson.JSONObject;
import com.platform.service.business.aspect.AspectOrders;
import com.platform.service.business.aspect.ControllerAspectBase;

@Aspect
@Configuration
@Order(AspectOrders.LOG_ORDER)
public class LogAspect extends ControllerAspectBase {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);

	@Override
	@Around(value = "pointCut()")
	protected Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {

		long costTimeStart = System.currentTimeMillis();
		Object o = pjp.proceed();
		// 不打印返回数据
		// LOGGER.debug("print API返回的数据如下：");
		// if(o != null){
		// LOGGER.debug(JSON.toJSON(o).toString());
		// }
		LOGGER.debug("请求方法：");
		LOGGER.debug(pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName());
		LOGGER.debug("请求参数：");
		LOGGER.debug(JSONObject.toJSONString(pjp.getArgs()));
		LOGGER.debug("返回参数：" + JSONObject.toJSONString(o));
		LOGGER.debug("api请求总耗时：" + (System.currentTimeMillis() - costTimeStart) + "ms");
		return o;
	}

}
