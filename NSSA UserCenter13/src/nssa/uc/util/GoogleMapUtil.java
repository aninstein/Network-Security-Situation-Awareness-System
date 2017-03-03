package nssa.uc.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;

public class GoogleMapUtil {


	public static String ip2addr(String ip) throws Exception {
		
		DefaultHttpClient httpclient = new DefaultHttpClient();
		String html = "";

		try {
			HttpGet httpget = null;
			httpget = new HttpGet("http://www.ip138.com/ips138.asp?ip=" + ip + "&action=2");
			HttpResponse response = httpclient.execute(httpget);			
			HttpEntity entity = response.getEntity();

			html = EntityUtils.toString(entity,"GB2312");
			EntityUtils.consume(entity);

		} catch (IOException e) {
			throw e;
		} finally {
			httpclient.getConnectionManager().shutdown();
		}

        Parser myParser =Parser.createParser(html, "gb2312");
        NodeFilter filter =new TagNameFilter ("li");
        NodeList nodeList =myParser.parse(filter);

        String result = nodeList.elementAt(0).toPlainTextString();
        String address = result.split("£∫")[1];
        
        
		return address;

	}
	
	public static void main(String[] args) {
		try {
			System.out.println(ip2addr("59.67.148.66"));
		} catch (Exception e) {
			System.out.println("Õ¯¬Á“Ï≥£");
		}
	}

}
