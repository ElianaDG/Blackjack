import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class CardDeck {

    private Stack<Integer> deck;

    public CardDeck(){
        List<Integer> deck1 = Arrays.asList(1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3,
        4, 4, 4, 4, 5, 5, 5, 5, 6, 6, 6, 6, 7, 7, 7, 7, 8, 8, 8, 8, 9, 9, 9, 9,
                10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10);

        Collections.shuffle(deck1);
        deck = new Stack<>();
        deck.addAll(deck1);
    }

    public int nextCard(){return deck.pop();}

    public boolean isEmpty() {return deck.isEmpty();}
}
