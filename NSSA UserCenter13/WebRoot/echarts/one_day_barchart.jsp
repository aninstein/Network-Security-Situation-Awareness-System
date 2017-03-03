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
	  	
	  	/*各个数据数组*/
	  	int SQL_num[]=new int[9];
 	  	int PHPWebShell_num[]=new int[9];
 	  	int Exploit_num[]=new int[9];
 	  	int Dos_num[]=new int[9];
	  	int i=0;
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
	  	
	  	stmt.close();
	  	conn.close();
  %>
<meta http-equiv=Content-Type content=text/html;charset=utf-8>


<!-- 引入 echarts.js -->
<script type="text/javascript" src="echarts.min.js"></script>

<body>
<!-- 一天的柱状图的Dom -->
<div id="echarts_bar_day" style="width:800px;height:480px;"></div>
<script type="text/javascript">
var myChart = echarts.init(document.getElementById('echarts_bar_day'));


var SQL_num=[<%=SQL_num[0]%>,<%=SQL_num[1]%>,<%=SQL_num[2]%>,<%=SQL_num[3]%>,<%=SQL_num[4]%>,<%=SQL_num[5]%>,<%=SQL_num[6]%>,<%=SQL_num[7]%>,<%=SQL_num[8]%>];
var PHPWebShell_num=[<%=PHPWebShell_num[0]%>,<%=PHPWebShell_num[1]%>,<%=PHPWebShell_num[2]%>,<%=PHPWebShell_num[3]%>,<%=PHPWebShell_num[4]%>,<%=PHPWebShell_num[5]%>,<%=PHPWebShell_num[6]%>,<%=PHPWebShell_num[7]%>,<%=PHPWebShell_num[8]%>];
var Exploit_num=[<%=Exploit_num[0]%>,<%=Exploit_num[1]%>,<%=Exploit_num[2]%>,<%=Exploit_num[3]%>,<%=Exploit_num[4]%>,<%=Exploit_num[5]%>,<%=Exploit_num[6]%>,<%=Exploit_num[7]%>,<%=Exploit_num[8]%>];
var Dos_num=[<%=Dos_num[0]%>,<%=Dos_num[1]%>,<%=Dos_num[2]%>,<%=Dos_num[3]%>,<%=Dos_num[4]%>,<%=Dos_num[5]%>,<%=Dos_num[6]%>,<%=Dos_num[7]%>,<%=Dos_num[8]%>];

var option = {
    title: {
        text: '一天内各类安全事件统计\n',
        left: 'left'
    },
    tooltip : {
        trigger: 'axis',
        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    toolbox: {
        show: true,
        feature: {
            dataZoom: {},
            dataView: {readOnly: false},
            magicType: {type: ['line', 'bar']},
            restore: {},
            saveAsImage: {}
        }
    },
    legend: {
        data:['SQL','PHPWebShell','Exploit','Dos']
    },
    grid: {
        left: '3%',
        right: '10%',
        bottom: '3%',
        containLabel: true
    },
    xAxis : [
        {
            type : 'category',
            name:'时间',
            data : ['00:00','03:00', '06:00', '09:00', '12:00', '15:00', '18:00','21:00','24:00']
        }
    ],
    yAxis : [
        {
            type : 'value',
            name:'数值'
        }
    ],
    series : [
        {
            name:'SQL',
            type:'bar',
            barWidth: 12,
            data: SQL_num
        },
        {
            name:'PHPWebShell',
            type:'bar',
			barWidth: 12,
            data: PHPWebShell_num
        },
        {
            name:'Exploit',
            type:'bar',
			barWidth: 12,
            data: Exploit_num
        },
        {
            name:'Dos',
            type:'bar',
			barWidth: 12,
            data: Dos_num
        }
        /*{
            name:'搜索引擎',
            type:'bar',
            data:[862, 1018, 964, 1026, 1679, 1600, 1570],
            markLine : {
                lineStyle: {
                    normal: {
                        type: 'dashed'
                   bhy70 }
                },
                data : [
                    [{type : 'min'}, {type : 'max'}]
                ]
            }
        }*/
    ]
};
myChart.setOption(option);
</script>
</body>
