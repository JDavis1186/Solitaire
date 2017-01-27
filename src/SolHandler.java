import java.awt.event.*;
import java.util.Stack;

/**
 * Handles the mouse clicks and game actions for {@link Solitaire}.
 * One of these is attached to every card and to the {@link @BackImage}
 * of each card stack.
 * 
 * @author Josh Davis
 * @version %I% %G%
 */
public class SolHandler implements MouseListener{
	private int StackID=0;
	private boolean clickflag;
	private static boolean winner;
	
	/**
	 * Sets the ID value of the stack. Called when the SolHandler is first created,
	 * and whenever moving its card from one stack to another.
	 * 
	 * @param id	The ID number for the stack
	 */
	public void setStack(int id){
		StackID = id;
	}
	
	/**
	 * The big method that figures out what to do when the user clicked a card
	 * or background image.
	 * <p>
	 * If a card was clicked that can be selected, it's selected.
	 * If the card that was clicked was already selected, it's unselected.
	 * If a card was selected, and the user now clicked another card that the selected
	 * card can be placed on, that's what happens.
	 * And if the user is placing a King into one of the foundations, this method calls
	 * {@link winConditionCheck} to see if the game's been won.
	 * <p>
	 * Basically, this is the brains of the operation.
	 * 
	 * @param event	The mouse event that got us here
	 */
	private void clicked(MouseEvent event){
		// If the stack that is currently selected is clicked again, unselect it
		if (Solitaire.held.whichStack()==StackID){
			Solitaire.held.reset();
		// Otherwise...
		} else { 
			switch (StackID){
			case 0: // We clicked the deck (the stock)
					if (Solitaire.held.isEmpty()){
					// Make sure we don't have anything selected, because if we do we shouldn't be messing with the deck
						if (!Solitaire.stock.empty()){
							Solitaire.stock.draw();
						//If there's still cards to draw from the deck, do that
						} else {
							Solitaire.stock.reset();
						//If the deck was empty, reset it
						}
					}
					break;
			case 1: // We clicked the draw pile
					if ((Solitaire.held.isEmpty())&&(!Solitaire.stock.drawIsEmpty())){
					// If we aren't holding anything & the draw pile isn't empty
							Solitaire.held.holdThisCard(Solitaire.stock.drawPeek(), StackID);
							// Select the top card on the draw pile
					}
					// We wouldn't do anything to the draw pile if something is selected
					break;
			case 2:
			case 3:
			case 4:
			case 5: // We clicked one of the foundations
					if(!winner){
					//Only do anything if the game hasn't been won.
						if (!Solitaire.held.isEmpty()){
						//If we're holding anything
							if(Solitaire.foundations[StackID-2].check(Solitaire.held.whichCard())){
							//Check if it's a valid card to put on that foundation & if so...
								push(Solitaire.held.pop());
								Solitaire.held.reset();
								//Pop the held card and push it to the clicked stack; reset the hold.
								if(Solitaire.foundations[StackID-2].peek().whatvalue()==13){
									winConditionCheck();
								}
							}
						} else {
							if(!Solitaire.foundations[StackID-2].isEmpty())
							Solitaire.held.holdThisCard(Solitaire.foundations[StackID-2].peek(), StackID);
							//Hold the top card on the foundation (if one exists)
						}
					}
					break;
			default: // We clicked one of the 7 piles 
					if (Solitaire.held.isEmpty()){
					// If we're not holding a card...
						if(!Solitaire.piles[StackID-6].empty()){
						// And the pile we clicked is not empty...
							Card tempCard = (Card)event.getSource();
							if(tempCard.isfaceup()){
							// And the card we clicked is face up...
								Solitaire.held.holdThisCard(tempCard, StackID);
								// We select the card
							} else {
							// If it wasn't face up...
								if(tempCard==Solitaire.piles[StackID-6].peek()){
								// And it's the top card on the pile...
									tempCard.flip();
									// Flip it over
								}
							}
						}
					} else {
					// If we are holding a card...
						if(Solitaire.piles[StackID-6].check(Solitaire.held.whichCard())){
						//Check if it's a valid card to put on that pile & if so...
							pileMove();
							Solitaire.held.reset();
							// Move the held cards to that pile & reset selection
						}
					}
			}
		}
	}
	
	/**
	 * Moves the selected card and any cards on top of it onto the clicked {@link Pile}.
	 */
	private void pileMove(){
		//Make a buffer stack to put the cards on while we move them
		Stack<Card> buffer = new Stack<Card>();
		//Put the first card from the highlighted card on the buffer
		buffer.push(Solitaire.held.pop());
		//Until the specifically highlighted card is on the buffer, add another card to the buffer
		while(buffer.peek()!=Solitaire.held.whichCard()){
			buffer.push(Solitaire.held.pop());
		}
		//Then move the cards from the buffer to the appropriate stack until the buffer is empty
		while(!buffer.isEmpty()){
			push(buffer.pop());
		}
	}
	
	/**
	 * Checks if the user has won by seeing if there is a King on the top of all four {@link Foundation}s.
	 */
	private void winConditionCheck(){
		//Temporarily set the winner flag to true
		winner=true;
		//If any of the foundations don't have a king on top, set the flag to false
		for(int count=0; count<4; count++){
			if(Solitaire.foundations[count].peek().whatvalue()!=13)
				winner=false;
		}
		//If the flag is still set to true
		if(winner){
			Solitaire.winner();
		}
	}
	
	/**
	 * Required class for extending MouseListener, tracking mouse clicks.
	 * We don't use it because mouse clicks as defined by Java are too precise
	 * so it doesn't pick up the user's intended clicks much of the time.
	 */
	public void mouseClicked(MouseEvent event){
		//Don't do anything, click defined elsewhere
		//Java clicks are too picky
	}
	
	/**
	 * Required class for extending MouseListener. We don't use it for anything.
	 */
	public void mouseEntered(MouseEvent event){
		//Do nothing
	}
	
	/**
	 * Tracks when the mouse cursor leaves the object this handler is attached to.
	 */
	public void mouseExited(MouseEvent event){	
		// We left the card without releasing the mouse press, so we're not counting it as a click
		clickflag = false;
	}
	
	/**
	 * Tracks when the user presses a mouse button down.
	 */
	public void mousePressed(MouseEvent event){
		// We recognized pressing the mouse with a flag
		clickflag = true;		
	}
	
	/**
	 * Tracks when the user releases the mouse button. If they did this while the mouse
	 * is still hovering over the object they pressed it down in, we count it as
	 * a click and call {@link clicked}
	 */
	public void mouseReleased(MouseEvent event){
		// We released the mouse after pressing it on the same card, so we're counting that as a click
		if(clickflag){
			clicked(event);
		}
	}
	
	/**
	 * Pushes the specified card onto the clicked stack.
	 * 
	 * @param card The Card that's being pushed.
	 */
	public void push(Card card){
		//Make sure the stack is not the deck or draw pile
		if(StackID > 1){
			//If it's one of the foundations...
			if(StackID < 6){
				//If the foundation wasn't empty, unhighlight the top card before putting the new card on it
				if(!Solitaire.foundations[StackID-2].isEmpty())
					Solitaire.foundations[StackID-2].peek().highlightOff();
				//Put the card on the right foundation
				Solitaire.foundations[StackID-2].place(card);
			//If it's one of the piles...
			} else if(StackID <= 12){
				//If the pile wasn't empty, unhighlight the top card before putting the new card on it
				if(!Solitaire.piles[StackID-6].isEmpty())
					Solitaire.piles[StackID-6].peek().highlightOff();
				//Put the card on the right pile
				Solitaire.piles[StackID-6].place(card);
			}
		}
	}
}
