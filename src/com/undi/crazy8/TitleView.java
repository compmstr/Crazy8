package com.undi.crazy8;

import com.undi.crazy8.ui.UIButton;
import com.undi.crazy8.ui.UICallback;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;

public class TitleView extends View {

	private Bitmap titleGraphic;
	UIButton playButton;
	private int screenW, screenH;
	Rect srcRect = new Rect();
	Rect destRect = new Rect();
	Context savedContext;
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public TitleView(Context context) {
		super(context);
		this.savedContext = context;
		/*
		int hideStatusFlag;
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
				hideStatusFlag = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
			}else{
				hideStatusFlag = View.STATUS_BAR_HIDDEN;
			}
			setSystemUiVisibility(hideStatusFlag);
		}
		*/
		playButton = new UIButton(BitmapFactory.decodeResource(getResources(), R.drawable.play_button),
				0.25, 0.7, 0.5,
				screenW, screenH,
				new UICallback() {
					@Override
					public void run(Object... args) {
						//Run the game View
						Intent gameIntent = new Intent(savedContext, GameActivity.class);
						//Can use <Intent>.putExtra to send data to new activity
						savedContext.startActivity(gameIntent);
					}
				});
	}
	
	@Override
	protected void onDraw(Canvas canvas){
		if(titleGraphic != null){
			int titleX = (screenW - titleGraphic.getWidth()) / 2;
			canvas.drawBitmap(titleGraphic, titleX, 0, null);
		}
		if(playButton != null) playButton.draw(canvas);
	}
	
	private Bitmap scaleBitmapToWidthRelative(Bitmap src, double targetWidth){
		int newW = (int) (screenW * targetWidth);
		int newH = (int) (newW * ( src.getHeight() / (double)src.getWidth() ));
		return Bitmap.createScaledBitmap(src, newW, newH, false);
	}
	
	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh){
		super.onSizeChanged(w, h, oldw, oldh);
		this.screenW = w;
		this.screenH = h;
		if(titleGraphic != null){
			titleGraphic.recycle();
			titleGraphic = null;
		}
		Bitmap rawGraphic = BitmapFactory.decodeResource(getResources(), R.drawable.title_graphic);
		titleGraphic = scaleBitmapToWidthRelative(rawGraphic, 0.75);
		playButton.rescaleGraphic(w, h);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent evt){
		int eventAction = evt.getAction();
		int x = (int)evt.getX();
		int y = (int)evt.getY();
		
		switch(eventAction){
		case MotionEvent.ACTION_DOWN:
			playButton.checkPressed(x, y);
			break;
		case MotionEvent.ACTION_MOVE:
			playButton.checkPressed(x, y);
			break;
		case MotionEvent.ACTION_UP:
			playButton.checkReleased(x, y);
			break;
		}
		
		invalidate();
		return true;
	}

}
