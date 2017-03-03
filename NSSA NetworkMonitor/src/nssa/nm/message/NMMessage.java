package nssa.nm.message;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NMMessage {
	private String clientType;
	private String clientNo;
	private String clientIP;
	private String msg;
	private String sip;
	private String sport;
	private String dip;
	private String dport;
	private String packet;
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
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getSip() {
		return sip;
	}
	public void setSip(String sip) {
		this.sip = sip;
	}
	public String getSport() {
		return sport;
	}
	public void setSport(String sport) {
		this.sport = sport;
	}
	public String getDip() {
		return dip;
	}
	public void setDip(String dip) {
		this.dip = dip;
	}
	public String getDport() {
		return dport;
	}
	public void setDport(String dport) {
		this.dport = dport;
	}
	public String getPacket() {
		return packet;
	}
	public void setPacket(String packet) {
		this.packet = packet;
	}
	public NMMessage() {
		InetAddress addr;
		String ip = null;
		try {
			addr = InetAddress.getLocalHost();
			ip=addr.getHostAddress().toString();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setClientType("NM");
		this.setClientNo("");
		this.setClientIP(ip);
	}
	@Override
	public String toString() {
		return "NMMessage [clientType=" + clientType + ", clientNo=" + clientNo
				+ ", clientIP=" + clientIP + ", msg=" + msg + ", sip=" + sip
				+ ", sport=" + sport + ", dip=" + dip + ", dport=" + dport
				+ ", packet=" + packet + "]";
	}
	
}
