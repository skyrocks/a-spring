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
    
  </head>

  <body>
      <div class="container">
        <div class="row">
          <div class="col-md-6">
            <h2>用户详细信息页面</h2> <br>
              姓名: ${user.userName}  <br>
              邮箱: ${user.loginName} <br/>
            <a href="example/list">返回用户列表</a>
          </div>
        </div>
      </div>
    	
  </body>

  <script src="http://libs.baidu.com/libs/jquery/2.1.1/jquery.min.js"></script>
  <script src="http://libs.baidu.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>

</html>
