package com.platform.service.business.common.validator;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

import com.platform.service.business.common.annotation.EnumValue;
import com.platform.service.business.common.util.EnumUtil;

public class EnumValidator implements ConstraintValidator<EnumValue, Object> {
    
    private Set<String> cadidators;
    
    private String stringDelimiter;
    
    private String messageTemplate;
    
    private Map<String, Object> msgTmpValueMap;
    
    @Override
    public void initialize(EnumValue constraintAnnotation) {
        cadidators = new LinkedHashSet<String>();
        String[] enums = constraintAnnotation.enums();
        // 如果设置了enums，以enums为准
        if (null != enums && 0 != enums.length) {
            for (String enumVal : enums) {
                cadidators.add(enumVal);
            }
        } else {
            // 查看是否设置了enumClass
            Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClass();
            if (!Thread.State.class.equals(enumClass)) {
                Collection<?> enumValues = EnumUtil.getEnumMap(enumClass).keySet();
                for (Object enumValue : enumValues) {
                    cadidators.add(String.valueOf(enumValue));
                }
                // FIXME 处理提示错误信息时不能显示可选枚举值的问题
            }
        }
        
        stringDelimiter = constraintAnnotation.stringDelimiter();
        
        messageTemplate = constraintAnnotation.message();
        msgTmpValueMap = new HashMap<String, Object>();
        msgTmpValueMap.put("enums", cadidators.toString());
    }
    
    @SuppressWarnings("rawtypes")
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        // 空值直接通过，由@NotNull之类的控制
        if (null == value || "".equals(value)) {
            return true;
        }
        // 没设置候选限制，通过
        if (cadidators.isEmpty()) {
            return true;
        }
        
        boolean valid = true;
        if (value.getClass().isArray()) {
            // 如果是数组，遍历检查
            for (Object valueUnit : (Object[]) value) {
                // 因为cadidators为String，转化为String比较
                valueUnit = String.valueOf(valueUnit);
                if (!cadidators.contains(valueUnit)) {
                    valid = false;
                    break;
                }
            }
        } else if (value instanceof Collection) {
            // 如果是Collection，遍历检查
            for (Object valueUnit : (Collection) value) {
                // 因为cadidators为String，转化为String比较
                valueUnit = String.valueOf(valueUnit);
                if (!cadidators.contains(valueUnit)) {
                    valid = false;
                    break;
                }
            }
        } else {
            // 其他类型转化为字符串处理
            String stringValue = String.valueOf(value);
            
            if (!StringUtils.isEmpty(stringDelimiter)) {
                // 如果设置了分割符，分割后遍历
                String[] values = stringValue.split(stringDelimiter);
                for (String valueUnit : values) {
                    if (!cadidators.contains(valueUnit)) {
                        valid = false;
                        break;
                    }
                }
            } else {
                // 没设置分割符，直接检查
                valid = cadidators.contains(stringValue);
            }
        }
        
        if (!valid) {
            context.disableDefaultConstraintViolation();
            //            String message = StrSubstitutor.replace(messageTemplate, msgTmpValueMap, "{", "}");
            String message = messageTemplate + msgTmpValueMap.get("enums");
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        }
        
        return valid;
    }
}
