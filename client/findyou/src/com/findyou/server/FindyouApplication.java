/**
 * 
 */
package com.findyou.server;

import com.findyou.service.UserService;

import android.app.Application;

/**
 * @author Administrator
 *
 */
public class FindyouApplication extends Application {

	private UserService userService = new UserService();
	
	public String friendPhoneNum;
	
	private String myPhoneNum;

	/**
	 * @return the myPhoneNum
	 */
	public String getMyPhoneNum() {
		// TODO ��userService�л�ȡ����
		return myPhoneNum;
	}

	public boolean hasMyPhoneNum() {
		String num = getMyPhoneNum();
		return num != null && !num.trim().equals("");
	}
	
	/**
	 * @param myPhoneNum the myPhoneNum to set
	 */
	public void setMyPhoneNum(String myPhoneNum) {
		this.myPhoneNum = myPhoneNum;
	}

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
