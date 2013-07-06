/**
 * 
 */
package com.findyou;

import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.findyou.model.CodeMsg;
import com.findyou.model.LocationInfo;
import com.findyou.service.LocationService;
import com.findyou.utils.LayerUtils;

/**
 * @author Administrator
 *
 */
public class MyLocationListener implements BDLocationListener {
	
	private MapView mMapView;
	
	private LocationService locationService = new LocationService();
	
	private String myTelphoneNumber;
	
	/**
	 * @param mMapView
	 */
	public MyLocationListener(MapView mMapView, String myTelphoneNumber) {
		super();
		this.mMapView = mMapView;
		this.myTelphoneNumber = myTelphoneNumber;
	}
	@Override
	public void onReceiveLocation(BDLocation location) {
		if (location == null)
			return ;
		
		logLocationInfo(location);
		
		MyLocationOverlay myOverlay = LayerUtils.getMyLocationOverlay(mMapView, location.getLatitude(), location.getLongitude());
		mMapView.getOverlays().clear();
		mMapView.getOverlays().add(myOverlay);
		
		setViewToLocation(location.getLatitude(), location.getLongitude());
		
		sendLocationInfoToServer(location);
		mMapView.refresh();
	}
	
	// 把位置信息上传到服务器上
	private void sendLocationInfoToServer(final BDLocation location) {
		new Thread() {
			
			@Override
			public void run() {
				CodeMsg result = locationService.saveUserLocaltion(convertToLocationInfo(location));
				Log.i("sendLocationInfoToServer", "codemsg:" + result);
			}
		}.start();
		
	}
	
	private LocationInfo convertToLocationInfo(BDLocation location) {
		LocationInfo info = new LocationInfo();
		info.setAddr(location.getAddrStr());
		info.setLatitude(location.getLatitude());
		info.setLontitude(location.getLongitude());
		info.setRadius(location.getRadius());
		info.setUserId(this.myTelphoneNumber);
		return info;
	}
	
	private void logLocationInfo(BDLocation location) {
		StringBuffer sb = new StringBuffer(256);
		sb.append("time : ");
		sb.append(location.getTime());
		sb.append("\nerror code : ");
		sb.append(location.getLocType());
		sb.append("\nlatitude : ");
		sb.append(location.getLatitude());
		sb.append("\nlontitude : ");
		sb.append(location.getLongitude());
		sb.append("\nradius : ");
		sb.append(location.getRadius());
		if (location.getLocType() == BDLocation.TypeGpsLocation){
			sb.append("\nspeed : ");
			sb.append(location.getSpeed());
			sb.append("\nsatellite : ");
			sb.append(location.getSatelliteNumber());
		} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
			sb.append("\naddr : ");
			sb.append(location.getAddrStr());
		} 
		Log.i("Location", sb.toString());
	}
	
	/**
	 * 把视野定位到当前所在位置
	 * @param latitude
	 * @param longitude
	 */
	private void setViewToLocation(double latitude, double longitude) {
		MapController mMapController=mMapView.getController();
		// 得到mMapView的控制权,可以用它控制和驱动平移和缩放
		GeoPoint point =new GeoPoint((int)(latitude* 1E6),(int)(116.404* 1E6));
		//用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
		mMapController.setCenter(point);//设置地图中心点
	}
	
	public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null){
				return ;
			}
			StringBuffer sb = new StringBuffer(256);
			sb.append("Poi time : ");
			sb.append(poiLocation.getTime());
			sb.append("\nerror code : ");
			sb.append(poiLocation.getLocType());
			sb.append("\nlatitude : ");
			sb.append(poiLocation.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(poiLocation.getLongitude());
			sb.append("\nradius : ");
			sb.append(poiLocation.getRadius());
			if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation){
				sb.append("\naddr : ");
				sb.append(poiLocation.getAddrStr());
			} 
			if(poiLocation.hasPoi()){
				sb.append("\nPoi:");
				sb.append(poiLocation.getPoi());
			}else{				
				sb.append("noPoi information");
			}
			Log.i("Location", sb.toString());
		}
}
