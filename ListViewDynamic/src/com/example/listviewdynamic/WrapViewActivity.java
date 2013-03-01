package com.example.listviewdynamic;


import android.os.Bundle;
import android.app.ListActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class WrapViewActivity extends ListActivity {
	TextView selection;
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main);
		setListAdapter(new IconicAdapter());
		
		// findViewById() expensive operation b/c
		// find widgets anywhere in the tree of children of the row's root View
		selection = (TextView)findViewById(R.id.selection);
	}
	
	private String getModel(int position) {
		return(((IconicAdapter)getListAdapter()).getItem(position));
	}
	
	public void onListItemClick(ListView parent, View v, int position, long id) {
	 	selection.setText(getModel(position));
	}
	
	class IconicAdapter extends ArrayAdapter<String> {
		// constructor
		IconicAdapter() {
			super(WrapViewActivity.this, R.layout.row, SmartPhones);
		}
		
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			ViewWrapper wrapper = null;
			
			// efficient recycling 
			if (row == null) {						
				LayoutInflater inflater = getLayoutInflater();
				
				// inflate a row and attaching instance to the row View using setTag().
				row = inflater.inflate(R.layout.row, parent, false);
				wrapper = new ViewWrapper(row);
				row.setTag(wrapper);
			}
			else {
				// efficient holder
				wrapper = (ViewWrapper)row.getTag();
			}
			
			wrapper.getLabel().setText(getModel(position));
			
			if (SmartPhones[position].startsWith("Samsung")) {
				wrapper.getIcon().setImageResource(R.drawable.ok);
			}
			else {
				wrapper.getIcon().setImageResource(R.drawable.radio);
			}		
			return(row);
		}
	}
	
	static final String[] SmartPhones = new String[] {
		"HTC Rezound",  "Samsung Galaxy S II Skyrocket", 
		"Samsung Galaxy Nexus", "Motorola Droid Razr", 
		"Samsung Galaxy S", "Samsung Epic Touch 4G", 
		"iPhone 4S", "HTC Titan"
	};
}