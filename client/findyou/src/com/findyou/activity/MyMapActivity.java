/**
 * 
 */
package com.findyou.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.MKOfflineMap;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.findyou.R;
import com.findyou.data.FindyouConstants;
import com.findyou.model.GetFriendLocationResponse;
import com.findyou.model.MapViewLocation;
import com.findyou.server.FindyouApplication;
import com.findyou.service.LocationService;
import com.findyou.task.GetFriendLocationThread;
import com.findyou.utils.BMapUtil;
import com.findyou.utils.StringUtils;



/**
 * @author Administrator
 *
 */
public class MyMapActivity extends Activity {
	
	BMapManager mBMapManager;
	
	MapView mMapView = null;
	
	LocationService locationService = new LocationService();
	
	MapViewLocation friendLocation;
	
	private static final int REQUEST_CONTACT = 1;
	
	public static final int SELECT_FRIEND = 1;
	public static final int PHONE_NUM_SETTING = 2;
	public static final int SEND_MY_LOCATION = 3;
	public static final int EXIT = 4;
	private GetFriendLocationThread getFriendLocationThread;
	
	MKOfflineMap mOffline;
	
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler(){  
        
        public void handleMessage(Message msg) {  
            switch (msg.what) {  
            case GetFriendLocationResponse.SHOW_FRIEND:  
            	GetFriendLocationResponse response = (GetFriendLocationResponse) msg.getData().getSerializable(FindyouConstants.RESULT_NAME);
            	if(response.getCode() != FindyouConstants.SUCCESS_CODE) {
            		showMessage(response.getMsg());
            		return;
            	}
            	locationService.showFriendLocation(friendLocation, response.getLatitude(), response.getLontitude());
    			break;
            }
        };  
    };
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FindyouApplication app = (FindyouApplication) this.getApplication();
		mBMapManager = BMapUtil.initBMapManager(app);
		//注意：请在试用setContentView前初始化BMapManager对象，否则会报错
		setContentView(R.layout.activity_map);
		mMapView = (MapView) findViewById(R.id.bmapView);
		mMapView.setBuiltInZoomControls(true);//设置启用内置的缩放控件
		
		friendLocation = new MapViewLocation(mMapView);
		
		MapController mMapController = mMapView.getController();
		mMapController.setZoom(12);//设置地图zoom级别
		
		String phoneNumber = app.getMyPhoneNum();
		if(phoneNumber == null || phoneNumber.trim().equals("")) {
			this.showMessage("查询不到您的手机号,请在菜单中设置手机号,方便你的朋友找到你!");
		}
		//开启定位服务
		locationService.start(getApplicationContext(), mMapView, phoneNumber);
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
        if(getFriendLocationThread == null || !getFriendLocationThread.isAlive()) {
        	getFriendLocationThread = new GetFriendLocationThread(phoneNumber, mHandler);
        	getFriendLocationThread.start();
        }
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
	
	@Override
	protected void onDestroy(){
	        mMapView.destroy();
	        if(mBMapManager!=null){
	        	mBMapManager.destroy();
	        	mBMapManager=null;
	        }
	        super.onDestroy();
	}
	@Override
	protected void onPause(){
	        mMapView.onPause();
	        if(mBMapManager!=null){
	        	mBMapManager.stop();
	        }
	        super.onPause();
	}
	@Override
	protected void onResume(){
	        mMapView.onResume();
	        if(mBMapManager!=null){
	        	mBMapManager.start();
	        }
	       super.onResume();
	}


}
