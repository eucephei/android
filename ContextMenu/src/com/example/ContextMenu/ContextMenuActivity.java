package com.example.ContextMenu;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("UseSparseArrays")
public class ContextMenuActivity  extends ListActivity {

	TextView selection;
	String[] items = {
		"HTC Rezound",  "Samsung Galaxy S II Skyrocket", 
		"Samsung Galaxy Nexus", "Motorola Droid Razr", 
		"Samsung Galaxy S", "Samsung Epic Touch 4G", 
		"iPhone 4S", "HTC Titan"
		};
	
	public static final int ONE_ID = Menu.FIRST+7;
	public static final int TWO_ID = Menu.FIRST+4;
	public static final int EIGHT_ID = Menu.FIRST+1;
	public static final int SIXTEEN_ID = Menu.FIRST+2;
	public static final int TWENTY_FOUR_ID = Menu.FIRST+3;
	public static final int THIRTY_TWO_ID = Menu.FIRST+5;

	private boolean groupItemsVisible = false;
	private Menu theMenu = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.main);
    	
    	// register our list widget as having a context menu
    	setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, items));
		selection = (TextView) findViewById(R.id.selection);
		registerForContextMenu(getListView());
    }
    
     public void onListItemClick(ListView parent, View v, int position, long id) {
    	selection.setText(items[position]);
     }

	 @Override 
	 public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {  	
		populateMenu(menu);
	 } 

    // Creates the menu items 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {    
    	populateMenu(menu);
    	
    	// append inflating menu
		theMenu = menu;
		new MenuInflater(getApplication()).inflate(R.menu.my_menu, menu);
    	
    	return(super.onCreateOptionsMenu(menu));
    }

    // Handles item selections 
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {    
    	
    	// hides/unhides group_items in my_menu.xml when click do_something
		if (item.getItemId() == R.id.do_something) {
			groupItemsVisible = !groupItemsVisible;	
			theMenu.setGroupVisible(R.id.group_items, groupItemsVisible);
		} 
    	
    	return (applyMenuChoice(item) || super.onOptionsItemSelected(item));
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {  
    	return (applyMenuChoice(item) || super.onContextItemSelected(item));
    }
    
    private void populateMenu(Menu menu) {
		menu.add(Menu.NONE, ONE_ID, Menu.NONE, "1 Pixel");
		menu.add(Menu.NONE, TWO_ID, Menu.NONE, "2 Pixels");
		menu.add(Menu.NONE, EIGHT_ID, Menu.NONE, "8 Pixels");
		menu.add(Menu.NONE, SIXTEEN_ID, Menu.NONE, "16 Pixels");
		menu.add(Menu.NONE, TWENTY_FOUR_ID, Menu.NONE, "24 Pixels");
		menu.add(Menu.NONE, THIRTY_TWO_ID, Menu.NONE, "32 Pixels");
	}
    
    // add seven menu choices, each with a unique identifier. 
	private boolean applyMenuChoice(MenuItem item) {
		switch (item.getItemId()) {
			case ONE_ID:
				getListView().setDividerHeight(1);
				return(true);
				
			case TWO_ID:
				getListView().setDividerHeight(2);
				return(true);

			case EIGHT_ID:
				getListView().setDividerHeight(8);
				return(true);
		
			case SIXTEEN_ID:
				getListView().setDividerHeight(16);
				return(true);
		
			case TWENTY_FOUR_ID:
				getListView().setDividerHeight(24);
				return(true);
		
			case THIRTY_TWO_ID:
				getListView().setDividerHeight(32);
				return(true);
		}

		return(false);
	}

}
