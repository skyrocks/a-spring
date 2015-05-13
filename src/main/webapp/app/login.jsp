<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh">
  <head>
    <meta charset="utf-8">
    <title>首页</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
  	<meta name="description" content="spring mvc, spring security, mybatis 最新版整合测试">
  	<meta name="keywords" content="spring mvc spring security mybatis">
  	<meta name="author" content="aolong.com">
  	
  	<base href="<%= basePath %>">

    <!-- Le styles -->
    <link href="http://libs.baidu.com/libs/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet">
    <link href="http://cdn.staticfile.org/font-awesome/3.2.1/css/font-awesome.css" rel="stylesheet" >

    <!--[if lt IE 9]>
    <script src="http://libs.baidu.com/libs/html5shiv/3.7/html5shiv.min.js"></script>
    <script src="http://libs.baidu.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    
    <style type="text/css">
      body {
        background: url(../resources/img/login-bg.png) no-repeat;
        background-size: 100%;
      }

      .box-shadow{  
        position: absolute;
        top: 50%;
        left: 50%;
        padding: 30px 30px;
        margin-left: -150px;
        margin-top: -180px;
        width: 300px;
        height: 360px;
        background: url(../resources/img/1.png) repeat;
        border-radius: 10px;
      } 
      .title{
        border-bottom:1px solid #c7c7c7;margin-bottom:8px; padding-right:10px; font-size:20px;font-weight:bold;
      }
      .error{
        color:red;margin-bottom:8px;height:15px;
      }
    </style>
  </head>

  <body>
  		
      <div class="box-shadow">
          <div class="title">
            <span>东华大学</span><span class="pull-right"><i class="icon-flag"></i></span>
          </div>
          <div class="error">
            <span >${SPRING_SECURITY_LAST_EXCEPTION.message}</span>
          </div>
          <form action="login" method="post">  
            <div class="input-group">
              <span class="input-group-addon" id="user-addon"><i class="icon-user"></i></span>
              <input type="text" class="form-control" name="username" placeholder="用户名" aria-describedby="user-addon">
            </div>
            <br>
            <div class="input-group">
              <span class="input-group-addon" id="user-addon"><i class="icon-key"></i></span>
              <input type="password" class="form-control" name="password" placeholder="密码" aria-describedby="user-addon">
            </div>
            <br>
            <div>
              <div>
                  <input type="text" class="form-control" style="display:inline-block;width:50%" name="code" placeholder="验证码">&nbsp;&nbsp;
                  <img src="captcha" onclick="this.src='captcha?t='+new Date()*1" height="30" width="70" />
              </div>
            </div>
            <div class="row">
              <div class="col-sm-7 col-md-7 col-xs-12">
                <div class="checkbox">
                  <label>
                    <input type="checkbox" name="remember-me" value="true">
                    <span style="font-size:12px;">一周内记住我</span>
                  </label>
                </div>
              </div>
              <div class="col-sm-5 col-md-5 col-xs-12 ">
                <div class="pull-right" style="margin-top: 10px;margin-bottom: 10px;">
                  <span style="font-size:12px;">忘记密码？</span>
                </div>
              </div>
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <!-- <input value="submit" type="submit"/>   -->
            <br>
            <button type="submit" class="btn btn-success btn-block">登 录</button>
          </form> 
      </div>
   		<!-- <a href="login.html?locale=zh_CN">简体中文</a>
          <a href="login.html?locale=en_US">English</a> --> 
  </body>

  <script src="http://libs.baidu.com/libs/jquery/2.1.1/jquery.min.js"></script>
  <script src="http://libs.baidu.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>
    
</html>
