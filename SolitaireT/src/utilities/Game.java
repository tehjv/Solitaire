package utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
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
		// shuffle();
		setTableau();
		setStock();
		// initializing foundation
		for (int i = 0; i < 4; i++) {
			foundations.add(new Deck());
		}
		gameDisplay.displaySetup();
		boolean gameFinished = false;
		int numberOfMoves = 0;

		while (gameFinished == false) {
			gameSolver.listMoves();
			System.out.print(gameSolver.getMoveList());
			System.out.print("\n\n");
			gameSolver.executeMove(gameSolver.chooseMove());
			gameDisplay.displaySetup();
			numberOfMoves++;

			if (foundations.get(0).size() + foundations.get(1).size() + foundations.get(2).size()
					+ foundations.get(3).size() == 52) {
				gameFinished = true;
			}
		}

		System.out.println("\n\n\nSOLITAIRE SOLVER APPLICATION won after " + numberOfMoves + " moves.");

		// gameSolver.listMoves();
		// System.out.print(gameSolver.getMoveList());
		// System.out.print("\n\n");
		// gameSolver.executeMove(gameSolver.chooseMove());
		// gameDisplay.displaySetup();
		//
		// gameSolver.listMoves();
		// System.out.print(gameSolver.getMoveList());
		// System.out.print("\n\n");
		// gameSolver.executeMove(gameSolver.chooseMove());
		// gameDisplay.displaySetup();
		//
		// gameSolver.listMoves();
		// System.out.print(gameSolver.getMoveList());
		// System.out.print("\n\n");
		// gameSolver.executeMove(gameSolver.chooseMove());
		// gameDisplay.displaySetup();
		//
		// gameSolver.listMoves();
		// System.out.print(gameSolver.getMoveList());
		// System.out.print("\n\n");
		// gameSolver.executeMove(gameSolver.chooseMove());
		// gameDisplay.displaySetup();
		//
		// gameSolver.listMoves();
		// System.out.print(gameSolver.getMoveList());
		// System.out.print("\n\n");
		// gameSolver.executeMove(gameSolver.chooseMove());
		// gameDisplay.displaySetup();
		//
		// gameSolver.listMoves();
		// System.out.print(gameSolver.getMoveList());
		// System.out.print("\n\n");
		// gameSolver.executeMove(gameSolver.chooseMove());
		// gameDisplay.displaySetup();

		// last time

		// gameSolver.listMoves();
		// System.out.print(gameSolver.getMoveList());
		// System.out.print("\n\n");
		// gameSolver.executeMove(gameSolver.chooseMove());
		// gameDisplay.displaySetup();
		//
		// gameSolver.listMoves();
		// System.out.print(gameSolver.getMoveList());
		// System.out.print("\n\n");
		// gameSolver.executeMove(gameSolver.chooseMove());
		// gameDisplay.displaySetup();

		// gameSolver.listMoves();
		// System.out.print(gameSolver.getMoveList());
		// System.out.print("\n\n");
		// gameSolver.executeMove(gameSolver.chooseMove());
		// gameDisplay.displaySetup();

		// testing area tests

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
		boolean wasteRefresh = true;

		public void showWelcomeMessage() {
			System.out.println("STARTING SOLITAIRE SOLVER APPLICATION");
			System.out.println("Loading initial setup");
		}

		public void displaySetup() {
			System.out.print("\n");
			System.out.print("Foundations:\t\t\t");
			displayFoundations();
			System.out.print("\nStock/Pile:\t\t\t\t\t");
			displayStock();
			System.out.print("\nWaste/Draw Pile: \t");
			displayWaste();

			System.out.print("\n");
			System.out.print("Tableau:");
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
				System.out.print("[    ]" + "\t  " + stock.size());
			} else {
				System.out.print(stock.get(0) + "\t  " + stock.size());
			}
		}

		public void displayWaste() {
			if (waste.isEmpty()) {
				System.out.print("\t\t\t\t");
			} else {

				int cardsToDisplay = waste.size() % 3;

				System.out.print("   Get\t");
				System.out.print("Here->\t");

				if (wasteRefresh) {
					for (int i = 1; i < 4; i++) {
						if (waste.get(waste.size() - i) != null) {
							System.out.print(waste.get(waste.size() - i) + "\t");
						}
					}
				} else {
					for (int i = 1; i < cardsToDisplay + 1; i++) {
						if (waste.get(waste.size() - i) != null) {
							System.out.print(waste.get(waste.size() - i) + "\t");
						}
					}
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
		int consecutiveMovesFromWaste = 0;
		// move prioty: 0 = acemove, 1 = deucemove
		// boolean hasAceMove = false;
		int movePriority = 40;

		public Move chooseMove() {
			int moveConsidered = 0; // index of MoveList
			if (!moveList.isEmpty()) {
				boolean consideredAMove = false;
				do {
					for (int i = 0; i < moveList.size(); i++) {
						if (moveList.get(i).cardToMove.getRank() == Rank.Ace) {
							movePriority = 0;
							moveConsidered = i;
						}
					}
					if (movePriority != 40) {
						break;
					}
					for (int i = 0; i < moveList.size(); i++) {
						if (moveList.get(i).cardToMove.getRank() == Rank.Two) {
							movePriority = 1;
							moveConsidered = i;
						}
					}
					if (movePriority != 40) {
						break;
					}
					consideredAMove = true;
				} while (consideredAMove == false);

			}

			if (moveList.isEmpty()) {
				return null;
			} else if (moveList.size() == 1) {
				return moveList.get(0);
			} else if ((movePriority == 0 || movePriority == 1)) {
				return moveList.get(moveConsidered);
			} else {
				return null;
			}

		}

		public void listMoves() {
			determineMovesTableauToFoundations();
			determineMovesWasteToFoundations();
			determineMovesTableauToTableau();
			determineMovesWasteToTableau();
		}

		public void executeMove(Move chosenMove) {
			// draw
			if (chosenMove == null) {
				if (stock.size() == 0) {
					stock.addAll(waste);
					for (int i = 0; i < stock.size(); i++) {
						waste.get(i).setFaceUpStatus(false);
					}
					waste.clear();

					if (stock.size() >= 3) {
						waste.addAll(stock.subList(0, 3));
					} else {
						waste.addAll(stock.subList(0, stock.size() - 1));
					}

					stock.removeAll(waste);
					for (int i = 0; i < waste.size(); i++) {
						waste.get(i).setFaceUpStatus(true);
					}

				} else {
					if (stock.size() >= 3) {
						waste.addAll(stock.subList(0, 3));
					} else {
						waste.addAll(stock.subList(0, stock.size() - 1));
					}

					stock.removeAll(waste);
					for (int i = 0; i < waste.size(); i++) {
						waste.get(i).setFaceUpStatus(true);
					}
					gameDisplay.wasteRefresh = true;
				}
				System.out.println("Chosen move: Draw from Stock");
			} else if (chosenMove.initialL < 7 && chosenMove.moveL == 8) {
				foundations.get(chosenMove.foundationSuit)
						.add(tableau.get(chosenMove.initialL).remove(tableau.get(chosenMove.initialL).size() - 1));
			} else if (chosenMove.initialL == 7 && chosenMove.moveL == 8) {
				foundations.get(chosenMove.foundationSuit).add(waste.remove(waste.size() - 1));
				if (gameDisplay.wasteRefresh == true) {
					gameDisplay.wasteRefresh = false;
				}
				consecutiveMovesFromWaste++;
				if (consecutiveMovesFromWaste == 3) {
					if (gameDisplay.wasteRefresh == false) {
						gameDisplay.wasteRefresh = true;
					}
				}
			} else if (chosenMove.initialL < 7 && chosenMove.moveL < 7) {
				tableau.get(chosenMove.moveL)
						.add(tableau.get(chosenMove.initialL).remove(tableau.get(chosenMove.initialL).size() - 1));
			} else if (chosenMove.initialL == 7 && chosenMove.moveL < 7) {
				tableau.get(chosenMove.moveL).add(waste.remove(waste.size() - 1));
				if (gameDisplay.wasteRefresh == true) {
					gameDisplay.wasteRefresh = false;
				}
				consecutiveMovesFromWaste++;
				if (consecutiveMovesFromWaste == 3) {
					if (gameDisplay.wasteRefresh == false) {
						gameDisplay.wasteRefresh = true;
					}
				}
			}

			if (chosenMove != null) {
				System.out.println("Chosen move: " + moveList.get(moveList.indexOf(chosenMove)).toString());
			}
			moveList.clear();
			checkTableauCards();
		}

		public void checkTableauCards() {
			for (int i = 0; i < tableau.size(); i++) {
				if (tableau.get(i).isEmpty() == false) {
					if (tableau.get(i).get(tableau.get(i).size() - 1).isFaceUpStatus() == false) {
						tableau.get(i).get(tableau.get(i).size() - 1).setFaceUpStatus(true);
					}
				}
			}
		}

		public ArrayList<Move> getMoveList() {
			return moveList;
		}

		private void determineMovesTableauToFoundations() {
			for (int i = 0; i < tableau.size(); i++) {
				if (tableau.get(i).isEmpty() == false) {

					Card card = tableau.get(i).get(tableau.get(i).size() - 1);

					int a = card.getSuit().ordinal(); // suit value of card
					if (card.getRank() == Rank.Ace) {
						moveList.add(new Move(card, 0, i, 8));
						// break;
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

		private void determineMovesWasteToFoundations() {
			if (!waste.isEmpty()) {
				Card card = waste.get(waste.size() - 1);
				int a = card.getSuit().ordinal();
				if (card.getRank() == Rank.Ace) {
					moveList.add(new Move(card, 1, 7, 8));

				} else {
					if (!foundations.get(a).isEmpty()) {
						if (foundations.get(a).get(foundations.get(a).size() - 1).getRank()
								.ordinal() == card.getRank().ordinal() - 1) {
							moveList.add(new Move(card, 1, 7, 8));

						}
					}
				}
			}
		}

		private void determineMovesTableauToTableau() {
			// check for empty space
			boolean hasEmptySpace = false;
			int emptySpace = -1;
			for (int i = 0; i < tableau.size(); i++) {
				if (tableau.get(i).isEmpty()) {
					hasEmptySpace = true;
					emptySpace = i;
					break;
				}
			}
			for (int i = 0; i < tableau.size(); i++) {
				if (tableau.get(i).isEmpty() == false) {
					Card card = tableau.get(i).get(tableau.get(i).size() - 1);
					if (card.getRank() == Rank.King && hasEmptySpace && tableau.get(i).size() != 1) {
						moveList.add(new Move(card, 2, i, emptySpace));
					}

				}

				// TODO: tableau to tableau move other than king to empty space

			}
		}

		private void determineMovesWasteToTableau() {
			if (!waste.isEmpty()) {
				Card card = waste.get(waste.size() - 1);
				int a = card.getRank().ordinal();
				for (int i = 0; i < 7; i++) {
					if (!tableau.get(i).isEmpty()
							&& tableau.get(i).get(tableau.get(i).size() - 1).getRank()
									.ordinal() == card.getRank().ordinal() + 1
							&& tableau.get(i).get(tableau.get(i).size() - 1).getColor() != card.getColor()) {
						moveList.add(new Move(card, 3, 7, i));
					}
				}
			}
		}

		private class Move {

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
}
