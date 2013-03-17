package com.example.android.preference;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ShareActionProvider;
import android.widget.Toast;

public class OverviewActivity extends Activity {

    SharedPreferences preferences;
    ShareActionProvider mShareActionProvider;
    
    @Override 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Initialize preferences
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        
        Button button = (Button)findViewById(R.id.Button01);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String username = preferences.getString("username", "n/a");
                String password = preferences.getString("password", "n/a");
                showPrefs(username, password);
            }
        });

        Button buttonChangePreferences = (Button)findViewById(R.id.Button02);
        buttonChangePreferences.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                updatePreferenceValue();
            }
        });
    }

    private void showPrefs(String username, String password) {
        Toast.makeText(OverviewActivity.this, "Username: " + username + "; password: " + password, Toast.LENGTH_LONG).show();

    }

    private void updatePreferenceValue() {
        Editor edit = preferences.edit();
        String username = preferences.getString("username", "n/a");
        
        // Revert the current user name and save again
        StringBuffer buffer = new StringBuffer();
        for (int i = username.length() - 1; i >= 0; i--) {
            buffer.append(username.charAt(i));
        }
        edit.putString("username", buffer.toString());
        edit.commit();
        
        // Quick feedback message for the user.
        Toast.makeText(OverviewActivity.this, "Reverted string sequence of username.", Toast.LENGTH_LONG).show();
    }
    
    
    @Override 
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate menu resource file.
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        
        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) item.getActionProvider();
        mShareActionProvider.setShareHistoryFileName(
     	       ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
        mShareActionProvider.setShareIntent(createShareIntent());
        
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch (item.getItemId()) {
            // Have only 1 menu option visible
            case R.id.preferences:
            	// Launch Preference activity
            	i = new Intent(OverviewActivity.this, MyPreferencesActivity.class);
            	startActivity(i);
            	
            	// Some feedback to the user
            	Toast.makeText(OverviewActivity.this, "Enter your user credentials.", Toast.LENGTH_LONG).show();
            	break;
            case R.id.preferences2:
      	      	i = new Intent(Intent.ACTION_VIEW,
      	      			Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345"));
      	        startActivity(i);
      	      	break;
        }
        
        return true;
    }
    
    // Call to update the share intent
    void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }
    
    private Intent createShareIntent() {
    	  Intent shareIntent = new Intent(Intent.ACTION_SEND);
    	  shareIntent.setType("text/plain");
    	  shareIntent.putExtra(Intent.EXTRA_TEXT, "http://android-er.blogspot.com/");
    	  
    	  return shareIntent;
    }
}