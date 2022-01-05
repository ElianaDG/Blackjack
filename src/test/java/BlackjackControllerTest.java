import javafx.scene.control.Label;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;

public class BlackjackControllerTest {
    private Deck deck;
    private BlackjackController controller;
    private List<Label> dealerLabels;
    private List<Label> playerLabels;
    private Label message;
    private Label earnings;

    @BeforeClass
    public static void beforeClass(){com.sun.javafx.application.PlatformImpl.startup(()->{});}

    private void givenBlackjackController(){
        deck = mock(Deck.class);
        controller = new BlackjackController(deck);
        dealerLabels = Arrays.asList(
                mock(Label.class),
                mock(Label.class),
                mock(Label.class),
                mock(Label.class)
        );
        controller.dealerCardsLabels = dealerLabels;

        playerLabels = Arrays.asList(
                mock(Label.class),
                mock(Label.class),
                mock(Label.class),
                mock(Label.class)
        );
        controller.myCardsLabels = playerLabels;

        message = mock(Label.class);
        controller.messageLabel = message;

        earnings = mock(Label.class);
        controller.earningsLabel = earnings;
    }

    @Test
    public void onDealerNaturalBlackjack(){
        //given

        //when

        //then

    }
    @Test
    public void onPlayerNaturalBlackjack(){
        //given

        //when

        //then

    }
    @Test
    public void onBothNaturalBlackjack(){
        //given

        //when

        //then

    }
    @Test
    public void onPlayerBust(){
        //given

        //when

        //then

    }
    @Test
    public void onDealerBust(){
        //given

        //when

        //then

    }
    @Test
    public void onStand(){
        //given

        //when

        //then

    }
    @Test
    public void onHit(){
        //given

        //when

        //then

    }
    @Test
    public void onBet(){
        //given

        //when

        //then

    }


}


