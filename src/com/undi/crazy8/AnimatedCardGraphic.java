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

	public AnimatedCardGraphic(Context context, float cardWidthPercent) {
		super(context, cardWidthPercent);
		anims = new LinkedList<CardAnimation>();
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
					callback.run();
				}
				iter.remove();
			}else{
				CardRef card = anim.getCard();
				drawCard(canvas, card, where.x, where.y);
			}
		}
		if(isAnimationRunning()){
			view.postInvalidate();
		}
	}
	
	public boolean isAnimationRunning(){
		return !anims.isEmpty();
	}
}
