/**
 * 
 */
package com.findyou.utils;

import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;

/**
 * @author Administrator
 *
 */
public class LayerUtils {

	public static MyLocationOverlay getMyLocationOverlay(MapView mMapView, double latitude, double longitude) {
		MyLocationOverlay myLocationOverlay = new MyLocationOverlay(mMapView);
		LocationData locData = new LocationData();
		locData.latitude = latitude;  
		locData.longitude = longitude;  
		locData.direction = 2.0f;  
		myLocationOverlay.setData(locData);
		return myLocationOverlay;
	}
}
