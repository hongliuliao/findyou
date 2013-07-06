/**
 * 
 */
package com.findyou.service;

import android.test.AndroidTestCase;

/**
 * @author Administrator
 *
 */
public class UserServiceTest extends AndroidTestCase {

	UserService userService = new UserService();
	
	public void testSaveUser() {
		String id = userService.saveUser("13850878345");
		assertTrue(id != null);
	}
	
	public void testGetUserId() {
		String id = this.userService.getUserId("13850878345");
		assertTrue(id != null);
	}
}
