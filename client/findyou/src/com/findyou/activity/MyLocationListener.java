/**
 * 
 */
package com.findyou.activity;

import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.map.MapView;
import com.findyou.model.CodeMsg;
import com.findyou.model.LocationInfo;
import com.findyou.model.MapViewLocation;
import com.findyou.server.FindyouApplication;
import com.findyou.service.LocationService;

/**
 * @author Administrator
 *
 */
public class MyLocationListener implements BDLocationListener {
	
	private LocationService locationService = LocationService.getInstance();
	
	private FindyouApplication application;
	
	private MapViewLocation mapViewLocation;
	
	private boolean isFrist = true;
	
	/**
	 * @param mMapView
	 */
	public MyLocationListener(MapView mMapView, FindyouApplication application) {
		super();
		this.application = application;
		mapViewLocation = new MapViewLocation(mMapView);
	}
	
	@Override
	public void onReceiveLocation(BDLocation location) {
		if (location == null || location.getLatitude() == 0 || location.getLongitude() == 0)
			return ;
		logLocationInfo(location);
		
		try {
			if(!mapViewLocation.isLocationChanged(location.getLatitude(), location.getLongitude())) {
				return ;
			}
			mapViewLocation.setLocation(location.getLatitude(), location.getLongitude());
			if(isFrist || application.isRequest()) {
				mapViewLocation.setViewToLocation(location.getLatitude(), location.getLongitude());
				isFrist = false;
				application.setRequest(false);
			}
			mapViewLocation.refresh();
			
			sendLocationInfoToServer(location);
		} catch (Exception e) {
			Log.e("MyLocationListener", "onReceiveLocation error! which location:" + location.toJsonString(), e);
		}
	}
	
	// 把位置信息上传到服务器上
	private void sendLocationInfoToServer(final BDLocation location) {
		new Thread() {
			
			@Override
			public void run() {
				if(!application.hasMyPhoneNum()) {
					return;
				}
				CodeMsg result = locationService.saveUserLocaltion(convertToLocationInfo(location, application.getMyPhoneNum()));
				Log.i("sendLocationInfoToServer", "codemsg:" + result);
			}
		}.start();
		
	}
	
	private LocationInfo convertToLocationInfo(BDLocation location, String userId) {
		LocationInfo info = new LocationInfo();
		info.setAddr(location.getAddrStr());
		info.setLatitude(location.getLatitude());
		info.setLontitude(location.getLongitude());
		info.setRadius(location.getRadius());
		info.setUserId(userId);
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
