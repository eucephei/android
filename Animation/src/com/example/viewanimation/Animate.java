package com.example.viewanimation;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

public class Animate extends Activity implements
        AdapterView.OnItemClickListener, View.OnClickListener {
    private ListView mPhotosList;
    private ViewGroup mContainer;
    private ImageView mImageView;

    // Names of the photos we show in the list
    private static final String[] PHOTOS_NAMES = new String[] {
            "Antarctica",
            "Arctic",
            "Brazil",
            "Madagascar",
            "Mongolia"
    };

    // Resource identifiers for the photos we want to display
    private static final int[] PHOTOS_RESOURCES = new int[] {
            R.drawable.antarctica,
            R.drawable.arctic,
            R.drawable.brazil,
            R.drawable.madagascar,
            R.drawable.mongolia
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        mPhotosList = (ListView) findViewById(android.R.id.list);
        mImageView = (ImageView) findViewById(R.id.picture);
        mContainer = (ViewGroup) findViewById(R.id.container);

        // Prepare the ListView
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, PHOTOS_NAMES);

        mPhotosList.setAdapter(adapter);
        mPhotosList.setOnItemClickListener(this);

        // Prepare the ImageView
        mImageView.setClickable(true);
        mImageView.setFocusable(true);
        mImageView.setOnClickListener(this);

        // Since we are caching large views, we want to keep their cache
        // between each animation
        mContainer.setPersistentDrawingCache(ViewGroup.PERSISTENT_ANIMATION_CACHE);
    }

    /**
     * Setup a new 3D rotation on the container view.
     *
     * @param position the item that was clicked to show a picture, or -1 to show the list
     * @param start the start angle at which the rotation must begin
     * @param end the end angle of the rotation
     */
    private void applyRotation(int position, float start, float end) {
        // Find the center of the container
        final float centerX = mContainer.getWidth() / 2.0f;
        final float centerY = mContainer.getHeight() / 2.0f;

        // Create a new 3D rotation with the supplied parameter
        // The animation listener is used to trigger the next animation
        final Rotation rotation =
                new Rotation(start, end, centerX, centerY, 310.0f, true);
        
        rotation.setDuration(500);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());
        rotation.setAnimationListener(new DisplayNextView(position));

        mContainer.startAnimation(rotation);
    }

    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
    	
        // Preload the image then start the animation
        mImageView.setImageResource(PHOTOS_RESOURCES[position]);
        applyRotation(position, 0, 90);
    }

    public void onClick(View v) {
        applyRotation(-1, 180, 90);
    }

    /**
     * This class listens for the end of the first half of the animation.
     * It then posts a new action that effectively swaps the views when the container
     * is rotated 90 degrees and thus invisible.
     */
    private final class DisplayNextView implements Animation.AnimationListener {
        private final int mPosition;

        private DisplayNextView(int position) {
            mPosition = position;
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
            mContainer.post(new SwapViews(mPosition));
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    /**
     * This class is responsible for swapping the views and start the second
     * half of the animation.
     */
    private final class SwapViews implements Runnable {
        private final int mPosition;

        public SwapViews(int position) {
            mPosition = position;
        }

        public void run() {
            final float centerX = mContainer.getWidth() / 2.0f;
            final float centerY = mContainer.getHeight() / 2.0f;
            Rotation rotation;
            
            if (mPosition > -1) {
                mPhotosList.setVisibility(View.GONE);
                mImageView.setVisibility(View.VISIBLE);
                mImageView.requestFocus();

                rotation = new Rotation(90, 180, centerX, centerY, 310.0f, false);
            } else {
                mImageView.setVisibility(View.GONE);
                mPhotosList.setVisibility(View.VISIBLE);
                mPhotosList.requestFocus();

                rotation = new Rotation(90, 0, centerX, centerY, 310.0f, false);
            }

            rotation.setDuration(5000);
            rotation.setFillAfter(true);
            rotation.setInterpolator(new DecelerateInterpolator());

            mContainer.startAnimation(rotation);
        }
    }

}