package com.example.selectionlistview;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SelectionListViewActivity extends ListActivity {
	
	MenuDialog customMenuDialog;
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
		
		// MenuDialog
		registerForContextMenu(getListView());
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
	
	private class MenuDialog extends AlertDialog {
		public MenuDialog(Context context) {
			super(context);
			setTitle("Custom Options Menu");
			View cus_menu = getLayoutInflater().inflate(R.layout.custom_menu_layout, null);
			setView(cus_menu);
		}
		
		@Override
		public boolean onKeyUp(int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_MENU) {
				dismiss();
				return true;
			}
			return super.onKeyUp(keyCode, event);
		}
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			if (customMenuDialog == null) {
				customMenuDialog = new MenuDialog(this);
			}
			customMenuDialog.show();
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}
}