package com.undi.crazy8;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {

	public GameView(Context context) {
		super(context);
	}
	
	@Override
	protected void onDraw(Canvas canvas){
		
	}
	

	@Override
	public boolean onTouchEvent(MotionEvent evt){
		int eventAction = evt.getAction();
		int x = (int)evt.getX();
		int y = (int)evt.getY();
		
		switch(eventAction){
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
			break;
		}
		
		invalidate();
		return true;
	}
}
