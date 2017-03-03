package nssa.uc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpRetryException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.sun.mail.iap.Response;
public class BaiduMapIpAdress {

	

	public static String ip2addr(String Ip) throws IOException, ParserException {
		String AdressOfIp = null;
		try{
		    long begintime = System.currentTimeMillis();
//	        String Ip="142.122.253.18";
	        URL url = new URL("http://test.ip138.com/query/?ip="+Ip+"&datatype=text");
	        HttpURLConnection urlcon = (HttpURLConnection)url.openConnection();
	        urlcon.connect();       
	        InputStream is = urlcon.getInputStream();
	        BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
	        StringBuffer bs = new StringBuffer();
	        String l = null;
	        while((l=buffer.readLine())!=null){
	            bs.append(l);
	        }
	       
			
			AdressOfIp=bs.toString();
	        AdressOfIp=AdressOfIp.substring(0,0)+AdressOfIp.substring(14,AdressOfIp.length());

	        }catch(IOException e){
//	       System.out.println(e);	        
	        e.printStackTrace();
	        }
		return AdressOfIp;
		}
}
