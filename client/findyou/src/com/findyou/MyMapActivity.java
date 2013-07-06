/**
 * 
 */
package com.findyou;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.findyou.model.LocationInfo;
import com.findyou.model.MapViewLocation;
import com.findyou.service.FriendService;
import com.findyou.service.LocationService;


/**
 * @author Administrator
 *
 */
public class MyMapActivity extends Activity {
	
	BMapManager mBMapMan = null;
	MapView mMapView = null;
	
	LocationService locationService = new LocationService();
	FriendService friendservice=new FriendService();
	
	MapViewLocation mapViewLocation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBMapMan=new BMapManager(getApplication());
		mBMapMan.init("EB21E59591611451362F228A82E72CA98AEDC437", null);  
		//ע�⣺��������setContentViewǰ��ʼ��BMapManager���󣬷���ᱨ��
		setContentView(R.layout.activity_map);
		mMapView=(MapView)findViewById(R.id.bmapView);
		mMapView.setBuiltInZoomControls(true);
		mapViewLocation = new MapViewLocation(mMapView);
		//�����������õ����ſؼ�
		MapController mMapController=mMapView.getController();
		// �õ�mMapView�Ŀ���Ȩ,�����������ƺ�����ƽ�ƺ�����
		GeoPoint point =new GeoPoint((int)(39.915* 1E6),(int)(116.404* 1E6));
		//�ø����ľ�γ�ȹ���һ��GeoPoint����λ��΢�� (�� * 1E6)
		mMapController.setCenter(point);//���õ�ͼ���ĵ�
		mMapController.setZoom(12);//���õ�ͼzoom����
		
		//������λ����
		locationService.start(getApplicationContext(), mMapView, getMyTelPhoneNumber());
		
		//����к���
		Intent intent = getIntent();
		if(intent != null) {
			String photoNumber = intent.getStringExtra("phoneNumber");
			if(photoNumber != null) {
				startGetFriendLocation(photoNumber);
			}
		}
	}
	
	private void startGetFriendLocation(final String phoneNumber) {
    	
    	new Thread() {
    		
    		@Override
    		public void run() {
    			LocationInfo info = locationService.getUserLocation(phoneNumber);
    			mapViewLocation.setLocation(info.getLatitude(), info.getLontitude());
    			mapViewLocation.reflush();
    		}
    	}.start();
    }
	
	private String getMyTelPhoneNumber() {
		TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getLine1Number();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, 1, 0, "ѡ�����");
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {  
        case 1:  
            Log.i("MapActivity", "select show friends");
            Intent intent = new Intent();
            intent.setClass(this, PhotoBookActivity.class);
            startActivity(intent);
            break;  
        default:  
            break;  
        }  
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onDestroy(){
	        mMapView.destroy();
	        if(mBMapMan!=null){
	                mBMapMan.destroy();
	                mBMapMan=null;
	        }
	        super.onDestroy();
	}
	@Override
	protected void onPause(){
	        mMapView.onPause();
	        if(mBMapMan!=null){
	               mBMapMan.stop();
	        }
	        super.onPause();
	}
	@Override
	protected void onResume(){
	        mMapView.onResume();
	        if(mBMapMan!=null){
	                mBMapMan.start();
	        }
	       super.onResume();
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);  
		String []rest=friendservice.getFriendResult(1).split(";");
		GeoPoint point =new GeoPoint(Integer.parseInt(rest[2].substring(9)),Integer.parseInt(rest[3].substring(10)));
	}
	
	
}
