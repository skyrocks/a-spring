package com.aolong.common.security;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.octo.captcha.service.image.ImageCaptchaService;

public class UsernamePasswordCaptchaAuthenticationFilter extends
        UsernamePasswordAuthenticationFilter {

    @Resource
    private ImageCaptchaService imageCaptchaService;
    
    @Override  
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException{
          
        String captchaId = request.getSession().getId();
        String code = request.getParameter("code");
        
        Boolean isCorrect = imageCaptchaService.validateResponseForID(captchaId, code);
          
        if (!isCorrect){
            throw new BadCaptchaException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCaptcha", "Bad captcha"));
        }

        return super.attemptAuthentication(request, response);  
    }

}
