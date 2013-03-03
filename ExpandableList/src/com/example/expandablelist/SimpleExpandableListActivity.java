package com.example.expandablelist;

import java.util.ArrayList;
import android.app.ExpandableListActivity;
import android.os.Bundle;

public class SimpleExpandableListActivity extends ExpandableListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 
		ArrayList<Group> groupData = new ArrayList<Group>();
		ArrayList<ArrayList<Child>> childData = new ArrayList<ArrayList<Child>>();
	
		
		groupData.add(new Group("Vacations"));
		ArrayList<Child> children = new ArrayList<Child>();

		children.add(new Child("Neuschwanstein Castle", "Buy Ticket", "View"));
		children.add(new Child("Interlaken Schweiz", "Buy Ticket", "View"));
		childData.add(children);
		
		groupData.add(new Group("Hotels"));
		children = new ArrayList<Child>();
		children.add(new Child("The Lugger, Cornwall", "Reserve", "Cancel"));
		children.add(new Child("Mama Shelter, Paris", "Reserve", "Cancel"));
		children.add(new Child("Hotel Alpenrose,Wengen", "Reserve", "Cancel"));
		childData.add(children);

			 
		groupData.add(new Group("Airlines"));
		children = new ArrayList<Child>();
		children.add(new Child("Blue", "+1", "-1"));
		children.add(new Child("Red", "+1", "-1"));
		children.add(new Child("Green", "+1", "-1"));
		childData.add(children);
		
		SimpleExpandableListAdapter listAdapter =
				new SimpleExpandableListAdapter(this, groupData, childData);
		
		this.setListAdapter(listAdapter);
	}
}
