package nssa.nm.capture;

import java.util.ArrayList;
import java.util.List;

import org.jnetpcap.nio.JBuffer;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.protocol.network.Icmp;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.jnetpcap.protocol.tcpip.Udp;
import org.json.JSONException;

import nssa.nm.message.MessageCenter;
import nssa.nm.message.NMMessage;
import nssa.nm.vo.Rule;

public class PacketMatch {
	
	private static PacketMatch pm;

	private Ip4 ip = new Ip4();
	private Icmp icmp = new Icmp();
	private Tcp tcp = new Tcp();
	private Udp udp = new Udp();
	
	private List<Rule> icmpRules;
	private List<Rule> tcpRules;
	private List<Rule> udpRules;
	
	private NMMessage message;
	
	public static PacketMatch getInstance() {
		if (pm == null) {
			pm = new PacketMatch();
		}
		return pm;
	}
	
	public void loadRules() {
		/*RuleDao dao = new RuleDao();
		try {
			icmpRules = dao.list("icmp");
			tcpRules = dao.list("tcp");
			udpRules = dao.list("udp");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,e.toString(),"´íÎó",JOptionPane.ERROR_MESSAGE);
		}*/
		icmpRules = new ArrayList<Rule>();
		tcpRules = new ArrayList<Rule>();
		udpRules = new ArrayList<Rule>();
	}
	
	public void handlePacket(PcapPacket packet) {
		message = new NMMessage();
		if (packet.hasHeader(ip)) {
			handleIp(packet);
		}
		if (packet.hasHeader(icmp)) {
			handleIcmp(packet);

		}
		if (packet.hasHeader(tcp)) {
			handleTcp(packet);
		}
		if (packet.hasHeader(udp)) {
			handleUdp(packet);
		}
	}
	
	private void handleIp(PcapPacket packet) {
		packet.getHeader(ip);
		byte[] sIP = new byte[4], dIP = new byte[4];
		sIP = ip.source();
		dIP = ip.destination();
		String srcIP = org.jnetpcap.packet.format.FormatUtils.ip(sIP);
		String dstIP = org.jnetpcap.packet.format.FormatUtils.ip(dIP);
		message.setSip(srcIP);
		message.setDip(dstIP);
	}
	
	private void handleIcmp(PcapPacket packet) {
		packet.getHeader(icmp);
		
		byte[] buff = new byte[packet.getTotalSize()];
		packet.transferStateAndDataTo(buff);
		JBuffer jb = new JBuffer(buff);
		String content = jb.toHexdump();
		//for(int i = 0; i < icmpRules.size(); i++) {
			//if(content.contains(icmpRules.get(i).getContent())) {
				//message.setMsg(icmpRules.get(i).getMsg());
				message.setPacket(content);
				sendMessage();
			//}
		//}
	}
	
	private void handleTcp(PcapPacket packet) {
		packet.getHeader(tcp);
		String srcPort = String.valueOf(tcp.source());
		String dstPort = String.valueOf(tcp.destination());
		message.setSport(srcPort);
		message.setDport(dstPort);
		
		byte[] buff = new byte[packet.getTotalSize()];
		packet.transferStateAndDataTo(buff);
		JBuffer jb = new JBuffer(buff);
		String content = jb.toHexdump();
		//for(int i = 0; i < tcpRules.size(); i++) {
			//if(content.contains(tcpRules.get(i).getContent())) {
				//message.setMsg(tcpRules.get(i).getMsg());
				message.setPacket(content);
				sendMessage();
			//}
		//}
	}
	
	private void handleUdp(PcapPacket packet) {
		packet.getHeader(udp);
		String srcPort = String.valueOf(udp.source());
		String dstPort = String.valueOf(udp.destination());
		message.setSport(srcPort);
		message.setDport(dstPort);
		
		byte[] buff = new byte[packet.getTotalSize()];
		packet.transferStateAndDataTo(buff);
		JBuffer jb = new JBuffer(buff);
		String content = jb.toHexdump();
		//for(int i = 0; i < udpRules.size(); i++) {
			//if(content.contains(udpRules.get(i).getContent())) {
				//message.setMsg(udpRules.get(i).getMsg());
				message.setPacket(content);
				sendMessage();
			//}
		//}
	}
	
	private void sendMessage() {
		try {
			MessageCenter.sendMessage(message);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
