/**
 * 
 */
package com.findyou.service;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MapView;
import com.findyou.MyLocationListener;
import com.findyou.model.CodeMsg;
import com.findyou.model.LocationInfo;
import com.findyou.utils.HttpClientUtils;
import com.findyou.utils.JsonUtils;

/**
 * @author Administrator
 *
 */
public class LocationService {

	private static final String SAVE_LOCATION_URL = "https://raw.github.com/hongliuliao/findyou/master/client/findyou/test/saveMyInfo.json";
	
	public LocationClient mLocationClient = null;
	
	public void start(Context context, MapView mMapView) {
		mLocationClient = new LocationClient(context);     //����LocationClient��
        mLocationClient.registerLocationListener( new MyLocationListener(mMapView));    //ע���������
        mLocationClient.setLocOption(getOption());
        mLocationClient.start();
	}
	
	private LocationClientOption getOption() {
    	LocationClientOption option = new LocationClientOption();
    	option.setOpenGps(true);
    	option.setAddrType("all");//���صĶ�λ���������ַ��Ϣ
    	option.setCoorType("bd09ll");//���صĶ�λ����ǰٶȾ�γ��,Ĭ��ֵgcj02
    	option.setScanSpan(5000);//���÷���λ����ļ��ʱ��Ϊ5000ms
    	option.disableCache(true);//��ֹ���û��涨λ
    	option.setPoiNumber(5);	//��෵��POI����	
    	option.setPoiDistance(1000); //poi��ѯ����		
    	option.setPoiExtraInfo(true); //�Ƿ���ҪPOI�ĵ绰�͵�ַ����ϸ��Ϣ
    	return option;
    }
	
	public CodeMsg saveUserLocaltion(LocationInfo info) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", info.getUserId());
		params.put("latitude", info.getLatitude() + "");
		params.put("lontitude", info.getLontitude() + "");
		params.put("radius", info.getRadius() + "");
		params.put("addr", info.getAddr() + "");
		
		String result = HttpClientUtils.getHttpGetResult(SAVE_LOCATION_URL, params);
		return JsonUtils.toCodeMsg(result);
	}
	
}
