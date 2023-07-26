import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class Game extends JFrame implements GameState {

    // variables that i want to access all throught the program
    // to make it easier.
    
	protected ArrayList<Card> playerHand = new ArrayList<Card>();
	protected ArrayList<Card> dealerHand = new ArrayList<Card>();
    private int userMoney = 1000;
    Deck gameDeck;
    JPanel dealerCards = new JPanel();
    JLabel hiddencard = new JLabel();
    JPanel playerCards = new JPanel();
    JLabel lblPlayerHand = new JLabel();
    JLabel lblDealerHand = new JLabel();

    
    JLabel lblTitle = new JLabel();
    JLabel moneyLabel;
    
    /**
     * Create the swing gui and populate with
     * png images of cards and make buttons.
     * @param deckFile Scanner in the file of cards.
     * @throws FileNotFoundException
     */
    public Game(Scanner deckFile) throws FileNotFoundException {
    
    	gameOperations(deckFile);
    	setup();
    	buttons();
        makeJPanels();
       
        moneyLabel = new JLabel();
        playerCards.add(moneyLabel);
        String moneyLabelText = Integer.toString(userMoney);
        moneyLabel.setText("Money: " + moneyLabelText);
      
        setVisible(true);
        initialDraw();
        revalidate();
        deckFile.close();
    }

    /**
     * Create the swing gui and populate with
     * png images of cards and make buttons. Add 
     * the custom amount of money.
     * @param deckFile Scanner in the file of cards.
     * @param money money user wants to play with.
     * @throws FileNotFoundException
     */
    public Game(Scanner deckFile, int money) throws FileNotFoundException {
    	
    	gameOperations(deckFile);
    	setup();
    	buttons();
        makeJPanels();
       
        moneyLabel = new JLabel();
        playerCards.add(moneyLabel);
        this.userMoney = money;
        String moneyLabelText = Integer.toString(userMoney);
        moneyLabel.setText("Money: " + moneyLabelText);
      
        setVisible(true);
        initialDraw();
        revalidate();
        deckFile.close();
    }

    /**
     * make the panels to store dealer and player cards in.
     */
    public void makeJPanels() {
        dealerCards.setPreferredSize(new Dimension(500, 200));
        dealerCards.setBackground(Color.WHITE);
        getContentPane().add(dealerCards);
        dealerCards.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        lblDealerHand = new JLabel("Dealer's Hand: ");
        dealerCards.add(lblDealerHand);
       
        // Set preferred size for player hand and dealer hand panels
        playerCards.setPreferredSize(new Dimension(500, 200));
        
        // Set background color for player hand and dealer hand panels
        playerCards.setBackground(Color.WHITE);
        getContentPane().add(playerCards);
        playerCards.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
      
        lblPlayerHand= new JLabel("Player's Hand: ");
        playerCards.add(lblPlayerHand);
    }
    
    
    /** 
     * shuffle and create a deck object.
     * @param deckFile
     */
    public void gameOperations(Scanner deckFile) {
    	gameDeck = new Deck(deckFile);
    	System.out.println("Shuffling....");
    	gameDeck.shuffle();
    	System.out.println("Done.");
    	System.out.println("initial draw");
    }
    
    /**
     * creates the jframe.
     */
    public void setup() {
    	// Set up the JFrame
        setTitle("Blackjack Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600); // Set the desired size for the window
     
        Color bgColor = new Color(0x35, 0x65, 0x4D); // Hex value #35654d
        getContentPane().setBackground(bgColor);
    }
    
    /** 
     * create all the buttons and helper 
     * methods to write out their actionlisteners.
     * @throws FileNotFoundException
     */
    public void buttons() throws FileNotFoundException {
    	// Create components
        lblTitle = new JLabel("Welcome to Blackjack!");
        JButton hitBut = new JButton("Hit");
        JButton standBut = new JButton("Stand");
        JButton nextRoundBut = new JButton("Next Round");
        JButton saveBut = new JButton("Save");
        JButton resetBut = new JButton("Reset");
        // Set layout manager for JFrame
        getContentPane().setLayout(new FlowLayout(FlowLayout.LEFT));
        JPanel separator_1 = new JPanel();
        separator_1.setPreferredSize(new Dimension(270, 1));
        getContentPane().add(separator_1);
        // Add components to JFrame
        getContentPane().add(lblTitle);
        getContentPane().add(hitBut);
        getContentPane().add(standBut);
        getContentPane().add(nextRoundBut);
        getContentPane().add(saveBut);
        getContentPane().add(resetBut);
        
        hitButton(hitBut);
        standButton(standBut, hitBut);
        nextRoundButton(nextRoundBut, hitBut);
        saveButton(saveBut); 
        resetButton(resetBut, hitBut);

    }

    /**
     * action listener for the reset button
     * @param resetBut reset button JButton obj.
     */
    public void resetButton(JButton resetBut, JButton hitBut) {
        resetBut.addActionListener(e -> {
            System.out.println("Resetting..");
            hitBut.setEnabled(true);
            resetGame();
        });
    }

    
    /** 
     * action listener for the save button.
     * @param saveBut save jbutton object.
     * @throws FileNotFoundException
     */
    public void saveButton(JButton saveBut) throws FileNotFoundException {
        saveBut.addActionListener(e -> {
            System.out.println("Saving...");
            try (PrintWriter saveFile = new PrintWriter("game_state.txt")) {
                saveFile.print("");
                saveFile.println(this.userMoney);
                saveFile.println(this.gameDeck.toString());
                saveFile.close();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            System.out.println("Done");
        });
    }

    /**
     * action listener for the next round button.
     * clears the list of player and dealer hands
     * resets the text fields and removes all png 
     * images of cards. recall the initial draw to 
     * essentially reset the state of the game.
     * @param nextRoundBut next round jbutton obj.
     */
    public void nextRoundButton(JButton nextRoundBut, JButton hitBut) {
        nextRoundBut.addActionListener(e -> {
            System.out.println("Next Round Hit");
            playerHand.clear();
            dealerHand.clear();
            lblDealerHand.setText("Dealer's Hand: ");
            lblPlayerHand.setText("Player's Hand: ");;
            lblTitle.setText("Welcome to BlackJack!");
            
             // Remove image icons from playerCards JPanel
            for (Component component : playerCards.getComponents()) {
                if (component instanceof JLabel) {
                    JLabel label = (JLabel) component;
                    label.setIcon(null); // Set the icon to null to remove it
                }
            }

            // Remove image icons from dealerCards JPanel
            for (Component component : dealerCards.getComponents()) {
                if (component instanceof JLabel) {
                    JLabel label = (JLabel) component;
                    label.setIcon(null); // Set the icon to null to remove it
                }
            }
            // populate the panels with the initial 
            // cards.
            // reset the hit button.
            hitBut.setEnabled(true);
            initialDraw();   
        });
    }

    /**
     * action listener for the hitbutton.
     * when pressed, card is drawn from game deck
     * and added to the player hand and 
     * player cards panel. calls the 
     * display method to display the respective png 
     * of the card and set the text to the numerical value
     * of the sum of the player hand. if the player has over 21
     * then display that the player has lost to the window.
     * @param hitBut hit button jbutton obj.
     */
    public void hitButton(JButton hitBut) {
    	hitBut.addActionListener(e -> {
            // Handle hit button click
            System.out.println("Hit button clicked.");
            Card addingCard = this.gameDeck.drawCards();
            playerHand.add(addingCard);
            Deck playerDeck = new Deck(playerHand); 
            display(addingCard, playerCards);
            lblPlayerHand.setText("Player's Hand: " + playerDeck.getNumericValueDeck());
            getContentPane().revalidate();

            if (playerDeck.getNumericValueDeck() > 21) {
                lblTitle.setText("You lose.");
                moneyLabel.setText("Money: " + (userMoney - 10) + "");
                // reenabled on next round button press.
                hitBut.setEnabled(false);
                return;
            } 
        });
    }
    
    Deck dealerDeck; /* deck object used in this method. */
    /**
     * action listener for stand button.
     * the flipped png image corresponds to the 
     * first card in the dealer hand arraylist
     * so once the user hits stand, we can display this card
     * to the user by replacing this flipped image icon with
     * the respective png image of the first card in the dealer
     * deck/list. While the sum of the dealer hand is less than 17
     * they are allowed to draw more cards, if they are over the drawing 
     * will stop. then we check the winner.
     * @param standBut jbutton obj for stand button.
     */
    public void standButton(JButton standBut, JButton hitBut) {
    	standBut.addActionListener(e -> {
            // Handle stand button click
            System.out.println("Stand button clicked.");
            ImageIcon unflipped = new ImageIcon("PNG-cards-1.3/"
                + dealerHand.get(0).toString() + ".png");
                unflipped.setImage(unflipped.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
            hiddencard.setIcon(unflipped);
            dealerDeck = new Deck(dealerHand);
            while (dealerDeck.getNumericValueDeck() < 17 
                && dealerDeck.getNumericValueDeck() < 21) {
                    Card draw = this.gameDeck.drawCards();
                    int drawVal = draw.getNumericValue();
                    dealerHand.add(draw);
                    dealerDeck = new Deck(dealerHand);
                    display(dealerHand.get(dealerHand.size() - 1), dealerCards);
                }
                lblDealerHand.setText("Dealer's Hand: " + dealerDeck.getNumericValueDeck());
            hitBut.setEnabled(false);
            checkWinner();
        });
    }

    /**
     * check who won.
     * checking which player is over 21
     * 
     */
    public void checkWinner() {
        Deck playerDeck = new Deck(playerHand);
        if (playerDeck.getNumericValueDeck() > 21) {
            lblTitle.setText("Dealer Wins");
            moneyLabel.setText("Money: " + (userMoney) + "");
            moneyLabel.revalidate();
            return;
        } else if (dealerDeck.getNumericValueDeck() > 21) {
            userMoney += 20;
            moneyLabel.setText("Money: " + (userMoney) + "");
            lblTitle.setText("Player Wins");
            moneyLabel.revalidate();
            return;
        }
        checkWinnerHelper(playerDeck);
    }

    /**
     * check which is closer to 21 if there was no bust.
     * @param playerDeck
     */
    public void checkWinnerHelper(Deck playerDeck) {
        if (dealerDeck.getNumericValueDeck() - 21 > playerDeck.getNumericValueDeck() - 21) {
            lblTitle.setText("Dealer Wins");
            moneyLabel.setText("Money: " + (userMoney) + "");
            moneyLabel.revalidate();
        } else if (dealerDeck.getNumericValueDeck() - 21 < playerDeck.getNumericValueDeck() - 21) {
            userMoney += 20;
            moneyLabel.setText("Money: " + (userMoney) + "");
            lblTitle.setText("Player Wins");
            moneyLabel.revalidate();
        } else {
            userMoney += 10;
            lblTitle.setText("It's a Push");
            moneyLabel.setText("Money: " + userMoney);
            moneyLabel.revalidate();
        }
    }

    /**
     * at the start of every blackjack game
     * the dealer gets the first card face down, 
     * then the player gets one, then the dealer
     * gets a card face up and finally the player gets
     * the last one. CAlls the display method
     * to get the png of the image to diplay
     * onto the jpanels.
     */
    public void initialDraw() {
        
        if (userMoney < 10) {
            System.out.println("Out of money. Try again");
            System.exit(1);
        }
        userMoney -= 10;
        moneyLabel.setText("Money: " + userMoney);
        Card one = this.gameDeck.drawCards();
        dealerHand.add(one);
        Card two = this.gameDeck.drawCards();
        playerHand.add(two);
        Card three = this.gameDeck.drawCards();
        dealerHand.add(three);
        Card four = this.gameDeck.drawCards();
        playerHand.add(four);
        // display flipped card.
        ImageIcon hidden = new ImageIcon("PNG-cards-1.3/hidden.png");
        hidden.setImage(hidden.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        hiddencard = new JLabel(hidden);
        dealerCards.add(hiddencard);

        display(playerHand.get(0), playerCards);
        display(dealerHand.get(1), dealerCards);
        display(playerHand.get(1), playerCards);

        Deck playerDeck = new Deck(playerHand);
        lblPlayerHand.setText("Player's Hand: " + playerDeck.getNumericValueDeck() + "");
        Deck dealerDeck = new Deck(dealerHand);
        lblDealerHand.setText("Dealer's Hand: " + dealerHand.get(1).getNumericValue() 
            + " ?");
    }

    /**
     * display images based on the filename 
     * needed. Goes to the package of pngs and 
     * grabs what is needed.
     * @param obj the object to display.
     * @param panel where to display.
     */
    public void display(Card obj, JPanel panel) {
        ImageIcon pic = new ImageIcon("PNG-cards-1.3/" + obj.toString() + ".png");
        pic.setImage(pic.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        JLabel pictest = new JLabel(pic);
        panel.add(pictest);
    }

    @Override
    /**
     * implements the resetGame method in the 
     * GameState interface, the current state
     * of the table is reset and repopulated with 
     * a new shuffled deck.
     */
    public void resetGame() {
        Scanner kb = new Scanner(System.in);
        for (Component component : playerCards.getComponents()) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                label.setIcon(null); // Set the icon to null to remove it
            }
        }
        // Remove image icons from dealerCards JPanel
        for (Component component : dealerCards.getComponents()) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                label.setIcon(null); // Set the icon to null to remove it
            }
        }
        playerHand.clear();
        dealerHand.clear();
        userMoney = 1000; /* default value */
        try {
            Scanner deckFile = new Scanner(new File("deck_of_cards.txt"));
            gameDeck = new Deck(deckFile);
            gameDeck.shuffle();
            initialDraw();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
