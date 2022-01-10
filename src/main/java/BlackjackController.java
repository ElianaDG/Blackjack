import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class BlackjackController {

    @FXML
    List<ImageView> playerCards, dealerCards;
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
    Card dealerFaceDownCard;
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

    public void onStartRound(ActionEvent actionEvent) throws IOException {
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

    private void dealPlayer() throws IOException {
        Card card = deck.dealCard();
        playerTotal += card.getValue();
        playerCardsTotalLabel.setText(String.valueOf(playerTotal));
        if (card.getValue() == 11) {
            playerAces++;
        }
        for (ImageView imageView : playerCards) {
            if (imageView.getImage() == null) {
                imageView.setImage(new Image(String.valueOf(card.showCard())));
                break;
            }
        }
    }

    private void firstRound() throws IOException {
        dealPlayer();
        dealPlayer();

        Card cardOne = deck.dealCard();
        dealerTotal += cardOne.getValue();
        if (cardOne.getValue() == 11) {
            dealerAces++;
        }
        dealerCards.get(0).setImage(new Image(String.valueOf(cardOne.showCard())));
        dealerCardsTotalLabel.setText(String.valueOf(cardOne.getValue()));

        Card dealerFaceDownCard = deck.dealCard();
        dealerFaceDown = dealerFaceDownCard.getValue();
        dealerTotal += dealerFaceDownCard.getValue();
        if (dealerFaceDownCard.getValue() == 11 && cardOne.getValue() == 11) {
            dealerTotal -= 10;
            dealerFaceDown = 1;
        } else if(dealerFaceDownCard.getValue() == 11){
            dealerAces++;
        }
    }

    private void dealersTurn() throws IOException {
        dealerCards.get(1).setImage(new Image(String.valueOf(dealerFaceDownCard.showCard())));
        dealerCardsTotalLabel.setText(String.valueOf(dealerTotal));

        while (dealerTotal <= 16) {
            Card card = deck.dealCard();
            dealerTotal += card.getValue();
            if(card.getValue() == 11 && (dealerTotal > 21)){
                messageLabel.setText("Dealer has to count Ace as value 1");
                dealerTotal -= 10;
            }
            dealerCardsTotalLabel.setText(String.valueOf(dealerTotal));
            for (ImageView imageView : dealerCards) {
                if (imageView.getImage() == null) {
                    imageView.setImage(new Image(String.valueOf(card.showCard())));
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

    public void onStand(ActionEvent actionEvent) throws IOException {
        if (gameInProgress == false) {
            messageLabel.setText("Click Start to begin a new game.");
        } else{
            dealersTurn();
        }
    }

    public void onHit(ActionEvent actionEvent) throws IOException {
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
        for (ImageView imageView : playerCards) {
            imageView.setImage(null);
        }
        for (ImageView imageView : dealerCards) {
            imageView.setImage(null);
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
