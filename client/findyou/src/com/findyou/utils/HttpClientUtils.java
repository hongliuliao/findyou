/**
 * 
 */
package com.findyou.utils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.util.Log;

/**
 * @author Administrator
 *
 */
public class HttpClientUtils {

	private static final String TAG = "HttpClient";
	
	public static String getHttpGetResult(String requestUrl, Map<String, String> inputParams) {
		List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();  
		for (Entry<String, String> e : inputParams.entrySet()) {
			params.add(new BasicNameValuePair(e.getKey(), e.getValue()));  
		}
		String param = URLEncodedUtils.format(params, "UTF-8");
		String resultUrl = requestUrl + "?" + param;
		HttpGet getMethod = new HttpGet(resultUrl);
		Log.i("HttpClient", "send http request which url:" + resultUrl);
		HttpClient httpClient = new DefaultHttpClient(); 
		try {  
		    HttpResponse response = httpClient.execute(getMethod); //发起GET请求  
		    if(response.getStatusLine().getStatusCode() != 200) { // success
		    	throw new RuntimeException("get http result error which url:" + resultUrl);
		    }
		    String result = EntityUtils.toString(response.getEntity(), "utf-8");
		    Log.i(TAG, "resCode = " + response.getStatusLine().getStatusCode()); //获取响应码  
		    Log.i(TAG, "result = " + result);//获取服务器响应内容
		    return result;
		} catch (Exception e) {  
			e.printStackTrace();
			return null;
		}
	} 
}
