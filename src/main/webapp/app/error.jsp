<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	int iErrorCode = 500;
%>
<!DOCTYPE html>
<html lang="zh">
  <head>
    <meta charset="utf-8">
    <title>首页</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="spring mvc, spring security, mybatis 最新版整合测试">
	<meta name="keywords" content="spring mvc spring security mybatis">
	<meta name="author" content="skyrocks">
	
	<base href="<%= basePath %>">

	  <!-- Le styles -->
	  <link href="http://libs.baidu.com/libs/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet">

	  <!--[if lt IE 9]>
	  <script src="http://libs.baidu.com/libs/html5shiv/3.7/html5shiv.min.js"></script>
	  <script src="http://libs.baidu.com/libs/respond.js/1.4.2/respond.min.js"></script>
	  <![endif]-->
    
    <style type="text/css">

    	body{
			background: #eee;
		}

		.box-shadow{  
		  -webkit-box-shadow:0 8px 17px 0 rgba(0,0,0,.2),0 6px 20px 0 rgba(0,0,0,.19);
		  -moz-box-shadow:0 8px 17px 0 rgba(0,0,0,.2),0 6px 20px 0 rgba(0,0,0,.19); 
		  box-shadow:0 8px 17px 0 rgba(0,0,0,.2),0 6px 20px 0 rgba(0,0,0,.19); 
		  min-height:300px;
		  background: #fff;
		  padding:20px 35px;
		}  
    </style>
  </head>

  <body>
	  <c:choose>
          <c:when test="${!empty SPRING_SECURITY_403_EXCEPTION}">
              <%  
              	  iErrorCode = 403;
              %>
          </c:when>
          <c:otherwise>
      		  <% 
      		  	  Object errorCode = request.getAttribute("javax.servlet.error.status_code");
      		  	  if (errorCode != null){
      		  	  	  iErrorCode = (Integer)errorCode;
      		  	  }
      		  %>
          </c:otherwise>
      </c:choose>  
      <div class="container">
        <div style="height:30px;">
		  	
		  </div>
		  <div class="row">
		  	<div class="col-md-10 col-md-offset-1 col-xs-10 col-xs-offset-1 box-shadow">
      			<h2>Oh,my god! what happened ???</h2>
				<div style="border-bottom:1px solid #ccc;"></div>
      			  <% 
			          if (iErrorCode == 403){
			      %>        
					  <h3>HTTP Status 403 - 禁止访问</h3>  
					  ${errDesc}		    
			      <%        
			          }else if (iErrorCode == 404){
			      %>        
			          <h3>HTTP Status 404 - 无法找到文件</h3>    
			          ${errDesc}
			      <%
				      }else{
				  %>        
				  	  <h3>HTTP Status <%=iErrorCode%> - 内部服务器错误</h3>
				      ${errDesc}
				  <%        
				      }
			      %>
      			  <h4 style="margin-top:50px;">请联系系统管理员！</h4>    
      		</div>
		  </div>
	  </div>

  </body>

  <script src="http://libs.baidu.com/libs/jquery/2.1.1/jquery.min.js"></script>
  <script src="http://libs.baidu.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>
  
</html>
