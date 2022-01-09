import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Card extends Parent {

    enum Suit{
        SPADES, DIAMONDS, HEARTS, CLUBS;

//        final Image image;
//
//        Suit() {
//            this.image = new Image(Card.class.getResourceAsStream("images/".concat(name().toLowerCase()).concat(".png")),
//                    32, 32, true, true);
//        }
    }
    enum Rank{
        ACE(11), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7),
        EIGHT(8), NINE(9), TEN(10), JACK(10), QUEEN(10), KING(10);

        int value;
        Rank(int value){
            this.value = value;
        }

        String showCard(){
            return ordinal() < 9 ? String.valueOf(value) : name().substring(0, 1);
        }
    }

    public final Suit suit;
    public final Rank rank;
    public final int value;

    public Card(Suit suit, Rank rank){
        this.suit = suit;
        this.rank = rank;
        this.value = rank.value;
//
//        int width = 100;
//        int height = 140;
//        Rectangle bg = new Rectangle(width, height);
//        bg.setArcWidth(20);
//        bg.setArcHeight(20);
//        bg.setFill(Color.WHITE);
//
//        Text text1 = new Text(rank.showCard());
//        text1.setFont(Font.font(18));
//        text1.setX(width - text1.getLayoutBounds().getWidth() - 10);
//        text1.setY(text1.getLayoutBounds().getHeight());
//
//        Text text2 = new Text(text1.getText());
//        text2.setFont(Font.font(18));
//        text2.setX(10);
//        text2.setY(height - 10);

//        ImageView view = new ImageView(suit.image);
//        view.setRotate(180);
//        view.setX(WIDTH - 32);
//        view.setY(HEIGHT - 32);
//
//        getChildren().addAll(bg, new ImageView(suit.image), view, text1, text2);
    }

    @Override
    public String toString() {
        return rank.toString() + " of " + suit.toString();
    }
}
