<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<%@ page import="com.aolong.core.Uploader" %>
<%@ page import="com.aolong.core.Const" %>
<%@ page import="javax.servlet.ServletContext"%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>
<% 
    //仅做示例用，请自行修改
	String path = "image";
	String imgStr ="";
	String realpath = getRealPath(request,path)+"/";
	List<File> files = getFiles(realpath,new ArrayList());
	for(File file :files ){
		imgStr+=file.getPath().replace(getRealPath(request,path),"")+"ue_separate_ue";
	}
	if(imgStr!=""){
		 imgStr = imgStr.replace(request.getSession().getServletContext().getRealPath("/"), "");
    }
	out.print(imgStr);		
%>
<%!
public List getFiles(String realpath, List files) {
	
	File realFile = new File(realpath);
	if (realFile.isDirectory()) {
		File[] subfiles = realFile.listFiles();
		for(File file :subfiles ){
			if(file.isDirectory()){
				getFiles(file.getAbsolutePath(),files);
			}else{
				if(!getFileType(file.getName()).equals("")) {
					files.add(file);
				}
			}
		}
	}
	return files;
}

public String getRealPath(HttpServletRequest request,String path){
	String realPath = request.getSession().getServletContext().getRealPath("/")+"/"+Const.UEDITORPATH;
	return new File(realPath) + "/" + path;
}

public String getFileType(String fileName){
	String[] fileType = {".gif" , ".png" , ".jpg" , ".jpeg" , ".bmp"};
	Iterator<String> type = Arrays.asList(fileType).iterator();
	while(type.hasNext()){
		String t = type.next();
		if(fileName.toLowerCase().endsWith(t)){
			return t;
		}
	}
	return "";
}
%>