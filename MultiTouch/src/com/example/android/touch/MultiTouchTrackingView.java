package com.example.android.touch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;

@SuppressLint("ViewConstructor")
class MultiTouchTrackingView extends ImageViewWithZoom {

	private final MultiTouchActivity mta;

	public MultiTouchTrackingView(MultiTouchActivity mta,
			Context context) {
		super(context);
		this.mta = mta;
		setBackgroundColor(Color.WHITE);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		for (int i = 0; i < mta.MAX_POINTERS; i++) {
			PointF point = this.mta.points[i];
			if (point != null) {
				this.mta.paint.setColor(this.mta
						.getColor(i));
				canvas.drawCircle(point.x, point.y,
						this.mta.radius,
						this.mta.paint);
				String text = this.mta.getActionText(i);
				float textWidth = this.mta.paint
						.measureText(text);
				canvas.drawText(text, point.x - textWidth / 2, point.y
						+ this.mta.radius
						+ this.mta.calcDevicePixels(8),
						this.mta.paint);
			}
		}
		canvas.drawText("Pointer Count: "
				+ this.mta.pointerCount, 10,
				this.mta.calcDevicePixels(30),
				this.mta.paintInfoText);
	}
}