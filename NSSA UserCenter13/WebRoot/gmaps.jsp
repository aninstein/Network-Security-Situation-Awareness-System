<%@ page language="java" import="java.util.*,nssa.uc.util.GoogleMapUtil" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%
	//String ip = "59.67.148.66";
	String ip = request.getParameter("ip");
	//if(ip==null)
	//{out.print("<form action='gmaps.jsp/'' >请输入ip地址：<input type='text' name='ip'><input type='submit'></form>");}
	String address = GoogleMapUtil.ip2addr(ip);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>IP定位</title>
<script
	src="http://ditu.google.com/maps?file=api&v=2&sensor=true&key=ABQIAAAAR6GoO24jAZ-_eNEXBZw6XBTwM0brOpm-All5BF6PoaKBxRWWERTbkshwGU9qjMeklCXg-txjqFke9A"
	type="text/javascript"></script>
<script type="text/javascript">
	var map;

	function locate(address) {
		var geocoder = new GClientGeocoder();
		geocoder.getLatLng(address, function(point) {
			if (!point) {
				alert(address + "not found");
			} else {
				map.panTo(point);
				if (marker) {
					map.closeInfoWindow();
					map.removeOverlay(marker);
				}

				var marker = new GMarker(point);

				var div = document.createElement("div");
				div.style.color = "red";
				div.innerHTML = address;

				marker.openInfoWindow(div);

				map.addOverlay(marker);
			}
		});
	}

	function load() {
		if (GBrowserIsCompatible()) {
			map = new GMap2(document.getElementById("map"));
			map.addControl(new GSmallMapControl());
			map.addControl(new GMapTypeControl());
			map.addControl(new GScaleControl());

			locate('<%= address%>');

			map.setCenter(new GLatLng(108,34), 16);
		}
	}
</script>
</head>
<body onload="load()" onunload="GUnload()">
	<h3>ip地址定位：<%=ip%></h3>
	<div id="map" style="width:1000px;height:500px"></div>
</body>
</html>
