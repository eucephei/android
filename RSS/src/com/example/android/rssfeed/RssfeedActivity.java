package com.example.android.rssfeed;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class RssfeedActivity extends Activity implements
    ListFragment.OnItemSelectedListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_rssfeed);
  }

  @Override
  public void onRssItemSelected(String link) {
    DetailFragment fragment = (DetailFragment) getFragmentManager()
        .findFragmentById(R.id.detailFragment);
    
    if (fragment != null && fragment.isInLayout()) {
      fragment.setText(link);
    } else {
      Intent intent = new Intent(getApplicationContext(),
          DetailActivity.class);
      intent.putExtra(DetailActivity.EXTRA_URL, link);
      startActivity(intent);

    }
  }
  
  //Other methods which this class implements
  
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main_menu, menu);
    return true;
  }
  
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
    case R.id.action_refresh:
      Toast.makeText(this, "Action refresh selected", Toast.LENGTH_SHORT)
          .show();
      break;
    case R.id.action_settings:
      Toast.makeText(this, "Action Settings selected", Toast.LENGTH_SHORT)
          .show();
      break;

    default:
      break;
    }

    return true;
  }
 
} 
