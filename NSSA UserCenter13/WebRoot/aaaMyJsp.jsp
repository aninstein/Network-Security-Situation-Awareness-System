<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.nio.charset.Charset"%>
<%@page import="nssa.uc.util.HttpRequest"%>
<%@page import="java.io.PrintWriter"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'aaaMyJsp.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  
  <%
  
			//加载驱动 //驱动注册
		Class.forName("com.mysql.jdbc.Driver");
			// 创建连接
		Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/nssa?useUnicode=true&characterEncoding=UTF-8", "root", "root");
	  	Statement stmt=conn.createStatement();
	  	
	  	
	  	ResultSet rs=stmt.executeQuery("select * from day_linchart");
	 
	  	
	  	double Assets_num[]=new double[9];//Assets_num表示威胁指数了
 	  	double Risk_numl[]=new double[9];//Risk_numl表示脆弱指数了
 	  	double Threaten_num[]=new double[9];//Threaten_num表示稳定指数了
 	  	double Positive_num[]=new double[9];//Positive_num表示容灾指数了
	  	int i=0;
	  	while(rs.next())
	  	{
	  	
	  		String time=rs.getString("time");
	  		
	  		String Assets=rs.getString("Assets");
	  		Assets_num[i]=  Double.parseDouble(Assets);
	  		
	  		String Risk=rs.getString("Risk");
	  		Risk_numl[i]= Double.parseDouble(Risk);
	  		
	  		String Threaten=rs.getString("Threaten");
	  		Threaten_num[i]=  Double.parseDouble(Threaten);
	  		
	  		String Positive=rs.getString("Positive");
	  		Positive_num[i]=  Double.parseDouble(Positive);
	  		i++;
	  	}
	  	

// 	  	for(i=0;i<9;i++)
// 	  	{
// 	  		System.out.println("SQL_num["+i+"]="+Assets_num[i]);
// 	  		System.out.println("PHPWebShell_num["+i+"]="+Risk_numl[i]);
// 	  		System.out.println("Exploit_num["+i+"]="+Threaten_num[i]);
// 	  		System.out.println("Dos_num["+i+"]="+Positive_num[i]);
// 	  	}
	  	
  
	  	stmt.close();
	  	conn.close();
  %>
  
  
  <%for(i=0;i<9;i++){%>
  <p>SQL_num=<%=Assets_num[i] %></p>
  <p>PHPWebShell_num=<%=Risk_numl[i] %></p>
  <p>Exploit_num=<%=Threaten_num[i] %></p>
  <p>Dos_num=<%=Positive_num[i] %></p>
  <%} %>
  
  
  
  
  
  
  
  
  <%
//发送 GET 请求      http://test.ip138.com/query/?ip=117.25.13.123&datatype=text
//   String s=HttpRequest.sendGet("http://test.ip138.com/query/?ip=117.25.13.123&datatype=text", "key=123&v=456");
  
//   s=s.getBytes(Charset.forName("utf-8")).toString();
  
//   System.out.println(s);
  
  %>
  
    <%//=s%>
  </body>
</html>
