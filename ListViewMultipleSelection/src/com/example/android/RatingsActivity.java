package com.example.android;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class RatingsActivity extends ListActivity {
	
	float INIT_RATING = 2.5f;
	float MAX_RATING = 5.0f;
	
	RatingBar avgRating;
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

        Bundle b = getIntent().getExtras();
        String[] resultArr = b.getStringArray("selectedItems");	
        
		ArrayList<RowModel> list = new ArrayList<RowModel>();
		for (String s : resultArr) {
			list.add(new RowModel(s));
		}
		
		setListAdapter(new RatingAdapter(list));
	}

	
	private RowModel getModel(int position) {
		return(((RatingAdapter)getListAdapter()).getItem(position));
	}
	
	// String[] to list of RowModel objects
	class RatingAdapter extends ArrayAdapter<RowModel> {
		
		float avgRate = INIT_RATING;
		
		RatingAdapter(ArrayList<RowModel> list) {
			super(RatingsActivity.this, R.layout.row, list);
		}
		
		@SuppressLint("UseValueOf")
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			ViewWrapper wrapper;
			RatingBar rate;		
			
			// ArrayAdapter subclass sees if convertView is null
			if (row == null) {
				
				// Create a new row by inflating a simple layout and also attach a ViewWrapper
				LayoutInflater inflater = getLayoutInflater();
				row = inflater.inflate(R.layout.row, parent, false);
				wrapper = new ViewWrapper(row);
				row.setTag(wrapper);
				
				rate=wrapper.getRatingBar();
				RatingBar.OnRatingBarChangeListener l =
				          new RatingBar.OnRatingBarChangeListener() {
					
				// add an anonymous onRatingChanged() listener that looks at the row's tag,
				// getTag() and converts that into an Integer
				public void onRatingChanged(RatingBar ratingBar, 
							float rating, boolean fromTouch) {
						Integer myPosition = (Integer)ratingBar.getTag();
						
						//  rating bar can get the actual RowModel for the row
						RowModel model = getModel(myPosition);
						model.rating = rating;
						LinearLayout parent = (LinearLayout)ratingBar.getParent();
						TextView label = (TextView)parent.findViewById(R.id.label);
						label.setText(model.toString());
						
						// Toast average rating
						DecimalFormat decimalFormat = new DecimalFormat("#.#");
						avgRate = Float.valueOf(decimalFormat.format((avgRate * model.count + rating)
								/ ++model.count));
						Toast.makeText(RatingsActivity.this,
								model.count + " Ratings Average: " + avgRate, Toast.LENGTH_SHORT).show();
					}
				};
				
				rate.setOnRatingBarChangeListener(l);
			}
			else {
				// RatingBar has the proper contents and has a tag via setTag()
				wrapper =(ViewWrapper)row.getTag();
				rate = wrapper.getRatingBar();
			}

			RowModel model = getModel(position);
			
			wrapper.getLabel().setText(model.toString());
			rate.setTag(new Integer(position));
			rate.setRating(model.rating);
			
			return(row);
		}
	}
	

	@SuppressLint("DefaultLocale")
	class RowModel {
		
		String label;
		float rating = INIT_RATING;
		int count;
		
		RowModel(String label) {
			this.label = label;
		}
		
		public String toString() {
			if (rating >= MAX_RATING) {
				return(label.toUpperCase());
			}	
			return(label);
		}
	}
	
}
