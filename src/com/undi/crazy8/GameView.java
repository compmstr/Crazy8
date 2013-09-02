package com.undi.crazy8;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {
	private CardGraphic cardGraphic;
	private Context context;
	private float scale;
	private Paint whitePaint;
	private Crazy8Game game;
	private int screenH, screenW;
	private int movingCardIdx = -1;
	private int movingCardX, movingCardY;
	
	public GameView(Context context) {
		super(context);
		this.context = context;
		
		scale = context.getResources().getDisplayMetrics().scaledDensity;
		whitePaint = new Paint();
		whitePaint.setAntiAlias(true);
		whitePaint.setColor(Color.WHITE);
		whitePaint.setStyle(Paint.Style.STROKE);
		whitePaint.setTextAlign(Paint.Align.LEFT);
		//Set to roughly 15pt font, scaled for the screen
		whitePaint.setTextSize(scale * 15);
		
		game = new Crazy8Game();
		game.startGame();
	}
	
	@Override
	protected void onDraw(Canvas canvas){
		canvas.drawText("Player Score: " + Integer.toString(game.getPlayerScore()), 10, 
				screenH - whitePaint.getTextSize() - 10 , whitePaint);
		canvas.drawText("Computer Score: " + Integer.toString(game.getPlayerScore()), 10, 
				whitePaint.getTextSize() + 10 , whitePaint);
		
		//draw the player's hand
		int drawX = 5;
		int drawY = (int) (screenH - cardGraphic.getCardHeight() - whitePaint.getTextSize() - (50 * scale));
		List<CardRef> playerHand = game.getPlayerHand();
		int cardsToDraw = Math.min(7, playerHand.size());
		for(int i = 0; i < cardsToDraw; i++){
			if(i != movingCardIdx){
				CardRef card = playerHand.get(i);
				cardGraphic.drawCard(canvas, card, drawX, drawY);
			}
			drawX += cardGraphic.getCardWidth() + 5;
		}
		//draw the opponent's hand
		drawX = 5;
		drawY = (int) (whitePaint.getTextSize() + (50 * scale));
		cardsToDraw = game.getOppHand().size();
		for(int i = 0; i < cardsToDraw; i++){
			cardGraphic.drawCardBack(canvas, drawX, drawY);
			drawX += 5;
		}
		
		//Draw the draw pile
		drawX = (screenW / 2) - cardGraphic.getCardWidth() - 10;
		drawY = (screenH / 2) - (cardGraphic.getCardHeight() / 2);
		cardGraphic.drawCardBack(canvas, drawX, drawY);
		
		//Draw the discard pile
		if(!game.getDiscardPile().isEmpty()){
			drawX = (screenW / 2) + 10;
			drawY = (screenH / 2) - (cardGraphic.getCardHeight() / 2);
			cardGraphic.drawCard(canvas, game.getDiscardPile().get(0), drawX, drawY);
		}
		
		//Draw the moving card
		if(movingCardIdx != -1){
			cardGraphic.drawCard(canvas, game.getPlayerHand().get(movingCardIdx), 
					//movingCardX - cardGraphic.getCardWidth() / 2, movingCardY - cardGraphic.getCardHeight() / 2); //center of card
					//movingCardX, movingCardY); //top left of card
					movingCardX - cardGraphic.getCardWidth(), movingCardY - cardGraphic.getCardHeight());
		}
	}
	
	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh){
		if(cardGraphic == null){
			cardGraphic = CardGraphic.getInstance(this.context, 0.1f);
		}
		cardGraphic.onResize(w, h);
		screenH = h;
		screenW = w;
	}

	/**
	 * Returns the index into the player's hand under x, y.. or -1 if no card
	 * @param x
	 * @param y
	 * @return
	 */
	private int playerCardAtLocation(int x, int y){
		int startX = 5;
		int startY = (int) (screenH - cardGraphic.getCardHeight() - whitePaint.getTextSize() - (50 * scale));
		int height = cardGraphic.getCardHeight();
		//7 cards, plus 6 5-pixel gaps
		int cardWidth = cardGraphic.getCardWidth() + 5;
		int width = cardWidth * Math.min(7, game.getPlayerHand().size());
		if(x > startX && x <= startX + width &&
				y > startY && y <= startY + height){
			return (x - startX) / cardWidth;
		}
		return -1;
	}

	@Override
	public boolean onTouchEvent(MotionEvent evt){
		int eventAction = evt.getAction();
		int x = (int)evt.getX();
		int y = (int)evt.getY();
		
		switch(eventAction){
		case MotionEvent.ACTION_DOWN:
			if(game.isPlayerTurn()){
				movingCardIdx = playerCardAtLocation(x, y);
				movingCardX = x;
				movingCardY = y;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			movingCardX = x;
			movingCardY = y;
			break;
		case MotionEvent.ACTION_UP:
			movingCardIdx = -1;
			break;
		}
		
		invalidate();
		return true;
	}
}
