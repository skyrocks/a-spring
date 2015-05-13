<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh" ng-app="UserApp">
<head>
    <meta charset="UTF-8">
    <title></title>

    <base href="<%= basePath %>">

    <script src="http://libs.baidu.com/angular.js/1.4.0-beta.4/angular.js"></script>
    <script src="app/example/angular.js"></script>

</head>
<body>
    Your name: <input type="text" ng-model="yourname" placeholder="World">
    <hr>
    Hello {{yourname || 'World'}}!

    <div ng-controller="UserListCtrl">
        <ul>
           <li ng-repeat="user in users">
                姓名{{user.userName}}
                登录{{user.loginName}}
           </li>
        </ul>
    </div>
</body>
<script>

</script>
</html>