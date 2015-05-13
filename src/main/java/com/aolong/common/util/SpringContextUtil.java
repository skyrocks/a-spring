package com.aolong.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextUtil implements ApplicationContextAware
{
    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext()
    {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext paramApplicationContext)
            throws BeansException
    {
        if (applicationContext == null)
            applicationContext = paramApplicationContext;
    }
    
    public static Object getBean(String beanid)
    {
        return applicationContext.getBean(beanid);  
        
    }
    public static boolean containsBean(String beanid)
    {
    	return applicationContext.containsBean(beanid);
    }
}
