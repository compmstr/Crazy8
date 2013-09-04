package com.undi.crazy8;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.View;

public class AnimatedCardGraphic extends CardGraphic {
	
	List<CardAnimation> anims;
	List<Runnable> pendingCallbacks;

	public AnimatedCardGraphic(Context context, float cardWidthPercent) {
		super(context, cardWidthPercent);
		anims = new LinkedList<CardAnimation>();
		pendingCallbacks = new LinkedList<Runnable>();
	}
	

	public void animateCardBack(Point from, Point to, long durationMs, Runnable callback){
		animateCard(CardRef.CARD_BACK, from, to, durationMs, callback);
	}
	public void animateCard(CardRef card, Point from, Point to, long durationMs, Runnable callback){
		anims.add(new CardAnimation(card, from, to, durationMs, callback));
	}
	
	/**
	 * Draws all of the current animations, cleaning them up if they're finished
	 */
	public void drawAnimations(Canvas canvas, View view){
		long frameTime = System.currentTimeMillis();
		Iterator<CardAnimation> iter = anims.iterator();
		while(iter.hasNext()){
			CardAnimation anim = iter.next();
			Point where = anim.getCurPoint(frameTime);
			if(where == null){
				Runnable callback = anim.getCallback();
				if(callback != null){
					pendingCallbacks.add(callback);
				}
				iter.remove();
			}else{
				CardRef card = anim.getCard();
				drawCard(canvas, card, where.x, where.y);
			}
		}
		//We have to run callbacks separately so that the callbacks can
		//  add animations if needed, without coming up with a ConcurrentModificationException
		Iterator<Runnable> callbackIter = pendingCallbacks.iterator();
		while(callbackIter.hasNext()){ 
			callbackIter.next().run(); 
			callbackIter.remove();
		}
		if(isAnimationRunning()){
			view.postInvalidate();
		}
	}
	
	public boolean isAnimationRunning(){
		return !anims.isEmpty();
	}
}
