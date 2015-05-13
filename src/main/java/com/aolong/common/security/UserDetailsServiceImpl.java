package com.aolong.common.security;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aolong.core.user.mapper.UserMapper;
import com.aolong.core.user.model.UserModel;

@Service
@Transactional(rollbackFor=Exception.class)
public class UserDetailsServiceImpl implements UserDetailsService {
	
    @Resource
    private UserMapper userMapper;
    
	public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
		
	    UserModel userModel = this.userMapper.findById(loginName);
	    if (userModel == null) {
	        throw new UsernameNotFoundException(new MessageFormat("用户 {0} 不存在").format(new Object[] { loginName }));
	    }
	    
	    Set<GrantedAuthority> grantedAuths = obtionGrantedAuthorities();
        
	    User userdetail = new UserPrincipal(userModel.getLoginName(), 
                            	            userModel.getUserName(),
                            	            userModel.getUserId(),
                            	            "role",
                            	            "",
                            	            userModel.getPassword(), 
                            	            true,
                            	            true,
                            	            true,
                            	            true,
                            	            grantedAuths);
	    
	    return userdetail;
	}
	
	//取得用户的权限
	private Set<GrantedAuthority> obtionGrantedAuthorities() {
		Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
		authSet.add(new SimpleGrantedAuthority("ROLE_USER"));
		return authSet;
	}
	
	//取得指定角色的权限
    @SuppressWarnings("unused")
    private Set<GrantedAuthority> obtionGrantedAuthorities(String[] roles) {
        Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
        for(String role : roles){
            authSet.add(new SimpleGrantedAuthority("ROLE_"+role));
        }
        return authSet;
    }
    
    
}