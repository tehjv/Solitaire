package objects;

import constants.Color;
import constants.Rank;
import constants.Suit;

/**
 * This class is used to create Card objects.
 */

public class Card {
	private Rank rank;
	private Suit suit;
	private boolean faceUpStatus;
	private Color color;

	/**
	 * Creates card object, sets rank and suit value.
	 */
	public Card(Rank rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
		if (suit.ordinal() < 2) {
			color = Color.RED;
		} else {
			color = Color.BLACK;
		}

	}

	/**
	 * Gets card Rank.
	 */
	public Rank getRank() {
		return rank;
	}

	/**
	 * Sets card's Rank.
	 */
	public void setRank(Rank rank) {
		this.rank = rank;
	}

	/**
	 * Gets card's Suit.
	 */
	public Suit getSuit() {
		return suit;
	}

	/**
	 * Sets card's Suit.
	 */
	public void setSuit(Suit suit) {
		this.suit = suit;
	}

	/**
	 * Overrides original function to print card properties in format: Suit
	 * initial - Rank initial
	 */
	@Override
	public String toString() {
		String[] rankDisplays = { "A ", "2 ", "3 ", "4 ", "5 ", "6 ", "7 ", "8 ", "9 ", "10", "J ", "Q ", "K " };
		char[] suitDisplays = { 'D', 'H', 'S', 'C' };
		String a = rankDisplays[rank.ordinal()];
		char b = suitDisplays[suit.ordinal()];
		if (faceUpStatus == true) {
			return "[" + b + "-" + a + "]";
		} else {
			return "[<::>]";
		}
	}

	public boolean isFaceUpStatus() {
		return faceUpStatus;
	}

	public void setFaceUpStatus(boolean faceUpStatus) {
		this.faceUpStatus = faceUpStatus;
	}

	public Color getColor() {
		return color;
	}

}
