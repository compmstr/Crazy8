package com.undi.crazy8;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Singleton class to hold the card graphics
 * @author corey
 *
 */
public class CardGraphic {
	private Bitmap cardBack;
	private Bitmap cardFaces;
	private float cardWidthPercent;
	private int cardWidth;
	private int cardHeight;
	private Rect srcRect = new Rect();
	private Rect destRect = new Rect();
	private static CardGraphic _instance = null;
	private Context context;
	private int screenW, screenH;
	
	private CardGraphic(Context context, float cardWidthPercent){
		this.cardWidthPercent = cardWidthPercent;
		this.context = context;
	}
	
	public static CardGraphic getInstance(Context context, float cardWidthPercent){
		if(_instance == null){
			_instance = new CardGraphic(context, cardWidthPercent);
		}
		return _instance;
	}
	
	/**
	 * Callback for when the screen size changes.
	 * Updates screenW and screenH, and reloads graphics
	 * @param screenW
	 * @param screenH
	 */
	public void onResize(int screenW, int screenH){
		this.screenW = screenW;
		this.screenH = screenH;
		load();
	}
	
	/**
	 * Loads the graphics for the cards, and scales them appropriately 
	 * to the provided screen width/height
	 * @param screenW
	 * @param screenH
	 */
	public void load(){
		Bitmap raw = BitmapFactory.decodeResource(context.getResources(), R.drawable.card_fronts);
		cardWidth = (int) (cardWidthPercent * screenW);
		cardHeight = cardWidth * 2;
		cardFaces = Bitmap.createScaledBitmap(raw, cardWidth * 13, cardHeight * 4, false);
		raw = BitmapFactory.decodeResource(context.getResources(), R.drawable.card_back);
		cardBack = Bitmap.createScaledBitmap(raw, cardWidth, cardHeight, false);
	}
	
	public void drawCardBack(Canvas canvas, float percentX, float percentY){
		if(cardBack != null){
			canvas.drawBitmap(cardBack, percentX * screenW, percentY * screenH, null);
		}
	}
	public void drawCard(Canvas canvas, CardRef ref, float percentX, float percentY){
		if(cardFaces != null){
			destRect.left = (int) (percentX * screenW);
			destRect.right = destRect.left + cardWidth;
			destRect.top = (int) (percentY * screenH);
			destRect.bottom = destRect.top + cardHeight;
			srcRect.left = ref.getRankNum() * cardWidth;
			srcRect.right = srcRect.left + cardWidth;
			srcRect.top = ref.getSuitNum() * cardHeight;
			srcRect.bottom = srcRect.top + cardHeight;
			canvas.drawBitmap(cardFaces, srcRect, destRect, null);
		}
	}

	public float getCardHeightPercent(){
		return (float)cardHeight / screenH;
	}
}
