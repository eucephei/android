package com.example.httpconnect;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HttpActivity extends Activity {

	private ImageView image;
	private TextView text;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        image = (ImageView) findViewById(R.id.image);
        text = (TextView) findViewById(R.id.text);
        
        downloadTwitterIcon();
        
        Button textBtn = (Button)findViewById(R.id.Button);
        textBtn.setOnClickListener(new OnClickListener() {
  			public void onClick(View v) {
  				downloadTwitterStream();
  			}
          });

    }
    
	public void downloadTwitterIcon() {
		Handler handler = new Handler(new Handler.Callback() {
		    @Override
			public boolean handleMessage(Message message) {
		    	boolean SUCCESS = false;
		    	
				switch (message.what) {
					case HttpConnection.DID_START: {
						Log.d("Image", "Starting Connection");
						break;
					}
					case HttpConnection.DID_SUCCEED: {
						Bitmap response = (Bitmap) message.obj;
						image.setImageBitmap(response);
						SUCCESS = true;
						break;
					}
					case HttpConnection.DID_ERROR: {
						Exception e = (Exception) message.obj;
						e.printStackTrace();
						break;
					}
				} 
				return SUCCESS;
			}
		} );
		new HttpConnection(handler).bitmap
			("http://developer.android.com/images/google/gps-plus-signin-hero.jpg");
	}

	public void downloadTwitterStream() {
		Handler handler = new Handler(new Handler.Callback() {
			public boolean handleMessage(Message message) {
		    	boolean SUCCESS = false;
				switch (message.what) {
					case HttpConnection.DID_START: {
						text.setText("Starting connection...");
						break;
					}
					case HttpConnection.DID_SUCCEED: {
						String response = (String) message.obj;
						text.setText(response);
						break;
					}
					case HttpConnection.DID_ERROR: {
						Exception e = (Exception) message.obj;
						e.printStackTrace();
						text.setText("Connection failed.");
						break;
					}
				}
				return SUCCESS;
			}
		} );
		new HttpConnection(handler).get
			("http://developer.android.com/guide/components/fundamentals.html");
	}
}