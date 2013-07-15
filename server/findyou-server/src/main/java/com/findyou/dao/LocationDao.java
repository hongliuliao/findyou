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
	
	public LocationInfo getLocationInfo(long userId) {
		return LOCATION_MAP.get(userId);
	}
	
	public boolean saveLocationInfo(LocationInfo info) {
		LOCATION_MAP.put(Long.parseLong(info.getUserId()), info);
		return true;
	}
}
