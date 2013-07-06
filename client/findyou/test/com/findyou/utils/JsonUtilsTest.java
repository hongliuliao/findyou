/**
 * 
 */
package com.findyou.utils;

import android.test.AndroidTestCase;

import com.findyou.model.CodeMsg;
import com.findyou.model.LocationInfo;

/**
 * @author Administrator
 *
 */
public class JsonUtilsTest extends AndroidTestCase {

	public void testToCodeMsg() {
		CodeMsg codeMsg = JsonUtils.toCodeMsg("{'code':0,'msg':'success'}");
		assertTrue(codeMsg.getCode() == 0);
	}
	
	public void testToLocationInfo() {
		LocationInfo info = JsonUtils.toLocationInfo("{	'id':11,'userId':'19886633','latitude':22.1,'lontitude':23.4,'radius':23.6,'addr':'ÖÐ¹ú'}");
		assertTrue(info != null);
	}
}
