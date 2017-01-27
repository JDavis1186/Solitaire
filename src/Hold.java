/**
 * Keeps track of the selected {@link Card} and what stack it's in for {@link Solitaire}.
 * 
 * @author Josh Davis
 * @version %I% %G%
 */
public class Hold {
	private Card heldCard;
	private int StackID = 99;
	
	/**
	 * Selects the given Card and notes its current stack.
	 * Also checks for cards or empty stacks that the card can legally be
	 * placed on and calls the methods to highlight them in blue.
	 * 
	 * @param thisCard The Card the user has selected
	 * @param thisStack The id of the card stack the selected card came from
	 */
	public void holdThisCard(Card thisCard, int thisStack){
		//Hold the card, set the stack ID
		heldCard = thisCard;
		StackID = thisStack;
		//Highlight the held card
		heldCard.highlightOn();
		//For each foundation
		for(int i=0;i<4;i++){
			//If it's a valid place to put the held card
			if(Solitaire.foundations[i].check(heldCard)){
				//Highlight the background if it's empty
				if(Solitaire.foundations[i].isEmpty()){
					Solitaire.foundations[i].backHighlightOn();
				//Highlight the top card if it's not empty
				} else {
					Solitaire.foundations[i].peek().highlightBlue();
				}
			}
		}
		//For each pile
		for(int i=0;i<7;i++){
			if(Solitaire.piles[i].check(heldCard)){
				//Highlight the background if it's empty
				if(Solitaire.piles[i].isEmpty()){
					Solitaire.piles[i].backHighlightOn();
				//Highlight the top card if it's not empty
				} else {
					Solitaire.piles[i].peek().highlightBlue();
				}
			}
		}
	}
	
	/**
	 * Returns the currently selected card.
	 * 
	 * @return	The currently selected card
	 */
	public Card whichCard(){
		return heldCard;
	}
	
	/**
	 * Returns the int identifying which stack the
	 * currently selected card is in.
	 * 
	 * @return The currently selected stack's ID
	 */
	public int whichStack(){
		return StackID;
	}
	
	/**
	 * Resets the selection to nothing.
	 * Used whenever the user manually unselects the card
	 * by clicking on it a second time or moves the card
	 * somewhere. Also turns all the highlights off for all
	 * cards and background images.
	 */
	public void reset(){
		if(heldCard!=null)
			heldCard.highlightOff();
		heldCard = null;
		StackID = 99;
		//Turn off all the highlights
		for(int i=0;i<4;i++){
			if(!Solitaire.foundations[i].isEmpty())
				Solitaire.foundations[i].peek().highlightOff();
			Solitaire.foundations[i].backHighlightOff();
		}
		for(int i=0;i<7;i++){
			if(!Solitaire.piles[i].isEmpty())
				Solitaire.piles[i].peek().highlightOff();
			Solitaire.piles[i].backHighlightOff();
		}
	}
	
	/**
	 * Pops the card off of the top of the stack the currently selected card is in.
	 * This may not be the selected card if it's in the middle of a {@link Pile}.
	 * 
	 * @return The Card from the top of the selected stack
	 */
	public Card pop(){
		if (StackID < 2){
			return Solitaire.stock.drawPop();
		} else if (StackID < 6){
			return Solitaire.foundations[StackID-2].pop();
		} else if (StackID <= 12){
			Card tempcard = Solitaire.piles[StackID-6].pop();
			if(!Solitaire.piles[StackID-6].isEmpty()){
					if(!Solitaire.piles[StackID-6].peek().isfaceup()){
						Solitaire.piles[StackID-6].peek().flip();
					}
			}
			return tempcard;
		} else {
			System.err.println("Something went wrong, trying to pop with nothing held!");
			return null;
		}
	}
	
	/**
	 * Peeks at the card on the top of the stack the currently selected card is in.
	 * This may not be the selected card if it's in the middle of a {@link Pile}.
	 * 
	 * @return The Card from the top of the selected stack
	 */
	public Card peek(){
		if (StackID < 2){
			return Solitaire.stock.drawPeek();
		} else if (StackID < 6){
			return Solitaire.foundations[StackID-2].peek();
		} else if (StackID <= 12){
			return Solitaire.piles[StackID-6].peek();
		} else {
			return null;
		}
	}
	
	/**
	 * Checks if a card is currently selected.
	 * 
	 * @return boolean indicating if a card is selected
	 */
	public boolean isEmpty(){
		if(heldCard == null)
			return true;
		else
			return false;
	}
	
	/**
	 * Checks if the selected card is on the top of its stack.
	 * 
	 * @return boolean indicating if selected card is on top of its stack
	 */
	public boolean oneCard(){
		if(StackID < 6){
			return true;
		} else if(StackID <= 12){
			if(Solitaire.piles[StackID-6].peek()==heldCard){
				return true;
			} else return false;
		} else return false;
	}
}
