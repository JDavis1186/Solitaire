/**
 * The foundations (four card stacks you try to put all the cards into) for {@link Solitaire}
 * @author Josh Davis
 * @version %I% %G%
 */
public class Foundation extends CardStack {
	private int margin = Solitaire.margin;
	private int cardwidth = Solitaire.cardwidth;
	private BackImage back;
	
	/**
	 * Constructor for the foundation.
	 * The argument number defines which foundation this is (increasing from left to right),
	 * which is used to determine both the id number of the stack.
	 * 
	 * @param number The number indicating which foundation this is
	 */
	public Foundation(int number){
		//Define the stack id number for this foundation
		id=number;
		//set the x + y coordinates for the foundation
		xOrigin = (margin*(2+number))+(cardwidth*(1+number));
		yOrigin = margin;
		//Set the background image
		back = new BackImage(xOrigin, yOrigin,id);
		Solitaire.addThis(back);
	}
	
	/**
	 * Places a card onto this foundation.
	 * 
	 * @param card The Card being placed on this foundation
	 */
	public void place(Card card){
		//Set the card's handler's stack ID to the one for this foundation
		card.setHandlerStack(id);
		//Move the card's position in the window to this foundation's
		card.setLocation(xOrigin, yOrigin);
		//Layer the card in front
		Solitaire.moveCardToFront(card);
		//Push it onto this foundation
		push(card);
	}
	
	/**
	 * Checks if the specified card can legally be placed on this foundation.
	 * 
	 * @param card The Card we want to know if we can place on this foundation
	 * @return boolean indicating if the specified Card can be placed here
	 */
	public boolean check(Card card){
		//First, make sure we're only trying to put one card here
		if(Solitaire.held.oneCard()){
			//If there's no cards on this foundation and the card we're checking is an Ace, it's good
			if(isEmpty()){
				if(card.whatvalue()==1){
					return true;
				} else return false;
			//If the foundation isn't empty, check if the card is the same suit & one value higher than the top card
			} else {
				if((peek().whatsuit()==card.whatsuit())&&(peek().whatvalue()==(card.whatvalue()-1))){
					return true;
				} else return false;
			}
		} else return false;
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
	 * Clears all cards from this Foundation, returning them to the deck.
	 */
	public void clearCards(){
		//Remove all the cards
		while(!isEmpty()){
			Solitaire.stock.returnCard(pop());
		}
	}
}
