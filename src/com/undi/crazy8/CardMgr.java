package com.undi.crazy8;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class CardMgr {

	public static List<CardRef> getSortedDeck(){
		List<CardRef> ret = new LinkedList<CardRef>();
		for(CardRef.Suit suit : CardRef.Suit.values()){
			for(CardRef.Rank rank : CardRef.Rank.values()){
				ret.add(new CardRef(suit, rank));
			}
		}
		return ret;	
	}

	public static List<CardRef> getShuffledDeck(){
		List<CardRef> ret = getSortedDeck();
		Collections.shuffle(ret, new Random());
		return ret;
	}
	
	public static void draw(List<CardRef> from, List<CardRef> to){
		draw(from, to, 1);
	}
	public static boolean draw(List<CardRef> from, List<CardRef> to, int count){
		if(from.size() < count){
			return false;
		}
		for(int i = 0; i < count; i++){
			to.add(0, from.remove(0));
		}
		return true;
	}
	
	/**
	 * Deal perHand cards to all hands, from deck.
	 * @param deck
	 * @param perHand
	 * @param hands
	 * @return true on success, false on failue (ex: if deck is empty)
	 */
	public static boolean deal(List<CardRef> deck, int perHand, List<CardRef>...hands){
		if(deck.size() < hands.length * perHand){
			return false;
		}
		for(int i = 0; i < perHand; i++){
			for(List<CardRef> curHand: hands){
				draw(deck, curHand);
			}
		}
		return true;
	}
	
	/**
	 * Takes all the card left in the hands, puts them into the deck,
	 *   and reshuffles the deck
	 * @param deck - the deck to reshuffle
	 * @param hands - the hands to put back into the deck
	 */
	public static void reshuffle(List<CardRef> deck, List<CardRef>...hands){
		for(List<CardRef> curHand : hands){
			deck.addAll(curHand);
			curHand.clear();
		}
		reshuffle(deck);
	}
	/**
	 * Reshuffles the passed in deck
	 * @param deck
	 */
	public static void reshuffle(List<CardRef> deck){
		Collections.shuffle(deck, new Random());
	}
}
