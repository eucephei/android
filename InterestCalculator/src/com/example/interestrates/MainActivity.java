package com.example.interestrates;

import java.text.NumberFormat;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends Activity {
	
	private static final String LOAN_AMOUNT = "LOAN_AMOUNT";
	private static final String CUSTOM_INTEREST_RATE = "CUSTOM_INTEREST_RATE";
	   
	private double currentLoanAmount ; 		// loan amount entered by the user
	private EditText loanEditText; 			// accepts user input for loan amount
	   
	private double currentCustomRate; 		// interest rate % set with the SeekBar
	private TextView customRateTextView; 	// custom rate 
	   
	private EditText tenYearEditText; 		// 10 yrs 
	private EditText fifteenYearEditText; 	// 15 yrs 
	private EditText thirtyYearEditText;  	// 30 yrs 
	   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	    // check if app just started 
	    if ( savedInstanceState == null ) 
	    {
	    	currentLoanAmount = 0.0; 		// initialize the loan amount to zero
	        currentCustomRate = 5.00; 		// initialize the custom rate to 5.00%
	    } // end if
	    else // app is being restored from memory
	    {
	    	// initialize the loan amount to saved amount
	    	currentLoanAmount = savedInstanceState.getDouble(LOAN_AMOUNT); 
	         
	        // initialize the custom rate to saved interest rate 
	        currentCustomRate = savedInstanceState.getDouble(CUSTOM_INTEREST_RATE); 
	    } 
	      
	    // get the TextView displaying the custom interest rate
	    customRateTextView = (TextView) findViewById(R.id.customRateTextView);
	      
	    // get the loanEditText 
	    loanEditText = (EditText) findViewById(R.id.loanEditText);
	    // loanEditTextWatcher handles loanEditText's onTextChanged event
	    loanEditText.addTextChangedListener(loanEditTextWatcher);
	
	    // get the SeekBar used to set the custom interest rate
	    SeekBar customSeekBar = (SeekBar) findViewById(R.id.customSeekBar);
	    customSeekBar.setOnSeekBarChangeListener(customSeekBarListener);
	      
	    // get references to the 10yr, 15yr and 30yr EditTexts
	    tenYearEditText = (EditText) findViewById(R.id.tenYearEditText);
	    fifteenYearEditText = (EditText) findViewById(R.id.fifteenYearEditText);
	    thirtyYearEditText = (EditText) findViewById(R.id.thirtyYearEditText);	      
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	// calculate compound interest rate income
	private double formula(double loan, double rate, int term)
	{
		double c = rate / 100.;
		return loan * (1 + c * Math.pow(1 + c, term));
	}
	   
	// updates 10, 15 and 30 yr EditTexts
	private void updateNetIncome() 
	{
	    // calculate net income
	    double tenYears = 
	    		  formula(currentLoanAmount,currentCustomRate, 10 );
	    double fifteenYears = 
	    		  formula(currentLoanAmount,currentCustomRate, 15 );
	    double thirtyYears = 
	    		  formula(currentLoanAmount,currentCustomRate, 30 );
	    
	    // 10, 15 and 30 yr income EditTexts
	    NumberFormat format = NumberFormat.getCurrencyInstance();
	    tenYearEditText.setText(format.format(tenYears));
	    fifteenYearEditText.setText(format.format(fifteenYears));
	    thirtyYearEditText.setText(format.format(thirtyYears));
	} 

	// updates the custom rate 
	private void updateCustomRate() 
	{
	   // set customRateTextView's text to match the position of the SeekBar
		customRateTextView.setText(String.format("%.02f", currentCustomRate) + "%");
	    updateNetIncome(); // update the 10, 15 and 30 yr EditTexts
	} 

	// save values of loanEditText and customSeekBar
	@Override
	protected void onSaveInstanceState(Bundle outState)
	{  
		super.onSaveInstanceState(outState);
		outState.putDouble(LOAN_AMOUNT, currentLoanAmount);  
		outState.putDouble(CUSTOM_INTEREST_RATE, currentCustomRate);
	} 
	   
	// called when the user changes the position of SeekBar
	private OnSeekBarChangeListener customSeekBarListener = 
			new OnSeekBarChangeListener() 
	{
		// update currentCustomRate, then call updateCustomRate  
		public void onProgressChanged(SeekBar seekBar, int progress,
	         boolean fromUser) 
		{
	         // get currentCustomRate from the position of the SeekBar's thumb
	         currentCustomRate = seekBar.getProgress() / 100.0;
	         // update EditTexts for custom rate 
	         updateCustomRate(); 
		} 

	      
		public void onStartTrackingTouch(SeekBar seekBar) {}   
		public void onStopTrackingTouch(SeekBar seekBar) {}   
	}; 
   
	// event-handling object that responds to loanEditText's events   
	private TextWatcher loanEditTextWatcher = new TextWatcher()    
	{ 
		// called when the user enters a number  
		public void onTextChanged(CharSequence s, int start, int before, int count)   
		{         
	         // convert loanEditText's text to a double
	         try
	         {
	            currentLoanAmount = Double.parseDouble(s.toString());
	         } 
	         catch (NumberFormatException e)
	         {
	            currentLoanAmount = 0.0; // default if an exception occurs
	         } 
	         
	         updateNetIncome(); // update the 10, 15 and 30 yr EditTexts
	      } 

		public void afterTextChanged(Editable s) {}   
		public void beforeTextChanged(CharSequence s, int start, int count,
	         int after) {}    
	}; 
}






