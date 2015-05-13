package com.aolong.common.locale;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AcceptHeaderLocaleResolver extends org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver{
    
    private Locale local;
    
    public Locale resolveLocale(HttpServletRequest request)
    {
        return local == null ? request.getLocale() : local;
    }
   
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale)
    {
        local = locale;
    }
}
