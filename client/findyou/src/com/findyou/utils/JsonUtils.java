/**
 * 
 */
package com.findyou.utils;

import org.json.JSONObject;

import com.findyou.model.CodeMsg;

/**
 * @author Administrator
 *
 */
public class JsonUtils {

	public static CodeMsg toCodeMsg(String result) {
		try {
			JSONObject json = new JSONObject(result);
			return new CodeMsg(json.getInt("code"), json.getString("msg"));
		} catch (Exception e) {
			throw new RuntimeException("can not convert to codemsg which result:" + result, e);
		}
		
		
	}
}
