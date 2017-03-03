
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
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<%
  
			//加载驱动 //驱动注册
		Class.forName("com.mysql.jdbc.Driver");
			// 创建连接
		Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/nssa?useUnicode=true&characterEncoding=UTF-8", "root", "root");
	  	Statement stmt=conn.createStatement();
	  	
	  	
	  	ResultSet rs=stmt.executeQuery("select * from week_barchart");
	  	
	  	/*各个数据数组*/
	  	int SQL_num[]=new int[7];
 	  	int PHPWebShell_num[]=new int[7];
 	  	int Exploit_num[]=new int[7];
 	  	int Dos_num[]=new int[7];
	  	int i=0;
	  	while(rs.next())
	  	{
	  	
	  		String time=rs.getString("day");
	  		
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
	  	
	  	stmt.close();
	  	conn.close();
  %>


<meta http-equiv=Content-Type content=text/html;charset=utf-8>


<!-- 引入 echarts.js -->
<script type="text/javascript" src="echarts.min.js"></script>

<body>
<!-- 一天的柱状图的Dom -->
<div id="echarts_bar_week" style="width: 800px; height:530px;"></div><!-- margin-top:40px; float:left;-->
<script type="text/javascript">
var myChart = echarts.init(document.getElementById('echarts_bar_week'));

var SQL_num=[<%=SQL_num[0]%>,<%=SQL_num[1]%>,<%=SQL_num[2]%>,<%=SQL_num[3]%>,<%=SQL_num[4]%>,<%=SQL_num[5]%>,<%=SQL_num[6]%>];
var PHPWebShell_num=[<%=PHPWebShell_num[0]%>,<%=PHPWebShell_num[1]%>,<%=PHPWebShell_num[2]%>,<%=PHPWebShell_num[3]%>,<%=PHPWebShell_num[4]%>,<%=PHPWebShell_num[5]%>,<%=PHPWebShell_num[6]%>];
var Exploit_num=[<%=Exploit_num[0]%>,<%=Exploit_num[1]%>,<%=Exploit_num[2]%>,<%=Exploit_num[3]%>,<%=Exploit_num[4]%>,<%=Exploit_num[5]%>,<%=Exploit_num[6]%>];
var Dos_num=[<%=Dos_num[0]%>,<%=Dos_num[1]%>,<%=Dos_num[2]%>,<%=Dos_num[3]%>,<%=Dos_num[4]%>,<%=Dos_num[5]%>,<%=Dos_num[6]%>];


var option = {
	title: {
        text: '最近一周各类安全事件统计\n',
        left: 'left'
    },
    tooltip : {
        trigger: 'axis',
        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    legend: {
        data: ['SQL', 'PHPWebShell','Exploit','Dos'],
		left: 'right'
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis:  {
        type: 'value'
    },
    yAxis: {
        type: 'category',
        data: ['周一','周二','周三','周四','周五','周六','周日']
    },
    series: [
        {
            name: 'SQL',
            type: 'bar',
            stack: '总量',
            label: {
                normal: {
                    show: true,
                    position: 'insideRight'
                }
            },
            data: SQL_num
        },
        {
            name: 'PHPWebShell',
            type: 'bar',
            stack: '总量',
            label: {
                normal: {
                    show: true,
                    position: 'insideRight'
                }
            },
            data: PHPWebShell_num
        },
        {
            name: 'Exploit',
            type: 'bar',
            stack: '总量',
            label: {
                normal: {
                    show: true,
                    position: 'insideRight'
                }
            },
            data: Exploit_num
        },
        {
            name: 'Dos',
            type: 'bar',
            stack: '总量',
            label: {
                normal: {
                    show: true,
                    position: 'insideRight'
                }
            },
            data: Dos_num
        }
    ]
};
myChart.setOption(option);
</script>
</body>