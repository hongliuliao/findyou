/**
 * 
 */
package com.findyou.service;

import android.content.Context;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.findyou.MyLocationListener;

/**
 * @author Administrator
 *
 */
public class LocationService {

	public LocationClient mLocationClient = null;
	
	public BDLocationListener myListener = new MyLocationListener();
	
	public void start(Context context) {
		mLocationClient = new LocationClient(context);     //����LocationClient��
        mLocationClient.registerLocationListener( myListener );    //ע���������
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
	
	
}
