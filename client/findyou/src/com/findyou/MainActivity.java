package com.findyou;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
//假如用到位置提醒功能，需要import该类

public class MainActivity extends Activity {

	private static final int REQUEST_CONTACT = 1;
	
	private String tag = "MainActivity";// for log
	
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);  
        startActivityForResult(intent,REQUEST_CONTACT);
        Log.i(STORAGE_SERVICE, "start activity");
        
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener( myListener );    //注册监听函数
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
        String username = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        TextView textView = (TextView) findViewById(R.id.friendText);
        textView.setText(username);
        
        String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
        cursor.close();  
        
        String phoneNumber = getPhoneNumber(contactId);  
        textView.setText(textView.getText() + phoneNumber);
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
		return phoneNumber;
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
		Log.i(tag, "select main item");
		setTitle("定位");
		mLocationClient.requestLocation();
    	return true;
    }
}
