<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
session.setAttribute("username", null);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>注销登录</title>
    <meta http-equiv=Content-Type content=text/html;charset=utf-8>
    <script language="javascript">
	window.location.href="welcome.html"; 
</script>
  </head>
  <body>
  <br><br><br><br><br><br><br><br>
  <div align="center" style="opacity:0.85">
<img src="images/logo/logo-m2.gif">
<h2>正在退出···</h2> 
</div>
    
  </body>
</html>
