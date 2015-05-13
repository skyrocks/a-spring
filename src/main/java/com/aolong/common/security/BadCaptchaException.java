package com.aolong.common.security;

import org.springframework.security.core.AuthenticationException;

public class BadCaptchaException extends AuthenticationException{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public BadCaptchaException(String msg){
        super(msg);
    }

    public BadCaptchaException(String msg, Throwable t){
        super(msg, t);
    }
    
}
