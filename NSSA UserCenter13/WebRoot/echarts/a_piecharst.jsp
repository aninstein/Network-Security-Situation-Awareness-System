<!DOCTYPE html>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.nio.charset.Charset"%>
<%@page import="nssa.uc.util.HttpRequest"%>
<%@page import="java.io.PrintWriter"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

  <%
  
			//加载驱动 //驱动注册
		Class.forName("com.mysql.jdbc.Driver");
			// 创建连接
		Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/nssa?useUnicode=true&characterEncoding=UTF-8", "root", "root");
	  	Statement stmt=conn.createStatement();
	  	
	  	
	  	ResultSet rs=stmt.executeQuery("select * from day_barchart");
	  	
// 		/*各个数据数组*/
		int SQL_num[]=new int[9];
 	  	int PHPWebShell_num[]=new int[9];
 	  	int Exploit_num[]=new int[9];
 	  	int Dos_num[]=new int[9];
	  	int i=0;
	  	
	  	/*各个和值*/
	  	int SQL_sum=0,  PHPWebShell_sum=0,  Exploit_sum=0,  Dos_sum=0;
	  	while(rs.next())
	  	{
	  	
	  		String time=rs.getString("time");
	  		
	  		String SQL=rs.getString("SQL");
	  		SQL_num[i]= Integer.parseInt(SQL);
	  		
	  		String PHPWebShell=rs.getString("PHPWebShell");
	  		PHPWebShell_num[i]=Integer.parseInt(PHPWebShell);
	  		
	  		String Exploit=rs.getString("Exploit");
	  		Exploit_num[i]= Integer.parseInt(Exploit);
	  		
	  		String Dos=rs.getString("Dos");
	  		Dos_num[i]= Integer.parseInt(Dos);
	  		i++;
	  	}
	  	
	  	for(i=0;i<9;i++)
	  	{
	  		SQL_sum=SQL_sum+SQL_num[i];
	  		PHPWebShell_sum=PHPWebShell_sum+PHPWebShell_num[i];
	  		Exploit_sum=Exploit_sum+Exploit_num[i];
	  		Dos_sum=Dos_sum+Dos_num[i];
	  	}
	  	
  
	  	stmt.close();
	  	conn.close();
  %>
<meta http-equiv=Content-Type content=text/html;charset=utf-8>


<!-- 引入 echarts.js -->
<script type="text/javascript" src="echarts.min.js"></script>

<body>
<!-- 一天饼图的Dom -->
<div id="piechart_a1" style="width: 630px;height:395px;"></div>
<script type="text/javascript">
var myChart = echarts.init(document.getElementById('piechart_a1'));

var SQL_num=<%=SQL_sum%>;
var PHPWebShell_num=<%=PHPWebShell_sum%>;
var Exploit_num=<%=Exploit_sum%>;
var Dos_num=<%=Dos_sum%>;

var option = {
    
    title : {
        text: '一天流量分析饼状图',
        x:'center'
    },
    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        orient: 'vertical',
        left: 'right',
        data: ['SQL','PHPWebShell','Exploit','Dos']
    },
    series : [
        {
            name: '访问来源',
            type: 'pie',
            radius : '65%',
            center: ['50%', '60%'],
            data:[
                {value:SQL_num, name:'SQL'},
                {value:PHPWebShell_num, name:'PHPWebShell'},
                {value:Exploit_num, name:'Exploit'},
                {value:Dos_num, name:'Dos'},
            ],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
};
myChart.setOption(option);
</script>
</body>