package com.example.android.touch;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.Menu;

public class MultiTouchActivity extends Activity implements OnTouchListener {

	public final int MAX_POINTERS = 5;

	private MultiTouchTrackingView touchView;
	Paint paint;
	Paint paintInfoText;

	PointF[] points;
	private int[] lastActions;
	int pointerCount;

	private float displayDensity;
	int radius;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		displayDensity = getResources().getDisplayMetrics().density;
		points = new PointF[MAX_POINTERS];
		lastActions = new int[MAX_POINTERS];

		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setTextSize(calcDevicePixels(15));

		paintInfoText = new Paint();
		paintInfoText.setColor(Color.BLACK);
		paintInfoText.setAntiAlias(true);
		paintInfoText.setTextSize(calcDevicePixels(18));

		touchView = new MultiTouchTrackingView(this, this);
		touchView.setOnTouchListener(this);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(touchView);

		radius = calcDevicePixels(45);
	}
	
	public int calcDevicePixels(int deviceIndependentPixel) {
		return (int) (deviceIndependentPixel * displayDensity + 0.5f);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int action = event.getAction() & MotionEvent.ACTION_MASK;
		int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
		pointerCount = event.getPointerCount();

		int actionId = event.getPointerId(pointerIndex);
		Log.d("ball", "MotionEvent - pointer ID:  " + actionId
				+ ", action: " + mapActionCodeToString(action)
				+ ", pointer count: " + pointerCount);
		if (actionId < MAX_POINTERS) {
			lastActions[actionId] = action;
		}

		for (int i = 0; i < pointerCount; i++) {
			int pointerId = event.getPointerId(i);
			if (pointerId < MAX_POINTERS) {
				points[pointerId] = new PointF(event.getX(i), event.getY(i));
				if (action == MotionEvent.ACTION_MOVE) 
					lastActions[pointerId] = action;
			}
		}

		touchView.invalidate();
		return true;
	}

	public int getColor(int pointerId) {
		int color;
		switch (lastActions[pointerId]) {
		case MotionEvent.ACTION_DOWN:
			color = 0xaa2F2F2F; // BLACK
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			color = 0xaaCCB647; // BROWN
			break;
		case MotionEvent.ACTION_UP:
			color = 0xaaD6DEE4; // GREY
			break;
		case MotionEvent.ACTION_POINTER_UP:
			color = 0xaa98C5AB; // LIGHT GREEN 
			break;
		case MotionEvent.ACTION_MOVE:
			color = 0xaa7EB5D6; // LIGHT BLUE
			break;
		default:
			color = 0xaa274257; // DARK BLUE
		}
		return color;
	}

	public String getActionText(int pointerId) {
		String action = mapActionCodeToString(lastActions[pointerId]);
		return pointerId + ": " + action;
	}

	private String mapActionCodeToString(int actionCode) {
		String action;
		switch (actionCode) {
		case MotionEvent.ACTION_DOWN:
			action = "Down";
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			action = "Pointer Down";
			break;
		case MotionEvent.ACTION_UP:
			action = "Up";
			break;
		case MotionEvent.ACTION_POINTER_UP:
			action = "Pointer Up";
			break;
		case MotionEvent.ACTION_MOVE:
			action = "Move";
			break;
		default:
			action = "Other (" + actionCode + ")";
		}
		return action;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
