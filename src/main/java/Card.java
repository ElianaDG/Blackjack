import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Card {

    private final int suit;
    private final int rank;
    private final int value;
    private int x;
    private int y;

    public Card(int suit, int rank, int value){
        this.suit = suit;
        this.rank = rank;
        this.value = value;
    }

    public int getSuit(){return suit;}

    public int getRank() {return rank;}

    public int getValue() {return value;}

    public BufferedImage showCard() throws IOException {
        int width = 950;
        int height = 392;
        BufferedImage deckImage = ImageIO.read(new File("resources/cards.png"));
        BufferedImage[][] cardImages = new BufferedImage[4][13];

        for(int suit = 0; suit < 4; suit++){
            for(int rank = 0; rank < 13; rank++){
                cardImages[suit][rank] = deckImage.getSubimage((rank * width / 13), (suit * height / 4), (width / 13), (height / 4));
            }
        }
        return cardImages[this.getSuit()][this.getRank()];
    }
}
