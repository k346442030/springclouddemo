package com.platform.service.business.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {})
@ReportAsSingleViolation
@Pattern(regexp = "")
public @interface Mobile {
    
    Class<?>[] groups() default {};
    
    String message() default "手机号码格式不正确";
    
    Class<? extends Payload>[] payload() default {};
    
    @OverridesAttribute(constraint = Pattern.class, name = "regexp")
    String regexp() default "^(1[34578]\\d{9})?$";
    
}
