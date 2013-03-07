package com.example.constants;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;

public class PlanetConstants extends ListActivity {
	private static final int ADD_ID  =  Menu.FIRST;
	//private static final int EDIT_ID  =  Menu.FIRST+1;
	private static final int DELETE_ID  =  Menu.FIRST+2;
	private static final int CLOSE_ID  =  Menu.FIRST+3;
	private static final String[] PROJECTION  =  new String[] {
			Provider.Constants._ID, Provider.Constants.TITLE,
			Provider.Constants.VALUE};
	private Cursor constantsCursor;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		constantsCursor = getContentResolver().query(
				Provider.Constants.CONTENT_URI, PROJECTION, null, null, null);
				
		ListAdapter adapter = 
			new SimpleCursorAdapter(this,
				R.layout.row, constantsCursor,
				new String[] {Provider.Constants.TITLE,
				Provider.Constants.VALUE},
				new int[] {R.id.title, R.id.value},
				0);
		
		setListAdapter(adapter);
		registerForContextMenu(getListView());
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		constantsCursor.close();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, ADD_ID, Menu.NONE, "Add")
				.setIcon(R.drawable.add)
				.setAlphabeticShortcut('a');
		menu.add(Menu.NONE, CLOSE_ID, Menu.NONE, "Close")
				.setIcon(R.drawable.eject)
				.setAlphabeticShortcut('c');

		return(super.onCreateOptionsMenu(menu));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case ADD_ID:
				add();
				return(true);
				
			case CLOSE_ID:
				finish();
				return(true);
		}

		return(super.onOptionsItemSelected(item));
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
						ContextMenu.ContextMenuInfo menuInfo) {
		menu.add(Menu.NONE, DELETE_ID, Menu.NONE, "Delete")
				.setIcon(R.drawable.delete)
				.setAlphabeticShortcut('d');
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case DELETE_ID:
				AdapterView.AdapterContextMenuInfo info = 
					(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

				delete(info.id);
				return(true);
		}

		return(super.onOptionsItemSelected(item));
	}
	
	private void add() {
		LayoutInflater inflater = LayoutInflater.from(this);
		View addView = inflater.inflate(R.layout.add_edit, null);
		final DialogWrapper wrapper = new DialogWrapper(addView);
		
		new AlertDialog.Builder(this)
			.setTitle(R.string.add_title)
			.setView(addView)
			.setPositiveButton(R.string.ok,
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
										int whichButton) {
					processAdd(wrapper);
				}
			})
			.setNegativeButton(R.string.cancel,
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
										int whichButton) {
					// ignore, just dismiss
				}
			})
			.show();
	}
	
	private void delete(final long rowId) {
		if (rowId>0) {
			new AlertDialog.Builder(this)
				.setTitle(R.string.delete_title)
				.setPositiveButton(R.string.ok,
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
										int whichButton) {
						processDelete(rowId);
					}
				})
				.setNegativeButton(R.string.cancel,
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
												int whichButton) {
					// ignore, just dismiss
					}
				})
				.show();
		}
	}
	
	private void processAdd(DialogWrapper wrapper) {
		ContentValues values = new ContentValues(2);
		
		values.put(Provider.Constants.TITLE, wrapper.getTitle());
		values.put(Provider.Constants.VALUE, wrapper.getValue());
		
		getContentResolver()
			.insert(Provider.Constants.CONTENT_URI, values);
		
		while(constantsCursor.moveToNext()){}
		if(constantsCursor!=null) constantsCursor.close();

		
	}
	
	private void processDelete(long rowId) {
		Uri uri = ContentUris
			.withAppendedId(Provider.Constants.CONTENT_URI, rowId);
		getContentResolver().delete(uri, null, null);
		
		while(constantsCursor.moveToNext()){}
		if(constantsCursor!=null) constantsCursor.close();
		// constantsCursor.requery();
	}
	
	class DialogWrapper {
		EditText titleField = null;
		EditText valueField = null;
		View base = null;
		
		DialogWrapper(View base) {
			this.base = base;
			valueField = (EditText)base.findViewById(R.id.value);
		}
		
		String getTitle() {
			return(getTitleField().getText().toString());
		}
		
		float getValue() {
			return Float.valueOf(getValueField().getText().toString());
		}
		
		private EditText getTitleField() {
			if (titleField == null) {
				titleField = (EditText)base.findViewById(R.id.title);
			}
			
			return(titleField);
		}
		
		private EditText getValueField() {
			if (valueField == null) {
				valueField = (EditText)base.findViewById(R.id.value);
			}
			
			return(valueField);
		}
	}
}