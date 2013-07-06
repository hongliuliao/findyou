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
//�����õ�λ�����ѹ��ܣ���Ҫimport����

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
        
        mLocationClient = new LocationClient(getApplicationContext());     //����LocationClient��
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);  
        if(requestCode != REQUEST_CONTACT){
        	return;
        }
        //�绰��  
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
		setTitle("��λ");
		mLocationClient.requestLocation();
    	return true;
    }
}
