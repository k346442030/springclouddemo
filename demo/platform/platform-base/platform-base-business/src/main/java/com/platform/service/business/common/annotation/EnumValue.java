package com.platform.service.business.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.platform.service.business.common.validator.EnumValidator;

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = EnumValidator.class)
public @interface EnumValue {
    
    Class<?>[] groups() default {};
    
    String message() default "枚举值错误";
    
    Class<? extends Payload>[] payload() default {};
    
    String errorCode() default "";
    
    /**
     * 可选值枚举
     * @return
     */
    String[] enums() default {};
    
    /**
     * 字符串分割符，应为正则，用于{@link java.lang.String#split(String)}
     * @return
     */
    String stringDelimiter() default "";
    
    /**
     * 可选值枚举类
     * @return
     */
    Class<? extends Enum<?>> enumClass() default Thread.State.class;
    
}
