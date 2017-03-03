package nssa.hs.message;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.sun.http.HttpRequest;

import nssa.hs.gui.MainForm;
import nssa.hs.util.Base64Util;
import nssa.hs.util.JsonUtil;

public class MessageCenter {

	public static String HostAddress="http://127.0.0.1:8080/";
	public static void sendMessage(HSMessage message) {
		
		String jsonstr = JsonUtil.getJsonStringFromObject(message);
		String base64str = Base64Util.encodeStr(jsonstr);
		
		MainForm.ports=message.getPorts().substring(0, message.getPorts().length()-1)+"("+MainForm.ports+")";
		MainForm.InsertDataToDB();
		
		System.out.println("message="+message);
		System.out.println("base64str="+base64str);
		
//*************************	
//		HttpRequest.sendPost(HostAddress+"TestXinanServer/Test", "json="+message);
//		HttpRequest.sendPost(HostAddress+"TestXinanServer/Test", "json="+base64str);
		
		
		
//		System.out.println(jsonstr);
//		System.out.println(base64str);
		//MessageSocket socket = new MessageSocket();
		//socket.connect();
		//socket.send(base64str);
//		JSONObject obj= new JSONObject();
//		obj.put("ports",ports );	
//		obj.put("ip",ip );
//		obj.put("clientType",getClientType());
//		obj.put("clientNo",clientNo );
//		obj.put("clientIP",clientIP );
		
		
//		String objStr=obj.toString();
//    	HttpRequest.sendPost(HostAddress+"TestXinanServer/Test", "json="+objStr);
	}
	static String time;
	public static Map toMap(String jsonString) throws JSONException {
		JSONObject jsonObject = new JSONObject(jsonString);
        Map result = new HashMap();
        Iterator iterator = jsonObject.keys();
        String key = null;
        String value = null;
        
        Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		time=format.format(date);
       
        
        
        

        while (iterator.hasNext()) {

            key = (String) iterator.next();
            value = jsonObject.getString(key);
            result.put(key, value);

        }
        return result;
	}
	
}
