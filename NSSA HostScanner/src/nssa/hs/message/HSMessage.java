package nssa.hs.message;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.sun.http.HttpRequest;

import net.sf.json.JSONObject;

public class HSMessage {
	private String clientType;
	private String clientNo;
	private String clientIP;
	private String ip;
	private String ports;
	public static String HostAddress="http://127.0.0.1:8080/";

	public String getClientType() {
		return clientType;
	}
	public void setClientType(String clientType) {
		this.clientType = clientType;
	}
	public String getClientNo() {
		return clientNo;
	}
	public void setClientNo(String clientNo) {
		this.clientNo = clientNo;
	}
	public String getClientIP() {
		return clientIP;
	}
	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPorts() {
		return ports;
	}
	public void setPorts(String ports) {
		this.ports = ports;
	}
	public HSMessage() {
		InetAddress addr;
		String ip = null;
		try {
			addr = InetAddress.getLocalHost();
			ip=addr.getHostAddress().toString();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setClientType("HS");
		this.setClientNo("");
		this.setClientIP(ip);
		
		JSONObject obj= new JSONObject();
		obj.put("ports",ports);	
		//System.out.println("ports="+ports);
		obj.put("ip",ip );
		obj.put("clientType",getClientType());
		obj.put("clientNo",clientNo );
		obj.put("clientIP",clientIP );
		//obj.put("HSMessage",getJsonStringFromObject() );
		
		String objStr=obj.toString();
		
		//***************************
//     	HttpRequest.sendPost(HostAddress+"TestXinanServer/Test", "json="+objStr);
//		System.out.println("\n∑¢ÀÕ GET «Î«Û******"+DetectCore.name()+"*********\n");
	}
	private Object getJsonStringFromObject() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String toString() {
//		return "HSMessage [clientType=" + clientType + ", clientNo=" + clientNo
//				+ ", clientIP=" + clientIP + ", ip=" + ip + ", ports=" + ports
//				+ "]";
		String str=null;
		
		JSONObject obj= new JSONObject();
		obj.put("ports",ports);	
		//System.out.println("ports="+ports);
		obj.put("ip",ip );
		obj.put("clientType",getClientType());
		obj.put("clientNo",clientNo );
		obj.put("clientIP",clientIP );
		//obj.put("HSMessage",getJsonStringFromObject() );
		
		str=obj.toString();
     	//HttpRequest.sendPost(HostAddress+"TestXinanServer/Test", "json="+objStr);
		
     	return str;
	}
	
}
	
