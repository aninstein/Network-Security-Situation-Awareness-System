package nssa.hs.util;

public class IPUtil {
	
	public static boolean compare(String startIp,String endIp) {
		String[] startIps=startIp.split("\\.");
		String[] endIps=endIp.split("\\.");
		for(int i=0;i<4;i++){
			if(Integer.parseInt(startIps[i])>Integer.parseInt(endIps[i])){
				return false;
			}
		}
		return true;
	}
	
	public static boolean valid(String ip) {
		String ipreg =  "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
		if(ip.matches(ipreg)) {
			return true;
		} else {
			return false;
		}
	}
}
