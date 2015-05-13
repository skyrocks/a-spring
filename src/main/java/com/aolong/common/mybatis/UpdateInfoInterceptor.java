/**
 * 
 */
package com.aolong.common.mybatis;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import com.aolong.common.util.SecurityUtil;

@Intercepts({@Signature(type= Executor.class,method = "update",args = {MappedStatement.class,Object.class})})
public class UpdateInfoInterceptor implements Interceptor {

    private String updateDateFiled = "lastUpdateDate";
    private String updateUserFiled = "lastUpdateUserId";
    
    public Object intercept(Invocation invocation) throws Throwable {
        
        Object[] args = invocation.getArgs();
        
        if (ReflectUtil.getDeclaredField(args[1], this.updateDateFiled) != null){
            ReflectUtil.setFieldValue(args[1], this.updateDateFiled, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        }
        if (ReflectUtil.getDeclaredField(args[1], this.updateUserFiled) != null){
            ReflectUtil.setFieldValue(args[1], this.updateUserFiled, SecurityUtil.getUserPrincipal().getUserId());
        }
        
        return invocation.proceed();
    }
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
    public void setProperties(Properties properties) {
    }
    public String getUpdateDateFiled() {
        return updateDateFiled;
    }
    public void setUpdateDateFiled(String updateDateFiled) {
        this.updateDateFiled = updateDateFiled;
    }
    public String getUpdateUserFiled() {
        return updateUserFiled;
    }
    public void setUpdateUserFiled(String updateUserFiled) {
        this.updateUserFiled = updateUserFiled;
    }
    
    
}