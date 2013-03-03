package com.example.viewpager;

import android.app.Activity;  
import android.os.Bundle;  
import android.support.v4.view.ViewPager;  
import android.view.View;  
import android.view.View.OnClickListener;  
import android.widget.ImageView;  
import android.widget.LinearLayout;  
import android.widget.TextView;  
  
public class GalleryPagerActivity extends Activity {  
       
     // mainLayout is the child of the HorizontalScrollView ...  
     private LinearLayout mainLayout;  
       
     // this is an array that holds the IDs of the drawables ...  
     private int[] images = {
             R.drawable.everest1, R.drawable.everest2,
             R.drawable.everest3, R.drawable.everest4,
             R.drawable.everest5, R.drawable.everest1_160x160, 
             R.drawable.everest2_160x160, R.drawable.everest3_160x160,
             R.drawable.everest4_160x160, R.drawable.everest5_160x160,
             R.drawable.everest1, R.drawable.everest2,
       };
     
     private View cell;  
     private TextView text;  
     private ViewPager viewPager;  
       
     @Override  
     public void onBackPressed() { 
          if(viewPager != null && viewPager.isShown()){  
               viewPager.setVisibility(View.GONE);  
          } else {  
               super.onBackPressed();  
          }  
     }  
       
  /** Called when the activity is first created. */  
  @Override  
  public void onCreate(Bundle icicle) {  
    super.onCreate(icicle);  
    setContentView(R.layout.main);  
  
    viewPager = (ViewPager) findViewById(R.id._viewPager);  
    mainLayout = (LinearLayout) findViewById(R.id._linearLayout);  
      
    for (int i = 0; i < images.length; i++) {  
                 
         cell = getLayoutInflater().inflate(R.layout.cell, null);  
           
         final ImageView imageView = (ImageView) cell.findViewById(R.id._image);  
         imageView.setOnClickListener(new OnClickListener() {  
                      
                    @Override  
                    public void onClick(View v) {  
                           
                         viewPager.setVisibility(View.VISIBLE);  
                         viewPager.setAdapter
                         (new GalleryPagerAdapter(GalleryPagerActivity.this, images));  
                         viewPager.setCurrentItem(v.getId());  
                    }  
               });  
           
         imageView.setId(i);  
         imageView.setImageResource(images[i]);  
         text = (TextView) cell.findViewById(R.id._imageName);  
         text.setText("Everest#"+(i+1));  
           
         mainLayout.addView(cell);  
      }  
  }  
}  