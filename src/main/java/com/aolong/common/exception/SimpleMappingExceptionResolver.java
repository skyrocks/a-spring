package com.aolong.common.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.web.servlet.ModelAndView;

import com.aolong.common.util.JsonUtil;

public class SimpleMappingExceptionResolver extends org.springframework.web.servlet.handler.SimpleMappingExceptionResolver {

    private static final Logger logger = LoggerFactory.getLogger(SimpleMappingExceptionResolver.class);
    
    @Override
    protected ModelAndView doResolveException(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex) {
        
        logger.error(ex.getMessage(), ex);
        
        if (ex instanceof DataAccessException){
            //TODO 数据库异常发邮件等等。。。
        }
        
        if ( request.getHeader("accept").indexOf("application/json") > -1 || 
             ( request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1) ) {
            
            response.setContentType("application/json;charset=utf-8");
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("success", false);
            result.put("errDesc", ex.getMessage());
            try {
                PrintWriter writer = response.getWriter();
                writer.write(JsonUtil.toJson(result));
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            request.setAttribute("errDesc", ex.getMessage()); 
            String viewName = determineViewName(ex, request); //获取spring mvc异常配置页面
            if (viewName != null) {
                Integer statusCode = determineStatusCode(request, viewName);
                if (statusCode != null) {
                    applyStatusCodeIfPossible(request, response, statusCode);
                }
                return getModelAndView(viewName, ex, request);
            } else {
                return null; //未获得spring mvc异常配置页面,交由web.xml error-page处理
            }
        }
    }
}
