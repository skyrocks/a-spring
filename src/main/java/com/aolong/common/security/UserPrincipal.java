package com.aolong.common.security;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserPrincipal extends User{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String loginName;
    private String userName;
    private String userId;
    private String userRole;
    private String language;
    private String loginTime;
    
    public UserPrincipal(String loginName, String userName, String userId, String userRole, String language, String password, boolean enabled,
            boolean accountNonExpired, boolean credentialsNonExpired,
            boolean accountNonLocked,
            Collection<? extends GrantedAuthority> authorities) {
        
        super(loginName, password, enabled, accountNonExpired, credentialsNonExpired,
                accountNonLocked, authorities);
        
        this.loginName = loginName;
        this.userName = userName;
        this.userId = userId;
        this.userRole = userRole;
        this.language = language;
        this.loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

    }
    
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}
