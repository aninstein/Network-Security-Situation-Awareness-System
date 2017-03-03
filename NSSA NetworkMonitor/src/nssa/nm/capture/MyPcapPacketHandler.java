package nssa.nm.capture;

import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;

@SuppressWarnings("hiding")
public class MyPcapPacketHandler<Object> implements PcapPacketHandler<Object>  {
	
	@Override
	public void nextPacket(PcapPacket packet, Object obj) {
		PacketMatch packetMatch = PacketMatch.getInstance();
		packetMatch.handlePacket(packet);
	}
}
