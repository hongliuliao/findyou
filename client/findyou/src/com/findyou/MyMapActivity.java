/**
 * 
 */
package com.findyou;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
import com.findyou.server.FindyouApplication;
import com.findyou.service.LocationService;


/**
 * @author Administrator
 *
 */
public class MyMapActivity extends Activity {
	
	BMapManager mBMapMan = null;
	MapView mMapView = null;
	
	LocationService locationService = new LocationService();
	
	MapViewLocation mapViewLocation;
	
	private final static int SHOW_FRIEND = 1;
	
	private static String FRIEND_LATITUDE = "FRIEND_LATITUDE";
	private static String FRIEND_LONTITUDE = "FRIEND_LONTITUDE";
	
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler(){  
        
        public void handleMessage(Message msg) {  
            switch (msg.what) {  
            case SHOW_FRIEND:  
            	double latitude = msg.getData().getDouble(FRIEND_LATITUDE);
            	double lontitude = msg.getData().getDouble(FRIEND_LONTITUDE);
            	mapViewLocation.setLocation(latitude, lontitude);
    			mapViewLocation.reflush();
    			break;
            }  
        };  
    };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBMapMan=new BMapManager(getApplication());
		mBMapMan.init("EB21E59591611451362F228A82E72CA98AEDC437", null);  
		//注意：请在试用setContentView前初始化BMapManager对象，否则会报错
		setContentView(R.layout.activity_map);
		mMapView=(MapView)findViewById(R.id.bmapView);
		mMapView.setBuiltInZoomControls(true);
		mapViewLocation = new MapViewLocation(mMapView);
		//设置启用内置的缩放控件
		MapController mMapController=mMapView.getController();
		// 得到mMapView的控制权,可以用它控制和驱动平移和缩放
		GeoPoint point =new GeoPoint((int)(39.915* 1E6),(int)(116.404* 1E6));
		//用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
		mMapController.setCenter(point);//设置地图中心点
		mMapController.setZoom(12);//设置地图zoom级别
		
		//开启定位服务
		locationService.start(getApplicationContext(), mMapView, getMyTelPhoneNumber());
		
	}
	
	private void startGetFriendLocation(final String phoneNumber) {
    	
		
    	new Thread() {
    		
    		@Override
    		public void run() {
    			LocationInfo info = locationService.getUserLocation(phoneNumber);
    			if(info == null) {
    				Looper.prepare();
    				showMessage("该好友信息不存在,请确定TA在线上!");
    				Looper.loop();
    				return;
    			}
    			Message msg = new Message();
    			msg.what = SHOW_FRIEND;
    			msg.getData().putDouble(FRIEND_LATITUDE, info.getLatitude());
    			msg.getData().putDouble(FRIEND_LONTITUDE, info.getLontitude());
    			mHandler.sendMessage(msg);
    		}
    	}.start();
    }
	
	private void showMessage(String message) {
		new AlertDialog.Builder(MyMapActivity.this).setTitle("提示").setMessage(message).setPositiveButton("确定", null).show();
	}
	
	private String getMyTelPhoneNumber() {
		TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getLine1Number();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, 1, 0, "选择好友");
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
		//如果有好友
		FindyouApplication application = (FindyouApplication) this.getApplication();
		String photoNumber = application.getFriendPhoneNum();
		if(photoNumber != null) {
			startGetFriendLocation(photoNumber);
		}
        mMapView.onResume();
        if(mBMapMan!=null){
            mBMapMan.start();
        }
       super.onResume();
	}

}
