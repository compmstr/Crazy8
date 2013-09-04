package com.undi.crazy8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.undi.crazy8.CardRef.Rank;
import com.undi.crazy8.CardRef.Suit;

public class ComputerPlayer {
	
	Game game;
	
	public ComputerPlayer(Game game){
		this.game = game;
	}

	/**
	 * Select a play to make
	 * @param hand
	 * @param topOfDiscard
	 * @return the card to play, or null if no valid play
	 */
	public CardRef selectPlay(List<CardRef> hand){
		List<CardRef> validMoves = new ArrayList<CardRef>();
		for(CardRef card: hand){
			if(game.isValidMove(card)){
				validMoves.add(card);
			}
		}
		if(!validMoves.isEmpty()){
			Collections.sort(validMoves, new Comparator<CardRef>() {
				@Override
				public int compare(CardRef lhs, CardRef rhs) {
					//Play low value cards and eights last
					if(lhs.rank == rhs.rank){
						return 0;
					}
					if(lhs.getRankNum() > rhs.getRankNum() || rhs.rank == Rank.EIGHT){
						return -1;
					}
					if(lhs.getRankNum() < rhs.getRankNum() || lhs.rank == Rank.EIGHT){
						return 1;
					}
					return 0;
				}
			});
			return validMoves.get(0);
		}else{
			return null;
		}
	}

	public CardRef.Suit chooseSuit(List<CardRef> hand){
		int numHearts, numSpades, numClubs, numDiamonds;
		numHearts = numSpades = numClubs = numDiamonds = 0;
		for(CardRef card : hand){
			switch(card.suit){
			case HEARTS:
				numHearts++;
				break;
			case SPADES:
				numSpades++;
				break;
			case CLUBS:
				numClubs++;
				break;
			case DIAMONDS:
				numDiamonds++;
				break;
			}
		}
		int maxCount = Math.max(Math.max(numClubs, numDiamonds), Math.max(numHearts, numSpades));
		if(numClubs == maxCount){ return Suit.CLUBS; }
		if(numHearts == maxCount){ return Suit.HEARTS; }
		if(numDiamonds == maxCount){ return Suit.DIAMONDS; }
		if(numSpades == maxCount){ return Suit.SPADES; }
		//Fallback, shouldn't really get here
		return CardRef.Suit.values()[new Random().nextInt(4)];
	}
}
