import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.List;

public class BlackjackController {

    @FXML
    List<Label> myCardsLabels, dealerCardsLabels;
    @FXML
    TextField betAmount;
    @FXML
    Label totalBetLabel, myCardsTotalLabel, dealerCardsTotalLabel,
            messageLabel, earningsLabel;
    int totalBet = 0;
    int earnings = 0;
    int myCardsTotal = 0;
    int dealerCardsTotal = 0;
    int myOnes = 0;
    int dealerOnes = 0;
    int dealerFaceDown = 0;
    boolean gameOver = false;
    boolean dealerBlackjack = false;
    boolean meBlackjack = false;

    private final CardDeck deck;

    public BlackjackController(CardDeck deck) {
        this.deck = deck;
    }

    public void onStartRound(ActionEvent actionEvent) {
        if (!gameOver) {
            messageLabel.setText("There is a game in progress");
        } else {
            //player must bet to join round
            while (betAmount.getText() == null) { //validate input.   .equals("") ??
                messageLabel.setText("You must place a bet to enter the round.");
            }
            totalBet += Integer.parseInt(betAmount.getText());

            firstRound();

            //check for natural blackjacks
            if((dealerFaceDown == 1 && dealerCardsTotal == 10) ||
                    (dealerFaceDown == 10 && dealerOnes == 1)){
                dealerBlackjack = true;
            }
            if(myOnes == 1 && myCardsTotal == 10){
                meBlackjack = true;
            }
            if(dealerBlackjack && meBlackjack){
                messageLabel.setText("You and the dealer both have a natural blackjack.");
                gameOver = true;
                roundOver();
            } else if(dealerBlackjack){
                messageLabel.setText("dealer has blackjack. You lose");
                gameOver = true;
                roundOver();
            } else if(meBlackjack){
                messageLabel.setText("You have blackjack and the dealer does not. You win!");
                earnings += (totalBet * 1.5);
                earningsLabel.setText(String.valueOf(earnings));
                gameOver = true;
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

            //settlement:
            //players loses bet if they bust, even if dealer also busts
            //if dealer busts, all players <=21 get paid out
            //if dealer stands at <=21, he pays players with a higher total (not busted ones)
            //if dealer stands and player has same total, player keeps bet, no other exchange
        }
    }

    public void dealPlayers() {
        //deal each player a card face up, including dealer
        int card = deck.nextCard();
        if (card != 1) {
            myCardsTotal += card;
        } else {
            myOnes++;
        }
        //myCardsTotalLabel.setText(String.valueOf(myCardsTotal));
        for (Label label : myCardsLabels) {
            if (label.getText().equals("")) {
                label.setText(String.valueOf(card));
                break;
            }
        }
    }

    public void firstRound() {
        dealPlayers();
        dealPlayers();

        int card = deck.nextCard();
        if (card != 1) {
            dealerCardsTotal += card;
        } else {
            dealerOnes++;
        }
        dealerCardsLabels.get(0).setText(String.valueOf(card));

        dealerFaceDown = deck.nextCard();

    }

    public void dealersTurn() {
        //turns his face down card right side up
        dealerCardsLabels.get(1).setText(String.valueOf(dealerFaceDown));
        dealerCardsTotal += dealerFaceDown;

        while (dealerCardsTotal <= 16) {
            int card = deck.nextCard();
            for (Label label : dealerCardsLabels) {
                if (label.getText().equals("")) {
                    label.setText(String.valueOf(card));
                    break;
                }
            }
            dealerCardsTotal += card;
            dealerCardsTotalLabel.setText(String.valueOf(myCardsTotal));
        }
        if (dealerCardsTotal > 21) {
            messageLabel.setText("Dealer busted");
            earnings += totalBet;
            earningsLabel.setText(String.valueOf(earnings));
            gameOver = true;
            roundOver();
        } else if (myCardsTotal < dealerCardsTotal ||
                myCardsTotal == dealerCardsTotal) {
            messageLabel.setText("The dealer beat you");
            earnings -= totalBet;
            earningsLabel.setText(String.valueOf(earnings));
            gameOver = true;
            roundOver();
        } else {
            messageLabel.setText("You win!");
            earnings += totalBet;
            earningsLabel.setText(String.valueOf(earnings));
            gameOver = true;
            roundOver();
        }
        //if he gets an ace and if he uses it as an 11 it would take him to >= 17, but not over 21
        //then he has to count it as an 11 and stand
    }

    public void onBet(ActionEvent actionEvent) {
        totalBet += Integer.parseInt(betAmount.getText());
        getCard();
        if (myCardsTotal > 21) {
            roundOver();
        }
    }

    public void onStand(ActionEvent actionEvent) {
        if (!gameOver) {
            messageLabel.setText("Click Start to begin a new game.");
        }
        dealersTurn();
        gameOver = true;
        roundOver();
    }

    public void onHit(ActionEvent actionEvent) {
        if (!gameOver) {
            messageLabel.setText("Click Start to begin a new game.");
        }
        getCard();
        if (myCardsTotal > 21) {
            messageLabel.setText("You busted!");
            gameOver = true;
            roundOver();
        } else {
            messageLabel.setText("You are still safe");
        }
        //deal with 1 vs 11
    }

    private void roundOver() {
        for (Label card : myCardsLabels) {
            card.setText("");
        }
        for (Label card : dealerCardsLabels) {
            card.setText("");
        }
        earningsLabel.setText(String.valueOf(earnings));
    }

    public void getCard() {
        int newCard = deck.nextCard();
        for (Label card : myCardsLabels) {
            if (card.getText() == null) { //.equals("") ??
                card.setText(String.valueOf(newCard));
                myCardsTotal += newCard;
                myCardsTotalLabel.setText(String.valueOf(myCardsTotal));
            }
        }
    }

}
