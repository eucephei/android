package com.example.Launch;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class LaunchActivity extends Activity {
	private EditText firstname;
	private EditText lastname;
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main);
		
		Button btn = (Button) findViewById(R.id.contacts);
		firstname = (EditText) findViewById(R.id.firstname);
		lastname = (EditText) findViewById(R.id.lastname);
		
		btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View view) {
				String _firstname = firstname.getText().toString();
				String _lastname = lastname.getText().toString();
				Uri uri = Uri.parse("geo:"+_firstname+","+_lastname);
				Log.v("LaunchActivity", uri.toString());
				
				// startActivity(new Intent(Intent.ACTION_VIEW, uri));
				startActivity(new Intent(Intent.ACTION_VIEW, ContactsContract.Contacts.CONTENT_URI));
			}
		});
	}
}