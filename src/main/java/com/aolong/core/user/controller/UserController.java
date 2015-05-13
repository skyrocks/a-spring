package com.aolong.core.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.aolong.common.controller.BaseController;
import com.aolong.common.model.PageModel;
import com.aolong.common.model.PageParamsModel;
import com.aolong.common.util.Const;
import com.aolong.common.util.GUID;
import com.aolong.core.user.model.UserModel;
import com.aolong.core.user.service.IUserService;

@Controller
@RequestMapping(value="/user")
public class UserController extends BaseController{
    
    @Resource
    private IUserService userService;
    
    //带数据返回页面； 第一种方式    返回类简单  推荐
    @RequestMapping(value="/list", method=RequestMethod.GET)
    public String list(Model model){
        
        PageModel page = this.findPage(0, Const.LIMIT);
        model.addAttribute("users", page);
        return "user/list";
    }
  
    //带数据返回页面； 第二种方式 
    @RequestMapping(value="/list2", method=RequestMethod.GET)
    public ModelAndView list(){
        
        PageModel page = this.findPage(0, Const.LIMIT);
        return getModelAndView("user/list", "users", page);
    }
    
    //返回分页, 一般场景是局部刷新，所以返回数据，不返回页面
    @ResponseBody
    @RequestMapping(value="/page/{start}/{limit}")
    public PageModel findPage(@PathVariable int start, @PathVariable int limit){
        
        PageParamsModel pageParams = new PageParamsModel();
        pageParams.setStart(start);
        pageParams.setLimit(limit);
        PageModel page = this.userService.findAll(pageParams);
                
        return page;
    }
    
    //新页面做添加
    @RequestMapping(value="/add",method=RequestMethod.GET)
    public String add(Model model){
        model.addAttribute("user", new UserModel());
        return "user/add";
    }

    //保存数据并刷新页面  同上方法一起使用
    @RequestMapping(value="/add",method= RequestMethod.POST)
    public String add(UserModel user){
        user.setUserId(GUID.NEWID());
        this.userService.add(user);
        return "redirect:/user/list";
    }

    //保存数据并刷新页面  使用自动验证，但是如果提交的表单enctype="multipart/form-data"  user获取不到内容
    @RequestMapping(value="/add2",method=RequestMethod.POST)
    public String add2(@Validated UserModel user,BindingResult br){
        if(br.hasErrors()){
            return "user/add";
        }
        user.setUserId(GUID.NEWID());
        this.userService.add(user);
        return "redirect:/user/list";
    }

    //保存数据并刷新页面  同上方法一起使用
    @ResponseBody
    @RequestMapping(value="/add3",method= RequestMethod.POST)
    public String add3(UserModel user){
        user.setUserId(GUID.NEWID());
        this.userService.add(user);
        return success();
    }

    //新页面做修改
    @RequestMapping(value="/{userId}/update",method=RequestMethod.GET)
    public String update(@PathVariable String userId, Model model){
        model.addAttribute("user", this.userService.findById(userId));
        return "user/update";
    }
    //保存数据并刷新页面  同上方法一起使用
    @RequestMapping(value="/{userId}/update",method=RequestMethod.POST)
    public String update(@PathVariable String userId, @Validated UserModel user,BindingResult br){
        if(br.hasErrors()){
            return "user/update";
        }
        this.userService.modify(user);
        return "redirect:/user/list";
    }
    
    //删除并刷新页面
    @RequestMapping(value="/{userId}/delete",method=RequestMethod.GET)
    public String delete(@PathVariable String userId){
        Object[] ids = {userId};
        this.userService.remove(ids);
        return "redirect:/user/list";
    }

    //删除
    @ResponseBody
    @RequestMapping(value="/{userId}/delete2",method=RequestMethod.POST)
    public String delete2(@PathVariable String userId){
        Object[] ids = {userId};
        this.userService.remove(ids);
        return success();
    }
    
    //明细页显示   第一种方式    返回类简单  推荐 ；    第二种方式 返回 ModelAndView
    @RequestMapping(value="/{userId}",method=RequestMethod.GET)
    public String detail(@PathVariable String userId, Model model){
        model.addAttribute("user", this.userService.findById(userId));
        return "user/detail";
    }
    
    
    //返回model数据,由框架做json序列化
    @ResponseBody
    @RequestMapping(value="/{userId}/view")
    public UserModel detail2(@PathVariable String userId){

        UserModel model = this.userService.findById(userId);
        return model;
    }
    
    //返回map数据,由框架做json序列化
    @ResponseBody
    @RequestMapping(value="/map")
    public Map<String, Object> findMap(){
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userName", "admin");
                
        return map;
    }

}

