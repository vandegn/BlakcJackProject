import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class FrontEnd {
	
	public static Scanner kb = new Scanner(System.in);

	/**
	 * main class for running the program
	 * @param args
	 */
	public static void main(String[] args) {
		String input = "";
		System.out.println("BlackJack: 10 dollar entry. 10 dollars per round.");
		System.out.println("1. Custom money");
		System.out.println("2. Default money (100)");
		System.out.println("3. Quit (q)");
		while (!input.equalsIgnoreCase("q")) {
			input = kb.next();
			
			if (input.equals("1")) {
				try {
					mainHelperFor1();
				} catch (InputMismatchException | FileNotFoundException err) {
					System.out.println("Invalid option");
				}
			}

			if (input.equals("2")) {
				try {
					mainHelperFor2();
				} catch (FileNotFoundException e) {
					System.out.println("Invalid option");
				}
			}
		}
	}
				
	/**
	 * get the filename of deck and 
	 * create Game object.
	 * @throws FileNotFoundException if file not found.
	 */
	public static void mainHelperFor2() throws FileNotFoundException {
		// default money 
		System.out.println("Enter Deck File: ");
		String deckFile = kb.next();
		Scanner inDeckFile = new Scanner(new File(deckFile));
		Game game = new Game(inDeckFile);
	}

	/**
	 * ask user for informatoin if they 
	 * play with custom money.
	 * @throws FileNotFoundException if file not found.
	 */
	public static void mainHelperFor1() throws FileNotFoundException {
		System.out.println("Enter amount: ");
		int money = kb.nextInt();
		System.out.println("Enter Deck File: ");
		String deckFile = kb.next();
		Scanner inDeckFile = new Scanner(new File(deckFile));
		Game game = new Game(inDeckFile, money);
	}
}
