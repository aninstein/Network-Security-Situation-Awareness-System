
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
//	  	double all_sum[]=new double[9];
// 	  	double All_Sum=0;
	  	double Threaten=0;
	  	
	  	for(i=0;i<9;i++){
	  		Assets_sum=Assets_sum+Assets_num[i]*0.4083;
	  		Risk_sum=Risk_sum+Risk_num[i]*0.07781;
	  		Threaten_sum=Threaten_sum+Threaten_num[i]*0.33941;
	  		Positive_sum=Positive_sum+Positive_num[i]*0.17448;
	  		//权重：w=(0.40830,0.07781,0.33941,0.17448)
	  		
//	  		all_sum[i]=Assets_sum+Risk_sum+Threaten_sum+Positive_sum;
	  		
// 	  		All_Sum=All_Sum+all_sum[i];
	  	}
	  	
	  	DecimalFormat df = new DecimalFormat( "00.0");
	  	Threaten=((double)Threaten_sum/(49.5*0.33941))*100;//化成百分比,49.5是5.5*9*1,之所以*1是因为w的和为1
	  	Threaten=Double.parseDouble(df.format(Threaten));//字符串化double；
	  

	  	
	  	stmt.close();
	  	conn.close();
  %>
<meta http-equiv=Content-Type content=text/html;charset=utf-8>


<!-- 引入 echarts.js -->
<script type="text/javascript" src="echarts.min.js"></script>

<body>
<!-- 一天的折线图的Dom -->
<div id="echarts_meter3" style="width:450px; height:300px;"></div><!-- margin-top:40px; float:left;-->
<script type="text/javascript">

var Threaten_num=<%=Threaten%>;//资产指数
var myChart = echarts.init(document.getElementById('echarts_meter3'));
var option= {
    title: {
        text: '当前稳定指数\n',
        left:'center'
    },
    tooltip : {
        formatter: "{a} <br/>{b} : {c}%"
    },
    toolbox: {
        feature: {
            restore: {},
            saveAsImage: {}
        }
    },
    series: [
        {
            name:'稳定态势',
            type: 'gauge',
            detail: {formatter:'{value}%'},
            data: [{value: Threaten_num, name: '数值'}]
        }
    ]
};
myChart.setOption(option);
</script>
</body>