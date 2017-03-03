package nssa.hs.scanner;

import java.io.IOException;
import java.net.Socket;

import nssa.hs.message.HSMessage;
import nssa.hs.message.MessageCenter;

public class ScannerCore {
	
	public void scan(String sip, String eip, int sport, int eport) {
		
		StringBuilder ports = new StringBuilder(); 
		String[] sips = sip.split("\\.");
		String[] eips = eip.split("\\.");
		for(int ip0 = Integer.parseInt(sips[0]); ip0 <= Integer.parseInt(eips[0]); ip0++) {
			for(int ip1 = Integer.parseInt(sips[1]); ip1 <= Integer.parseInt(eips[1]); ip1++) {
				for(int ip2 = Integer.parseInt(sips[2]); ip2 <= Integer.parseInt(eips[2]); ip2++) {
					for(int ip3 = Integer.parseInt(sips[3]); ip3 <= Integer.parseInt(eips[3]); ip3++) {
						String ip = ip0 + "." + ip1 + "." + ip2 + "." +ip3;
						for(int port = sport; port <= eport; port++) {
								Socket s;
								try {
									s = new Socket(ip,port);
									s.close();
									ports.append(String.valueOf(port) + ",");
								} catch (IOException e) {
									continue;
									//e.printStackTrace();
								}
						}
						HSMessage msg = new HSMessage();
						msg.setIp(ip);
						msg.setPorts(ports.toString());
						MessageCenter.sendMessage(msg);
						
					}
				}
			}
		}
	}


}
