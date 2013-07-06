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
		String requestUrl = "https://raw.github.com/hongliuliao/findyou/master/client/findyou/test/saveMyInfo.json";
		Map<String, String> params = new HashMap<String, String>();
		String result = HttpClientUtils.getHttpGetResult(requestUrl, params);
		Log.i("test", result);
		assertTrue(result != null);
	}
}
