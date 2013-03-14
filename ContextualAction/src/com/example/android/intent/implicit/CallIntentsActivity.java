package com.example.android.intent.implicit;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class CallIntentsActivity extends Activity {
	
	SharedPreferences preferences;
	
  protected Object mActionMode;
  protected Spinner spinner;
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Define the contextual action mode
    View view = findViewById(R.id.spinner);
    
    view.setOnLongClickListener(new View.OnLongClickListener() {
      // Called when the user long-clicks on someView
      public boolean onLongClick(View view) {
        if (mActionMode != null) {
          return false;
        }

        // Start the CAB using the ActionMode.Callback defined above
        mActionMode = CallIntentsActivity.this
            .startActionMode(mActionModeCallback);
        view.setSelected(true);
        return true;
      }
    });
    
    // Spinner for Implicit Intents
    spinner = (Spinner) findViewById(R.id.spinner);
    ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this,
        R.array.intents, android.R.layout.simple_spinner_item);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(adapter);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.mainmenu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    Toast.makeText(this, "Just a test", Toast.LENGTH_SHORT).show();
    return true;
  }

  private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

    // Called when the action mode is created; startActionMode() was called
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
    	
      // Inflate a menu resource providing context menu items
      MenuInflater inflater = mode.getMenuInflater();
      // Assumes "contexual.xml" menu resources
      inflater.inflate(R.menu.contextual, menu);
      
      return true;
    }

    // Always onCreateActionMode, but multiple times if the mode is invalidated.
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
    	// nothing done
    	return false; 
    }

    // Called when the user selects a contextual menu item
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
      switch (item.getItemId()) {
      	case R.id.toast:
      		Toast.makeText(CallIntentsActivity.this, "contextual action menu",
      				Toast.LENGTH_LONG).show();
      		mode.finish(); 
      		return true;
      	default:
      		return false;
      }
    }

    // Called when the user exits the action mode
    public void onDestroyActionMode(ActionMode mode) {
      mActionMode = null;
    }
  };

  public void onClick(View view) {
	    int position = spinner.getSelectedItemPosition();
	    Intent intent = null;
	    switch (position) {
	    case 0:
	      intent = new Intent(Intent.ACTION_VIEW,
	          Uri.parse("http://www.google.com"));
	      break;
	    case 1:
	      intent = new Intent(Intent.ACTION_CALL,
	          Uri.parse("tel:(+206)12345789"));
	      break;
	    case 2:
	      intent = new Intent(Intent.ACTION_DIAL,
	          Uri.parse("tel:(+625)12345789"));
	      startActivity(intent);
	      break;
	    case 3:
	      intent = new Intent(Intent.ACTION_VIEW,
	          Uri.parse("geo:38.899533,-77.036476"));
	      startActivity(intent);
	      break;
	    case 4:
	      intent = new Intent(Intent.ACTION_VIEW,
	    	  Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345"));
	      break;
	    case 5:
	      intent = new Intent("android.media.action.IMAGE_CAPTURE");
	      break;
	    case 6:
	      intent = new Intent(Intent.ACTION_VIEW,
	          Uri.parse("content://contacts/people/"));
	      break;
	    case 7:
	      intent = new Intent(Intent.ACTION_EDIT,
	          Uri.parse("content://contacts/people/1"));
	      break;

	    }
	    if (intent != null) {
	      startActivity(intent);
	    }
	  }

	  @Override
	  public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (resultCode == Activity.RESULT_OK && requestCode == 0) {
	      @SuppressWarnings("deprecation")
		String result = data.toURI();
	      Toast.makeText(this, result, Toast.LENGTH_LONG).show();
	    }
	  }
  
} 