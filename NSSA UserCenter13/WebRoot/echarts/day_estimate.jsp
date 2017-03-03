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
	  	
	  	
	  	ResultSet rs=stmt.executeQuery("select * from day_linchart");
	  	
double Assets_num[]=new double[9];//Assets_num表示威胁指数了
 	  	double Risk_num[]=new double[9];//Risk_numl表示脆弱指数了
 	  	double Threaten_num[]=new double[9];//Threaten_num表示稳定指数了
 	  	double Positive_num[]=new double[9];//Positive_num表示容灾指数了
	  	int i=0;
	  	while(rs.next())
	  	{
	  	
	  		String time=rs.getString("time");
	  		
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
	  	double Assets_sum=0;
	  	double Risk_sum=0;
	  	double Threaten_sum=0;
	  	double Positive_sum=0;
	  	double all_sum[]=new double[9];
 	  	double All_Sum=0;
	  	double Assets=0;
	  	
	  	for(i=0;i<9;i++){		
	  		
	  		all_sum[i]=Assets_num[i]*0.4083+Risk_num[i]*0.07781+Threaten_num[i]*0.33941+Positive_num[i]*0.17448;
	  		DecimalFormat df = new DecimalFormat( "00.0");
	  		all_sum[i]=Double.parseDouble(df.format(all_sum[i]));
	  		//权重：w=(0.40830,0.07781,0.33941,0.17448)
	  		
 	  		All_Sum=All_Sum+all_sum[i];
	  	}
	  			
		All_Sum=All_Sum/49.5;
// 		double all_sum[]={1.5,2.8,3.6,4.1,2.1,3.5,3.1,2.5,1.9};	  	
	  	
	  	stmt.close();
	  	conn.close();
	  
  %>
<meta http-equiv=Content-Type content=text/html;charset=utf-8>


<!-- 引入 echarts.js -->
<script type="text/javascript" src="echarts.min.js"></script>

<body>
<!-- 一天的柱状图的Dom -->
<div id="echarts_estimate" style="width:850px;height:500px;"></div>
<script type="text/javascript">
var myChart = echarts.init(document.getElementById('echarts_estimate'));
var newArray=[<%=all_sum[0]%>,<%=all_sum[1]%>,<%=all_sum[2]%>,<%=all_sum[3]%>,<%=all_sum[4]%>,<%=all_sum[5]%>,<%=all_sum[6]%>,<%=all_sum[7]%>,<%=all_sum[8]%>];//势态指数
var option2 = {
    title: {
        text: '日态势评估表示图',
        subtext: 'T-S模糊神经网络方法对测试系统网络状态进行评估'
    },
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        data:['总体势态评估']
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
        data:['00:00','03:00', '06:00', '09:00', '12:00', '15:00', '18:00','21:00','24:00']
    },
    yAxis: [
        {
            type : 'value'
        }
    ],
   series: [
       
        {
            name: '总体势态评估',
            type:'line',
            data:newArray,
              itemStyle:{
                  normal:{
                      color:'#4E00B6',
                     lineStyle:{
                      color:'#4E00B6',
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
                      color:'#4E00B6',
                      width: 3
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
                      color:'#4E00B6',
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

myChart.setOption(option2);
</script>
</body>