//package com.platform.server.aspect;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.actuate.metrics.CounterService;
//import org.springframework.boot.actuate.metrics.GaugeService;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//import com.platform.service.business.aspect.AspectOrders;
//import com.platform.service.business.config.ConfigValues;
//
//@Aspect
//@Component
//@Order(AspectOrders.MONITOR_ORDER)
//public class MonitorAspect {
//
//	@Autowired
//	private CounterService counterService;
//	@Autowired
//	private GaugeService gaugeService;
//
//	@Before("execution(* " + ConfigValues.BUSINESS_CONTROLLER_PACKEG + ".*.*(..))")
//	public void countServiceInvoke(JoinPoint joinPoint) {
//		counterService.increment(joinPoint.getSignature().toString());
//	}
//
//	@Around("execution(* " + ConfigValues.BUSINESS_CONTROLLER_PACKEG + ".*.*(..))")
//	public Object latencyService(ProceedingJoinPoint pjp) throws Throwable {
//		long start = System.currentTimeMillis();
//		Object o = pjp.proceed();
//		long end = System.currentTimeMillis();
//		gaugeService.submit(pjp.getSignature().toString(), end - start);
//		return o;
//	}
//
//}
