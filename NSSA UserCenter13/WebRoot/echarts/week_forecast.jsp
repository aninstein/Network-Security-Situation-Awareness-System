<!DOCTYPE html>
<%@page import="java.text.DecimalFormat"%>
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
	  	
	  		/*各种百分比数据*/
	  	/*各种和数*/
	  	double all_sum[]=new double[7];
//  	  	double All_Sum=0;
	  	
	  	for(i=0;i<7;i++){
	  		
	  		all_sum[i]=Assets_num[i]*0.4083+Risk_num[i]*0.07781+Threaten_num[i]*0.33941+Positive_num[i]*0.17448;
	  		DecimalFormat df = new DecimalFormat( "00.0");
	  		all_sum[i]=Double.parseDouble(df.format(all_sum[i]));
	  		//权重：w=(0.40830,0.07781,0.33941,0.17448)
	  		
//  	  		All_Sum=All_Sum+all_sum[i];
	  	}
	  			
// 		All_Sum=All_Sum/38.5;
	  	
	  	stmt.close();
	  	conn.close();
  %>
<meta http-equiv=Content-Type content=text/html;charset=utf-8>


<!-- 引入 echarts.js -->
<script type="text/javascript" src="echarts.min.js"></script>

<body>
<!-- 一天的柱状图的Dom -->
<div id="echarts_forecast" style="width:850px;height:500px;"></div>
<script type="text/javascript">
var myChart = echarts.init(document.getElementById('echarts_forecast'));

var all_sum=[<%=all_sum[0]%>,<%=all_sum[1]%>,<%=all_sum[2]%>,<%=all_sum[3]%>,<%=all_sum[4]%>,<%=all_sum[5]%>,<%=all_sum[6]%>];;

var option1 = {
    title: {
        text: '周态势预测表示图',
        subtext: 'ISVM方法对测试系统网络未来某周态势进行预测'
    },
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        data:['ISVM测试']
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
    xAxis:  {
        type: 'category',
        boundaryGap: false,
        data: ['周一','周二','周三','周四','周五','周六','周日']
    },
    yAxis: [
        {
            type : 'value'
        }
    ],
    series: [
       
        {
            name: 'ISVM测试',
            type:'line',
            data:all_sum,
              itemStyle:{
                  normal:{
                      color:'#2E8500',
                     lineStyle:{
                      color:'#2E8500',
                      width: 3
                      },
                  },
              },
             markPoint: {
                data: [
                    {type: 'min', name: '最小值'}
                ]
            },
            markLine: {
                data: [
                    {type: 'average',
                    name: '平均值',
                        itemStyle:{
                  normal:{
                      lineStyle:{
                      color:'#2E8500',
                      width: 2
                      },
                  },
              },
                    },
                    [{
                        symbol: 'arrow',
                        label: {
                            normal: {
                                formatter: '最大值'
                            }
                        },
                        type: 'max',
                        name: '最大值'
                    },
                    {
                        symbol: 'circle',
                        x: '60%',
                        y: '10%',
                        itemStyle:{
                  normal:{
                      lineStyle:{
                      color:'#2E8500',
                      width: 2
                      },
                  },
              },
                    }
                     ]
                     
                ]
                
            }
            
        }
    ]
    
};

myChart.setOption(option1);
</script>
</body>
