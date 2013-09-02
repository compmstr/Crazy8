package com.undi.crazy8;

import java.util.List;
import java.util.Random;

public class ComputerPlayer {

	/**
	 * Select a play to make
	 * @param hand
	 * @param topOfDiscard
	 * @return the card to play, or null if no valid play
	 */
	public static CardRef selectPlay(List<CardRef> hand, CardRef topOfDiscard){
		for(CardRef card: hand){
			if(Crazy8Game.isValidMove(card, topOfDiscard)){
				return card;
			}
		}
		return null;
	}

	public CardRef.Suit chooseSuit(){
		return CardRef.Suit.values()[new Random().nextInt(4)];
	}
}
