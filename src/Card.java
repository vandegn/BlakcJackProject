import java.util.Scanner;

public class Card extends Deck implements Cloneable {
	protected String numCard;
	protected String suit;
	
	/**
	 * constructs card objects.
	 * @param num number on the card.
	 * @param suitString suit of the card.
	 */
	public Card(String num, String suitString) {
		super(new Scanner(""));
		this.numCard = num;
		this.suit = suitString;
	}
	
	/**
	 * to string representation of.
	 * a card object It is created like.
	 * a filename because the png package.
	 * of cards is formatted this way.
	 */
	public String toString() {
		return (numCard.toLowerCase() + "_") 
				+ "of_" + this.getSuit().toLowerCase();
	}
	
	@Override
	/**
	 * perform a deep copy of a card object.
	 */
	protected Object clone() {
		Object clone = null;
		if (this instanceof Card) {
			Card clonedCard = (Card) clone;
			clonedCard.suit = new String(this.suit);
			clonedCard.numCard = this.numCard;
			return clonedCard;
		}
		return clone;
		
	}

    /**
	 * checks if the card is a face card.
	 * @return true is yes, false if no.
	 */
	protected boolean isFaceCard() {
		// not counting the Ace because more special operations will need to 
		// be done on that category of card.
		if (this.numCard.equals("Jack") || this.numCard.equals("Queen") 
				|| this.numCard.equals("King")) {
			return true;
		}
		return false;
	}
	
	/**
	 * checks if the card object is an 
	 * Ace card.
	 * @return true, false.
	 */
	protected boolean isAce() {
		if (this.numCard.equals("Ace")) {
			return true;
		}
		return false;
	}

	/**
	 * compares two card objects.
	 * @param obj card object to comapre to.
	 * @return number indicating if its good or bad or equal.
	 */
	protected int compareTo(Card obj) {
		// comparing to the parameter card 
		// 1 is better, 0 is equal, -1 is worse
		// counting Aces as better than all (11 value) because it 
		// can be both 1 or 11 depending on the total sum
		// of your hand, making it a very versatile card and 
		// also can allow you to get an immediate 21.
	    // Comparing the values
	    if (this.getNumericValue() > obj.getNumericValue()) {
	        return 1;
	    } else if (this.getNumericValue() == obj.getNumericValue()) {
	        return 0;
	    } else {
	        return -1;
	    }
	}
	
	/**
	 * get the numnber representation of card.
	 * face cards are 10 and aces are either 11.
	 * or 1 depending on other factors.
	 * @return number of card.
	 */
	protected int getNumericValue() {
		int val = 0;
		if (this.isAce()) {
	        val = 11;
	    } else if (this.isFaceCard()) {
	        val = 10;
	    } else {
	        val = Integer.parseInt(this.numCard);
	    }

	   return val;
	}
	
	/**
	 * simply return the suit of this card obj.
	 * @return suit.
	 */
	protected String getSuit() {
		return this.suit;
	}
}
