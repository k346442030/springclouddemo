package com.platform.service.business.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Pointcut;

import com.platform.service.business.config.ConfigValues;

public abstract class ControllerAspectBase {
    
    @Pointcut("execution(public * " + ConfigValues.BUSINESS_CONTROLLER_PACKEG
            + ".*.*(..)) || execution(public * com.platform.service.business.controller.base.*.*(..))"
            + "|| execution(public * com.platform.service.business.controller.mock.*.*(..))")
    public void pointCut() {
    }
    
    // @Around注解写到这里会导致aop执行两次，具体原因不明
    protected abstract Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable;
    
}
