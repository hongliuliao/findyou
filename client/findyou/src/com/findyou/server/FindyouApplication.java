/**
 * 
 */
package com.findyou.server;

import android.app.Application;
import android.content.Context;
import android.telephony.TelephonyManager;

import com.baidu.mapapi.BMapManager;
import com.findyou.model.UserInfo;
import com.findyou.service.PhoneService;
import com.findyou.utils.BMapUtil;
import com.findyou.utils.DatabaseConfigUtil;
import com.findyou.utils.StringUtils;


/**
 * @author Administrator
 *
 */
public class FindyouApplication extends Application {

	public String friendPhoneNum;
	
	private String friendName;
	
	public BMapManager mBMapManager;
	
	private volatile boolean isRequest;
	
	private boolean started;
	
	@Override
	public void onCreate() {
		super.onCreate();
		DatabaseConfigUtil.initDatabase(this);// 初始化数据库
		mBMapManager = BMapUtil.initBMapManager(this);
	}
	
	/**
	 * @return the started
	 */
	public boolean isStarted() {
		return started;
	}

	/**
	 * @param started the started to set
	 */
	public void setStarted(boolean started) {
		this.started = started;
	}

	/**
	 * @return the isRequest
	 */
	public boolean isRequest() {
		return isRequest;
	}

	/**
	 * @param isRequest the isRequest to set
	 */
	public void setRequest(boolean isRequest) {
		this.isRequest = isRequest;
	}

	/**
	 * @return the mBMapManager
	 */
	public BMapManager getmBMapManager() {
		return mBMapManager;
	}

	/**
	 * @return the friendName
	 */
	public String getFriendName() {
		return friendName;
	}

	/**
	 * @param friendName the friendName to set
	 */
	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}

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
		UserInfo userInfo = new PhoneService().getUserInfo(1);
		if(userInfo == null) {
			return null;
		}
		return userInfo.getPhoneNumber();// 1表示手机号
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
		myPhoneNum = StringUtils.filterPhoneNumber(myPhoneNum);
		UserInfo userInfo=new UserInfo();
		userInfo.setPhoneNumber(myPhoneNum);
		new PhoneService().saveUserInfo(userInfo);
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
