package com.aolong.common.service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aolong.common.mapper.CommonDao;
import com.aolong.common.model.PageModel;
import com.aolong.common.model.PageParamsModel;

public abstract class BaseService<T> implements IBaseService<T> {
    
    @Resource
    private CommonDao commonDao;
    
    public T findById(String id){
        return commonDao.selectOne(getNamespace()+".findById", id);
    }
    
    public List<T> findAll(){
        return commonDao.selectList(getNamespace()+".findAll");
    }
    
    public List<T> findAll(Map<String,Object> params){
        return commonDao.selectList(getNamespace()+".findAll", params);
    }
    
    public PageModel findAll(PageParamsModel pageParams){
        return commonDao.selectPage(getNamespace()+".findAll", pageParams);
    }
    
    public PageModel findAll(PageParamsModel pageParams, Map<String,Object> params){
        return commonDao.selectPage(getNamespace()+".findAll", pageParams, params);
    }
    
    public void add(T model){
        commonDao.insert(getNamespace()+".add", model);
    }
    
    public void modify(T model){
        commonDao.update(getNamespace()+".modify", model);
    }
    
    public void remove(Object ids){
        commonDao.delete(getNamespace()+".remove", ids);
    }
    
    private String getNamespace(){
        Type genType = getClass().getGenericSuperclass();   
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();   
        @SuppressWarnings("rawtypes")
        Class clazz =  (Class)params[0];   
        String namespace = clazz.getName();
        return namespace.replaceFirst(".model.", ".mapper.") .replaceFirst("Model$", "Mapper");
    }
    
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
}
