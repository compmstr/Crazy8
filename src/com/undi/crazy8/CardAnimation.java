package com.undi.crazy8;

import android.graphics.Point;

public class CardAnimation {
	private final Point from, to;
	private final long timeStart, durationMs;
	private final CardRef card;
	private final Runnable callback;

	/**
	 * A card animation
	 * @param card - the CardRef to draw
	 * @param from - point to start the animation
	 * @param to - point to end the animation
	 * @param durationMs
	 * @param callback - Callback to run on the end of the animation
	 */
	public CardAnimation(CardRef card, Point from, Point to, long durationMs){
		this(card, from, to, durationMs, null);
	}
	public CardAnimation(CardRef card, Point from, Point to, long durationMs, Runnable callback){
		this.card = card;
		this.from = from;
		this.to = to;
		this.durationMs = durationMs;
		this.timeStart = System.currentTimeMillis();
		this.callback = callback;
	}

	/**
	 * Returns the current interpolated point for this animation
	 * Returns null if we're past the end of the animation
	 * @param frameTime
	 * @return
	 */
	public Point getCurPoint(long frameTime){
		double interpPoint = (frameTime - timeStart) / (double) durationMs;

		if(interpPoint <= 1.0){
			Point ret = new Point();
			ret.x = (int) (from.x + ((to.x - from.x) * interpPoint));
			ret.y = (int) (from.y + ((to.y - from.y) * interpPoint));
			return ret;
		}else{
			return null;
		}
	}
	
	public CardRef getCard(){ return card; }
	public Runnable getCallback(){ return callback; }
}
