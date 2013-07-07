/**
 * 
 */
package com.findyou.model;

import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.findyou.utils.LayerUtils;

/**
 * @author Administrator
 *
 */
public class MapViewLocation {

	private MapView mapView;

	private MyLocationOverlay lastMyOverlay;
	
	/**
	 * @param mapView
	 */
	public MapViewLocation(MapView mapView) {
		super();
		this.mapView = mapView;
	}
	
	public MapViewLocation setLocation(double latitude, double longitude) {
		MyLocationOverlay myOverlay = LayerUtils.getMyLocationOverlay(mapView, latitude, longitude);
		if(lastMyOverlay == null) {
			mapView.getOverlays().add(myOverlay);
			lastMyOverlay = myOverlay;
//			mapView.getOverlays().remove(lastMyOverlay);
		}
		
		LocationData locData = lastMyOverlay.getMyLocation();
		locData.latitude = latitude;  
		locData.longitude = longitude;  
		locData.direction = 2.0f;
		return this;
	}
	
	public void setViewToLocation(double latitude, double longitude) {
		MapController mMapController=mapView.getController();
		// 得到mMapView的控制权,可以用它控制和驱动平移和缩放
		GeoPoint point =new GeoPoint((int)(latitude* 1E6),(int)(longitude* 1E6));
		//用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
		mMapController.setCenter(point);//设置地图中心点
	}
	
	public void reflush() {
		mapView.refresh();
	}
}
