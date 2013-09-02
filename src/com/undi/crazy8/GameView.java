package com.undi.crazy8;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {
	private CardGraphic cardGraphic;
	private Context context;

	public GameView(Context context) {
		super(context);
		this.context = context;
	}
	
	@Override
	protected void onDraw(Canvas canvas){
		float heightPercent = cardGraphic.getCardHeightPercent();
		/*
		cardGraphic.drawCardBack(canvas, 0.0f, 0.0f);
		cardGraphic.drawCardBack(canvas, 0.1f, 0.0f);
		cardGraphic.drawCardBack(canvas, 0.2f, 0.0f);
		cardGraphic.drawCardBack(canvas, 0.3f, 0.0f);
		cardGraphic.drawCardBack(canvas, 0.4f, 0.0f);
		cardGraphic.drawCardBack(canvas, 0.5f, 0.0f);
		cardGraphic.drawCard(canvas, new CardRef(CardRef.Suit.DIAMOND, CardRef.Rank.SIX), 0.0f, heightPercent);
		cardGraphic.drawCard(canvas, new CardRef(CardRef.Suit.DIAMOND, CardRef.Rank.SEVEN), 0.1f, heightPercent);
		cardGraphic.drawCard(canvas, new CardRef(CardRef.Suit.DIAMOND, CardRef.Rank.EIGHT), 0.2f, heightPercent);
		cardGraphic.drawCard(canvas, new CardRef(CardRef.Suit.DIAMOND, CardRef.Rank.NINE), 0.3f, heightPercent);
		cardGraphic.drawCard(canvas, new CardRef(CardRef.Suit.DIAMOND, CardRef.Rank.TEN), 0.4f, heightPercent);
		cardGraphic.drawCard(canvas, new CardRef(CardRef.Suit.DIAMOND, CardRef.Rank.JACK), 0.5f, heightPercent);

		cardGraphic.drawCard(canvas, new CardRef(CardRef.Suit.SPADE, CardRef.Rank.SIX), 0.0f, heightPercent * 2);
		cardGraphic.drawCard(canvas, new CardRef(CardRef.Suit.SPADE, CardRef.Rank.SEVEN), 0.1f, heightPercent * 2);
		cardGraphic.drawCard(canvas, new CardRef(CardRef.Suit.SPADE, CardRef.Rank.EIGHT), 0.2f, heightPercent * 2);
		cardGraphic.drawCard(canvas, new CardRef(CardRef.Suit.SPADE, CardRef.Rank.NINE), 0.3f, heightPercent * 2);
		cardGraphic.drawCard(canvas, new CardRef(CardRef.Suit.SPADE, CardRef.Rank.TEN), 0.4f, heightPercent * 2);
		cardGraphic.drawCard(canvas, new CardRef(CardRef.Suit.SPADE, CardRef.Rank.JACK), 0.5f, heightPercent * 2);

		cardGraphic.drawCard(canvas, new CardRef(CardRef.Suit.HEART, CardRef.Rank.SIX), 0.0f, heightPercent * 3);
		cardGraphic.drawCard(canvas, new CardRef(CardRef.Suit.HEART, CardRef.Rank.SEVEN), 0.1f, heightPercent * 3);
		cardGraphic.drawCard(canvas, new CardRef(CardRef.Suit.HEART, CardRef.Rank.EIGHT), 0.2f, heightPercent * 3);
		cardGraphic.drawCard(canvas, new CardRef(CardRef.Suit.HEART, CardRef.Rank.NINE), 0.3f, heightPercent * 3);
		cardGraphic.drawCard(canvas, new CardRef(CardRef.Suit.HEART, CardRef.Rank.TEN), 0.4f, heightPercent * 3);
		cardGraphic.drawCard(canvas, new CardRef(CardRef.Suit.HEART, CardRef.Rank.JACK), 0.5f, heightPercent * 3);

		cardGraphic.drawCard(canvas, new CardRef(CardRef.Suit.CLUB, CardRef.Rank.SIX), 0.0f, heightPercent * 4);
		cardGraphic.drawCard(canvas, new CardRef(CardRef.Suit.CLUB, CardRef.Rank.SEVEN), 0.1f, heightPercent * 4);
		cardGraphic.drawCard(canvas, new CardRef(CardRef.Suit.CLUB, CardRef.Rank.EIGHT), 0.2f, heightPercent * 4);
		cardGraphic.drawCard(canvas, new CardRef(CardRef.Suit.CLUB, CardRef.Rank.NINE), 0.3f, heightPercent * 4);
		cardGraphic.drawCard(canvas, new CardRef(CardRef.Suit.CLUB, CardRef.Rank.TEN), 0.4f, heightPercent * 4);
		cardGraphic.drawCard(canvas, new CardRef(CardRef.Suit.CLUB, CardRef.Rank.JACK), 0.5f, heightPercent * 4);
		*/
	}
	
	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh){
		if(cardGraphic == null){
			cardGraphic = CardGraphic.getInstance(this.context, 0.1f);
		}
		cardGraphic.onResize(w, h);
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
