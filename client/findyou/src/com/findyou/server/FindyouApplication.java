/**
 * 
 */
package com.findyou.server;

import android.app.Application;
import android.content.Context;
import android.telephony.TelephonyManager;

import com.findyou.service.PhoneService;
import com.findyou.utils.StringUtils;

import domain.businessEntity.userinfo.UserInfo;

/**
 * @author Administrator
 *
 */
public class FindyouApplication extends Application {

	private PhoneService phoneService = new PhoneService();
	
	public String friendPhoneNum;
	
	private String myPhoneNum;

	/**
	 * @return the myPhoneNum
	 */
	public String getMyPhoneNum() {
		if(StringUtils.isNotBlank(myPhoneNum)) {
			return myPhoneNum;
		}
		String myPhoneNum = getMyTelPhoneNumber();
		if(StringUtils.isNotBlank(myPhoneNum)) {
			return myPhoneNum;
		}
		return this.phoneService.getUserInfo(1).getPhoneNumber();// 1表示手机号
	}
	
	private String getMyTelPhoneNumber() {
		TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getLine1Number();
	}

	public boolean hasMyPhoneNum() {
		String num = getMyPhoneNum();
		return num != null && !num.trim().equals("");
	}
	
	/**
	 * @param myPhoneNum the myPhoneNum to set
	 */
	public void setMyPhoneNum(String myPhoneNum) {
		UserInfo userInfo=new UserInfo();
		userInfo.setPhoneNumber(myPhoneNum);
		phoneService.saveUserInfo(userInfo);
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
