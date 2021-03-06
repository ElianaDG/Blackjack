import javafx.scene.Parent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Card extends Parent {

    enum Suit {
        SPADES, DIAMONDS, HEARTS, CLUBS;
    }
    enum Rank {
        ACE(11), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7),
        EIGHT(8), NINE(9), TEN(10), JACK(10), QUEEN(10), KING(10);
        int value;

        Rank(int value) {
            this.value = value;
        }
    }

        public final Suit suit;
        public final Rank rank;
        public final int value;

        public Card(Suit suit, Rank rank) {
            this.suit = suit;
            this.rank = rank;
            this.value = rank.value;
        }
    }
