import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * A simple Klondike Solitaire card game by Josh Davis.
 * 
 * @author Josh Davis
 * @version %I% %G%
 */
public class Solitaire extends JPanel implements ActionListener{
	private static JLayeredPane SolWindow;
	public static Deck stock;
	public static Hold held = new Hold();
	public static Foundation[] foundations;
	public static Pile[] piles;
	protected JButton newGameButton;
	private static JLabel winner;
		
	//This defines the margin between the different stacks of cards
	public final static int margin = 10;
	
	//This defines the width of the cards, used for positioning
	public final static int cardwidth=180;
	
	//This defines the height of the cards, used for positioning
	public final static int cardheight=251;
	
	//This defines the window size of the game
	private final static Dimension gamesize = new Dimension((margin*10)+(cardwidth*7),cardheight*4);
	
	/**
	 * Constructs the play field for the Solitaire game.
	 * Creates a button for starting a new game, then the
	 * play area, where the cards are to be placed. Then sets up
	 * the deck/draw pile, 4 foundations, and 7 piles, before initializing
	 * the first game for you. Also sets up an image for when you win,
	 * and hides it from view.
	 */
	public Solitaire(){
		//set the minimum size of the window
		setMinimumSize(gamesize);
		setMaximumSize(gamesize);
		//set the background to an appropriate shade of green
		setBackground(new Color(0,100,0));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setFocusable(true);
	
		
		//Make the button that lets you create a new game
		newGameButton = new JButton("New Game");
		newGameButton.setVerticalTextPosition(AbstractButton.CENTER);
		newGameButton.setHorizontalTextPosition(AbstractButton.CENTER);
		newGameButton.setMnemonic(KeyEvent.VK_N);
		newGameButton.setActionCommand("newGame");
		newGameButton.addActionListener(this);
		add(newGameButton);
		
				
		//Make the game area
		SolWindow = new JLayeredPane();
		SolWindow.setMinimumSize(gamesize);
		SolWindow.setMaximumSize(new Dimension(gamesize.width,cardheight*5));
		SolWindow.setBackground(new Color(0,100,0));
		add(SolWindow, new Integer(1));
		
		//Define, then hide, the winner image
		ImageIcon winnerIcon = new ImageIcon(getClass().getClassLoader().getResource("winner.png"));
		winner = new JLabel();
		winner.setIcon(winnerIcon);
		SolWindow.add(winner, new Integer(2));
		winner.setBounds((gamesize.width-winnerIcon.getIconWidth())/2,
				(gamesize.height-winnerIcon.getIconHeight())/2,
				winnerIcon.getIconWidth(), winnerIcon.getIconHeight());
		winner.setVisible(false);
		
		//Create the 4 foundations
		foundations = new Foundation[4];
		for(int count=0; count<4; count++){
			foundations[count] = new Foundation(count+2);
		}
		
		//Create the 7 piles, which themselves deal the appropriate number of cards
		piles = new Pile[7];
		for(int count=0; count<7; count++){
			piles[count] = new Pile(count);
		}
		
		//Create the deck
		stock = new Deck();
		
		newGame();
		
	}
	
	/**
	 * Calls the newGame function if the New Game button is hit.
	 */
    public void actionPerformed(ActionEvent e) {
        if ("newGame".equals(e.getActionCommand())) {
            newGame();
        }
    }
	
    /**
     * Moves the specified card so that it's layered on top of
     * the other cards in the game window.
     * 
     * @param card The Card to be moved to the front
     */
	public static void moveCardToFront(Card card){
		SolWindow.moveToFront(card);
	}
	
	/**
	 * Adds a visual component to the game window.
	 * 
	 * @param thing The Component to be added to the game window
	 */
	public static void addThis(Component thing){
		SolWindow.add(thing);
	}
	
	/**
	 * Starts a new game. Resets everything from any previous game
	 * first, then shuffles the deck and deals the cards to the piles.
	 */
	private void newGame(){
		//Reset the hold
				held.reset();
				
		//Clear the piles
		for(int count=0; count<7; count++){
			piles[count].clearCards();
		}
			
		//Clear the foundations
		for(int count=0; count<4; count++){
			foundations[count].clearCards();
		}
		
		//Hide the winner image
		winner.setVisible(false);
		//Repaint the window
		Solitaire.SolWindow.repaint();
		
		
					
		//Initialize the deck
		stock.reset();
		stock.shuffle();
		
		//Initialize the piles
		for(int count=0; count<7; count++){
			piles[count].init();
		}
	}
	
	/**
	 * Makes the victory image visible, because the user won.
	 */
	public static void winner(){
		winner.setVisible(true);
	}
	
	/**
	 * Creates the GUI and makes it visible
	 */
	private static void createAndShowGUI(){
		JFrame frame = new JFrame("JDavis presents: Solitaire!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(gamesize);
		JComponent newContentPane = new Solitaire();
		newContentPane.setOpaque(true);
		frame.setContentPane(newContentPane);
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run(){
				createAndShowGUI();
			}
		});
	}
}
