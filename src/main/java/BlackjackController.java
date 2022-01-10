import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.util.List;

public class BlackjackController {

    @FXML
    List<Label> playerCards, dealerCards;
    @FXML
    TextField betTextField;
    @FXML
    Label playerCardsTotalLabel, dealerCardsTotalLabel,
            messageLabel, totalEarningsLabel;
    double betAmount;
    double earnings, totalEarnings;
    int playerTotal;
    int dealerTotal;
    int playerAces;
    int dealerAces;
    int dealerFaceDown;
    boolean gameInProgress;

    private final Deck deck = new Deck();

    @FXML
    public void initialize(){
        messageLabel.setText("Betting is optional");
        gameInProgress = false;
        betAmount = 0;
        earnings = 0;
        totalEarnings = 0;
        playerTotal = 0;
        dealerTotal = 0;
        playerAces = 0;
        dealerAces = 0;
        dealerFaceDown = 0;
    }

    public void onStartRound(ActionEvent actionEvent) {
        if (gameInProgress == true) {
            messageLabel.setText("There is a game in progress");
        } else {
            clearTable();
            if (betTextField.getText() == null) {
                messageLabel.setText("You did not enter a bet.");
            } else{
                try{
                    betAmount = Integer.parseInt(betTextField.getText());
                    messageLabel.setText("You bet $" + betTextField.getText());
                }catch(Exception exc){
                    messageLabel.setText("You didn't enter a valid bet, so bet is zero");
                }
            }
            gameInProgress = true;

            firstRound();

            if(dealerTotal == 21 && playerTotal == 21){
                messageLabel.setText("You and the dealer both have a natural blackjack. Stalemate.");
                gameInProgress = false;
            } else if(dealerTotal == 21){
                messageLabel.setText("The dealer has blackjack. You lose $" + betAmount);
                totalEarnings -= betAmount;
                totalEarningsLabel.setText(String.valueOf(totalEarnings));
                gameInProgress = false;
            } else if(playerTotal == 21){
                earnings = (betAmount * 1.5);
                messageLabel.setText("You have blackjack and the dealer does not. You win " + earnings);
                totalEarnings += (earnings + betAmount);
                totalEarningsLabel.setText(String.valueOf(totalEarnings));
                gameInProgress = false;
            } else {
                messageLabel.setText("Choose to hit or stand.");
            }

            //dealer cannot double down or split pairs

            //splitting pairs make this a button that pops up
            //if user gets two of same card as first two cards, can count them as two hands
            //can bet on each hand separately
//        if(myCardsLabels.get(0).getText().equals(myCardsLabels.get(1).getText()) &&
//                !(myCardsLabels.get(0).getText().equals("1"))){
//            //splitting pairs
//        }
//        else if (myOnes == 2){
//            //if the pair is aces, the player can get one card for each ace
//            //if a ten is dealt to an ace, the payoff is equal to the bet, not 1.5
//        }


            //double down: make this a button that pops up

            //if first two cards equal 9, 10, or 11, player can double his bet on his turn
            //dealer gives player one more card, face down until after dealer finishes
            //cannot bet or hit after you double down

            //insurance: make this a button that pops up
            //if dealers face up card is an ace, player can set aside insurance
            //if dealer has natural blackjack, player gets to keep their initial bet

        }
    }

    private void dealPlayer() {
        Card card = deck.nextCard();
        playerTotal += card.value;
        playerCardsTotalLabel.setText(String.valueOf(playerTotal));
        if (card.value == 11) {
            playerAces++;
        }
        for (Label label : playerCards) {
            if (label.getText().equals("")) {
                label.setText(String.valueOf(card.value));
                label.applyCss();
                break;
            }
        }
    }

    private void firstRound() {
        dealPlayer();
        dealPlayer();

        Card cardOne = deck.nextCard();
        dealerTotal += cardOne.value;
        if (cardOne.value == 11) {
            dealerAces++;
        }
        dealerCards.get(0).setText(String.valueOf(cardOne.value));
        dealerCardsTotalLabel.setText(String.valueOf(cardOne.value));

        Card cardTwo = deck.nextCard();
        dealerFaceDown = cardTwo.value;
        dealerTotal += cardTwo.value;
        if (cardTwo.value == 11 && cardOne.value == 11) {
            dealerTotal -= 10;
            dealerFaceDown = 1;
        } else if(cardTwo.value == 11){
            dealerAces++;
        }
    }

    private void dealersTurn() {
        dealerCards.get(1).setText(String.valueOf(dealerFaceDown));
        dealerCardsTotalLabel.setText(String.valueOf(dealerTotal));

        while (dealerTotal <= 16) {
            Card card = deck.nextCard();
            dealerTotal += card.value;
            if(card.value == 11 && (dealerTotal > 21)){
                messageLabel.setText("Dealer has to count Ace as value 1");
                dealerTotal -= 10;
            }
            dealerCardsTotalLabel.setText(String.valueOf(dealerTotal));
            for (Label label : dealerCards) {
                if (label.getText().equals("")) {
                    label.setText(String.valueOf(card.value));
                    break;
                }
            }
        }
        if (dealerTotal > 21) {
            messageLabel.setText("Dealer busted. You win $" + betAmount);
            totalEarnings += (betAmount * 2);
            totalEarningsLabel.setText(String.valueOf(totalEarnings));
            gameInProgress = false;
        } else if (playerTotal < dealerTotal ||
                playerTotal == dealerTotal) {
            messageLabel.setText("The dealer beat you. You lost $" + betAmount + " this round.");
            totalEarnings -= betAmount;
            totalEarningsLabel.setText(String.valueOf(totalEarnings));
            gameInProgress = false;
        } else {
            messageLabel.setText("You win $" + betAmount);
            totalEarnings += (betAmount * 2);
            totalEarningsLabel.setText(String.valueOf(totalEarnings));
            gameInProgress = false;
        }
    }

    public void onStand(ActionEvent actionEvent) {
        if (gameInProgress == false) {
            messageLabel.setText("Click Start to begin a new game.");
        } else{
            dealersTurn();
        }
    }

    public void onHit(ActionEvent actionEvent) {
        if (gameInProgress == false) {
            messageLabel.setText("Click Start to begin a new game.");
        } else{
            dealPlayer();
            if (playerTotal > 21 && playerAces > 0) {
                messageLabel.setText("Your Ace is now a 1");
                playerTotal -= 10;
                playerCardsTotalLabel.setText(String.valueOf(playerTotal));
                playerAces--;
            } else if(playerTotal > 21 && playerAces == 0){
                messageLabel.setText("You busted! This round you lost $" + betAmount);
                totalEarnings -= betAmount;
                totalEarningsLabel.setText(String.valueOf(totalEarnings));
                gameInProgress = false;
            } else if(playerTotal == 21) {
                messageLabel.setText("You are at 21. STAND!!");
            } else if (playerTotal < 21){
                messageLabel.setText("You are at " + playerTotal + ". Choose your next move wisely...");
            }
        }
    }

    private void clearTable() {
        for (Label card : playerCards) {
            card.setText("");
        }
        for (Label card : dealerCards) {
            card.setText("");
        }
        dealerCardsTotalLabel.setText("");
        dealerTotal = 0;
        dealerFaceDown = 0;
        dealerAces = 0;
        playerCardsTotalLabel.setText("");
        playerTotal = 0;
        playerAces = 0;
        earnings = 0;
    }


}
