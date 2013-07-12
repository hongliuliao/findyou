/**
 * 
 */
package com.findyou.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.findyou.MyLocationListener;
import com.findyou.R;
import com.findyou.model.CodeMsg;
import com.findyou.model.LocationInfo;
import com.findyou.model.MapViewLocation;
import com.findyou.server.FindyouApplication;
import com.findyou.utils.BMapUtil;
import com.findyou.utils.HttpClientUtils;
import com.findyou.utils.JsonUtils;
import com.findyou.utils.StringUtils;

/**
 * @author Administrator
 *
 */
public class LocationService {
	
	private static final String DOMAIN_URL = "http://findyou2.duapp.com";

	private static final String SAVE_LOCATION_URL = DOMAIN_URL + "/saveAddr";
	
	private static final String GET_LOCATION_URL = DOMAIN_URL + "/getAddr";
	
	public LocationClient mLocationClient = null;
	
	public FindyouApplication context;
	
	public boolean isFriendFirstLocation = true;
	
	public void start(Context context, MapView mMapView, String myTelphoneNumber) {
		this.context = (FindyouApplication) context;
		mLocationClient = new LocationClient(context);     //声明LocationClient类
        mLocationClient.registerLocationListener( new MyLocationListener(mMapView, this.context));    //注册监听函数
        mLocationClient.setLocOption(getOption());
        mLocationClient.start();
	}
	
	public boolean requestLocation() {
		this.context.setRequest(true);
		return mLocationClient.requestLocation() == 0;
	}
	
	/**
     * 添加气泡
     */
    public void addPop(MapView mMapView, double latitude, double lontitude) {
    	final String friendName = context.getFriendName();
    	try {
			final GeoPoint friendPoint = new GeoPoint((int) (latitude * 1E6), (int) (lontitude * 1E6));
			final PopupOverlay pop = new PopupOverlay(mMapView, new PopupClickListener() {                  
		        @Override  
		        public void onClickedPopup(int index) {  
		                //在此处理pop点击事件，index为点击区域索引,点击区域最多可有三个  
		        	Log.i("SearchActivity", "Search map");
//		            Intent intent = new Intent();
//		        	intent.setClass(MyMapActivity.this, BusLineSearchActivity.class);
//		            startActivity(intent);
		        }  
		});
			pop.showPopup(getPopBitmap(friendName), friendPoint, 32);
		} catch (Exception e) {
			Log.e("Add Pop", "fail which friendName:" + friendName, e);
		}
    }
    
    private Bitmap getPopBitmap(String friendName) {
    	View popview = LayoutInflater.from(context).inflate(R.layout.pop, null);
		TextView popText = (TextView)popview.findViewById(R.id.pop_text); 
		popText.setText(friendName);
		return BMapUtil.getBitmapFromView(popview);
    }
	
	private LocationClientOption getOption() {
    	LocationClientOption option = new LocationClientOption();
    	option.setOpenGps(true);
    	option.setAddrType("all");//返回的定位结果包含地址信息
    	option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
//    	option.setScanSpan(10000);//设置发起定位请求的间隔时间为5000ms
    	option.disableCache(true);//禁止启用缓存定位
    	option.setPoiNumber(5);	//最多返回POI个数	
    	option.setPoiDistance(1000); //poi查询距离		
    	option.setPoiExtraInfo(true); //是否需要POI的电话和地址等详细信息
    	return option;
    }
	
	/**
	 * 保存用户的地理信息
	 * @param info 用户位置信息
	 * @return 状态码与消息
	 */
	public CodeMsg saveUserLocaltion(LocationInfo info) {
		Log.i("LocationInfoService", "start saveUserLocaltion which info:" + info);
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", info.getUserId());
		params.put("latitude", info.getLatitude() + "");
		params.put("lontitude", info.getLontitude() + "");
		params.put("radius", info.getRadius() + "");
		params.put("addr", info.getAddr() + "");
		params.put("r", new Random().nextInt(10000) + "");
		
		String result = HttpClientUtils.getHttpPostResult(SAVE_LOCATION_URL, params);
		return JsonUtils.toCodeMsg(result);
	}
	
	public LocationInfo getUserLocation(String phoneNumber) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", phoneNumber);
		params.put("r", new Random().nextInt(10000) + "");
		String result = HttpClientUtils.getHttpGetResult(GET_LOCATION_URL, params);
		if(StringUtils.isBlank(result)) {
			throw new RuntimeException("can not GET_LOCATION_URL which params:" + params);
		}
		return JsonUtils.toLocationInfo(result);
	}
	
	public void showFriendLocation(MapViewLocation mapViewLocation, double latitude, double lontitude) {
    	mapViewLocation.setLocation(latitude, lontitude);
    	if(isFriendFirstLocation) {
    		mapViewLocation.setViewToLocation(latitude, lontitude);
    		isFriendFirstLocation = false;
    	}
    	addPop(mapViewLocation.getMapView(), latitude, lontitude);
		mapViewLocation.reflush();
	}
	
}
