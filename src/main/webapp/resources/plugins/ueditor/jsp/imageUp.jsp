    <%@ page language="java" contentType="text/html; charset=utf-8"
             pageEncoding="utf-8"%>
 <%@ page import="java.util.Properties" %>
 <%@ page import="java.util.List" %>
 <%@ page import="java.util.Iterator" %>
 <%@ page import="java.util.Arrays" %>
 <%@ page import="java.io.FileInputStream" %>
 <%@ page import="com.aolong.core.Uploader" %>
 <%@ page import="java.io.File" %>
 <%@ page import="java.util.Map" %>

            <%

request.setCharacterEncoding( Uploader.ENCODEING );
response.setCharacterEncoding( Uploader.ENCODEING );

String currentPath = request.getRequestURI().replace( request.getContextPath(), "" );

File currentFile = new File( currentPath );

currentPath = currentFile.getParent() + File.separator;


Uploader up = new Uploader(request);

up.setSavePath( "image" );
String[] fileType = {".gif" , ".png" , ".jpg" , ".jpeg" , ".bmp"};
up.setAllowFiles(fileType);
up.setMaxSize(500 * 1024); //单位KB
up.upload();
response.getWriter().print("{'original':'"+up.Encode(up.getOriginalName())+"','url':'"+up.Encode(up.getUrl())+"','title':'"+up.Encode(up.getTitle())+"','state':'"+up.Encode(up.getState())+"'}");
%>
