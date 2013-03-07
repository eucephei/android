package com.example.thread;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


@SuppressLint("HandlerLeak")
public class HandlerThread extends Activity{

	private ProgressDialog progressDialog;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ((Button) findViewById(R.id.Button))
        	.setOnClickListener(new OnClickListener() {
        		public void onClick(View view) {
        				DoSomething();
			}      	
        });
    }

	protected void DoSomething() {
		// TODO Auto-generated method stub
		progressDialog = 
				ProgressDialog.show(this, "", "Doing something...");
		
		new Thread(new Runnable() {
			public void run() {
				try {
					for (int i=0;i<50;i++) {
						Thread.sleep(10);
						messageHandler.sendEmptyMessage(0);
					}
				}
				catch (Throwable t) {
				}
			}
		}).start();
	}

	private Handler messageHandler = new Handler() {
		
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			progressDialog.dismiss();
		}
	};
}