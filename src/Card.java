import javax.swing.*;

/**
 * Generic playing card class, with handler code for {@link Solitaire}
 * 
 * @author Josh Davis
 * @version %I% %G%
 */
public class Card extends JLabel {
	private boolean faceup;
	private int value;
	private char suit;
	private final ImageIcon cardback = new ImageIcon(getClass().getClassLoader().getResource("back.png"));
	private ImageIcon cardfront;
	private ImageIcon highlight;
	private ImageIcon bluelight;
	private SolHandler handler;

	//This defines the width of the cards, used for positioning and size
	public final static int cardwidth=180;
	
	//This defines the height of the cards, used for positioning and size
	public final static int cardheight=251;
	
	
	/**
	 * Constructor creates the card of the specified suit and value.
	 * 
	 * @param value Numerical representation of card value
	 * @param suit char representing card suit
	 */
	public Card(int value, char suit){
		if((value < 1) || (value > 13) || !((suit == 'H') || (suit == 'D') || (suit == 'C') || (suit == 'S'))){
			//Produce an error if there is an attempt to create an invalid card
			System.err.println("Invalid card suit " + suit + ", value " + value);
		} else {
			this.value = value;
			this.suit = suit;
			faceup = false;
			// Defining the images for the card face
			cardfront = new ImageIcon(getClass().getClassLoader().getResource("card" + suit + String.valueOf(value) + ".png"));
			highlight = new ImageIcon(getClass().getClassLoader().getResource("card" + suit + String.valueOf(value) + "h.png"));
			bluelight = new ImageIcon(getClass().getClassLoader().getResource("card" + suit + String.valueOf(value) + "b.png"));
			// Setting the image to the card back since the card is facedown
			setIcon(cardback);
			setBounds(100, 100, Solitaire.cardwidth, Solitaire.cardheight);
			// Adding the handler
			handler = new SolHandler();
			addMouseListener(handler);
		}
	}
	
	/**
	 * Flips the card over.
	 */
	public void flip(){
		faceup = !faceup;
		if(faceup){
			setIcon(cardfront);
		} else {
			setIcon(cardback);
		}
	}
	
	/**
	 * Checks whether the card is face up.
	 * 
	 * @return boolean indicating if the card is face up
	 */
	public boolean isfaceup(){
		return faceup;
	}
	
	/**
	 * Checks if the card's suit is red (hearts or diamonds).
	 * If it's not, it can be assumed to be black.
	 * 
	 * @return boolean indicating if the card is red
	 */
	public boolean isred(){
		return ((suit == 'H') || (suit == 'D'));
	}
	
	/**
	 * Checks what suit the card is.
	 * 
	 * @return char representing the card's suit
	 */
	public char whatsuit(){
		return suit;
	}
	
	/**
	 * Checks what the value of the card is.
	 * 
	 * @return numerical representation of card value
	 */
	public int whatvalue(){
		return value;
	}
	
	
	/**
	 * Highlights the card in yellow.
	 */
	public void highlightOn(){
		setIcon(highlight);
	}
	
	/**
	 * Highlights the card in blue.
	 */
	public void highlightBlue(){
		setIcon(bluelight);
	}
	
	/**
	 * Removes the card's highlight.
	 */
	public void highlightOff(){
		setIcon(cardfront);
	}
	
	/**
	 * Passes the stack ID to the handler, which keeps tracks of it.
	 * 
	 * @param StackID The ID number of the new card stack
	 */
	public void setHandlerStack(int StackID){
		handler.setStack(StackID);
	}
}
