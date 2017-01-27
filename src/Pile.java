/**
 * The piles (stacks of cards of alternating color) for {@link Solitaire}
 * @author Josh Davis
 * @version %I% %G%
 */
public class Pile extends CardStack {
	//defines how far to fan the cards apart when face up
	private final int yFan=55;
	//defines how far to fan the cards apart when face down
	private final int yFanDown=20;
	private int margin = Solitaire.margin;
	private BackImage back;
	
	/**
	 * Constructor for the pile.
	 * The argument number defines which pile this is (increasing from left to right),
	 * which is used to determine both the id number of the stack and how many cards
	 * will be dealt into it initially.
	 * 
	 * @param number The number indicating which pile this is
	 */
	public Pile(int number){
		//define the stack id number for this pile
		id=number+6;
		//Set the x y coordinates for the pile
		xOrigin= margin + ((margin+cardwidth)*(number));
		yOrigin= (margin * 2) + cardheight;
		//Set the background image
		back = new BackImage(xOrigin, yOrigin,id);
		Solitaire.addThis(back);
	}
	
	/**
	 * Deals the appropriate number for cards to this Pile and flips the top one.
	 */
	public void init(){
		//Take the appropriate number of cards from the stock and place them on the pile
		for(int count=0; count<=(id-6); count++)
			place(Solitaire.stock.pop());
		//flip the top card face up
		peek().flip();
	}
	
	/**
	 * Places a card onto this pile.
	 * 
	 * @param card The Card being placed on this pile
	 */
	public void place(Card card){
		//Set the card's handler's id to the one for this pile
		card.setHandlerStack(id);
		//If there's no card on the pile, place the card at the coordinates of the pile
		if(empty()){
			card.setLocation(xOrigin, yOrigin);
		//If there's a face up card on top of the pile, place it the appropriate distance below it
		} else if (peek().isfaceup()){
			card.setLocation(xOrigin, yFan+peek().getY());
		//If there's a face down card on top, place it the appropriate distance below it
		} else {
			card.setLocation(xOrigin, yFanDown+peek().getY());
		}
		//Layer the card on top of the others
		Solitaire.moveCardToFront(card);
		//push into onto the pile
		push(card);
	}
	
	
	/**
	 * Checks if the specified card can legally be placed on this pile.
	 * 
	 * @param card The Card we want to know if we can place on this pile
	 * @return boolean indicating if the specified Card can be placed here
	 */
	public boolean check(Card card){
		// Check if the stack is empty and the card is a King
		if(isEmpty()){
			if(card.whatvalue()==13){
				return true;
			} else
				return false;
		} else {
		// Otherwise, check if the stack isn't empty...
			// And the top card is 1 value more than & the opposite color of the card we're trying to put here
			if(((card.whatvalue()+1)==peek().whatvalue())&&
					(card.isred()!=peek().isred())){
							return true;
			} else
				return false;
		}
	}
	
	/**
	 * Turns the highlight on on this pile's background image.
	 */
	public void backHighlightOn(){
		back.highlightOn();
	}
	
	/**
	 * Removes the highlight from this pile's background image.
	 */
	public void backHighlightOff(){
		back.highlightOff();
	}
	
	/**
	 * Clears all cards from this Pile, returning them to the deck.
	 */
	public void clearCards(){
		//Remove all the cards
		while(!isEmpty()){
			Solitaire.stock.returnCard(pop());
		}
	}
}
