import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.List;

public class BlackjackController {

    @FXML
    List<Label> playerCards, dealerCards;
    @FXML
    TextField betAmount;
    @FXML
    Label totalBetLabel, myCardsTotalLabel, dealerCardsTotalLabel,
            messageLabel;
    int totalBet, earnings;
    int playerTotal, dealerTotal;
    int playerAces, dealerAces;
    int dealerFaceDown;
    boolean dealerBlackjack, playerBlackjack;
    boolean gameInProgress = false;

    private final Deck deck = new Deck();

    @FXML
    public void initialize(){
        messageLabel.setText("You got this!");
    }

    public void onStartRound(ActionEvent actionEvent) {
        if (gameInProgress == true) {
            messageLabel.setText("There is a game in progress");
        } else {
            //player must bet to join round
            gameInProgress = true;
            while (betAmount.getText().equals("")) {
                messageLabel.setText("You must place a bet to enter the round.");
            }
            totalBet += Integer.parseInt(betAmount.getText());

            firstRound();   //player has two face up cards, dealer gets one up and one down

            //check for natural blackjacks
            if((dealerFaceDown == 11 && dealerTotal == 10) ||
                    (dealerFaceDown == 10 && dealerTotal == 11)){
                dealerBlackjack = true;
            }
            if(playerTotal == 21){
                playerBlackjack = true;
            }
            if(dealerBlackjack && playerBlackjack){
                messageLabel.setText("You and the dealer both have a natural blackjack.");
                gameInProgress = false;
                roundOver();
            } else if(dealerBlackjack){
                messageLabel.setText("dealer has blackjack. You lose");
                gameInProgress = false;
                roundOver();
            } else if(playerBlackjack){
                earnings += (totalBet * 1.5);
                messageLabel.setText("You have blackjack and the dealer does not. You win " + earnings);
                gameInProgress = false;
                roundOver();
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

    public void dealPlayer() {
        Card card = deck.nextCard();
        playerTotal += card.value;
        if (card.value == 11) {
            playerAces++;
        }
        for (Label label : playerCards) {
            if (label.getText().equals("")) {
                label.setText(String.valueOf(card.value));
                break;
            }
        }
    }

    public void firstRound() {
        dealPlayer();
        dealPlayer();

        Card card = deck.nextCard();
        dealerTotal += card.value;
        if (card.value == 11) {
            dealerAces++;
        }
        dealerCards.get(0).setText(String.valueOf(card.value));

        dealerFaceDown = deck.nextCard().value;

    }

    public void dealersTurn() {
        dealerCards.get(1).setText(String.valueOf(dealerFaceDown));
        dealerTotal += dealerFaceDown;

        while (dealerTotal <= 16) {
            Card card = deck.nextCard();
            dealerTotal += card.value;
            if(card.value == 11 && (dealerTotal > 21)){
                messageLabel.setText("Dealer has to count Ace as value 1");
                dealerTotal -= 10;
            }
            for (Label label : dealerCards) {
                if (label.getText().equals("")) {
                    label.setText(String.valueOf(card));
                    break;
                }
            }
            dealerCardsTotalLabel.setText(String.valueOf(dealerTotal));
        }
        if (dealerTotal > 21) {
            earnings += totalBet;
            messageLabel.setText("Dealer busted. You win $" + earnings);
            gameInProgress = false;
            roundOver();
        } else if (playerTotal < dealerTotal ||
                playerTotal == dealerTotal) {
            earnings -= totalBet;
            messageLabel.setText("The dealer beat you. You made $" + earnings + " this round.");
            gameInProgress = false;
            roundOver();
        } else {
            earnings += totalBet;
            messageLabel.setText("You win $" + earnings);
            gameInProgress = false;
            roundOver();
        }
    }

    public void onBet(ActionEvent actionEvent) {
        totalBet += Integer.parseInt(betAmount.getText());
        dealPlayer();
        if (playerTotal > 21) {
            messageLabel.setText("You busted. Dealer wins");
            earnings -= totalBet;
            roundOver();
        }
    }

    public void onStand(ActionEvent actionEvent) {
        if (!gameInProgress) {
            messageLabel.setText("Click Start to begin a new game.");
        }
        dealersTurn();
    }

    public void onHit(ActionEvent actionEvent) {
        if (!gameInProgress) {
            messageLabel.setText("Click Start to begin a new game.");
        }
        dealPlayer();
        if (playerTotal > 21 && playerAces > 0) {
            playerTotal -= 10;
            playerAces--;
        } else if(playerTotal > 21 && playerAces == 0){
            earnings -= totalBet;
            messageLabel.setText("You busted! This round you made $" + earnings);
            roundOver();
        } else if(playerTotal == 21) {
            messageLabel.setText("You are at 21. STAND!!");
        } else if (playerTotal < 21){
            messageLabel.setText("You are at " + playerTotal + ". Choose your next move wisely...");
        }
    }

    private void roundOver() {
        for (Label card : playerCards) {
            card.setText("");
        }
        for (Label card : dealerCards) {
            card.setText("");
        }
    }


}
