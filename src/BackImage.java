import javax.swing.*;

/**
 * The background images for the different stacks of cards,
 * for use in Solitaire.
 * 
 * @author Josh Davis
 * @version %I% %G%
 * @see Solitaire
 */
public class BackImage extends JLabel {
	private int cardwidth = Solitaire.cardwidth;
	private int cardheight = Solitaire.cardheight;
	private ImageIcon normal = new ImageIcon(getClass().getClassLoader().getResource("space.png"));
	private ImageIcon highlight = new ImageIcon(getClass().getClassLoader().getResource("spaceHighlight.png"));
	
	/**
	 * Constructs and initializes this background image.
	 * The x and y arguments specify where the background image
	 * will be drawn within the game window.
	 * The StackID argument is passed to the {@link SolHandler}
	 * for this background image and is used to identify which stack
	 * of cards it belongs to.
	 * 
	 * @param x	horizontal placement of the background image, in pixels
	 * @param y	vertical placement of the background image, in pixels
	 * @param StackID	identifies which stack of cards this background image belongs to
	 */
	public BackImage(int x, int y, int StackID){
		//Add a handler
		SolHandler handler = new SolHandler();
		handler.setStack(StackID);
		addMouseListener(handler);
		//set the image and its location
		setIcon(normal);
		setBounds(x, y, cardwidth, cardheight);
	}
	
	/**
	 * Turns on the highlight for this background image.
	 * This is used to indicate the selected card can be placed here.
	 */
	public void highlightOn(){
		setIcon(highlight);
	}
	
	/**
	 * Turns off the highlight for this background image.
	 */
	public void highlightOff(){
		setIcon(normal);
	}
}
