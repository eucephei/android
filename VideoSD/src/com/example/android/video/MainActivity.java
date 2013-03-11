package com.example.android.video;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		VideoView videoView = (VideoView)this.findViewById(R.id.videoView);
		MediaController mc = new MediaController(this);    
		videoView.setMediaController(mc);
/*
		// (1) Web   
		videoView.setVideoURI(Uri.parse(    
			"http://static.clipcanvas.com/sample/clipcanvas_14348_H264_320x180.mp4"));
*/
	    // (2) SD card 
	    videoView.setVideoURI(Uri.parse(
	         "file:///sdcard/sample_mpeg4.mp4"));    
	   
	    videoView.requestFocus();
	    videoView.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
