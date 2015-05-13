package com.aolong.core.user.service;

import java.util.List;
import java.util.Map;

import com.aolong.common.model.PageModel;
import com.aolong.common.model.PageParamsModel;
import com.aolong.core.user.model.UserModel;

public interface IUserService {
    
    public UserModel findById(String id);
    
    public List<UserModel> findAll();
    
    public List<UserModel> findAll(Map<String,Object> params);
    
    public PageModel findAll(PageParamsModel pageParams);
    
    public PageModel findAll(PageParamsModel pageParams, Map<String,Object> params);
    
    public void add(UserModel model);
    
    public void modify(UserModel model);
    
    public void remove(Object ids);
    
}
