package com.example.rotation;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.view.View;
import android.widget.Button;

public class RotationActivity extends Activity {
	static final int PICK_REQUEST = 1337;
	Button viewButton = null;
	Uri contact = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setupViews();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
					Intent data) {
		if (requestCode == PICK_REQUEST) {
			if (resultCode == RESULT_OK) {
				contact = data.getData();
				viewButton.setEnabled(true);
			}
		}
	}

	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		
		setupViews();
	}
	
	private void setupViews() {
		setContentView(R.layout.main);
		
		Button btn = (Button)findViewById(R.id.contact);
		
		btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent i = new Intent(Intent.ACTION_PICK,
								Contacts.CONTENT_URI);
				startActivityForResult(i, PICK_REQUEST);
			}
		});
		
		viewButton = (Button)findViewById(R.id.view);
		
		viewButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				startActivity(new Intent(Intent.ACTION_VIEW, contact));
			}
		});	
		viewButton.setEnabled(contact != null);
	}
}