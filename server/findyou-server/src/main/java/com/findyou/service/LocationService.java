/**
 * 
 */
package com.findyou.service;

import com.findyou.dao.LocationDao;
import com.findyou.model.LocationInfo;

/**
 * 
 * @author liaohongliu
 *
 * 创建日期:2013-7-9 下午9:39:25
 */
public class LocationService {

	public LocationDao locationDao = new LocationDao();
	
	public LocationInfo getLocationInfo(long userId) {
		return locationDao.getLocationInfo(userId);
	}
	
	public boolean saveLocationInfo(long userId, double latitude, double lontitude, float radius, String addr) {
		LocationInfo info = new LocationInfo();
		info.setUserId(userId + "");
		info.setLatitude(latitude);
		info.setLontitude(lontitude);
		info.setRadius(radius);
		info.setAddr(addr);
		return locationDao.saveLocationInfo(info);
	}
}
