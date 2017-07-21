package com.platform.service.business.common.annotation;


import com.platform.service.business.common.enums.MethodType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEmpty {

	MethodType[] inputType();
}
