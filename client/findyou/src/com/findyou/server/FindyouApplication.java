/**
 * 
 */
package com.findyou.server;

import android.app.Application;

/**
 * @author Administrator
 *
 */
public class FindyouApplication extends Application {

	public String friendPhoneNum;

	/**
	 * @return the friendPhoneNum
	 */
	public String getFriendPhoneNum() {
		return friendPhoneNum;
	}

	/**
	 * @param friendPhoneNum the friendPhoneNum to set
	 */
	public void setFriendPhoneNum(String friendPhoneNum) {
		this.friendPhoneNum = friendPhoneNum;
	}
	
	
}
