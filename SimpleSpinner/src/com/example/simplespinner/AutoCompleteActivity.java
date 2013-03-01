package com.example.simplespinner;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

public class AutoCompleteActivity extends Activity {
    /** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// auto-complete
		AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.edit);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, WORLDCUP2010);
		textView.setAdapter(adapter);
		
		// spinner
    	Spinner s = (Spinner) findViewById(R.id.spinner);    
    	ArrayAdapter<?> spinnerAdapter = ArrayAdapter.createFromResource(            
    			this, R.array.planets, android.R.layout.simple_spinner_item);    
    	spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);    
    	s.setAdapter(spinnerAdapter);
	}
	
	static final String[] WORLDCUP2010 = new String[] {
		"Algeria",  "Argentina", "Australia", 
		"Brazil", "Cote d'Ivoire", "Cameroon", 
		"Chile", "Costa Rica", "Denmark", 
		"England", "France", "Germany",
		"Ghana",  "Greece", "Honduras",
		"Italy",  "Japan", "Netherlands",
		"New Zealand", "Nigeria", "North Korea",
		"Paraguay", "Portugal","Serbia",
		"Slovakia", "Slovenia", "South Africa",  
		"South Korea",  "Spain", "Switzerland",    
		"United States", "Uruguay" };
}