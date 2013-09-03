package com.undi.crazy8;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.widget.Toast;

import com.undi.crazy8.CardRef.Rank;
import com.undi.crazy8.CardRef.Suit;

public class Crazy8Game {
	private List<CardRef> deck;
	private List<CardRef> playerHand;
	private List<CardRef> oppHand;
	private List<CardRef> discardPile;
	
	private int playerScore;
	private int oppScore;
	private CardRef.Suit wildSuit = null;
	
	private ComputerPlayer computerPlayer;
	
	private Context context;
	
	private boolean playerTurn;
	
	public Crazy8Game(Context context){
		deck = CardMgr.getShuffledDeck();
		playerHand = new LinkedList<CardRef>();
		oppHand = new LinkedList<CardRef>();
		discardPile = new LinkedList<CardRef>();	
		computerPlayer = new ComputerPlayer(this);
		this.context = context;
		playerScore = 0;
		oppScore = 0;
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
		//Make sure we don't start with an 8
		while(getTopOfDiscard().rank == Rank.EIGHT){
			CardMgr.draw(deck, discardPile);
		}
		
		playerTurn = new Random().nextBoolean();
		if(!playerTurn){
			runComputerTurn();
		}
	}
	
	public int scoreCard(CardRef card){
		if(card.rank == Rank.EIGHT){
			return 50;
		}else if(card.rank == Rank.ACE){
			return 1;
		}else if(card.getRankNum() >= Rank.JACK.ordinal()){
			return 10;
		}else{
			return card.getRankNum() + 1;
		}
	}

	public void drawCard(List<CardRef> hand){
		CardMgr.draw(deck, hand);
		//If the deck is empty, we re-shuffle all but the first card in the discard pile
		//  back into the deck
		if(deck.isEmpty()){
			List<CardRef> allButTop = discardPile.subList(1, discardPile.size() - 1);
			deck.addAll(allButTop);
			while(discardPile.size() > 1){
				discardPile.remove(1);
			}
			CardMgr.reshuffle(deck);
			Toast.makeText(context, "Shuffling Discard into Deck...", Toast.LENGTH_SHORT).show();
		}
	}
	
	public boolean isValidMove(CardRef card){
		CardRef topOfDiscard = getTopOfDiscard();
		if(card.rank == topOfDiscard.rank ||
				((topOfDiscard.rank != Rank.EIGHT) &&  (card.suit == topOfDiscard.suit)) ||
				((topOfDiscard.rank == Rank.EIGHT) && (card.suit == wildSuit)) ||
				card.rank == CardRef.Rank.EIGHT){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean hasValidMove(List<CardRef> cards){
		for(CardRef card: cards){
			if(isValidMove(card)){
				return true;
			}
		}
		return false;
	}
	
	public CardRef getComputerPlay(){
		return computerPlayer.selectPlay(oppHand);
	}
	
	public void runComputerTurn(){
		if(!playerTurn){
			CardRef picked = getComputerPlay();
			if(picked != null){
				playCard(oppHand, picked);
				if(picked.rank == Rank.EIGHT && !isGameOver()){
					Suit selectedSuit = computerPlayer.chooseSuit(oppHand);
					setWildSuit(selectedSuit);
					Toast.makeText(context, "The Computer Chose: " + selectedSuit.toString(), Toast.LENGTH_SHORT).show();
				}
			}else{
				drawCard(oppHand);
				changeTurn();
			}
		}
	}
	
	public void changeTurn(){
		playerTurn = !playerTurn;
	}
	
	public boolean playCard(List<CardRef> hand, CardRef card){
		if(isValidMove(card)){
			hand.remove(card);
			discardPile.add(0, card);
			setWildSuit(null);
			changeTurn();
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Upon the completion of a hand, give points to the other player for
	 *   the total scores of the cards in the loser's hand
	 * @return total points awarded this round
	 */
	public int updateScores(){
		int pointsThisHand = 0;
		for(CardRef card : oppHand){
			playerScore += scoreCard(card);
			pointsThisHand += scoreCard(card);
		}
		for(CardRef card : playerHand){
			oppScore += scoreCard(card);
			pointsThisHand += scoreCard(card);
		}
		return pointsThisHand;
	}
	
	public boolean isGameOver(){
		return (playerHand.isEmpty() || oppHand.isEmpty());
	}
	
	public int getPlayerScore(){ return playerScore; }
	public int getOppScore(){ return oppScore; }
	public boolean isPlayerTurn() { return playerTurn; }
	
	public List<CardRef> getDeck() { return deck; }
	public List<CardRef> getPlayerHand() { return playerHand; }
	public List<CardRef> getOppHand() { return oppHand; }
	public List<CardRef> getDiscardPile() { return discardPile; }
	public CardRef getTopOfDiscard() { return discardPile.get(0); }
	
	public void setWildSuit(CardRef.Suit suit){ wildSuit = suit ;}
	
}
