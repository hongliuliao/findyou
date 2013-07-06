/**
 * 
 */
package com.findyou.model;

import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
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
		if(lastMyOverlay != null) {
			mapView.getOverlays().remove(lastMyOverlay);
		}
		lastMyOverlay = myOverlay;
		mapView.getOverlays().add(myOverlay);
		return this;
	}
	
	public void reflush() {
		mapView.refresh();
	}
}
