package com.example.android;

import java.io.InputStream;
import java.text.NumberFormat;
import java.text.ParsePosition;

import com.example.android.R;
import com.example.android.beans.Laptop;
import com.example.android.beans.ParcelableLaptop;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class FormActivity extends Activity implements OnClickListener {
	Button button;
	RadioGroup genderRadioGroup;
	EditText name;
	EditText age;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.form);

		//Get the ids of view objects
		findAllViewsId();

		button.setOnClickListener(this);
	}

	private void findAllViewsId() {
		button = (Button) findViewById(R.id.submit);
		name = (EditText) findViewById(R.id.name);
		age = (EditText) findViewById(R.id.age);
		genderRadioGroup = (RadioGroup) findViewById(R.id.gender);
	}

	@Override
	public void onClick(View v) {
		
     if (isNumeric(age.getText().toString())) {
		
		
		Intent intent = new Intent(getApplicationContext(), DisplayActivity.class);
		//Create a bundle object
		Bundle b = new Bundle();

		//Inserts a String value into the mapping of this Bundle
		b.putString("name", name.getText().toString());
		b.putString("age", age.getText().toString());
		int id = genderRadioGroup.getCheckedRadioButtonId();
		RadioButton radioButton = (RadioButton) findViewById(id);
		b.putString("gender", radioButton.getText().toString());

		//Add the bundle to the intent.
		intent.putExtras(b);
		
		//Add Laptop object to the intent.
		intent.putExtra("laptop", this.setLaptop());

		//start the DisplayActivity
		startActivity(intent);
		}
	}
	
	private ParcelableLaptop setLaptop() { 
		Laptop laptop = new Laptop();
		laptop.setId(1);
		laptop.setBrand("Kiwi s46N");
		laptop.setPrice(799.99);
	
		//if image placed inside res/drawable folder
		InputStream is = getResources().openRawResource(R.drawable.kiwi);
		Bitmap bitmap = BitmapFactory.decodeStream(is);

		laptop.setImageBitmap(bitmap);
		
		//Create Parcelable object
		return new ParcelableLaptop(laptop);		
	}
	
	public static boolean isNumeric(String str)
	{
	  NumberFormat formatter = NumberFormat.getInstance();
	  ParsePosition pos = new ParsePosition(0);
	  formatter.parse(str, pos);
	  return str.length() == pos.getIndex();
	}
	
	 @Override
	    protected void onResume()
	    {
	        super.onResume();
	    }
	     
	    @Override
	    protected void onPause()
	    {
	        super.onPause();
	    }
	     
	    @Override
	    public boolean onTouchEvent( MotionEvent event )
	    {
	        if( event.getAction() == MotionEvent.ACTION_UP )
	        {
	            return true;
	        }
	        return super.onTouchEvent( event );
	    }
}