package com.example.tabwidget;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.ViewFlipper;

public class TabWidgetActivity extends Activity {
	
	ViewFlipper flipper;
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main);

		TabHost tabs=(TabHost)findViewById(R.id.tabhost);
		tabs.setup();
		TabHost.TabSpec spec=tabs.newTabSpec("tag1");
		
		spec.setContent(R.id.tab1);
		spec.setIndicator("Analog Clock");
		tabs.addTab(spec);
	
		spec=tabs.newTabSpec("tag2");
		spec.setContent(R.id.tab2);
		spec.setIndicator("DigitalClock");
		tabs.addTab(spec);	
		
		spec=tabs.newTabSpec("tag3");
		spec.setContent(R.id.flip_me);
		spec.setIndicator("Flip Me");
		tabs.addTab(spec);
		
		flipper=(ViewFlipper)findViewById(R.id.details);
		Button btn=(Button)findViewById(R.id.flip_me);
		btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				flipper.showNext();
			}
		});
	}
}
