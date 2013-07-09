/**
 * 
 */
package com.findyou.dao;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.findyou.model.LocationInfo;

/**
 * 
 * @author liaohongliu
 *
 * 创建日期:2013-7-9 下午9:26:01
 */
public class LocationDao {

	public static Map<Long, LocationInfo> LOCATION_MAP = new ConcurrentHashMap<Long, LocationInfo>();
	
	static {
		LocationInfo info = new LocationInfo();
		info.setUserId(15801182328L + "");
		info.setLatitude(39.99020004272461);
		info.setLontitude(116.31400299072266);
		info.setRadius(41.35710144042969f);
		info.setAddr("北京市海淀区海淀西大街48号");
		LOCATION_MAP.put(Long.parseLong(info.getUserId()), info);
	}
	
	public LocationInfo getLocationInfo(long userId) {
		return LOCATION_MAP.get(userId);
	}
	
	public boolean saveLocationInfo(LocationInfo info) {
		LOCATION_MAP.put(Long.parseLong(info.getUserId()), info);
		return true;
	}
}
