package com.example.layouts;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

public class LayoutsActivity extends Activity implements OnCheckedChangeListener // View.OnClickListener {
{
	CheckBox cb;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
		final Button button = (Button) findViewById(R.id.mybutton);
		button.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	TextView tv = (TextView)findViewById(R.id.mytext);
		    	tv.setText("button pressed");
		    }         
		});   
		
        EditText et = (EditText)findViewById(R.id.edtext);
        et.setText("Android Market is an online software store " +
        	"developed by Google for Android devices. " +
        	"An application program (\"app\") called \"Market\" " +
        	"is preinstalled on some Android devices and " +
        	"allows users to browse and download apps published " +
        	"by third-party developers, hosted on Android Market.");
       
        cb = (CheckBox)findViewById(R.id.myCheckbox);
        cb.setOnCheckedChangeListener(this);
    }

    public void onCheckedChanged(CompoundButton buttonView,
			boolean isChecked) {
    		if(isChecked){
    			cb.setText("state: checked");
    		}
    		else {
    			cb.setText("state: unchecked");
    		}
    } 

}