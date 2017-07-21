package com.platform.service.business.common.util;

import com.platform.service.business.controller.request.inputModelBase.BaseInputJSONForm;

public class RequestUtil {
    
    @SuppressWarnings("unchecked")
    public static <T extends BaseInputJSONForm> T setInputModelRequest(Object request, T form) {
        if (form == null) {
            form = (T) new BaseInputJSONForm();
        }
        form.setObject(request);
        return form;
    }
    
    public static BaseInputJSONForm genInputModelRequest(Object request) {
        return setInputModelRequest(request, null);
    }
    
    public static <T extends BaseInputJSONForm> T genInputModelRequest(Object request, Class<T> formClass) {
        T form;
        try {
            form = formClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
        return setInputModelRequest(request, form);
    }
    
}
