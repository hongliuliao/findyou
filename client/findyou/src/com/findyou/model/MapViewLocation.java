/**
 * 
 */
package com.findyou.model;

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
		if(lastMyOverlay != null) {
			mapView.getOverlays().remove(lastMyOverlay);
		}
		lastMyOverlay = myOverlay;
		mapView.getOverlays().add(myOverlay);
		return this;
	}
	
	public void setViewToLocation(double latitude, double longitude) {
		MapController mMapController=mapView.getController();
		// �õ�mMapView�Ŀ���Ȩ,�����������ƺ�����ƽ�ƺ�����
		GeoPoint point =new GeoPoint((int)(latitude* 1E6),(int)(longitude* 1E6));
		//�ø����ľ�γ�ȹ���һ��GeoPoint����λ��΢�� (�� * 1E6)
		mMapController.setCenter(point);//���õ�ͼ���ĵ�
	}
	
	public void reflush() {
		mapView.refresh();
	}
}
