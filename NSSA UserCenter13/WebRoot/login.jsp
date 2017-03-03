<%@ page language="java" import="java.util.*,nssa.uc.dao.AdminDao" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>验证登录</title>
    <meta http-equiv=Content-Type content=text/html;charset=utf-8>
  </head>
  <body>
<%
String username = request.getParameter("username");
String password = request.getParameter("password");
AdminDao adminDao = new AdminDao();
if(adminDao.login(username, password)) {
	session.setAttribute("username", username);
%>
<div align="center" style="opacity:0.85">
<br><br><br><br><br><br><br>
<img src="images/logo/logo-m2.gif"></div>
<script language="javascript">
	window.location.href="index.jsp"; 
</script>
<%
} else {
%>
<script language="javascript">
	alert("登录失败，点击确定返回");
	window.history.back(-1);
</script>
<%
}
%>
  </body>
</html>