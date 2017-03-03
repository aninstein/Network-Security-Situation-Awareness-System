
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
	  	
	  	
	  	ResultSet rs=stmt.executeQuery("select * from week_linchart");
	  	
	  	/*各个数据数组*/
	  	double Assets_num[]=new double[7];//Assets_num表示威胁指数了
 	  	double Risk_num[]=new double[7];//Risk_numl表示脆弱指数了
 	  	double Threaten_num[]=new double[7];//Threaten_num表示稳定指数了
 	  	double Positive_num[]=new double[7];//Positive_num表示容灾指数了
	  	int i=0;
	  	while(rs.next())
	  	{
	  	
	  		String time=rs.getString("day");
	  		
	  		String Assets=rs.getString("Assets");
	  		Assets_num[i]=  Double.parseDouble(Assets);
	  		
	  		String Risk=rs.getString("Risk");
	  		Risk_num[i]= Double.parseDouble(Risk);
	  		
	  		String Threaten=rs.getString("Threaten");
	  		Threaten_num[i]=  Double.parseDouble(Threaten);
	  		
	  		String Positive=rs.getString("Positive");
	  		Positive_num[i]=  Double.parseDouble(Positive);
	  		i++;
	  	}
	  	
	  	stmt.close();
	  	conn.close();
  %>
<meta http-equiv=Content-Type content=text/html;charset=utf-8>


<!-- 引入 echarts.js -->
<script type="text/javascript" src="echarts.min.js"></script>

<body>
<!-- 一周的折线图的Dom -->
<div id="echarts_lin_week" style="width: 800px; height:530px;"></div><!-- margin-top:40px; float:left;-->
<script type="text/javascript">
/*a_week_linchart.js*/
var myChart = echarts.init(document.getElementById('echarts_lin_week'));

var Assets_num=[<%=Assets_num[0]%>,<%=Assets_num[1]%>,<%=Assets_num[2]%>,<%=Assets_num[3]%>,<%=Assets_num[4]%>,<%=Assets_num[5]%>,<%=Assets_num[6]%>];//资产指数
var Risk_num=[<%=Risk_num[0]%>,<%=Risk_num[1]%>,<%=Risk_num[2]%>,<%=Risk_num[3]%>,<%=Risk_num[4]%>,<%=Risk_num[5]%>,<%=Risk_num[6]%>];//风险指数
var Threaten_num=[<%=Threaten_num[0]%>,<%=Threaten_num[1]%>,<%=Threaten_num[2]%>,<%=Threaten_num[3]%>,<%=Threaten_num[4]%>,<%=Threaten_num[5]%>,<%=Threaten_num[6]%>];//威胁指数
var Positive_num=[<%=Positive_num[0]%>,<%=Positive_num[1]%>,<%=Positive_num[2]%>,<%=Positive_num[3]%>,<%=Positive_num[4]%>,<%=Positive_num[5]%>,<%=Positive_num[6]%>];//势态指数

var option = {
    title: {
        text: '最近一周安全势态折线图\n',
        left: 'left'
    },
    tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {c}'
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
        left: 'center',
        //right: '80%',
        data: ['威胁态势', '脆弱态势','稳定态势','容灾态势']
    },
    xAxis: {
        type: 'category',
        name: '日期',
        splitLine: {show: true},
        data: ['周一','周二','周三','周四','周五','周六','周日']
    },
    grid: {
        left: '3%',
        right: '10%',
        bottom: '3%',
        containLabel: true
    },
    yAxis: {
        type: 'value',
        name: '数值',
        splitLine: {show: true},
        
    },
   series: [
        {
            name: '威胁态势',
            type: 'line',
            data: Assets_num,
             markPoint: {
                data: [
                    {type: 'max', name: '最大值'},
                    {type: 'min', name: '最小值'}
                ]
            },
            markLine: {
                data: [
                    {type: 'average', name: '平均值'}
                ]
            }
        },
        {
            name: '脆弱态势',
            type: 'line',
            data: Risk_num,
             markPoint: {
                data: [
                    {type: 'max', name: '最大值'},
                    {type: 'min', name: '最小值'}
                ]
            },
            markLine: {
                data: [
                    {type: 'average', name: '平均值'}
                ]
            }
        },
        {
            name: '稳定态势',
            type: 'line',
            data: Threaten_num,
             markPoint: {
                data: [
                    {type: 'max', name: '最大值'},
                    {type: 'min', name: '最小值'}
                ]
            },
            markLine: {
                data: [
                    {type: 'average', name: '平均值'}
                ]
            }
        },
         {
            name: '容灾态势',
            type: 'line',
            data: Positive_num,
             markPoint: {
                data: [
                    {type: 'max', name: '最大值'},
                    {type: 'min', name: '最小值'}
                ]
            },
            markLine: {
                data: [
                    {type: 'average', name: '平均值'}
                ]
            }
        }
    ]
};
myChart.setOption(option);
</script>
</body>