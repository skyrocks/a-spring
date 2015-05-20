<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <meta name="${_csrf.headerName}" content="${_csrf.token}" id="_csrf">
    
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
        <h2>用户信息展示</h2>   
        <p><a href="example/add">新页面添加用户</a>&nbsp;&nbsp;<a href="javascript:;" id="addUser">弹出窗口添加用户</a></p>
        <div class="row">
          <div class="col-md-12" id="list">
            <c:forEach items="${users.data}" var="usermap">
      	       	<div data-userId="${usermap.userId}"> 姓名:
                    <a href="user/${usermap.userId}"><div style="display:inline-block;width:200px;">${usermap.userName}</div> </a>
                    <a href="user/${usermap.userId}/update">新页面修改</a>&nbsp;&nbsp;
                    <a href="user/${usermap.userId}/delete">删除并刷新</a>&nbsp;&nbsp;
                    <a href="javascript:;" onclick="javascript:viewUser('${usermap.userId}');" class="viewItem">弹出窗口查看</a>&nbsp;&nbsp;
                    <a href="javascript:;" onclick="javascript:deleteUser('${usermap.userId}');" class="deleteItem">删除不刷新</a>&nbsp;&nbsp;
                </div>
      	    </c:forEach>
          </div>
          <div id="page"></div>

          <form id="uploadForm" name="uploadForm" action="file/upload" enctype="multipart/form-data" method="POST">
                <input type="file" name="upload" multiple=true>
                <button type="button" class="btn btn-success" id="btnUpload" data-loading-text="上传中...">上传</button>
          </form>

          <div>
              <a href="file/download/123">下载文件</a>
          </div>

          <img src="file/download/123">

        </div>
      </div>



      <div class="modal fade" id="form-modal" tabindex="-1" role="dialog" aria-hidden="true">
          <div class="modal-dialog" >
              <div class="modal-content">
                  <div class="modal-header"><button class="close" type="button" data-dismiss="modal">×</button>
                      <h3 id="myModalLabel">Modal header</h3>
                  </div>
                  <div class="modal-body">
                      <form method="post" action="example/add3"  class="form-horizontal">
                          <div class="form-group" >
                              <label for="userName">姓名</label>
                              <div>
                                  <input type="text" class="form-control" id="userName" name="userName">
                              </div>
                          </div>
                          <div class="form-group" >
                              <label for="loginName">登录名</label>
                              <div>
                                  <input type="text" class="form-control" id="loginName" name="loginName">
                              </div>
                          </div>
                          <input type="hidden" value="123456" class="form-control" id="password" name="password">
                      </form>
                      <button class="btn btn-success" id="btnAdd" data-loading-text="正在保存...">添加</button>
                  </div>
              </div>
          </div>
      </div>
  </body>
 
  <script src="http://libs.baidu.com/libs/jquery/2.1.1/jquery.min.js"></script>
  <script src="http://libs.baidu.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>
  <script src="http://libs.useso.com/js/jquery.form/3.50/jquery.form.min.js"></script>
  <script src="http://cdn.staticfile.org/jquery-validate/1.13.1/jquery.validate.min.js"></script>
  <script src="resources/js/access.js"></script>
  <script src="resources/js/pagination.js"></script>
  
  <script>

      var total = ${users.total};

      jQuery(document).ready(function() {
	       
        $('#page').append( Pagination.create( 1, total, function(e){ loadData(e.data.page); }, 'left' ) );

        function loadData(page){
            
            $('#page').empty();
            $('#list').empty();

            var limit = 10;
            var start = (page - 1) * limit;

            var url = 'example/page/'+start+"/"+limit;
            var callback = function(result) {

                    var datas = result.data;
                    for ( var i = 0; i < datas.length; i++) {
                        var data = datas[i];
                        
                        var $div = $('<div data-userId="'+data.userId+'">姓名:'
                            +'<a href="example/'+data.userId+'"><div style="display:inline-block;width:200px;">'+data.userName+'</div></a>'
                            +'<a href="example/'+data.userId+'/update">新页面修改</a>&nbsp;&nbsp;'
                            +'<a href="example/'+data.userId+'/delete">删除并刷新</a>&nbsp;&nbsp;'
                            +'<a href="javascript:;" onclick="javascript:viewUser("'+data.userId+'");" class="viewItem">弹出窗口查看</a>&nbsp;&nbsp;'
                            +'<a href="javascript:;" onclick="javascript:deleteUser("'+data.userId+'");" class="deleteItem">删除不刷新</a>&nbsp;&nbsp;'
                            +'</div>');
                        
                        $('#list').append($div);
                    }
                    if (result.total > 0) {
                        $('#page').append( Pagination.create( page, result.total, function(e){ loadData(e.data.page); }, 'left' ) );
                    }
                };

            Access.get({url:url,callback:callback});
        }
      });

      function deleteUser(userId){
          var url = "example/"+userId+"/delete2";
          var callback = function(result){
              $('div[data-userId='+userId+']').remove();
          }
          Access.post({url:url,callback:callback});
      }

      function viewUser(userId){
          var url = "example/"+userId+"/view";
          var callback = function(result){
              if (result.success == false){
                  alert(result.errDesc);
              }else{
                  $('#form-modal form')[0].reset();
                  $('#form-modal').modal();
                  $('#userName').val(result.userName);
                  $('#loginName').val(result.loginName);
              }
          }
          Access.get({url:url,callback:callback});
      }

      $('#addUser').on('click',function(e){
          $('#form-modal form')[0].reset();
          $('#form-modal').modal();
      });
      $('#btnAdd').on('click',addUser);

      function addUser(e){
          var btn = $(e.currentTarget);
          btn.button('loading');
          var options = {
              clearForm:true,
              success: function (result){
                  btn.button('reset');
                  if(result.success){
                      window.location.reload();
                  }else{
                      alert(result.errDesc);
                  }
              }
          };
          $("#form-modal form").ajaxSubmit(options);
      }

      $('#btnUpload').on('click',function(e){
          var btn = $(e.currentTarget);
          btn.button('loading');
          var options = {
              clearForm:true,
              success: function (result){
                  btn.button('reset');
                  if(result.success){
                      alert('上传成功！');
                  }else{
                      alert(result.errDesc);
                  }
              }
          };
          $("#uploadForm").ajaxSubmit(options);
      });
  </script>
    
</html>
