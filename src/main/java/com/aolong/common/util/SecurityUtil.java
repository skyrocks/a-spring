package com.aolong.common.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.aolong.common.security.UserPrincipal;

public class SecurityUtil {

    public static WebAuthenticationDetails getWebAuthentication(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        WebAuthenticationDetails wauth = (WebAuthenticationDetails)auth.getDetails();
        
        return wauth;
    }
    
    public static UserPrincipal getUserPrincipal(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal user = (UserPrincipal)auth.getPrincipal();
        
        return user;
    }
    
    /**
     * 获取当前登录系统所有用户
     * @return List<UserPrincipal>
     */
    public static List<Object> getAllUserPrincipal(){
        
        SessionRegistry sessionRegistry = (SessionRegistry)SpringContextUtil.getBean("sessionRegistry");
        List<Object> principals = sessionRegistry.getAllPrincipals();
        
        for(Object principal : principals){
            sessionRegistry.getAllSessions(principal, true).get(0).getLastRequest();
        }
        return principals;
    }
    
    /**
     * 强制退出系统
     * @param userId
     * @return
     */
    public static boolean expire(String userId){
        
        SessionRegistry sessionRegistry = (SessionRegistry)SpringContextUtil.getBean("sessionRegistry");
        List<Object> principals = sessionRegistry.getAllPrincipals();
        for(Object principal : principals){
            UserPrincipal user = (UserPrincipal)principal;
            if (user.getUserId().equals(userId)){
                List<SessionInformation> sessionInfos = sessionRegistry.getAllSessions(principal, true); //第二个参数,单点登录时超出限制被弹出的用户
                for(SessionInformation sessionInfo : sessionInfos){
                    sessionInfo.expireNow();
                }
                return true;
            }
        }
        return false;
    }
    
    public static void main(String[] args){
        
        System.out.println(rr());
        logger.info("slf4j + logback info");
    }
    
    public static boolean rr(){
        
        for (int i = 0; i < 100; i++) {
            if (i==111){
                return true;
            }
        }
        return false;
        
    }
    
    public static final Logger logger = LoggerFactory.getLogger(SecurityUtil.class); 
}
