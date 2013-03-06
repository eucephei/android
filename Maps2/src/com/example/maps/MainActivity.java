package com.example.maps;

import java.util.Timer;
import java.util.TimerTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;
import android.app.FragmentTransaction;

public class MainActivity extends FragmentActivity {

	//Handle the Fragment
	private MapFragment frag;
	private GoogleMap map;
	
	private static final LatLng BRISBANE = new LatLng(-27.47093, 153.0235);
	private static final LatLng MELBOURNE = new LatLng(-37.81319, 144.96298);
	private static final LatLng SYDNEY = new LatLng(-33.87365, 151.20689);
	private static final LatLng ADELAIDE = new LatLng(-34.92873, 138.59995);
	private static final LatLng PERTH = new LatLng(-31.952854, 115.857342);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//Create a new MapFragment
		//Google said we should use this method instead of new MapFragment()
		frag = MapFragment.newInstance();

		//Add the MapFragment to the view
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.add(R.id.map, frag).commit();

		//Make a timer to wait for the map to initialize
		final Timer t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				if (frag != null && frag.isVisible()) {
					t.cancel();
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							//Get the Map Object from the MapFragment
							map = frag.getMap();
							if (map != null) {
								//If it is not null
								//Move the map camera to the Latitude and Longitude provided
								map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(22.3d, 114.1667d), 13));

								//Set the Google Map type
								map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
								//Set shown the my location
								map.setMyLocationEnabled(true);
								
								//Add markers by the options
								addMarkersToMap();
										
								map.setOnMarkerClickListener(new OnMarkerClickListener() {

									@Override
									public boolean onMarkerClick(Marker marker) {
										//Show a Toast
										Toast.makeText(getApplicationContext(), "Title:"+marker.getTitle()+"\nMessage:"+marker.getSnippet(), Toast.LENGTH_SHORT).show();
										return false;
									}
									
								});
							}
						}});
				}
			}
		}, 0, 200);
	}
	
	private void addMarkersToMap() {
	    // Uses a colored icon.
	   map.addMarker(new MarkerOptions()
	            .position(BRISBANE)
	            .title("Brisbane")
	            .snippet("Population: 2,074,200")
	            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
	    // Uses a custom icon.
	    map.addMarker(new MarkerOptions()
	            .position(SYDNEY)
	            .title("Sydney")
	            .snippet("Population: 4,627,300")
	            .icon(BitmapDescriptorFactory.fromResource(R.drawable.arrow)));
	    // Creates a dragable marker. Long press to drag.
	    map.addMarker(new MarkerOptions()
	            .position(MELBOURNE)
	            .title("Melbourne")
	            .snippet("Population: 4,137,400")
	            .draggable(true));
	    // A few more markers for good measure.
	    map.addMarker(new MarkerOptions()
	            .position(PERTH)
	            .title("Perth")
	            .snippet("Population: 1,738,800"));
	    map.addMarker(new MarkerOptions()
	            .position(ADELAIDE)
	            .title("Adelaide")
	            .snippet("Population: 1,213,000"));
	    
	    // Creates a marker rainbow demonstrating how to create default marker icons of different
	    // hues (colors).
	    int numMarkersInRainbow = 12;
	    for (int i = 0; i < numMarkersInRainbow; i++) {
	        map.addMarker(new MarkerOptions()
	                .position(new LatLng(
	                        -30 + 10 * Math.sin(i * Math.PI / (numMarkersInRainbow - 1)),
	                        135 - 10 * Math.cos(i * Math.PI / (numMarkersInRainbow - 1))))
	                .title("Marker " + i)
	                .icon(BitmapDescriptorFactory.defaultMarker(i * 360 / numMarkersInRainbow)));
	    }
	}

}
