<%@ page language="java" import="java.util.*,nssa.uc.dao.AdminDao" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>修改密码</title>
    <meta http-equiv=Content-Type content=text/html;charset=utf-8>
  </head>
  <body>
<%
String username = (String) session.getAttribute("username");
String oldPassword = request.getParameter("oldpassword");
String newPassword = request.getParameter("newpassword");
String newPassword2 = request.getParameter("newpassword2");
AdminDao adminDao = new AdminDao();
if((newPassword.equals(newPassword2)) && adminDao.modify(username, oldPassword, newPassword)) {
%>
<div align="center" style="opacity:0.85">
<br><br><br><br><br><br><br>
<img src="images/logo/logo-m2.gif"></div>
<script language="javascript">
	window.location.href="admininfo_success.html"; 
</script>
<%
} else {
%>
<div align="center" style="opacity:0.85">
<br><br><br><br><br><br><br>
<img src="images/logo/logo-m2.gif"></div>
<script language="javascript">
	window.location.href="admininfo_fail.html"; 
</script>
<%
}
%>
  </body>
</html>