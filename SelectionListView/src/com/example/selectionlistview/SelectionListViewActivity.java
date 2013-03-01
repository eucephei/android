package com.example.selectionlistview;

import android.os.Bundle;
import android.app.ListActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class SelectionListViewActivity extends ListActivity {
	TextView selection;
    /** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main);
		setListAdapter(new ArrayAdapter<String>(this,
			android.R.layout.simple_list_item_single_choice,
			WORLDCUP2010));
		selection=(TextView)findViewById(R.id.selection);
		
		// Selection
		final ListView listView = getListView();
		listView.setItemsCanFocus(false);
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);  
		

	}
	
	public void onListItemClick(ListView parent, View v, 
			int position,long id) {
	 	selection.setText(WORLDCUP2010[position]);
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