package nssa.ad.message;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ADMessage {
	private String clientType;
	private String clientNo;
	private String clientIP;
	private String name;
	private String domain;
	private String os;
	private String cpu;
	private String memory;
	private String disk;
	private String detail;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getCpu() {
		return cpu;
	}
	public void setCpu(String cpu) {
		this.cpu = cpu;
	}
	public String getMemory() {
		return memory;
	}
	public void setMemory(String memory) {
		this.memory = memory;
	}
	public String getDisk() {
		return disk;
	}
	public void setDisk(String disk) {
		this.disk = disk;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public ADMessage() {
		InetAddress addr;
		String ip = null;
		try {
			addr = InetAddress.getLocalHost();
			ip=addr.getHostAddress().toString();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		this.setClientType("AD");
		this.setClientNo("");
		this.setClientIP(ip);
	}
	@Override
	public String toString() {
		return "ADMessage [clientType=" + clientType + ", clientNo=" + clientNo
				+ ", clientIP=" + clientIP + ", name=" + name + ", domain="
				+ domain + ", os=" + os + ", cpu=" + cpu + ", memory=" + memory
				+ ", disk=" + disk + ", detail=" + detail + "]";
	}
	
}
