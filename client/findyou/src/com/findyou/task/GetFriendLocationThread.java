/**
 * 
 */
package com.findyou.task;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.findyou.data.FindyouConstants;
import com.findyou.model.GetFriendLocationResponse;
import com.findyou.model.LocationInfo;
import com.findyou.service.LocationService;

/**
 * @author Administrator
 *
 */
public class GetFriendLocationThread extends Thread {

	private LocationService locationService = new LocationService();
	
	private String phoneNumber;
	
	private Handler mHandler;
	
	/**
	 * @param phoneNumber
	 * @param mHandler
	 */
	public GetFriendLocationThread(String phoneNumber, Handler mHandler) {
		super();
		this.phoneNumber = phoneNumber;
		this.mHandler = mHandler;
	}

	@Override
	public void run() {
		GetFriendLocationResponse response = new GetFriendLocationResponse();
		try {
			LocationInfo info = locationService.getUserLocation(phoneNumber);
			if(info == null) {
				response.setCode(GetFriendLocationResponse.NOT_FOUND_CODE);
				response.setMsg("该好友信息不存在,请确定TA在线上!");
				this.mHandler.sendMessage(getMsg(response));
				return;
			}
			while(true) {
				response.setLatitude(info.getLatitude());
				response.setLontitude(info.getLontitude());
    			mHandler.sendMessage(getMsg(response));
    			Thread.sleep(60000);
			}
		} catch (Exception e) {
			Log.e("locationService", "getUserLocation error which phoneNumber:" + phoneNumber, e);
			response.setCode(FindyouConstants.EXCEPTION_CODE);
			response.setMsg("获取好友信息异常,请检查网络!ErrorMsg:" + e.getMessage());
			mHandler.sendMessage(getMsg(response));
			return;
		}
	}
	
	private Message getMsg(GetFriendLocationResponse response) {
		Message msg = new Message();
		msg.what = response.getId();
		msg.getData().putSerializable(FindyouConstants.RESULT_NAME, response);
		return msg;
	}
}
