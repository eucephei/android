package com.example.android;

import com.example.android.R;
import com.example.android.beans.Laptop;
import com.example.android.beans.ParcelableLaptop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayActivity extends Activity {

	TextView descTxt;
	ImageView imageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        	setContentView(R.layout.display);

        	Intent intent = getIntent();	
        	
            // display laptop object
            this.findViewsById();
    		ParcelableLaptop parcelableLaptop = (ParcelableLaptop) intent
    				.getParcelableExtra("laptop");
    		Laptop laptop = parcelableLaptop.getLaptop();
    		display(laptop);

    		// display form info
    		Bundle b = getIntent().getExtras();
    		TextView name = (TextView) findViewById(R.id.nameValue);
    		TextView age = (TextView) findViewById(R.id.ageValue);
    		TextView gender = (TextView) findViewById(R.id.genderValue);

    		name.setText(b.getCharSequence("name"));
    		age.setText(b.getCharSequence("age"));
    		gender.setText(b.getCharSequence("gender"));
	}
	
	private void findViewsById() {
		descTxt = (TextView) findViewById(R.id.desc);
		imageView = (ImageView) findViewById(R.id.icon);
	}
	
	private void display(Laptop laptop) {
		String desc = laptop.getId() + ": " + laptop.getBrand() + "\n"
				+ "$" + laptop.getPrice();
		descTxt.setText(desc);
		imageView.setImageBitmap(laptop.getImageBitmap());
	}	
}