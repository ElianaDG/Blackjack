import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {

    private final ArrayList<Card> deck;

    public Deck() {
        deck = new ArrayList<>();
        for (int suit = 0; suit < 4; suit++) {
            for (int rank = 0; rank < 13; rank++) {
                if (suit == 0) {
                    Card card = new Card(suit, rank, 11);
                    deck.add(card);
                } else if (rank >= 10) {
                    Card card = new Card(suit, rank, 10);
                } else {
                    Card card = new Card(suit, rank, rank + 1);
                }
            }
        }
    }

    public Card removeCard(int i) {
        return deck.remove(i);
    }

    public Card dealCard() {
        Random random = new Random();
        int num = random.nextInt(deck.size());
        return deck.get(num);
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

}
