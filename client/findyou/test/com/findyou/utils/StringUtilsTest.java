/**
 * 
 */
package com.findyou.utils;

import android.test.AndroidTestCase;

/**
 * @author Administrator
 *
 */
public class StringUtilsTest extends AndroidTestCase {

	public void testFilterPhoneNumber() {
		String phoneNumber = StringUtils.filterPhoneNumber("111 33333 454");
		assertTrue(phoneNumber.equals("11133333454"));
		
		phoneNumber = StringUtils.filterPhoneNumber("+86111 33333 454");
		assertTrue(phoneNumber.equals("11133333454"));
		
		phoneNumber = StringUtils.filterPhoneNumber("+86111 33863 454");
		assertTrue(phoneNumber.equals("11133863454"));
	}
}
