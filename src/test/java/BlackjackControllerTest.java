import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class BlackjackControllerTest {
    private Deck deck;
    private BlackjackController controller;
    private List<ImageView> dealerImages;
    private List<ImageView> playerImages;
    private Label messageLabel, playerCardsTotalLabel, dealerCardsTotalLabel;
    private TextField betAmount;

    @BeforeClass
    public static void beforeClass(){com.sun.javafx.application.PlatformImpl.startup(()->{});}

    private void givenBlackjackController(){
        deck = mock(Deck.class);
        controller = new BlackjackController();
        dealerImages = Arrays.asList(
                mock(ImageView.class),
                mock(ImageView.class),
                mock(ImageView.class),
                mock(ImageView.class)
        );
        controller.dealerCards = dealerImages;

        playerImages = Arrays.asList(
                mock(ImageView.class),
                mock(ImageView.class),
                mock(ImageView.class),
                mock(ImageView.class)
        );
        controller.playerCards = playerImages;

        messageLabel = mock(Label.class);
        controller.messageLabel = messageLabel;

        playerCardsTotalLabel = mock(Label.class);
        controller.playerCardsTotalLabel = playerCardsTotalLabel;
        dealerCardsTotalLabel = mock(Label.class);
        controller.dealerCardsTotalLabel = dealerCardsTotalLabel;

        betAmount = mock(TextField.class);
        controller.betTextField = betAmount;
    }

    @Test
    public void initialize(){
        //given
        givenBlackjackController();
        doReturn("Betting is optional").when(controller.messageLabel).getText();

        //when
        controller.initialize();

        //then
        verify(controller.messageLabel).setText("Betting is optional");

    }

    @Test
    public void onDealerNaturalBlackjack() throws IOException {
        //given
        givenBlackjackController();
        doReturn("21").when(controller.dealerCardsTotalLabel).getText();
        doReturn("15").when(controller.playerCardsTotalLabel).getText();
        doReturn("The dealer has blackjack. You lose").when(controller.messageLabel).getText();

        //when
        controller.onStartRound(mock(ActionEvent.class));

        //then
        verify(controller.dealerCardsTotalLabel).setText("21");
        verify(controller.playerCardsTotalLabel).setText("15");
        verify(controller.messageLabel).setText("The dealer has blackjack. You lose");
    }
    @Test
    public void onPlayerNaturalBlackjack() throws IOException {
        //given
        givenBlackjackController();
        int earnings = 100;
        doReturn("15").when(controller.dealerCardsTotalLabel).getText();
        doReturn("21").when(controller.playerCardsTotalLabel).getText();
        doReturn("You have blackjack and the dealer does not. You win " + earnings).when(controller.messageLabel).getText();

        //when
        controller.onStartRound(mock(ActionEvent.class));

        //then
        verify(controller.dealerCardsTotalLabel).setText("15");
        verify(controller.playerCardsTotalLabel).setText("21");
        verify(controller.messageLabel).setText("You have blackjack and the dealer does not. You win 100");
    }
    @Test
    public void onBothNaturalBlackjack() throws IOException {
        //given
        givenBlackjackController();
        doReturn("21").when(controller.dealerCardsTotalLabel).getText();
        doReturn("21").when(controller.playerCardsTotalLabel).getText();
        doReturn("You and the dealer both have a natural blackjack.").when(controller.messageLabel).getText();

        //when
        controller.onStartRound(mock(ActionEvent.class));

        //then
        verify(controller.dealerCardsTotalLabel).setText("21");
        verify(controller.playerCardsTotalLabel).setText("21");
        verify(controller.messageLabel).setText("You and the dealer both have a natural blackjack.");
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
    public void onStand() throws IOException {
        //given
        givenBlackjackController();

        //when
        controller.onStand(mock(ActionEvent.class));

        //then
        verifyNoInteractions(controller.playerCardsTotalLabel);

    }
    @Test
    public void onHit() throws IOException {
        //given
        givenBlackjackController();
        doReturn("21").when(controller.playerCardsTotalLabel).getText();
        doReturn("You are at 21. STAND!!").when(messageLabel).getText();

        //when
        controller.onHit(mock(ActionEvent.class));

        //then
        verify(controller.playerCardsTotalLabel).setText("21");
        verify(controller.messageLabel).setText("You are at 21. STAND!!");

    }

}


