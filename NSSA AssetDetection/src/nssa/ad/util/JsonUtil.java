package nssa.ad.util;

import net.sf.json.JSONObject;

public class JsonUtil {  

    public static Object getObjectFromJsonString(String jsonString,  
            Class<?> objCalss) {  
        Object obj;  
        JSONObject jsonObject = JSONObject.fromObject(jsonString);  
        obj = JSONObject.toBean(jsonObject, objCalss);  
        return obj;  
    }  
  
    public static String getJsonStringFromObject(Object obj) {
        JSONObject jsonObj;
        jsonObj = JSONObject.fromObject(obj);
        return jsonObj.toString();
    }
    
}