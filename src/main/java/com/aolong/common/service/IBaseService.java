package com.aolong.common.service;

import java.util.List;
import java.util.Map;

import com.aolong.common.model.PageModel;
import com.aolong.common.model.PageParamsModel;

public interface IBaseService<T> {
    
    public T findById(String id);
    
    public List<T> findAll();
    
    public List<T> findAll(Map<String,Object> params);
    
    public PageModel findAll(PageParamsModel pageParams);
    
    public PageModel findAll(PageParamsModel pageParams, Map<String,Object> params);
    
    public void add(T model);
    
    public void modify(T model);
    
    public void remove(Object ids);
    
}
