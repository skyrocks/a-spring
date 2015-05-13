package com.aolong.common.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.aolong.common.util.ControllerResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

public class BaseController {

    @RequestMapping(value="/view/{page}")
    public String findView(@PathVariable String page, HttpServletRequest request){
        return request.getServletPath().split("/")[1] + "/" + page.replace("!", "/");
    }
    
    protected ModelAndView getModelAndView(String page, String modelName, Object model){
        
        ModelAndView mav = new ModelAndView();
        mav.addObject(modelName,model);
        mav.setViewName(page);
        
        return mav;
    }
    
    protected ModelAndView getModelAndView(String page, Map<String, Object> map){
        
        ModelAndView mav = new ModelAndView();
        for(String key : map.keySet()){
            mav.addObject(key, map.get(key));
        }
        mav.setViewName(page);
        
        return mav;
    }

    protected String success(){
        return ControllerResult.getSuccessResult();
    }
    protected String error(String errDesc){
        return ControllerResult.getErrorResult(errDesc);
    }

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
}
