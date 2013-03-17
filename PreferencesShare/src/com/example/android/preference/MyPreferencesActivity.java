package com.example.android.preference;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.MenuItem;

public class MyPreferencesActivity extends PreferenceActivity {
	
	@Override 
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //addPreferencesFromResource(R.xml.preferences); // deprecated, use PreferenceFragment
        getFragmentManager().beginTransaction().replace(
        		android.R.id.content, new MyPreferenceFragment()).commit();
        getActionBar().setHomeButtonEnabled(true);
    }

    // This method is called once the menu is selected
    @Override 
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i("Menu", "OptionsMenu selected");
        switch (item.getItemId()) {
        	case android.R.id.home:
        		Intent intent = new Intent(this, OverviewActivity.class);
        		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        		startActivity(intent);
        		break;
        }
        return true;
    }

    public static class MyPreferenceFragment extends PreferenceFragment {
    	
    	@Override public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
    }
}