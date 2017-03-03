<!DOCTYPE html> 


<%@page import="nssa.uc.vo.SecurityEvent"%>
<%@page import="nssa.uc.dao.SecurityEventDao"%>
<%@page import="nssa.uc.vo.HostStat"%>
<%@page import="nssa.uc.dao.HostStatDao"%>
<%@page import="nssa.uc.util.PageUtil"%>
<%@page import="nssa.uc.util.GoogleMapUtil"%>
<%@page import="nssa.uc.util.BaiduMapIpAdress"%>
<%@page import="nssa.uc.util.Forecast"%>
<%@page import="nssa.uc.vo.HostAsset"%>
<%@page import="nssa.uc.dao.HostAssetDao"%>
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
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!-- 资产检测列表 -->
<%
	int pageNo_zichan = 1;//Integer.parseInt(request.getParameter("pageNo_zichan"));
	HostAssetDao hostAssetDao = new HostAssetDao();
	List<HostAsset> hostAssetList = null;
	//PageUtil pageUtil = null;

	hostAssetList = hostAssetDao.listAll(pageNo_zichan);
	/*pageUtil = new PageUtil(pageNo_zichan,hostAssetDao.countAll(), "#?");*/
%>

<!-- 主机状态列表 -->
<%
	int pageNo_zhuji = 1;//Integer.parseInt(request.getParameter("pageNo_zhuji"));
	HostStatDao hostStatDao = new HostStatDao();
	List<HostStat> hostStatList = null;
	//PageUtil pageUtil = null;

	hostStatList = hostStatDao.listAll(pageNo_zhuji);
	/*pageUtil = new PageUtil(pageNo_zhuji,hostStatDao.countAll(), "lisths.jsp?");*/
%>


<!-- 流量分析列表 -->
<%
	String msg = "all";//request.getParameter("msg");
	int pageNo_liuliang = 1;//Integer.parseInt(request.getParameter("pageNo_liuliang"));
	SecurityEventDao securityEventDao = new SecurityEventDao();
	List<SecurityEvent> securityEventList = null;
	PageUtil pageUtil = null;

		if (msg.equals("all")) {
	securityEventList = securityEventDao.listAll(pageNo_liuliang);
	pageUtil = new PageUtil(pageNo_liuliang,securityEventDao.countAll(), "list.jsp?msg="+ msg + "&");
		} 
		else {
	securityEventList = securityEventDao.listAllByMsg(msg,
			pageNo_liuliang);
	pageUtil = new PageUtil(pageNo_liuliang,securityEventDao.countByMsg(msg),"list.jsp?msg=" + msg + "&");
		}
%>

<!-- 评估报告列表 -->
<%
  		int i=0;//很重要的一个i
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
	  	double All_sum=0;

	  	DecimalFormat df = new DecimalFormat( "00.0");//固定定义
	  	
	  	for(i=0;i<9;i++){
	  		Assets_sum=Assets_sum+Assets_num[i];
	  		Risk_sum=Risk_sum+Risk_num[i];
	  		Threaten_sum=Threaten_sum+Threaten_num[i];
	  		Positive_sum=Positive_sum+Positive_num[i];
	  		//权重：w=(0.40830,0.07781,0.33941,0.17448)
	  		
	  		all_sum[i]=Assets_sum*0.4083+Risk_sum*0.07781+Threaten_sum*0.33941+Positive_sum*0.17448;
	  		
 	  		All_sum=All_sum+(all_sum[i]/9);
	  	}
	  	
	  	  	/*计算平均值*/
	  	Assets_sum=Double.parseDouble(df.format(Assets_sum/9));
	  	Risk_sum=Double.parseDouble(df.format(Risk_sum/9));
	  	Threaten_sum=Double.parseDouble(df.format(Threaten_sum/9));
	  	Positive_sum=Double.parseDouble(df.format(Positive_sum/9));
	  	All_sum=Double.parseDouble(df.format(All_sum/4));
	  	
	  	/*评价等级、安全等级等字符串*/
	  	
	  	String EvaluateLevel="";//评价等级
	  	String ScurityLevel="";//安全等级	
	  	
	  	String AssetsLevel="";//威胁态势
	  	String RiskLevel="";//脆弱态势
	  	String ThreatenLevel="";//稳定态势
	  	String PositiveLevel="";//容灾态势
	   	
	  	int All_op=(int)(All_sum/0.5);
	  	switch(All_op){
	  		case 0:
	  		case 1:
	  		case 2:EvaluateLevel="E1";ScurityLevel="优";AssetsLevel="低";RiskLevel="低";ThreatenLevel="高";PositiveLevel="高";break;
	  		case 3:
	  		case 4:EvaluateLevel="E2";ScurityLevel="良";AssetsLevel="中";RiskLevel="中/低";ThreatenLevel="高/中";PositiveLevel="高/中";break;
	  		case 5:
	  		case 6:EvaluateLevel="E3";ScurityLevel="一般";AssetsLevel="高/中";RiskLevel="高/中";ThreatenLevel="高/中";PositiveLevel="中";break;
	  		case 7:
	  		case 8:EvaluateLevel="E4";ScurityLevel="较差";AssetsLevel="高";RiskLevel="高/中";ThreatenLevel="低";PositiveLevel="中/低";break;
	  		case 9:
	  		case 10:
	  		case 11:EvaluateLevel="E5";ScurityLevel="差";AssetsLevel="高";RiskLevel="高";ThreatenLevel="低";PositiveLevel="低";break;
	  		default:break;
	  	}
	  	
// 	  	String SafetyEvaluation="";//评价等级

stmt.close();
	  	conn.close();
  %>
  

<html>
	<head>
		<!-- Title here -->
		<title>安全态势感知系统</title>
		<!-- Description, Keywords and Author -->
		<meta name="description" content="Your description">
		<meta name="keywords" content="Your,Keywords">
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="author" content="ResponsiveWebInc">
		
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		
		<link href='http://fonts.googleapis.com/css?family=Open+Sans:400italic,700italic,400,600' rel='stylesheet' type='text/css'>	 
		<link href='http://fonts.googleapis.com/css?family=Open+Sans+Condensed:300,700,300italic' rel='stylesheet' type='text/css'>
		
		
		<!-- Styles -->
		<!-- table CSS -->
		<link href="css/style_table.css" rel="stylesheet">
		<!-- table2 CSS -->
		<link href="css/style_table2.css" rel="stylesheet">
		<!-- computer_table -->
		<link href="css/style_comtable.css" rel="stylesheet" type="text/css" media="screen">
		<!-- Bootstrap CSS -->
		<link href="css/bootstrap.min.css" rel="stylesheet">
		<!-- Font awesome CSS -->
		<link href="css/font-awesome.min.css" rel="stylesheet">	
		<!-- Animate CSS -->
		<link href="css/animate.min.css" rel="stylesheet">
		<!-- Custom CSS -->
		<link href="css/style.css" rel="stylesheet">
		<!-- baidumap CSS -->
		<link href="css/div_open.css" rel="stylesheet">
		
		<!-- Favicon -->
		<link rel="shortcut icon" href="#">
		<style type="text/css">
		body,td,th {
			font-family: "Open Sans", sans-serif;
		}
        </style>
		<!-- 引入 echarts.js -->
        <script src="js/echarts.min.js"></script>
        
        <!-- 引入 prefixfree.min.js -->
        <script src="js/prefixfree.min.js" type="text/javascript"></script>
	</head>
	
	<body>
		
		<div class="outer">
		
			<!-- Sidebar Start-->
			<!-- Menu for tablets and mobile -->
			<div class="navigation" >
				<a href="#">Menu</a>
			</div>
			<div class="sidebar" style="position:fixed; top:0px; left:0px; z-index:10; width:auto; height:1080px;">
				<!-- Logo of company -->
				<div class="logo text-center">
				<table>
				<tr>
					<td><img src="images/logo/logo-m-65.png"></td>
					<td><h1><a href="#">NSSA</a></h1></td>
				</tr>
				<tr>
					<td colspan="2"><p>欢迎登录安全势态感知！</p></td>
				</tr>
				</table>
				</div>
				<!--<div class="sidebar-search">
					<form>
						<div class="input-group">
							<input type="text" class="form-control" placeholder="Text to search">
							<span class="input-group-btn">
								<button class="btn btn-danger" type="button"><i class="icon-search"></i></button>
							</span>
						</div>
					</form>
				</div>-->
				<!-- Navigation menu list -->
				<ul class="list-unstyled list">
					<li><a href="#top" class="anchorLink"><i class=" icon-cogs scolor"></i> 系统信息</a></li>
					<li><a href="#.team" class="anchorLink"><i class=" icon-list-ol scolor"></i> 资产检测列表</a></li>
					<li><a href="#.service" class="anchorLink"><i class="icon-desktop scolor"></i> 主机状态列表</a></li>
					<li><a href="#.feature" class="anchorLink"><i class="icon-list  scolor"></i> 流量分析列表</a></li>
					<li><a href="#.testimonial" class="anchorLink"><i class=" icon-signal scolor"></i> 各类安全事件</a></li>
					<li><a href="#.ptable" class="anchorLink"><i class="icon-bar-chart  scolor"></i> 安全态势图形</a></li>
					<li><a href="#.contact" class="anchorLink"><i class=" icon-warning-sign scolor"></i> 安全势态评估预测</a></li>
					<li class="dropdown">
							<a data-toggle="dropdown" href="#"><i class="icon-user scolor"></i> 用户管理 <span class="caret"></span></a>
							<ul class="dropdown-menu" role="menu" aria-labelledby="dLabel" >
								<li><a href="admininfo.html" target="main">修改密码</a></li>
								<li><a href="logout.jsp" onclick="window.parent.location.href='logout.jsp'">注销登录</a></li>
								<!--
								<li><a href="#">Something else here</a></li>
								<li><a href="#">Separated link</a></li>
								-->
							</ul>
					</li>
				</ul>
				<!-- Social media links -->
				<div class="social">
					<a href="contactUs.html" class="facebook"><i class="icon-comments"></i></a>
					<a href="contactUs.html" class="twitter"><i class="icon-phone-sign"></i></a>
					<a href="contactUs.html" class="google"><i class="icon-envelope"></i></a>
					<!--<a href="#" class="linkedin"><i class="icon-envelope"></i></a>-->
				</div>
			</div>
			
			<!-- Sidebar End -->
			
			<!-- Main Start -->
			
			<div class="main">
			
				<div id="carousel-example-generic" class="carousel slide carousel-fade">
				   
					<!--系统信息 start -->
					
					<div class="carousel-inner">
						<!-- Carousel item start -->
						<div class="item active">
							<!-- Carousel background images -->
							<img src="images/3.jpg" class="img-responsive" alt="" />
							<div class="carousel-caption">
								<div class="row">
									<div class="col-md-6">
										<!-- Images for carousel foreground -->
										<img src="images/s2.png" alt="" class="img-responsive" />
									</div>
									<div class="col-md-6">
										<!-- Carousel caption -->
										<div class="caption-content">
											<h3>基本信息</h3>
											<p>资产检测:&nbsp;检查网络中各个主机的硬件以及资产信息</p>
											<p>主机扫描:&nbsp;通过网络扫描检查各主机的上线情况，端口开放情况等</p>
											<p>流量分析:&nbsp;通过监听网络流量来检测入侵行为</p>
											<p>数据融合:&nbsp;各传感器通过NSSA协议将信息发送给服务中心存储，服务中心里边嵌入了用户中心作为显示</p>
										</div>
									</div>
								</div>
							</div>
						</div>
				  
						<div class="item">
							<img src="images/3.jpg"  class="img-responsive" alt=""/>
							<div class="carousel-caption">
								<div class="row">
									<div class="col-md-6">
										<img src="images/s3.png" alt="" class="img-responsive" />
									</div>
									<div class="col-md-6">
										<div class="caption-content">
											<h3>功能说明</h3>
											<p>本系统可以显示资产检测，主机扫描，流量分析等不同传感器发送来的的数据。</p>
								            <p>本系统可以根据上述数据提取出网络资产，风险，威胁等信息，进而计算出网络安全态势值。</p>
								            <p>本系统可以从宏观的角度在整体上把握网络安全的状态。</p>
										</div>
									</div>
								</div>
							</div>
						</div>
				  
						<div class="item">
							<img src="images/3.jpg"  class="img-responsive" alt=""/>
							<div class="carousel-caption">
								<div class="row">
									<div class="col-md-6">
										<img src="images/s1.png" alt="" class="img-responsive" />
									</div>
									<div class="col-md-6">
										<div class="caption-content">
											<h3>运行环境</h3>
											<p>操作系统：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;不限，安装有JAVA环境即可</p>
											<p>数据库：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;mysql</p>
											<p>传感器子系统：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Java SE</p>
											<p>服务中心子系统：&nbsp;Java SE（内嵌Java EE环境）</p>
											<p>用户中心子系统：&nbsp;Java EE（嵌入在服务中心内）</p>
											</div>
									</div>
								</div>
							</div>
						</div>
					</div>

					<!-- Controls -->

					<a class="left carousel-control" href="#carousel-example-generic" data-slide="prev">
						<span class="icon-prev"></span>
					</a>
					<a class="right carousel-control" href="#carousel-example-generic" data-slide="next">
						<span class="icon-next"></span>
					</a>
				</div>
				
				<!-- 系统信息 End -->
				
				
				
				<div class="container">
				
					<!--版权信息 Start-->
					
					<div class="hero">
						<div class="row">
							<div class="col-md-12">
								<h2>感谢使用网络安全势态感知系统！</h2>
								<p style="color:red;">版权声明</p>
								<p>本系统仅提供使用，任何违反互联网规定的行为，自行负责！</p>
								<p align="center"><span class="login-buttom-txt" style="font-family:黑体; color:#979797; font-weight:900;">
								Copyright &copy; 2016 安全态势感知系统小组</span></p>
							</div>
						</div>
					</div>
					<hr />
					<!--版权信息 End -->
					
					<!-- 主机资产查看 start-->
					<div class="team" style="height:1100px;">
						<h3>资产查看</h3>
						<div class="bor hidden-xs"></div>
				<ul class="pricing_table">
					<%
					for(i=0;i<hostAssetList.size();i++){
					%>
					
					<li class="price_block">
						<h3><%=(i+1)%></h3>
						<div class="price">
							<div class="price_figure">
							<span class="price_number"><img src="images/min_computer.png"></span><!--小电脑 -->
                            <!--<i class="icon-desktop scolor">小电脑 -->
							<span class="price_tenure"><%=hostAssetList.get(i).getName()%></span><!--主机名-->
							</div>
						</div>
						<table class="features">
								<tr><td><span class="login-buttom-txt" style="font-family:黑体; color:#979797; font-weight:900;">时间:</span><%=hostAssetList.get(i).getTime()%></td></tr>
								<tr><td><span class="login-buttom-txt" style="font-family:黑体; color:#979797; font-weight:900;">域名:</span><%=hostAssetList.get(i).getDomain()%></td></tr>
								<tr><td><span class="login-buttom-txt" style="font-family:黑体; color:#979797; font-weight:900;">操作系统:</span><%=hostAssetList.get(i).getOs()%></td></tr>
								<tr><td><span class="login-buttom-txt" style="font-family:黑体; color:#979797; font-weight:900;">处理器:</span><%=hostAssetList.get(i).getCpu()%></td></tr>
								<tr><td><span class="login-buttom-txt" style="font-family:黑体; color:#979797; font-weight:900;">内存:</span><%=hostAssetList.get(i).getMemory()%></td></tr>
								<tr><td><span class="login-buttom-txt" style="font-family:黑体; color:#979797; font-weight:900;">硬盘:</span><%=hostAssetList.get(i).getDisk()%></td></tr>
								<tr><td><span class="login-buttom-txt" style="font-family:黑体; color:#979797; font-weight:900;">其它:</span><%=hostAssetList.get(i).getDetail()%></td></tr>
						</table>
<!-- 						<div class="footer" style="height:20px;"> -->
<!-- 							<a href="#" class="action_button">查看详情</a> -->
<!-- 						</div> -->
					</li>
					
					<%
					}
					%>
					</ul>
					</div>
					<hr />
					
					<!-- 主机资产查看End --> 
						
					<!-- 主机状态列表start -->
					
					<div class="service" style="height:1080px;">
						<h3>主机状态列表</h3>
						<div class="bor hidden-xs"></div>
						<div style="background: url(images/big_computer3.png) no-repeat;width:1500px; height:950px; margin:0 auto;">
						<br><br><br><br>
						<div style="width:1300px;height:850px; margin:0 auto;">
						<table class="bordered" width="100%" border="0" cellspacing="0" cellpadding="0">
			    <thead>
                    <tr>
                     <th>ip</th>
 <th>时间</th>
 <th>端口列表</th>
                    </tr>
                </thead>
					<%
						for (i = 0; i < hostStatList.size(); i++) {
					%>
					<tbody>
					<tr class="evenTr">
						<td><%=hostStatList.get(i).getIp()%></td>
						<td><%=hostStatList.get(i).getTime()%></td>
						<td><%=hostStatList.get(i).getPorts()%></td>
					</tr>
					</tbody>
					<%
						}
					%>
		</table></div>
		</div>
					</div>
					<!-- 主机状态列表 End -->
					
					<!-- 流量分析列表 Start -->
					
					<div class="feature" style="height:1300px;">
						<h3>流量分析列表</h3>
						<div class="bor hidden-xs"></div>
													<table  class="table1" width="100%" >
													<thead>
														<tr>
															<th>序号</th>
															<th>时间</th>
															<th>消息</th>
															<th>源IP</th>
															<th>源端口</th>
															<th>目标IP</th>
															<th>目标端口</th>
															<th>数据包</th>
														</tr>
														</thead>
														<%
														int op=0;
															for (i = 0; i < securityEventList.size(); i++) {
															op=i;
														%>
														<tbody>
														<tr class="evenTr">
															<td><%=securityEventList.get(i).getId()%></td>
															<td><%=securityEventList.get(i).getTime()%></td>
															<td><%=securityEventList.get(i).getMsg()%></td>
															<td><a href="gmaps.jsp?ip=<%=securityEventList.get(i).getSip()%>"><%=securityEventList.get(i).getSip()%></a></td>
															<td><%=securityEventList.get(i).getSport()%></td>
															<td>
															<a href="javascript:void(0)"onclick="document.getElementById('light').style.display='block';document.getElementById('fade').style.display='block'">
															<%=securityEventList.get(i).getDip()%></a></td>
															<td><%=securityEventList.get(i).getDport()%></td>
															<td><a href="packet.jsp?id=<%=securityEventList.get(i).getId()%>">查看数据包</a></td>
														</tr>
														</tbody>
														<%
															
														}
														%>
													</table>
													<div id="light" class="div-open-Boundary ">
											<ul class="head-deep-blue">
<!--头深蓝色那条 -->
											<a href="javascript:void(0)" onclick="document.getElementById('light').style.display='none';document.getElementById('fade').style.display='none'">
									   <input type="submit" value="<<返回" class="button3"/></a>  
								            </ul>
								            <div id="login" class="div-deep-action show" align="center">
												<div id="fade" class="contact_1">
												<div class="google-map">
													<iframe frameborder="0" scrolling="no" marginheight="0" marginwidth="0" src="http://api.map.baidu.com/geocoder?address=<%=GoogleMapUtil.ip2addr(securityEventList.get(op).getDip())%>&output=html&src=yourCompanyName|yourAppName">
													</iframe>
												</div>
												</div>
								            </div>
								        </div>
									    <script class="cssdeck" src="js/div_open.js"></script>
					</div>
					
					<hr />
					
					<!-- 流量分析列表 End -->
					
					<div class="testimonial" style="z-index:10; height:1200px;">
						<h3>各类安全事件统计</h3>
						<div class="bor hidden-xs"></div>
                        <br>
                            <!-- 一天的柱状图的Dom -->

							<div style="width: 810px;height:490px; float:left">
						    <jsp:include page="echarts/one_day_barchart.jsp"/>
							</div>
                            <!-- 饼状图的Dom -->

							<div style="width: 650px;height:405px; float:left">
						    <jsp:include page="echarts/a_piecharst.jsp"/>
							</div>
                            <div style="clear:both;"></div><!-- 清除float属性 -->
                            <!-- 一周柱状图的Dom -->

							<div style="width: 810px; height:540px; margin-top:40px;float:left">
						    <jsp:include page="echarts/a_week_barchart_heng.jsp"/>
							</div>
                            <!-- 各类事件说明框 -->
                     <div class="contact_1">         
                     <div class="contact-details_1" style="height:530px; width:700px; float:right; z-index:10;">
					<h4>各类安全事件介绍</h4>
						<span><a href="http://baike.baidu.com/link?url=8pbENiNqNnKClrLzRtp3Tb7ijYO4Nje32fNf9lrNbSfLMLeeCoDnNBTn3WSHPDppw64_bawyOIM9a1xFueAWK_" target="_blank"><h5>SQL注入攻击</h5></a></span>
						<p>  SQL注入攻击是黑客对数据库进行攻击的常用手段。由于程序员在编写代码的时候，没有对用户输入数据的合法性进行判断，
						使应用程序存在安全隐患。用户可以提交一段数据库查询代码，根据程序返回的结果获得某些他想得知的数据，这就是所谓的SQL
						 Injection，即SQL注入。</p>
						
						<span><a href="http://baike.baidu.com/link?url=lsrSxSNK1MvxBCwcp8T0PQ97KG2suILo24EWkLsw2_f1x3LO8rnawRplbRSTuqRzsj9nWTUbW-srsAQsA-MI5_" target="_blank"><h5>PHPwebshell后门</h5></a></span>
						<p>webshell就是以asp、php、jsp或者cgi等网页文件形式存在的一种命令执行环境，也可以将其称做为一种网页后门。黑客在
						入侵了一个网站后，通常会将asp或php后门文件与网站服务器WEB目录下正常的网页文件混在一起，然后就可以使用浏览器来访问
						asp或者php后门，得到一个命令执行环境，以达到控制网站服务器的目的。      </p>
						
						<span><a href="http://baike.baidu.com/link?url=Zlwmh8ehLL3QWwK_JLQzUw7NyKHXUs0Mke7UO11iS1H0hYsv_TjWFBrCxg8FBPIAQsMYJCQjnrBhal9JRq7vuK" target="_blank"><h5>Exploit漏洞</h5></a></span>
						<p>Exploit意思就是利用，它在黑客眼里就是漏洞利用。有漏洞不一定就有Exploit（利用)。有Exploit就肯定有漏洞。</p>
						
						<span><a href="http://baike.baidu.com/link?url=7Cb7ZHlV_xxhoEfhu3kHSzYvEhATocwGUDTjPrIGj6FpkmJdpHrm3mdSTGu988eitRYdid4LRj1CtaGfF5Kgla" target="_blank"><h5>Dos攻击</h5></a></span>
						<p>DoS是Denial of Service的简称，即拒绝服务。DoS攻击会故意的攻击网络协议实现的缺陷或直接通过野蛮手段残忍
						地耗尽被攻击对象的资源，目的是让目标计算机或网络无法提供正常的服务或资源访问，使目标系统服务系统停止响应
						甚至崩溃，而在此攻击中并不包括侵入目标服务器或目标网络设备。这些服务资源包括网络带宽，文件系统空间容量，
						开放的进程或者允许的连接。这种攻击会导致资源的匮乏，无论计算机的处理速度多快、内存容量多大、网络带宽的速度
						多快都无法避免这种攻击带来的后果。</p>
					</div></div>
					</div>

				</div>
				<hr />
					
					<!-- 流量分析柱状图 End -->
					
					<!-- 安全势态折线图 Start -->
					
					<div class="ptable" style="z-index:10; height:1300px;">
						<h3>安全势态折线图</h3>
                        <div class="bor hidden-xs"></div>
                        <div style="width: 820px; height:1200px; float:left;">
                            <!-- 一天的柱状图的Dom -->
							<div style="width: 800px;height:540px;float:right;">
						    <jsp:include page="echarts/one_day_linchart.jsp"/>
							</div>
                            <!-- 一周柱状图的Dom -->
							<div style="width: 800px;height:540px; margin-top:50px;float:right;">
						    <jsp:include page="echarts/a_week_linchart.jsp"/>
							</div>
                        </div>
                        
                        <!--四个仪表盘--> 
                        <div style="float:left;">
							<table>
						<!--<div id="echarts_meter1" style="width:450px; height:300px;">-->
                            <tr><td>
							<div style="width:450px; height:300px;">
						    <jsp:include page="echarts/meter_for_linchart1.jsp"/>
							</div>
							</td></tr>
							<tr><td>
							<div style="width:450px; height:300px;">
						    <jsp:include page="echarts/meter_for_linchart2.jsp"/>
							</div>
							</td></tr>
							<tr><td>
							<div style="width:450px; height:300px;">
						    <jsp:include page="echarts/meter_for_linchart3.jsp"/>
							</div>
							</td></tr>
							<tr><td>
							<div style="width:450px; height:300px;">
						    <jsp:include page="echarts/meter_for_linchart4.jsp"/>
							</div>
							</td></tr>
                            </table>
                            </div>
                            <div style="clear:both;"></div><!--取消上面的float-->
				<hr /></div>
					<!-- 安全势态折线图 End -->
					
					
					<!-- 安全势态预测 Start -->
		<div class="contact" style="height:1700px;">
			<h3>安全势态评估预测</h3>
			<div class="bor hidden-xs"></div>
			<div style="width: 860px;height:510px;margin:0 auto;">
						    <jsp:include page="echarts/day_estimate.jsp"/>
							</div>
		<div class="contact-details" style="height:400px; width:100%;">
		<h4>安全评估报告</h4>
		<p>通过T-S模型得到今日态势情况：</p>
		<p>威胁态势值：<%=Assets_sum %>，脆弱态势值：<%=Risk_sum %>，稳定态势值：<%=Threaten_sum %>，容灾态势值：<%=Positive_sum %>，网络总体态势值：<%=All_sum %></p>
		<p>因此依据网络安全总体级别表可知，网络状态为“<%=ScurityLevel%>”，网络等级为“<%=EvaluateLevel %>”。</p>
		<br>
		<p>威胁态势为“<%=AssetsLevel %>”，脆弱态势为“<%=RiskLevel %>”，稳定态势为“<%=ThreatenLevel %>”，容灾态势为“<%=PositiveLevel %>”， </p>
		<p>依据专家经验等级矩阵可知，网络状态为“<%=ScurityLevel %>”。</p>
		
		</div>
		
		<div style="clear:both;"></div><!--取消上面的float-->
		<div style="width: 800px;height:540px;margin: 0 auto;margin-top:30px;">
						    <jsp:include page="echarts/week_forecast.jsp"/>
							</div>
		<div class="contact-details" style="height:400px; width:100%; ">
		<h4>网络安全预测分析</h4>
		<% if(Forecast.WeekForecast()>0&&Forecast.WeekForecast()<2.5){%>
		<p>ISVM对网络安全态势进行相对准确的预测，从该周态势对比图中还可以看出在该周内，安全态势值较平时都属于标注值之内，请注意网络攻击，积极维护管理以保持良好的网络安全环境。</p>
		<%} 
		else if(Forecast.WeekForecast()>=2.5&&Forecast.WeekForecast()<5.5){%>
		<p>ISVM对网络安全态势进行相对准确的预测，从该周态势对比图中还可以看出在该周内，安全态势值较平时都高，网络管理和维护人员应及时加强网络防护。</p>
		<% }else{%>
<!-- 		<p>平均值为：<%--<%=Forecast.WeekForecast() %>--%></p> -->
		<p>ISVM对网络安全态势进行相对准确的预测，从该周态势对比图中还可以看出在该周内，安全态势值很高，网络处于危险状态！管理人员需要采取安全措施对网络进行维护！</p>
		<%} %>

		</div>
		</div>
<!-- 安全势态预测 End -->
					
					
					<!-- 注脚 -->
				<footer>
					<div class="row">
						<div class="col-md-12">
							<hr />
							<br />
							<!-- You should not remove the footer link back. -->
							<p class="text-center" align="center"><span class="login-buttom-txt" style="font-family:黑体; color:#979797; font-weight:900;">
								<a href="#top">Copyright &copy; 2016 安全态势感知系统小组</a></span></p>
							<br />
						</div>
					</div>
				</footer>
				
				
				
				
				
			</div>	<!-- This end div for main class -->
		</div>
		
		<div class="clearfix"></div>
		
		
		<!-- Scroll to top -->
      <span class="totop"><a href="#"><i class="icon-chevron-up"></i></a></span> 

		
		<!-- Javascript files -->
		
		<!-- jQuery -->
		<script src="js/jquery.js"></script>
		<!-- Bootstrap JS -->
		<script src="js/bootstrap.min.js"></script>
		<!-- jquery Anchor JS -->
		<script src="js/jquery.arbitrary-anchor.js"></script>
		<!-- jQuery way points -->
		<script src="js/waypoints.min.js"></script>
		<!-- Respond JS for IE8 -->
		<script src="js/respond.min.js"></script>
		<!-- HTML5 Support for IE -->
		<script src="js/html5shiv.js"></script>
		<!-- Custom JS -->
		<script src="js/custom.js"></script>
	</body>	
</html>