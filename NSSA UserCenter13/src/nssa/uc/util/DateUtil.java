package nssa.uc.util;

public class DateUtil {
	
	public static int getDay(String date) {
		String[] ss = date.split("[- :]");
		return Integer.parseInt(ss[2]);
	}
	
	public static int getHour(String date) {
		String[] ss = date.split("[- :]");
		return Integer.parseInt(ss[3]);
	}
}
