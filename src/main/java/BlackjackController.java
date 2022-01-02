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
    Label totalBetLabel, myCardsTotalLabel, dealerCardsTotalLabel, errorMessage;
    int totalBet = 0;
    int myCardsTotal = 0;
    int dealerCardsTotal = 0;
    int myOnes = 0;
    int dealerOnes = 0;

    private final CardDeck deck;

    //ace is either 1 or 11
    //all picture cards are 10

    public BlackjackController(CardDeck deck) {
        this.deck = deck;
    }

    public void onStartRound(ActionEvent actionEvent){
        //player must bet to join round
        while(betAmount.getText() == null){
            errorMessage.setText("You must place a bet to enter the round.");
        }
        totalBet += Integer.parseInt(betAmount.getText());

        //deal each player a card face up, including dealer
        int card = deck.nextCard();
        if(card != 1){
            myCardsTotal += card;
        } else {
            myOnes++;
        }
        myCardsTotalLabel.setText(String.valueOf(myCardsTotal));
        myCardsLabels.get(0).setText(String.valueOf(card));

        card = deck.nextCard();
        if(card != 1){
            dealerCardsTotal += card;
        } else {
            dealerOnes++;
        }
        dealerCardsTotalLabel.setText(String.valueOf(dealerCardsTotal));
        dealerCardsLabels.get(0).setText(String.valueOf(card));

        //give each player another card face up, dealer gets card face down
        card = deck.nextCard();
        if(card != 1){
            myCardsTotal += card;
        } else {
            myOnes++;
        }
        myCardsTotalLabel.setText(String.valueOf(myCardsTotal));
        myCardsLabels.get(1).setText(String.valueOf(card));

        card = deck.nextCard();
        if(card != 1){
            dealerCardsTotal += card;
        } else {
            dealerOnes++;
        }

        //if any player has an ace and a picture card, natural blackjack
        if(myOnes == 1 && myCardsTotal == 10){
            if(dealerOnes == 1 && dealerCardsTotal == 10){
                //natural blackjack
                //i get my bet back
            } else {
                //round is over
                //pay me 1.5 my bet
            }
        }

        //dealer cannot double down or split pairs

        //splitting pairs make this a button that pops up
        //if user gets two of same card as first two cards, can count them as two hands
        //can bet on each hand separately
        if(myCardsLabels.get(0).getText().equals(myCardsLabels.get(0).getText()) && !(myCardsLabels.get(0).getText().equals("1"))){
            //splitting pairs
        }
        else if (myOnes == 2){
            //if the pair is aces, the player can get one card for each ace
            //if a ten is dealt to an ace, the payoff is equal to the bet, not 1.5
        }


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

    public void onBet(ActionEvent actionEvent){
        //bet from 2-500
        //get value of bet from input textField
        //keep track of bet total per round
    }

    public void onStand(ActionEvent actionEvent){
        //do nothing, does not ask for another card
        //go to next player (if one player, go to dealer)
    }
    public void onHit(ActionEvent actionEvent) {
        //gets another card
        //if busts, player loses round and the bank gets their bets
        //deal with 1 vs 11
    }
    public void onGetCard(ActionEvent actionEvent){
        //generate next card in next available player card label
    }

    //dealer goes after all of the players
    //turns his face down card right side up
    //if total >= 17, he has to stand
    //if total <= 16, he has to hit
    //continue to hit until total >= 17
    //if he gets an ace and if he uses it as an 11 it would take him to >= 17, but not over 21
    //then he has to count it as an 11 and stand
}
