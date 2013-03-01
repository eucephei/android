package com.example.listviewdynamic;

import com.example.listviewdynamic.R;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


// ViewWrapper not only holds onto the child widgets, 
// but it also lazy-finds the child widgets
// If we create a wrapper, and don't need a specific child, 
// we never use findViewById() to find it
class ViewWrapper {
	View base;
	TextView label = null;
	ImageView icon = null;
	
	ViewWrapper(View base) {
		this.base = base;
	}
	
	TextView getLabel() {
		if (label == null) {
			label = (TextView)base.findViewById(R.id.label);
		}
		return(label);
	}
	
	ImageView getIcon() {
		if (icon == null) {
			icon = (ImageView)base.findViewById(R.id.icon);
		}
		return(icon);
	}
}