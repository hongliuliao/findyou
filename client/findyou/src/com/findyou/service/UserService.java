/**
 * 
 */
package com.findyou.service;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.findyou.utils.HttpClientUtils;
import com.findyou.utils.JsonUtils;

/**
 * @author Administrator
 *
 */
public class UserService {

	private static final String SAVE_USER_URL = "http://iamhere1.duapp.com/saveUser.jsp";
	private static final String QUERY_USER_URL = "http://iamhere1.duapp.com/getUser.jsp";
	
	public String saveUser(String phoneNum) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("phoneNum", phoneNum);
		String result = HttpClientUtils.getHttpGetResult(SAVE_USER_URL, params);
		return JsonUtils.getStringValue(result, "id");
	}
	
	public String getUserId(String phoneNum) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("phoneNum", phoneNum);
		String result = HttpClientUtils.getHttpGetResult(QUERY_USER_URL, params);
		
		if(result == null || result.trim().equals("{}")) {
			return null;
		}
		try {
			JSONObject json = new JSONObject(result);
			return json.getString("id");
		} catch (Exception e) {
			throw new RuntimeException("getUserId error which phoneNum:" + phoneNum, e);
		}
	}
}
