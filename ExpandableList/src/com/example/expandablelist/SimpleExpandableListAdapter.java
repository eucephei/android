package com.example.expandablelist;

import java.util.List;
	 
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SimpleExpandableListAdapter extends AbstractExpandableListAdapter{

	public SimpleExpandableListAdapter( Context context, List<Group> groupData, 
										List<? extends List<Child>> childData) {
		super(context, groupData, childData);
	}
	
	@Override
	public View newChildView(boolean isLastChild, ViewGroup parent) {
		//received errors when using the other overloaded inflate methods
		//and when trying to attach the inflated layout to the parent
		return getInflater().inflate(R.layout.child_layout, parent, false);
	}
	
	@Override
	public void bindChildView(int childPosition, int groupPosition,
							  boolean isLastChild, View v, ViewGroup parent) {
		Child child = (Child) super.getChild(groupPosition, childPosition);
	
		TextView textView = (TextView) v.findViewById(R.id.textView);
		textView.setText(child.getText());
	 
		Button button1 = (Button) v.findViewById(R.id.button1);
		button1.setText(child.getButton1Text());
	 
		Button button2 = (Button) v.findViewById(R.id.button2);
		button2.setText(child.getButton2Text());
	}
	
	@Override
	public View newGroupView(boolean isExpanded, ViewGroup parent) {
		//received errors when using the other overloaded inflate methods
		//and when trying to attach the inflated layout to the parent
		return getInflater().inflate(R.layout.group_layout, parent, false);
	}
		 
	@Override
	public void bindGroupView(int groupPosition, boolean isExpanded, 
							  View v, ViewGroup parent) {
		Group group = (Group) super.getGroup(groupPosition);
	 
		TextView textView = (TextView) v.findViewById(R.id.textView);
		textView.setText(group.getText());
	}

}
