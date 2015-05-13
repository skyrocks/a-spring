package com.aolong.core.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.aolong.common.model.PageModel;
import com.aolong.common.model.PageParamsModel;
import com.aolong.common.util.Const;
import com.aolong.common.util.GUID;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aolong.common.controller.BaseController;
import com.aolong.core.user.model.UserModel;
import com.aolong.core.user.service.IUserService;

@Controller
@RequestMapping(value="/example")
public class ExampleController extends BaseController{

    @Resource
    private IUserService userService;

    //带数据返回页面； 第一种方式    返回类简单  推荐
    @RequestMapping(value="/list", method=RequestMethod.GET)
    public String list(Model model){

        PageModel page = this.findPage(0, Const.LIMIT);
        model.addAttribute("users", page);
        return "example/list";
    }

    //带数据返回页面； 第二种方式
    @RequestMapping(value="/list2", method=RequestMethod.GET)
    public ModelAndView list(){

        PageModel page = this.findPage(0, Const.LIMIT);
        return getModelAndView("example/list", "users", page);
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
        return "example/add";
    }

    //保存数据并刷新页面  同上方法一起使用
    @RequestMapping(value="/add",method= RequestMethod.POST)
    public String add(UserModel user){
        user.setUserId(GUID.NEWID());
        this.userService.add(user);
        return "redirect:/example/list";
    }

    //保存数据并刷新页面  使用自动验证，但是如果提交的表单enctype="multipart/form-data"  user获取不到内容
    @RequestMapping(value="/add2",method=RequestMethod.POST)
    public String add2(@Validated UserModel user,BindingResult br){
        if(br.hasErrors()){
            return "example/add";
        }
        user.setUserId(GUID.NEWID());
        this.userService.add(user);
        return "redirect:/example/list";
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
        return "example/update";
    }
    //保存数据并刷新页面  同上方法一起使用
    @RequestMapping(value="/{userId}/update",method=RequestMethod.POST)
    public String update(@PathVariable String userId, @Validated UserModel user,BindingResult br){
        if(br.hasErrors()){
            return "example/update";
        }
        this.userService.modify(user);
        return "redirect:/example/list";
    }

    //删除并刷新页面
    @RequestMapping(value="/{userId}/delete",method=RequestMethod.GET)
    public String delete(@PathVariable String userId){
        Object[] ids = {userId};
        this.userService.remove(ids);
        return "redirect:/example/list";
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
        return "example/detail";
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

/*

list.jsp

复制代码
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title><%=basePath%></title>
  </head>
  
  <body>
    <h2>用户信息展示</h2>   <p><a href="<%=basePath %>/user/add">添加信息</a></p>
    <c:forEach items="${users}" var="usermap">
        姓名:     <a href="<%=basePath %>/user/${usermap.value.username }">${usermap.value.username}  </a>
        密码:    ${usermap.value.password }
        邮箱:    ${usermap.value.email }
        <a href="<%=basePath %>/user/${usermap.value.username }/update">修改</a>
        <a href="<%=basePath %>/user/${usermap.value.username }/delete">删除</a>
        <br/>
    </c:forEach>
  </body>
</html>
复制代码
add.jsp


复制代码
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title><%=basePath%></title>
    </head>
  
  <body>
    <h2>增加用户信息页面</h2> <br>
    <!-- 此时没有写action,直接提交会提交给/add -->
    <sf:form method="post" modelAttribute="user">
        姓名:<sf:input path="username"/>  <sf:errors path="username" /> <br/>
        密码:<sf:password path="password"/> <sf:errors path="password" /> <br/>
        邮箱:<sf:input path="email"/> <sf:errors path="email" /> <br/>
        <input type="submit" value="添加" />
    </sf:form>
  </body>
</html>
复制代码
detail.jsp


复制代码
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title><%=basePath%></title>
  </head>
  
  <body>
    <h2>用户详细信息页面</h2> <br>
        姓名:     ${user.username}  <br>
        密码:    ${user.password } <br>
        邮箱:    ${user.email } <br/>
        <a href="<%=basePath %>/user/users">返回用户列表</a>
  </body>
</html>
复制代码
update.jsp


复制代码
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title><%=basePath%></title>
  </head>
  
  <body>
        <h2>修改用户信息页面</h2> <br>
        <!-- 此时没有写action,直接提交会提交给/update -->
        <sf:form method="post" modelAttribute="user">
            姓名:<sf:input path="username"/>  <sf:errors path="username" /> <br/>
            密码:<sf:password path="password"/> <sf:errors path="password" /> <br/>
            邮箱:<sf:input path="email"/> <sf:errors path="email" /> <br/>
            <input type="submit" value="修改" />
        </sf:form>
  </body>
</html>*/
