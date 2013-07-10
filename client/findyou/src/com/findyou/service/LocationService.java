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
		mLocationClient = new LocationClient(context);     //����LocationClient��
        mLocationClient.registerLocationListener( new MyLocationListener(mMapView, this.context));    //ע���������
        mLocationClient.setLocOption(getOption());
        mLocationClient.start();
	}
	
	public boolean requestLocation() {
		this.context.setRequest(true);
		return mLocationClient.requestLocation() == 0;
	}
	
	/**
     * �������
     */
    public void addPop(MapView mMapView, double latitude, double lontitude) {
    	final String friendName = context.getFriendName();
    	try {
			final GeoPoint friendPoint = new GeoPoint((int) (latitude * 1E6), (int) (lontitude * 1E6));
			final PopupOverlay pop = new PopupOverlay(mMapView, new PopupClickListener() {                  
		        @Override  
		        public void onClickedPopup(int index) {  
		                //�ڴ˴���pop����¼���indexΪ�����������,�����������������  
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
    	option.setAddrType("all");//���صĶ�λ���������ַ��Ϣ
    	option.setCoorType("bd09ll");//���صĶ�λ����ǰٶȾ�γ��,Ĭ��ֵgcj02
//    	option.setScanSpan(10000);//���÷���λ����ļ��ʱ��Ϊ5000ms
    	option.disableCache(true);//��ֹ���û��涨λ
    	option.setPoiNumber(5);	//��෵��POI����	
    	option.setPoiDistance(1000); //poi��ѯ����		
    	option.setPoiExtraInfo(true); //�Ƿ���ҪPOI�ĵ绰�͵�ַ����ϸ��Ϣ
    	return option;
    }
	
	/**
	 * �����û��ĵ�����Ϣ
	 * @param info �û�λ����Ϣ
	 * @return ״̬������Ϣ
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
