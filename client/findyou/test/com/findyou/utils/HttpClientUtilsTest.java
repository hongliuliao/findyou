/**
 * 
 */
package com.findyou.utils;

import java.util.HashMap;
import java.util.Map;

import android.test.AndroidTestCase;
import android.util.Log;

/**
 * @author Administrator
 *
 */
public class HttpClientUtilsTest extends AndroidTestCase {

	public HttpClientUtilsTest() {
		
	}
	
	public void testGetHttpGetResult() {
		String requestUrl = "https://raw.github.com/hongliuliao/findyou/master/client/findyou/test/getFriendInfo.json";
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", "111");
		params.put("userId", "222");
		String result = HttpClientUtils.getHttpGetResult(requestUrl, params);
		Log.i("test", result);
		assertTrue(result != null);
	}
}
