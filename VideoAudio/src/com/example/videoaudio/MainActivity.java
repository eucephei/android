package com.example.videoaudio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	
    private Button mlocalvideo;
    private Button mresourcesvideo;
    private Button mstreamvideo;

    private static final String AUDIOVIDEO = "videoaudio";
    private static final int LOCAL_VIDEO = 1;
    private static final int STREAM_VIDEO = 2;
    private static final int RESOURCES_VIDEO = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        
        mlocalvideo = (Button) findViewById(R.id.localvideo);
        mlocalvideo.setOnClickListener(mLocalVideoListener);
        mresourcesvideo = (Button) findViewById(R.id.resourcesvideo);
        mresourcesvideo.setOnClickListener(mResourcesVideoListener);
        mstreamvideo = (Button) findViewById(R.id.streamvideo);
        mstreamvideo.setOnClickListener(mStreamVideoListener);
	}
	
	    private OnClickListener mLocalVideoListener = new OnClickListener() {
	        public void onClick(View v) {
	            Intent intent =
	                    new Intent(MainActivity.this,
	                    		MyVideoPlayer.class);
	            intent.putExtra(AUDIOVIDEO, LOCAL_VIDEO);
	            startActivity(intent);
	        }
	    };
	    
	    private OnClickListener mResourcesVideoListener = new OnClickListener() {
	        public void onClick(View v) {
	            Intent intent =
	                    new Intent(MainActivity.this.getApplication(),
	                    		MyVideoPlayer.class);
	            intent.putExtra(AUDIOVIDEO, RESOURCES_VIDEO);
	            startActivity(intent);
	        }
	    };
	    
	    private OnClickListener mStreamVideoListener = new OnClickListener() {
	        public void onClick(View v) {
	            Intent intent =
	                    new Intent(MainActivity.this,
	                    		MyVideoPlayer.class);
	            intent.putExtra(AUDIOVIDEO, STREAM_VIDEO);
	            startActivity(intent);
	        }
	    };

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
