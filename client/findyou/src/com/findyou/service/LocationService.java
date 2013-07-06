/**
 * 
 */
package com.findyou.service;

import android.content.Context;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MapView;
import com.findyou.MyLocationListener;

/**
 * @author Administrator
 *
 */
public class LocationService {

	public LocationClient mLocationClient = null;
	
	public void start(Context context, MapView mMapView) {
		mLocationClient = new LocationClient(context);     //声明LocationClient类
        mLocationClient.registerLocationListener( new MyLocationListener(mMapView));    //注册监听函数
        mLocationClient.setLocOption(getOption());
        mLocationClient.start();
	}
	
	private LocationClientOption getOption() {
    	LocationClientOption option = new LocationClientOption();
    	option.setOpenGps(true);
    	option.setAddrType("all");//返回的定位结果包含地址信息
    	option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
    	option.setScanSpan(5000);//设置发起定位请求的间隔时间为5000ms
    	option.disableCache(true);//禁止启用缓存定位
    	option.setPoiNumber(5);	//最多返回POI个数	
    	option.setPoiDistance(1000); //poi查询距离		
    	option.setPoiExtraInfo(true); //是否需要POI的电话和地址等详细信息
    	return option;
    }
	
	
}
