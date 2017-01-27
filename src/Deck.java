import java.util.*;

/**
 * Card Deck class for {@link Solitaire} with included draw pile
 * 
 * @author Josh Davis
 * @version %I% %G%
 */
public class Deck extends CardStack {
	//Defines the four card suits
	private Character[] suits = {'S', 'C', 'H', 'D'};
	private CardStack drawPile = new CardStack();
	private int margin = Solitaire.margin;
	private int xFan = 30;
	private BackImage back;
	private int xOrigin = margin;
	private int yOrigin = margin;
	
	/**
	 * Constructs the deck. Generates the 52 playing
	 * cards, placing them on the deck face-down.
	 */
	public Deck(){
		//Set the background image
		back = new BackImage(xOrigin, yOrigin,0);
		Solitaire.addThis(back);
		//For each card suit, create the 13 cards and push them onto the deck
		for(Character suitsymbol : suits){
			for(int i = 1; i <= 13; i++){
				push(new Card(i, suitsymbol));
			}
		}
		//Shuffle the deck
		shuffle();
		//Add each card to the game window, set its position where the deck is, layer it on top
		for(Card card:this){
			Solitaire.addThis(card);
			card.setLocation(xOrigin, yOrigin);
			Solitaire.moveCardToFront(card);
		}
	}
	
	/**
	 * Draws 3 cards from the deck,
	 * fanning them out on the draw pile.
	 */
	public void draw(){
		//First shift all previously drawn cards to the left, so they're no longer fanned out
		for(Card card:drawPile){
			card.setLocation(margin*4 + cardwidth, yOrigin);
		}
		//The, three times...
		for(int i=0; i<3; i++){
			//As long as the deck isn't empty yet
			if(!empty()){
				//Draw a card
				drawPile.push(pop());
				//Move its screen location to the draw pile, fanned out
				drawPeek().setLocation((margin*(4)+cardwidth+(xFan*i)), yOrigin);
				//Flip it over
				drawPeek().flip();
				//Make sure it's drawn on top of the other drawn cards
				Solitaire.moveCardToFront(drawPeek());
				//Set its handler's stackID to 1 (identifying it as being in the draw pile)
				drawPeek().setHandlerStack(1);;
			}
		}
	}
	
	/**
	 * Moves all cards from the draw pile back onto the deck.
	 */
	public void reset(){
		//Until the draw pile is empty
		while(!drawPile.isEmpty()){
			//Flip the top drawpile card over
			drawPile.peek().flip();
			//Move its screen location to the deck location
			drawPile.peek().setLocation(xOrigin, yOrigin);
			//Change its handler StackID to 0 (for the deck)
			drawPile.peek().setHandlerStack(0);
			//Move it to the deck
			push(drawPile.pop());
		}
	}
	
	/** 
	 * Shuffles all cards in the deck.
	 */
	public void shuffle(){
		Collections.shuffle(this);
	}
	
	/**
	 * Pops one card from the top of draw pile.
	 * 
	 * @return Card from the top of the draw pile
	 */
	public Card drawPop(){
		return drawPile.pop();
	}
	
	/**
	 * Peeks at the card on the top of the draw pile.
	 * 
	 * @return Card from the top of the draw pile
	 */
	public Card drawPeek(){
		return drawPile.peek();
	}

	/**
	 * Checks if the draw pile is empty.
	 * 
	 * @return boolean indicating if the draw pile is empty
	 */
	public boolean drawIsEmpty(){
		return drawPile.isEmpty();
	}
	
	/**
	 * Returns a card to the deck.
	 * Used only when starting a new game.
	 * 
	 * @param card The Card being returned to the deck
	 */
	public void returnCard(Card card){
		if(card.isfaceup())
			card.flip();
		card.setLocation(xOrigin, yOrigin);
		card.setHandlerStack(0);
		push(card);
	}
}