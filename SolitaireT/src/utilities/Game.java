package utilities;

import java.util.ArrayList;
import java.util.Random;

import constants.Rank;
import constants.Suit;
import objects.Card;
import objects.Deck;

public class Game {
	GameDisplay gameDisplay = new GameDisplay();
	GameSolver gameSolver = new GameSolver();
	Deck deck = new Deck();
	Deck stock = new Deck();
	Deck waste = new Deck();
	ArrayList<Deck> tableau = new ArrayList<Deck>();
	ArrayList<Deck> foundations = new ArrayList<Deck>();

	public void startGame() {
		gameDisplay.showWelcomeMessage();
		setInitialDeck();
		//shuffle();
		setTableau();
		setStock();
		// initializing foundation
		for (int i = 0; i < 4; i++) {
			foundations.add(new Deck());
		}
		gameDisplay.displaySetup();
		gameSolver.determineMovesTableauToFoundations();
		System.out.print(gameSolver.getMoveList());
		System.out.print("\n");
		gameSolver.executeMove(gameSolver.chooseMove());
		gameDisplay.displaySetup();
		gameSolver.determineMovesTableauToFoundations();
		System.out.print(gameSolver.getMoveList());
	}

	public void setInitialDeck() {
		for (int i = 0; i < 4; i++) {
			Suit[] suitValues = { Suit.DIAMONDS, Suit.HEARTS, Suit.SPADES, Suit.CLUBS };
			Suit suit = suitValues[i];
			for (int j = 0; j < 13; j++) {
				Rank[] rankValues = { Rank.Ace, Rank.King, Rank.Queen, Rank.Jack, Rank.Ten, Rank.Nine, Rank.Eight,
						Rank.Seven, Rank.Six, Rank.Five, Rank.Four, Rank.Three, Rank.Two };
				Rank rank = rankValues[j];
				deck.add(new Card(rank, suit));
			}
		}
	}

	public void setTableau() {
		for (int j = 0; j < 7; j++) {
			tableau.add(new Deck());
		}

		for (int j = 0; j < 7; j++) {
			for (int i = j; i < 7; i++) {
				if (i == j) {
					tableau.get(i).add(deck.remove(0));
					tableau.get(i).get(i).setFaceUpStatus(true);
				} else {
					tableau.get(i).add(deck.remove(0));
				}
			}
		}
	}

	public void setStock() {
		stock.addAll(deck);
		deck.clear();
	}

	public GameDisplay getGamedisplay() {
		return gameDisplay;
	}

	public void setGamedisplay(GameDisplay gamedisplay) {
		this.gameDisplay = gamedisplay;
	}

	public void shuffle() {
		Deck tempDeck = new Deck();
		Random rand = new Random();
		int n = 0;
		int x = deck.size();
		for (int i = 0; i < x; i++) {
			if (deck.size() > 1) {
				n = rand.nextInt(deck.size() - 1);
			} else {
				n = 0;
			}
			tempDeck.add(deck.get(n));
			deck.set(n, deck.get(deck.size() - 1));
			deck.remove(deck.size() - 1);
		}

		deck = tempDeck;
	}

	public Deck getDeck() {
		return deck;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	private class GameDisplay {
		public void showWelcomeMessage() {
			System.out.println("STARTING SOLITAIRE SOLVER APPLICATION");
			System.out.println("Loading initial setup");
		}

		public void displaySetup() {
			System.out.print("Foundations:\t");
			displayFoundations();
			System.out.print("\nStock/Pile:\t");
			displayStock();
			System.out.print("\nWaste/Draw Pile: \t");
			displayWaste();

			System.out.print("\n");
			displayTableau();
		}

		public void displayTableau() {
			int mostCards = 0;
			for (int i = 0; i < tableau.size(); i++) {
				if (mostCards < tableau.get(i).size()) {
					mostCards = tableau.get(i).size();
				}
			}

			for (int i = 0; i < mostCards; i++) {
				for (int j = 0; j < tableau.size(); j++) {
					// front space
					if (i < tableau.get(j).size()) {
						System.out.print("\t" + tableau.get(j).get(i));
					} else {
						System.out.print("\t");
					}

				}
				System.out.print("\n");
			}

		}

		public void displayStock() {
			if (stock.isEmpty()) {
				System.out.print("[    ]");
			} else {
				System.out.print(stock.get(0));
			}
		}

		public void displayWaste() {
			if (waste.isEmpty()) {
				System.out.print("\t\t\t\t");
			} else {
				for (int i = 0; i < waste.size(); i++) {
					System.out.print(waste.get(i) + "\t");
				}
			}
		}

		public void displayFoundations() {
			char[] a = { 'D', 'H', 'S', 'C' };
			for (int i = 0; i < foundations.size(); i++) {
				if (foundations.get(i).isEmpty()) {
					System.out.print("[" + a[i] + "   ]\t");
				} else {
					System.out.print(foundations.get(i).get(foundations.get(i).size() - 1) + "\t");
				}
			}
		}
	}

	private class GameSolver {
		ArrayList<Move> moveList = new ArrayList<Move>();

		public Move chooseMove() {
			if(moveList.size()==1){
				return moveList.get(0);
			}else{
				return null;
			}
		
		}
		
		public void executeMove(Move chosenMove){
			if(chosenMove.initialL<7){
				foundations.get(chosenMove.foundationSuit).add(tableau.get(chosenMove.initialL).remove(0));
			}
			
			
			moveList.clear();
		}

		public ArrayList<Move> getMoveList() {
			return moveList;
		}

		private void determineMovesTableauToFoundations() {
			for (int i = 0; i < tableau.size(); i++) {
				if (tableau.get(i).isEmpty() == false) {
					System.out.println(tableau.get(1).get(1));
					Card card = tableau.get(i).get(0);

					int a = card.getSuit().ordinal(); // suit value of card
					if (card.getRank() == Rank.Ace) {
						moveList.add(new Move(card, 0, i, 8));
						break;
					} else if (card.getRank() == Rank.Two) {
						if (!foundations.get(a).isEmpty()) {
							if (foundations.get(a).size() == 1) {
								moveList.add(new Move(card, 0, i, 8));
							}
						}
					} else {
						if (!foundations.get(a).isEmpty()) {
							if (foundations.get(a).get(foundations.get(a).size() - 1).getRank()
									.ordinal() == card.getRank().ordinal() - 1) {
								moveList.add(new Move(card, 0, i, 8));
							}
						}
					}
				}
			}
		}
	}

	private class Move {
		public String color;
		public final String[] locations = { "Tableau Column 1", "Tableau Column 2", "Tableau Column 3",
				"Tableau Column 4", "Tableau Column 5", "Tableau Column 6", "Tableau Column 7", "Waste",
				"Foundations" };
		public final String[] moveTypes = { "from Tableau to Foundations", "from Waste to Foundations",
				"from Tableau to Tableau", "from Waste to Tableau", "Draw from Stock" };
		public Card cardToMove;
		public int moveType; // index of moveTypes
		public int initialL; // initial location index
		public int moveL; // proposed move location index
		public int foundationSuit;
		public Move(Card card, int mType, int fromL, int toL) {
			cardToMove = card;
			moveType = mType;
			initialL = fromL;
			moveL = toL;
			foundationSuit = card.getSuit().ordinal();
		}

		@Override
		public String toString() {
			return cardToMove.toString() + " from " + locations[initialL] + " to " + locations[moveL];
		}

	}

	// public ArrayList<Deck> getTableau() {
	// return tableau;
	// }
}
