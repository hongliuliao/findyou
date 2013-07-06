package com.findyou;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
//test
public class MainActivity extends Activity {

	private static final int REQUEST_CONTACT = 1;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);  
        startActivityForResult(intent,REQUEST_CONTACT);
        Log.i(STORAGE_SERVICE, "start activity");
        
        
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);  
        if(requestCode != REQUEST_CONTACT){
        	return;
        }
        //µç»°±¾  
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
    
}
