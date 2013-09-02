package com.undi.crazy8;

public class CardRef {
	public enum Suit {SPADE, HEART, CLUB, DIAMOND};
	public enum Rank {ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING};
	public final Suit suit;
	public final Rank rank;
	public CardRef(Suit suit, Rank rank){
		this.suit = suit;
		this.rank = rank;
	}
	
	public int getSuitNum(){
		return suit.ordinal();
	}
	public int getRankNum(){
		return rank.ordinal();
	}

}
