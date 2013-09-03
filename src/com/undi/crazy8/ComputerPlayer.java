package com.undi.crazy8;

import java.util.List;
import java.util.Random;

public class ComputerPlayer {
	
	Crazy8Game game;
	
	public ComputerPlayer(Crazy8Game game){
		this.game = game;
	}

	/**
	 * Select a play to make
	 * @param hand
	 * @param topOfDiscard
	 * @return the card to play, or null if no valid play
	 */
	public CardRef selectPlay(List<CardRef> hand){
		for(CardRef card: hand){
			if(game.isValidMove(card)){
				return card;
			}
		}
		return null;
	}

	public CardRef.Suit chooseSuit(){
		return CardRef.Suit.values()[new Random().nextInt(4)];
	}
}
