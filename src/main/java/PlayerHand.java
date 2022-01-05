import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;

public class PlayerHand {

    private final ObservableList<Node> cards;
    private final SimpleIntegerProperty value = new SimpleIntegerProperty(0);

    private int numAces = 0;

    public PlayerHand(ObservableList<Node> cards){
        this.cards = cards;
    }

    public void getCard(Card card){
        cards.add(card);

        if(card.rank == Card.Rank.ACE){
            numAces++;
        }

        if(value.get() + card.value > 21 && numAces > 0){
            value.set(value.get() + card.value - 10);
            numAces--;
        } else {
            value.set(value.get() + card.value);
        }
    }

    public void reset(){
        cards.clear();
        value.set(0);
        numAces = 0;
    }

    public SimpleIntegerProperty valueProperty() {
        return value;
    }

}
