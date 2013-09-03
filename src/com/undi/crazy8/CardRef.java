package com.undi.crazy8;

public class CardRef {
	public enum Suit {SPADES, HEARTS, CLUBS, DIAMONDS};
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
	
	@Override
	public boolean equals(Object other){
		if(other == this) return true;

		if(other.getClass() == CardRef.class){
			CardRef otherCard = (CardRef)other;
			if(otherCard.getRankNum() == getRankNum() &&
					otherCard.getSuitNum() == getSuitNum()){
				return true;
			}
			return false;
		}else{
			return false;
		}
	}

}
