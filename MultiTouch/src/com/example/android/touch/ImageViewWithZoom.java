package com.example.android.touch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

public class ImageViewWithZoom extends View {
	private Drawable image;
	private float scaleFactor = 1.0f;
	private ScaleGestureDetector scaleGestureDetector;

	public ImageViewWithZoom(Context context) {
		super(context);
		image = context.getResources().getDrawable(R.drawable.keycue_icon);
		setFocusable(true);
		image.setBounds(0, 0, image.getIntrinsicWidth(),
				image.getIntrinsicHeight());
		scaleGestureDetector = new ScaleGestureDetector(context,
				new ScaleListener());
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// Set the image bounderies
		canvas.save();
		canvas.scale(scaleFactor, scaleFactor);
		image.draw(canvas);
		canvas.restore();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		scaleGestureDetector.onTouchEvent(event);
		invalidate();
		return true;
	}

	private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			scaleFactor *= detector.getScaleFactor();

			// min/max scale for the object.
			scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f));

			invalidate();
			return true;
		}
	}
}
