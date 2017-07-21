package com.platform.service.business.controller.request.inputModelBase;

public class BaseSearchForm extends BaseInputJSONForm {
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    
    private Integer page;
    
    private Integer size;
    
    private String sort;
    
    public Integer getPage() {
        return page;
    }
    
    public void setPage(Integer page) {
        this.page = page;
    }
    
    public Integer getSize() {
        return size;
    }
    
    public void setSize(Integer size) {
        this.size = size;
    }
    
    public String getSort() {
        return sort;
    }
    
    public void setSort(String sort) {
        this.sort = sort;
    }
    
}
