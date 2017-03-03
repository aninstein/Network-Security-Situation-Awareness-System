<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
String msg = request.getParameter("msg");
%>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-color: #F8F9FA;
}
-->
</style>
<meta http-equiv=Content-Type content=text/html;charset=utf-8>
<link href="images/skin.css" rel="stylesheet" type="text/css" />

<!-- 引入 echarts.js -->
<script src="js/echarts.js"></script>
    
<body>
	<form name="form1" method="POST" action="modifypassword.jsp">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td width="17" height="29" valign="top" background="images/mail_leftbg.gif">
				<img src="images/left-top-right.gif" width="17" height="29" />
			</td>
			<td width="935" height="29" valign="top" background="images/content-bg.gif">
				<table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="left_topbg" id="table2">
					<tr>
						<td height="31"><div class="titlebt">报表查看</div></td>
					</tr>
				</table>
			</td>
			<td width="16" valign="top" background="images/mail_rightbg.gif">
				<img src="images/nav-right-bg.gif" width="16" height="29" /></td>
		</tr>
		<tr>
			<td height="71" valign="middle" background="images/mail_leftbg.gif">&nbsp;</td>
			<td valign="top" bgcolor="#F7F8F9">
			<table width="100%" height="138" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td height="13" valign="top">&nbsp;</td>
					</tr>
					<tr>
						<td valign="top">
							<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
								<tr>
									<td>
										<table width="100%" height="55" border="0" cellpadding="0" cellspacing="0">
											<tr>
												<td width="10%" height="55" valign="middle">
													<img src="images/title.gif" width="54" height="55"></td>
												<td width="90%" valign="top">
													<span class="left_txt2">您可以以柱状图的方式<a href="#">查看概况</a>，也可以以列表的方式<a href="list.jsp?msg=<%=msg %>&pageNo=1">查看详情</a></span></td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td>
										<table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="nowtable">
											<tr>
												<td class="left_bt2">&nbsp;&nbsp;&nbsp;&nbsp;柱状图</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
								</tr>
							</table>
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td colspan="3"></td>
								</tr>
								<tr>
								
									
									<!--    换为表格             -->
									
									<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    			<div id="main" style="width: 600px;height:400px;"></div>
    			<div id="main2" style="width: 600px;height:400px;"></div>
    				<script type="text/javascript">
        		// 基于准备好的dom，初始化echarts实例
        
       
        
        		var myChart = echarts.init(document.getElementById('main'));
        		var myChart2 = echarts.init(document.getElementById('main2'));
        			// 指定图表的配置项和数据
 				var option = {
            		title: {
                	text: 'ECharts 入门示例'
            		},
            		tooltip: {},
            			legend: {
                		data:['销量']
            			},
            			xAxis: {
                		data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
            				},
           				 yAxis: {},
            				series: [{
               					 name: '销量',
               				 type: 'bar',
                				data: [5, 20, 36, 10, 10, 20]
           							 }]
       								 };

        							// 使用刚指定的配置项和数据显示图表。
        							myChart.setOption(option);
        							myChart2.setOption(option);
    								</script>
									
									
									<!--
									<td height="30" colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
									<td height="30" colspan="3"><%if(msg.equals("all")){%><img src="servlet/BarChartServletAll?date=today"><%}else{%><img src="servlet/BarChartServletOne?msg=<%=msg%>&date=today"><%}%></td>
									<td height="30" colspan="3"><%if(msg.equals("all")){%><img src="servlet/BarChartServletAll?date=lastweek"><%}else{%><img src="servlet/BarChartServletOne?msg=<%=msg%>&date=lastweek"><%}%></td>
									-->
								</tr>
								<tr>
									<td height="30" colspan="3">&nbsp;</td>
								</tr>
								<tr>
									
								</tr>
								<tr>
									<td height="30" colspan="3">&nbsp;</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
			<td background="images/mail_rightbg.gif">&nbsp;</td>
		</tr>
		<tr>
			<td valign="middle" background="images/mail_leftbg.gif"><img
				src="images/buttom_left2.gif" width="17" height="17" /></td>
			<td height="17" valign="top" background="images/buttom_bgs.gif"><img
				src="images/buttom_bgs.gif" width="17" height="17" /></td>
			<td background="images/mail_rightbg.gif"><img
				src="images/buttom_right2.gif" width="16" height="17" /></td>
		</tr>
	</table>
	</form>
</body>
