package com.example.CListView;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;

public class CListViewActivity extends ListActivity {
	
	static final ArrayList<HashMap<String,String>> list = 
			new ArrayList<HashMap<String,String>>();
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clistview);
        
        // Set up our adapter
        SimpleAdapter adapter = new SimpleAdapter(
            	this,
            	list,
            	R.layout.crowview,
            	new String[] { "model","company","price" },
            	new int[] { R.id.text1,R.id.text2, R.id.text3 });
        populateList();
        setListAdapter(adapter);      
    }
    
    private void populateList() {
    	HashMap<String,String> map = new HashMap<String,String>();
    	map.put("model", "Samsung Galaxy Nexus");
    	map.put("company", "Samsung");
    	map.put("price", "$454.99");
    	list.add(map);
    	map = new HashMap<String,String>();
    	map.put("model", "Samsung Epic Touch 4G");
    	map.put("company", "Samsung");
    	map.put("price", "$544.99");
    	list.add(map);
    	map = new HashMap<String,String>();
    	map.put("model", "Motorola Droid Razr");
    	map.put("company", "Motorola");
    	map.put("price", "$834.99");
    	list.add(map);
    	map = new HashMap<String,String>();
    	map.put("model", "HTC Evo 3D");
    	map.put("company", "HTC");
        map.put("price", "$749.99");
    	list.add(map);
    	map = new HashMap<String,String>();
    	map.put("model", "Apple iPhone 4S 16GB");
    	map.put("company", "Apple");
    	map.put("price", "$644.99");
    	list.add(map);
    	map = new HashMap<String,String>();
    	map.put("model", "T-Mobile MyTouch 4G Slide");
    	map.put("company", "HTC");
    	map.put("price", "$239.99");
    	list.add(map);
    	map = new HashMap<String,String>();
    	map.put("model", "Samsung Galaxy S II");
    	map.put("company", "Samsung");
    	map.put("price", "$134.99");
    	list.add(map);
    	map = new HashMap<String,String>();
    	map.put("model", "Motorola Droid Bionic");
    	map.put("company", "Motorola");
    	map.put("price", "$939.99");
    	list.add(map);
    }
}