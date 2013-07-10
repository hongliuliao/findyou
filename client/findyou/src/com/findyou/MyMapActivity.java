/**
 * 
 */
package com.findyou;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.findyou.data.DataHelper;
import com.findyou.model.LocationInfo;
import com.findyou.model.MapViewLocation;
import com.findyou.server.FindyouApplication;
import com.findyou.service.LocationService;
import com.findyou.service.PhoneService;
import com.findyou.utils.StringUtils;



/**
 * @author Administrator
 *
 */
public class MyMapActivity extends Activity {
	public PhoneService phoneservice;
	public static DataHelper DATAHELPER;
	public String DATAFILENAME="myPhone.db";
	
	BMapManager mBMapMan = null;
	MapView mMapView = null;
	
	LocationService locationService = new LocationService();
	
	MapViewLocation mapViewLocation;
	
	private final static int SHOW_FRIEND = 1;
	
	private static String FRIEND_LATITUDE = "FRIEND_LATITUDE";
	private static String FRIEND_LONTITUDE = "FRIEND_LONTITUDE";
	
	public static final int SELECT_FRIEND = 1;
	public static final int PHONE_NUM_SETTING = 2;
	public static final int SEND_MY_LOCATION = 3;
	public static final int EXIT = 4;
	
	
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler(){  
        
        public void handleMessage(Message msg) {  
            switch (msg.what) {  
            case SHOW_FRIEND:  
            	double latitude = msg.getData().getDouble(FRIEND_LATITUDE);
            	double lontitude = msg.getData().getDouble(FRIEND_LONTITUDE);
            	locationService.showFriendLocation(mapViewLocation, latitude, lontitude);
    			break;
            }  
        };  
    };
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DATAHELPER=new DataHelper(getApplicationContext(), DATAFILENAME);
		mBMapMan=new BMapManager(getApplication());
		mBMapMan.init("EB21E59591611451362F228A82E72CA98AEDC437", null); 
		//ע�⣺��������setContentViewǰ��ʼ��BMapManager���󣬷���ᱨ��
		FindyouApplication application = (FindyouApplication) this.getApplication();
		application.mBMapManager = mBMapMan;
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
		
		FindyouApplication app = (FindyouApplication) this.getApplication();
		String phoneNumber = app.getMyPhoneNum();
		if(phoneNumber == null || phoneNumber.trim().equals("")) {
			this.showMessage("��ѯ���������ֻ���,���ڲ˵��������ֻ���,������������ҵ���!");
		}
		//������λ����
		locationService.start(getApplicationContext(), mMapView, phoneNumber);
	}
	
	private void startGetFriendLocation(final String phoneNumber) {
    	new Thread() {
    		
    		@Override
    		public void run() {
    			try {
    				while(true) {
    					LocationInfo info = locationService.getUserLocation(phoneNumber);
            			if(info == null) {
            				Looper.prepare();
            				showMessage("�ú�����Ϣ������,��ȷ��TA������!");
            				Looper.loop();
            				return;
            			}
            			Message msg = new Message();
            			msg.what = SHOW_FRIEND;
            			msg.getData().putDouble(FRIEND_LATITUDE, info.getLatitude());
            			msg.getData().putDouble(FRIEND_LONTITUDE, info.getLontitude());
            			mHandler.sendMessage(msg);
            			Thread.sleep(60000);
    				}
				} catch (Exception e) {
					Log.e("locationService", "getUserLocation error which phoneNumber:" + phoneNumber, e);
					Looper.prepare();
    				showMessage("��ȡ������Ϣ�쳣,��������!");
    				Looper.loop();
    				return;
				}
    		}
    	}.start();
    }
	
	private void showMessage(String message) {
		new AlertDialog.Builder(MyMapActivity.this).setTitle("��ʾ").setMessage(message).setPositiveButton("ȷ��", null).show();
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, SELECT_FRIEND, 0, "ѡ�����");
		menu.add(Menu.NONE, PHONE_NUM_SETTING, 0, "�����ֻ�����");
		menu.add(Menu.NONE, SEND_MY_LOCATION, 0, "��λ�Լ�");
		menu.add(Menu.NONE, EXIT, 0, "�˳�");
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {  
        case SELECT_FRIEND:  
            Log.i("MapActivity", "select show friends");
            Intent intent = new Intent();
            intent.setClass(this, PhotoBookActivity.class);
            startActivity(intent);
            break;  
        case PHONE_NUM_SETTING:
        	final EditText inputPhoneNum = new EditText(this);
        	FindyouApplication app = (FindyouApplication) getApplication();
        	inputPhoneNum.setText(app.getMyPhoneNum());
        	
        	AlertDialog.Builder builder = new AlertDialog.Builder(this);
        	builder.setTitle("�������ֻ���").setIcon(android.R.drawable.ic_dialog_info).setView(inputPhoneNum);
        	builder.setNegativeButton("ȷ��", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                	String phoneNum = inputPhoneNum.getText().toString();
                	if(StringUtils.isBlank(phoneNum)) {
                		return;
                	}
                	FindyouApplication app = (FindyouApplication) getApplication();
                	app.setMyPhoneNum(phoneNum);
                	Toast.makeText(getApplicationContext(), "�����ɹ�!", Toast.LENGTH_SHORT).show();
                }
            });
        	builder.setPositiveButton("ȡ��", null);
            builder.show();
            break;
        case SEND_MY_LOCATION:
        	boolean result = this.locationService.requestLocation();
        	Toast.makeText(getApplicationContext(), result ? "��λ�ɹ�!" : "��λʧ��!", Toast.LENGTH_SHORT).show();
        	break;
        case EXIT:
        	this.finish();
        	System.exit(0);
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
		//����к���
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
