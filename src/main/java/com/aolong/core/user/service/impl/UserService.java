package com.aolong.core.user.service.impl;

import javax.annotation.Resource;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aolong.common.service.BaseService;
import com.aolong.core.user.model.UserModel;
import com.aolong.core.user.service.IUserService;

@Service
@Transactional(rollbackFor=Exception.class)
public class UserService extends BaseService<UserModel> implements IUserService{
    
    @Resource
    private Md5PasswordEncoder passwordEncoder;
    
    public void add(UserModel model){
        
        model.setPassword(passwordEncoder.encodePassword(model.getPassword(), model.getLoginName()));
        
        super.add(model);
    }
}
