package com.undi.crazy8;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Crazy8Game {
	private List<CardRef> deck;
	private List<CardRef> playerHand;
	private List<CardRef> oppHand;
	private List<CardRef> discardPile;
	
	private int playerScore;
	private int oppScore;
	
	private boolean playerTurn;
	
	public Crazy8Game(){
		deck = CardMgr.getShuffledDeck();
		playerHand = new LinkedList<CardRef>();
		oppHand = new LinkedList<CardRef>();
		discardPile = new LinkedList<CardRef>();	
	}

	/**
	 * Start the game by dealing 7 cards to both players, and
	 * starting the discard pile with the next card
	 * Also sets the playerTurn boolean to a random value
	 */
	public void startGame(){
		CardMgr.reshuffle(deck, playerHand, oppHand, discardPile);
		CardMgr.deal(deck, 7, playerHand, oppHand);

		CardMgr.draw(deck, discardPile);
		
		//TODO: change to random after testing player turn stuff
		//playerTurn = new Random().nextBoolean();
		playerTurn = true;
	}

	private void drawCard(List<CardRef> hand){
		CardMgr.draw(deck, hand);
		//If the deck is empty, we re-shuffle all but the first card in the discard pile
		//  back into the deck
		if(deck.isEmpty()){
			List<CardRef> allButTop = discardPile.subList(1, discardPile.size() - 1);
			deck.addAll(allButTop);
			discardPile.removeAll(allButTop);
			CardMgr.reshuffle(deck);
		}
	}
	
	public static boolean isValidMove(CardRef card, CardRef topOfDiscard){
		if(card.rank == topOfDiscard.rank ||
				card.suit == topOfDiscard.suit ||
				card.rank == CardRef.Rank.EIGHT){
			return true;
		}else{
			return false;
		}
	}
	
	public int getPlayerScore(){ return playerScore; }
	public int getOppScore(){ return oppScore; }
	public boolean isPlayerTurn() { return playerTurn; }
	
	public List<CardRef> getDeck() { return deck; }
	public List<CardRef> getPlayerHand() { return playerHand; }
	public List<CardRef> getOppHand() { return oppHand; }
	public List<CardRef> getDiscardPile() { return discardPile; }
	
}
