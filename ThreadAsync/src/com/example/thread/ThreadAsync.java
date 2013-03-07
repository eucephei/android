package com.example.thread;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import java.util.ArrayList;

public class ThreadAsync extends ListActivity {
	
	private static String[] items={
		"NUCLEAR REACTION TYPE",
		"Nuclear fission: Thermal reactors",
		"Nuclear fission: Fast neutron reactors",
		"Nuclear fusion",
		"COOLANT",
		"Pressurized water reactor (PWR)",
		"Boiling water reactor (BWR)",
		"Liquid metal cooled reactor",
		"Gas cooled reactors",
		"Molten Salt Reactors (MSRs)",
		"MODERATOR MATERIALS",
		"Graphite moderated reactors",
		"Heavy water reactors",
		"Light water moderated reactors (LWRs)",
		"Molten salt reactors (MSRs)",
		"Liquid metal cooled reactors",
		"TECHNOLOGY",
		"Pressurized Water Reactors (PWR)",
		"Boiling Water Reactors (BWR)",
		"Pressurized Heavy Water Reactor (PHWR)",
		"Reaktor Bolshoy Moschnosti Kanalniy (High Power Channel Reactor) (RBMK)",
		"Gas Cooled Reactor (GCR) and Advanced Gas Cooled Reactor (AGR)",
		"Liquid Metal Fast Breeder Reactor (LMFBR)",
		"Pebble Bed Reactors (PBR)",
		"Molten Salt Reactors",
		"Aqueous Homogeneous Reactor (AHR)",
		"The Integral Fast Reactor (IFR)",
		"High Temperature Gas Cooled Reactor (HTGCR)",
		"Small, sealed, transportable, autonomous reactor (SSTAR)",
		"Clean And Environmentally Safe Advanced Reactor (CAESAR)",
		"Hydrogen Moderated Self-regulating Nuclear Power Module (HPM)",
		"Advanced Heavy Water Reactor (AHWR)"
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		setListAdapter(
				new ArrayAdapter<String>(this,
								android.R.layout.simple_list_item_1,
								new ArrayList<String>()));
		new AddStringTask().execute();
	}
	
	class AddStringTask extends AsyncTask<Void, String, Void> {
		@Override
		protected Void doInBackground(Void... unused) {
			for (String item : items) {
				publishProgress(item);
				SystemClock.sleep(200);
			}
			
			return(null);
		}
		
		@SuppressWarnings("unchecked")
		@Override
		protected void onProgressUpdate(String... item) {
			((ArrayAdapter<String>)getListAdapter()).add(item[0]);
		}
		
		@Override
		protected void onPostExecute(Void unused) {
			Toast
				.makeText(ThreadAsync.this, 
						"Done - Finished updating Reactor List!",
						Toast.LENGTH_SHORT)
				.show();
		}
	}
}
