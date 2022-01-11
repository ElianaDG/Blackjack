import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {

    private final Card[] deck = new Card[52];

    public Deck() {
        newDeck();
    }

    public void newDeck(){
        int ix = 0;
        for(Card.Suit suit : Card.Suit.values()){
            for(Card.Rank rank : Card.Rank.values()){
                deck[ix++] = new Card(suit, rank);
            }
        }
    }

    public Card nextCard(){
        Card card = null;
        while(card == null){
            int ix = (int) (Math.random() * deck.length);
            card = deck[ix];
            deck[ix] = null;
        }
        return card;
    }

}