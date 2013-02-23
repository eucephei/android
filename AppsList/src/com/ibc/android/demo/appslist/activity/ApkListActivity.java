package com.ibc.android.demo.appslist.activity;

import java.util.List;

import com.ibc.android.demo.appslist.adapter.ApkAdapter;
import com.ibc.android.demo.appslist.app.AppData;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ApkListActivity extends Activity
			implements OnItemClickListener {

	PackageManager packageManager;
	ListView apkList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		packageManager = getPackageManager();
		List<PackageInfo> packageList = packageManager
				.getInstalledPackages(PackageManager.GET_PERMISSIONS);

		apkList = (ListView) findViewById(R.id.applist);
		apkList.setAdapter(new ApkAdapter(this, packageList, packageManager));

		apkList.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long row) {
		PackageInfo packageInfo = (PackageInfo) parent
				.getItemAtPosition(position);
		AppData appData = (AppData) getApplicationContext();
		appData.setPackageInfo(packageInfo);

		Intent appInfo = new Intent(getApplicationContext(), ApkInfo.class);
		startActivity(appInfo);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	//	getMenuInflater().inflate(R.menu.activity_apk_list, menu);
		return true;
	}
}