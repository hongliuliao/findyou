/**
 * 
 */
package com.findyou.utils;

import org.json.JSONObject;

import com.findyou.model.CodeMsg;
import com.findyou.model.LocationInfo;

/**
 * @author Administrator
 *
 */
public class JsonUtils {

	public static CodeMsg toCodeMsg(String result) {
		try {
			if(result == null) {
				return new CodeMsg(1, "result is null");
			}
			JSONObject json = new JSONObject(result);
			return new CodeMsg(json.getInt("code"), json.getString("msg"));
		} catch (Exception e) {
			throw new RuntimeException("can not convert to codemsg which result:" + result, e);
		}
	}
	
	public static String getStringValue(String result, String key) {
		try {
			if(result == null) {
				return null;
			}
			JSONObject json = new JSONObject(result);
			return json.getString("id");
		} catch (Exception e) {
			throw new RuntimeException("getLongValue error which result:" + result, e);
		}
	}
	
	/**
	 * 转换json为对象
	 * @param jsonInfo LocationInfo json 形式 
	 * {	'id':11,
	 * 		'userId':'19886633'
	 * 		'latitude':22.1,
	 * 		'lontitude':23.4,
	 * 		'radius':23.6,
	 * 		'addr':'中国'
	 * }
	 * @return 转换后的 LocationInfo 对象 
	 */
	public static LocationInfo toLocationInfo(String jsonInfo) {
			try {
				JSONObject jsonObject = new JSONObject(jsonInfo);
				if(jsonObject.getInt("code") != 0) {
					return null;
				}
				// 取出数据来
				jsonObject = jsonObject.getJSONObject("data");
				LocationInfo info = new LocationInfo();
				
				info.setLatitude(jsonObject.getDouble("latitude"));
				info.setAddr(jsonObject.getString("addr"));
				info.setLontitude(jsonObject.getDouble("lontitude"));
				info.setRadius((float) jsonObject.getDouble("radius"));
				info.setUserId(jsonObject.getString("userId"));
				return info;
			} catch (Exception e) {
				throw new RuntimeException("can not convert to LocationInfo which result:" + jsonInfo, e);
			}
			
	}
	
}
