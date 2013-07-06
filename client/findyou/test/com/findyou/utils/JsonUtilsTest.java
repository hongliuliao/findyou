/**
 * 
 */
package com.findyou.utils;

import android.test.AndroidTestCase;

import com.findyou.model.CodeMsg;

/**
 * @author Administrator
 *
 */
public class JsonUtilsTest extends AndroidTestCase {

	public void testToCodeMsg() {
		CodeMsg codeMsg = JsonUtils.toCodeMsg("{'code':0,'msg':'success'}");
		assertTrue(codeMsg.getCode() == 0);
	}
}
