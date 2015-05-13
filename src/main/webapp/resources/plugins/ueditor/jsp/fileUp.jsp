<%--     <%@page import="java.io.File"%>
        <%@page import="java.util.Properties"%>
        <%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
        <%@ page import="ueditor.Uploader" %>
        <%@ page import="java.io.FileInputStream" %> --%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="java.util.Properties"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Arrays"%>
<%@ page import="java.io.FileInputStream"%>
<%@ page import="com.aolong.core.Uploader" %>
<%@ page import="java.io.File"%>
<%@ page import="java.util.Map"%>


<%
	request.setCharacterEncoding(Uploader.ENCODEING);
	response.setCharacterEncoding(Uploader.ENCODEING);

	String currentPath = request.getRequestURI().replace(
			request.getContextPath(), "");

	File currentFile = new File(currentPath);

	currentPath = currentFile.getParent() + File.separator;

	Uploader up = new Uploader(request);

	up.setSavePath("file"); //保存路径
	String[] fileType = { ".rar", ".doc", ".docx", ".zip", ".pdf",
			".txt", ".swf", ".wmv", ".avi", ".rm", ".rmvb", ".mpeg",
			".mpg", ".ogg", ".mov", ".wmv", ".mp4" }; //允许的文件类型
	up.setAllowFiles(fileType);
	up.setMaxSize(500 * 1024); //允许的文件最大尺寸，单位KB
	up.upload();
	response.getWriter().print(
			"{'url':'" + up.Encode(up.getUrl()) + "','fileType':'" + up.Encode(up.getType())
					+ "','state':'" + up.Encode(up.getState()) + "','original':'"
					+ up.Encode(up.getOriginalName()) + "'}");
%>
