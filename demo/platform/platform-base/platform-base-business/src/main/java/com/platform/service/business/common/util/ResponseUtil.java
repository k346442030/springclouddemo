package com.platform.service.business.common.util;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.platform.service.business.common.errors.ErrorInfoBase;
import com.platform.service.business.common.errors.ErrorInfos.CommonErr;
import com.platform.service.business.controller.response.BaseResponse;
import com.platform.service.business.controller.response.EntityListResponse;
import com.platform.service.business.controller.response.EntityResponse;
import com.platform.service.business.entity.BaseEntity;

public abstract class ResponseUtil {
    
    public static <T extends BaseResponse> T genErrorResult(ErrorInfoBase errInfo, Class<T> clazz) {
        try {
            T t = clazz.newInstance();
            return setErrorResult(errInfo, t);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static <T extends BaseResponse> T setErrorResult(ErrorInfoBase errInfo, T result) {
        result.setError_info(errInfo.getError_info());
        result.setError_no(errInfo.getError_no());
        return result;
    }
    
    public static BaseResponse genErrorResultVo(ErrorInfoBase errInfo) {
        return genErrorResult(errInfo, BaseResponse.class);
    }
    
    public static BaseResponse genSuccessResultVo() {
        return genErrorResult(CommonErr.SUCCESS, BaseResponse.class);
    }
    
    public static <T extends BaseResponse> T setSuccessResult(T result) {
        return setErrorResult(CommonErr.SUCCESS, result);
    }
    
    public static <T extends BaseEntity<?>> EntityListResponse genListResult(List<T> dataList, int page,
            long total, EntityListResponse listResponse) {
        if (listResponse == null) {
            listResponse = new EntityListResponse();
        }
        listResponse.setDataList(JSONArray.parseArray(JSONArray.toJSONString(dataList)));
        listResponse.setPage(page);
        listResponse.setTotal(total);
        return setSuccessResult(listResponse);
    }
    
    public static <T extends BaseEntity<?>> EntityListResponse genListResult(List<T> dataList, int page,
            long total) {
        return genListResult(dataList, page, total, null);
    }
    
    public static <T extends BaseEntity<?>> EntityResponse genEntityResult(T data, EntityResponse response) {
        if (response == null) {
            response = new EntityResponse();
        }
        response.setData(JSONObject.parseObject(JSONObject.toJSONString(data)));
        return setSuccessResult(response);
    }
    
    public static <T extends BaseEntity<?>> EntityResponse genEntityResult(T data) {
        return genEntityResult(data, null);
    }
    
    public static <T extends BaseResponse> T genErrorResult(Integer errorNo, String errorInfo, Class<T> clazz) {
        try {
            T t = clazz.newInstance();
            return setErrorResult(errorNo, errorInfo, t);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static <T extends BaseResponse> T setErrorResult(Integer errorNo, String errorInfo, T result) {
        result.setError_no(errorNo);
        result.setError_info(errorInfo);
        return result;
    }
    
    public static boolean isSuccess(BaseResponse res) {
        return res != null
                && (res.getError_no() == null || res.getError_no().equals(CommonErr.SUCCESS.getError_no()));
    }
}
