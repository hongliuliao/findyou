/**
 * 
 */
package com.findyou;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.findyou.model.LocationInfo;
import com.findyou.model.MapViewLocation;
import com.findyou.server.FindyouApplication;
import com.findyou.service.LocationService;
import com.findyou.utils.StringUtils;



/**
 * @author Administrator
 *
 */
public class MyMapActivity extends Activity {
	
	MapView mMapView = null;
	
	LocationService locationService = new LocationService();
	
	MapViewLocation friendLocation;
	
	private final static int SHOW_FRIEND = 1;
	
	private static final int REQUEST_CONTACT = 1;
	
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
            	locationService.showFriendLocation(friendLocation, latitude, lontitude);
    			break;
            }
        };  
    };
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//注意：请在试用setContentView前初始化BMapManager对象，否则会报错
		setContentView(R.layout.activity_map);
		mMapView = (MapView) findViewById(R.id.bmapView);
		mMapView.setBuiltInZoomControls(true);//设置启用内置的缩放控件
		friendLocation = new MapViewLocation(mMapView);
		
		MapController mMapController = mMapView.getController();
		mMapController.setZoom(12);//设置地图zoom级别
		
		FindyouApplication app = (FindyouApplication) this.getApplication();
		String phoneNumber = app.getMyPhoneNum();
		if(phoneNumber == null || phoneNumber.trim().equals("")) {
			this.showMessage("查询不到您的手机号,请在菜单中设置手机号,方便你的朋友找到你!");
		}
		//开启定位服务
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
            				showMessage("该好友信息不存在,请确定TA在线上!");
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
    				showMessage("获取好友信息异常,请检查网络!");
    				Looper.loop();
    				return;
				}
    		}
    	}.start();
    }
	
	private void showMessage(String message) {
		new AlertDialog.Builder(MyMapActivity.this).setTitle("提示").setMessage(message).setPositiveButton("确定", null).show();
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, SELECT_FRIEND, 0, "选择好友");
		menu.add(Menu.NONE, PHONE_NUM_SETTING, 0, "设置手机号码");
		menu.add(Menu.NONE, SEND_MY_LOCATION, 0, "定位自己");
		menu.add(Menu.NONE, EXIT, 0, "退出");
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {  
        case SELECT_FRIEND:  
            Log.i("MapActivity", "select show friends");
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);  
            startActivityForResult(intent,REQUEST_CONTACT);
            break;  
        case PHONE_NUM_SETTING:
        	final EditText inputPhoneNum = new EditText(this);
        	FindyouApplication app = (FindyouApplication) getApplication();
        	inputPhoneNum.setText(app.getMyPhoneNum());
        	
        	AlertDialog.Builder builder = new AlertDialog.Builder(this);
        	builder.setTitle("请输入手机号").setIcon(android.R.drawable.ic_dialog_info).setView(inputPhoneNum);
        	builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                	String phoneNum = inputPhoneNum.getText().toString();
                	if(StringUtils.isBlank(phoneNum)) {
                		return;
                	}
                	FindyouApplication app = (FindyouApplication) getApplication();
                	app.setMyPhoneNum(phoneNum);
                	Toast.makeText(getApplicationContext(), "操作成功!", Toast.LENGTH_SHORT).show();
                }
            });
        	builder.setPositiveButton("取消", null);
            builder.show();
            break;
        case SEND_MY_LOCATION:
        	boolean result = this.locationService.requestLocation();
        	Toast.makeText(getApplicationContext(), result ? "定位成功!" : "定位失败!", Toast.LENGTH_SHORT).show();
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
	protected void onResume() {
		// 如果有好友
		FindyouApplication application = (FindyouApplication) this.getApplication();
		String photoNumber = application.getFriendPhoneNum();
		if (photoNumber != null) {
			startGetFriendLocation(photoNumber);
		}
		mMapView.onResume();
		super.onResume();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);  
        if(requestCode != REQUEST_CONTACT){
        	return;
        }
        //电话本  
    	if (data == null) {
            return;
        }    
    	Cursor cursor = getContentResolver().query(data.getData(),null,null,null, null);  
        if(cursor == null){  
        	return;
        }
        cursor.moveToFirst();
        String friendName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        
        String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
        cursor.close();  
        
        String phoneNumber = getPhoneNumber(contactId);  
        
        FindyouApplication application = (FindyouApplication) this.getApplication();
        application.setFriendName(friendName);
        application.setFriendPhoneNum(phoneNumber);
	}
	
	private String getPhoneNumber(String contactId) {
		String phoneNumber = null;
		Cursor phone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,   
		        null,   
		        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,   
		        null,   
		        null);  
		if (phone.moveToNext()) {  
		    phoneNumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));  
		    Log.i("MainActivity", phoneNumber);
		}
		// 过滤手机 号,使之规范
		phoneNumber = StringUtils.filterPhoneNumber(phoneNumber);
		return phoneNumber;
	}

}
