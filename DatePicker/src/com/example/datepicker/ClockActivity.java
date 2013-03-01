package com.example.datepicker;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.dialog.DatePickerFragment;
import com.dialog.TimePickerDialogFragment;

@SuppressLint("HandlerLeak")
public class ClockActivity extends FragmentActivity {
	
	  int mHour = 15;
	    int mMinute = 15;
	 
	    /** This handles the message send from TimePickerDialogFragment on setting Time */
	    Handler mHandler = new Handler(){
	        @Override
	        public void handleMessage(Message m){
	            /** Creating a bundle object to pass currently set Time to the fragment */
	            Bundle b = m.getData();
	            /** Getting the Hour of day from bundle */
	            mHour = b.getInt("set_hour");
	            /** Getting the Minute of the hour from bundle */
	            mMinute = b.getInt("set_minute");
	            /** Displaying a short time message containing time set by Time picker dialog fragment */
	            Toast.makeText(getBaseContext(), b.getString("set_time"), Toast.LENGTH_SHORT).show();
	        }
	    };
	
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.main);
		 
		 /** Click Event Handler for button */
	        OnClickListener listener = new OnClickListener() {
	            @Override
	            public void onClick(View v) {
	 
	                /** Creating a bundle object to pass currently set time to the fragment */
	                Bundle b = new Bundle();
	 
	                /** Adding currently set hour to bundle object */
	                b.putInt("set_hour", mHour);
	 
	                /** Adding currently set minute to bundle object */
	                b.putInt("set_minute", mMinute);
	 
	                /** Instantiating TimePickerDialogFragment */
	                TimePickerDialogFragment timePicker = new TimePickerDialogFragment(mHandler);
	 
	                /** Setting the bundle object on timepicker fragment */
	                timePicker.setArguments(b);
	 
	                /** Getting fragment manger for this activity */
	                FragmentManager fm = getSupportFragmentManager();
	 
	                /** Starting a fragment transaction */
	                FragmentTransaction ft = fm.beginTransaction();
	 
	                /** Adding the fragment object to the fragment transaction */
	                ft.add(timePicker, "time_picker");
	 
	                /** Opening the TimePicker fragment */
	                ft.commit();
	            }
	        };
	        /** Getting an instance of Set button */
	        Button btnSet = (Button)findViewById(R.id.time);
	        /** Setting click event listener for the button */
	        btnSet.setOnClickListener(listener);
	  
	     // DatePicker
		 findViewById(R.id.date).setOnClickListener(new OnClickListener() {
			 @Override
			 public void onClick(View v) {
				 showDatePicker();
			 }
		 });
		 
	 }
	 
	 private void showDatePicker() {
		 DatePickerFragment date = new DatePickerFragment();
		 /**
		  * Set Up Current Date Into dialog
		  */
		 Calendar calender = Calendar.getInstance();
		 Bundle args = new Bundle();
		 args.putInt("year", calender.get(Calendar.YEAR));
		 args.putInt("month", calender.get(Calendar.MONTH));
		 args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
		 date.setArguments(args);
		 /**
		  * Set Call back to capture selected date
		  */
		 date.setCallBack(ondate);
		 date.show(getSupportFragmentManager(), "Date Picker");
	 }

	 OnDateSetListener ondate = new OnDateSetListener() {
		 @Override
		 public void onDateSet(DatePicker view, int year, int monthOfYear,
				 int dayOfMonth) {
			 Toast.makeText(
					 ClockActivity.this,
					 String.valueOf(year) + "-" + String.valueOf(monthOfYear)
					 + "-" + String.valueOf(dayOfMonth),
			 Toast.LENGTH_LONG).show();
	  }
	 };

	 @Override
	 public boolean onCreateOptionsMenu(Menu menu) {
	  // getMenuInflater().inflate(R.menu.activity_main, menu);
	  return true;
	 }

	}