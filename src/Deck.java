import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Deck implements Cloneable {

	protected ArrayList<Card> deck = new ArrayList<Card>();
	protected int cardCount;
	
	/**
	 * constructor for creating a deck object
	 * that contains all card objects.
	 * @param deckFile inside the deck file.
	 */
	protected Deck(Scanner deckFile) {
		while (deckFile.hasNextLine()) {
			Card cardObj = new Card(deckFile.next(), deckFile.next());
			deck.add(cardObj);
		}
		cardCount = deck.size();
	}
	/**
	 * constructor to create a new deck 
	 * from an existing list of 
	 * card objects.
	 */
	protected Deck(ArrayList<Card> customDeck) {
		for (Card obj : customDeck) {
			this.deck.add(obj);
		}
	}
	
	/**
	 * displays all the cards in the deck.
	 */
	public String toString() {
		String result = "";
		for (Card obj : deck) {
			result += obj.toString() + "\n";
		}
		
		return result;
	}
	
	/**
	 * if there are no more cards, the 
	 * program will stop and display message. 
	 * return and remove the first index of the
	 * deck list.
	 * @return card.
	 */
	protected Card drawCards() {
		if (this.deck.size() == 0) {
			throw new IndexOutOfBoundsException("No more cards");
		}
		Card result = new Card(this.deck.get(0).numCard, this.deck.get(0).suit);
		this.deck.remove(0);
		return result;
	}
	
	@Override
	/**
	 * clone a deck of cards.
	 */
	protected Object clone() {
		ArrayList<Card> clonedDeck = new ArrayList<Card>();
		for (Card obj : this.deck) {
			clonedDeck.add((Card) obj.clone());
			
		}
		return clonedDeck;
	}

	/**
	 * adds up the values of the cards in the deck.
	 * if an ace is present and the deck is higher than 21,
	 * then the ace will become a 1.
	 * @return sum of card values.
	 */
	protected int getNumericValueDeck() {
		int sum = 0;
		boolean hasAce = false; // Flag to keep track if Ace is present in the deck
	
		// Loop through each card in the deck
		for (Card card : this.deck) {
			int cardValue = card.getNumericValue();
			sum += cardValue;
			if (cardValue == 11) {
				hasAce = true; // Set flag if Ace is present
			}
		}
		// If the deck has an Ace and the sum is greater than 21, subtract 10 to account for Ace value change
		if (hasAce && sum > 21) {
			sum -= 10;
		}
		return sum;
	}
	
	/**
	 * shuffle the deck using the 
	 * Fisher-Yates algorithm. O(n) 
	 * algorithm. Commonly used to shuffle cards
	 * in simple online games.
	 */
	protected void shuffle() {
		int lengthOfArr = this.deck.size();
		Random rand = new Random();
		// Fisher Yates algorithm
		 for (int i = lengthOfArr - 1; i > 0; i--) {
	            int j = rand.nextInt(i + 1);
	            Card temp = this.deck.get(i);
	            this.deck.set(i, this.deck.get(j));
	            this.deck.set(j, temp);
	        }
		//return deck;
	}
	
	/**
	 * reset a deck of cards by removing all
	 * cards.
	 */
	protected Deck resetDeck() {
		this.deck.clear();
		this.cardCount = 0;
		return this;
	}
}
