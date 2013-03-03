package com.example.expandablelist;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.BaseExpandableListAdapter;

import java.util.List;

public abstract class AbstractExpandableListAdapter extends BaseExpandableListAdapter {

	private List<?> groupData;
	//the outer list uses the position of the group in groupData
	private List<? extends List<?>> childData; 
	//the inner list contains that group's children
	private LayoutInflater inflater;
	
	public AbstractExpandableListAdapter(Context context, 
								 List<?> groupData, 
								 List<? extends List<?>> childData) {
		this.groupData = groupData;
		this.childData = childData;
		this.inflater = LayoutInflater.from(context);
	}
	
	public Object getChild(int groupPosition, int childPosition) {
		return childData.get(groupPosition).get(childPosition);
	}
 
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}
 
	public View getChildView(int groupPosition, int childPosition, 
							 boolean isLastChild, View convertView, ViewGroup parent) {
		View v;
		
		if (convertView == null) {
			v = newChildView(isLastChild, parent);
		} else {
			v = convertView;
		}
		bindChildView(childPosition, groupPosition, isLastChild, v, parent);
		return v;
	}
	
	/**
	 * Instantiates a new View for a child.
	 * @param parent The eventual parent of this new View.
	 * @return A new child View
	 */
	public abstract View newChildView(boolean isLastChild, ViewGroup parent);
	
	/**
     * @param childPosition Position of the child in the childData list
	 * @param groupPosition Position of the child's group in the groupData list
	 * @param v The view to bind data to
	 * @param parent The eventual parent of v.
	 */
	public abstract void bindChildView( int childPosition, int groupPosition, 
										boolean isLastChild, View v, ViewGroup parent);
	
	public int getChildrenCount(int groupPosition) {
		return childData.get(groupPosition).size();
	}
	
	public Object getGroup(int groupPosition) {
		return groupData.get(groupPosition);
	}
	
	public int getGroupCount() {
		return groupData.size();
	}
		
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}
	
	public View getGroupView(int groupPosition, boolean isExpanded, 
							 View convertView, ViewGroup parent) {
		View v;
		if (convertView == null) {
			v = newGroupView(isExpanded, parent);
		} else {
			v = convertView;
		}
		bindGroupView(groupPosition, isExpanded, v, parent);
		
		return v;
	}
	
	/**
	 * Instantiates a new View for a group.
	 * @param isExpanded Whether the group is currently expanded.
	 * @param parent The eventual parent of this new View.
	 * @return A new group View
	 */
	public abstract View newGroupView(boolean isExpanded, ViewGroup parent);
	
	/**
	 * @param groupPosition Position of the group in the groupData list
	 * @param isExpanded Whether the group is currently expanded.
     * @param v The view to bind data to
	 * @param parent The eventual parent of v.
	 */
	public abstract void bindGroupView(int groupPosition, boolean isExpanded,
		View v, ViewGroup parent);
	 
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	public boolean hasStableIds() {
		return true;
	}

	public LayoutInflater getInflater() {
		return inflater;
	}	
}
