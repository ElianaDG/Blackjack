import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BlackjackApplication extends Application {

    private final Deck deck = new Deck();
    private PlayerHand playerHand;
    private PlayerHand dealerHand;
    private final Text message = new Text();

    private final SimpleBooleanProperty gameInProgress = new SimpleBooleanProperty(false);
    private final HBox dealerCards = new HBox(20);
    private final HBox playerCards = new HBox(20);

    private Parent initialize() {
        dealerHand = new PlayerHand(dealerCards.getChildren());
        playerHand = new PlayerHand(playerCards.getChildren());

        Pane root = new Pane();
        root.setPrefSize(800, 600);
        //is the background even necessary?
        Region background = new Region();
        background.setPrefSize(800, 600);
        background.setStyle("-fx-background-color: rgba(194, 229, 247, 1)");

        HBox rootLayout = new HBox(5);
        rootLayout.setPadding(new Insets(5, 5, 5, 5));
        Rectangle gameBoard = new Rectangle(550, 560);
        gameBoard.setArcWidth(50);
        gameBoard.setArcHeight(50);
        gameBoard.setFill(Color.rgb(170, 225, 240));
        Rectangle gameControls = new Rectangle(230, 560);
        gameControls.setArcWidth(50);
        gameControls.setArcHeight(50);
        gameControls.setFill(Color.rgb(210, 215, 217));

        VBox gameBoardBox = new VBox(50);
        gameBoardBox.setAlignment(Pos.TOP_CENTER);

        Text dealerTotal = new Text("DEALER: ");
        Text playerTotal = new Text("PLAYER: ");

        gameBoardBox.getChildren().addAll(dealerTotal, dealerCards, message, playerCards, playerTotal);

        VBox gameControlsBox = new VBox(20);
        gameControlsBox.setAlignment(Pos.CENTER);

        TextField betAmount = new TextField("Place Bet");
        betAmount.setDisable(true);
        betAmount.setMaxWidth(50);

        Text earnings = new Text("Earnings");

        Button startGameButton = new Button("Start Round");
        Button hitButton = new Button("Hit");
        Button standButton = new Button("Stand");

        HBox buttonsBox = new HBox(15, hitButton, standButton);
        buttonsBox.setAlignment(Pos.CENTER);

        gameControlsBox.getChildren().addAll(betAmount, startGameButton, earnings, buttonsBox);

        rootLayout.getChildren().addAll(new StackPane(gameBoard, gameBoardBox), new StackPane(gameControls, gameControlsBox));
        root.getChildren().addAll(background, rootLayout);

        startGameButton.disableProperty().bind(gameInProgress);
        hitButton.disableProperty().bind(gameInProgress.not()); //maybe get rid of not()
        standButton.disableProperty().bind(gameInProgress.not());   //maybe get rid of not()

        playerTotal.textProperty().bind(new SimpleStringProperty("Player: ").concat(playerHand.valueProperty().asString()));
        dealerTotal.textProperty().bind(new SimpleStringProperty("Dealer: ").concat(dealerHand.valueProperty().asString()));

        playerHand.valueProperty().addListener((obs, old, newValue) -> {
            if (newValue.intValue() >= 21) {
                gameOver();
            }
        });

        dealerHand.valueProperty().addListener((obs, old, newValue) -> {
            if (newValue.intValue() >= 21) {
                gameOver();
            }
        });

        startGameButton.setOnAction(event -> {
            startGame();
        });

        hitButton.setOnAction(event -> {
            playerHand.getCard(deck.nextCard());
        });

        standButton.setOnAction(event -> {
            while (dealerHand.valueProperty().get() < 17) {
                dealerHand.getCard(deck.nextCard());
            }

            gameOver();
        });

        return root;
    }

    private void startGame() {
        gameInProgress.set(true);
        message.setText("");

        deck.newDeck();

        dealerHand.reset();
        playerHand.reset();

        dealerHand.getCard(deck.nextCard());
        dealerHand.getCard(deck.nextCard());
        playerHand.getCard(deck.nextCard());
        playerHand.getCard(deck.nextCard());
    }

    private void gameOver() {
        gameInProgress.set(false);

        int dealerValue = dealerHand.valueProperty().get();
        int playerValue = playerHand.valueProperty().get();
        String winner = "Exceptional case: d: " + dealerValue + " p: " + playerValue;

        if (dealerValue == 21 || playerValue > 21 || dealerValue == playerValue
                || (dealerValue < 21 && dealerValue > playerValue)) {
            winner = "DEALER";
        } else {
            winner = "PLAYER";
        }

        message.setText(winner + " WON");
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(new Scene(initialize()));
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.setResizable(false);
        primaryStage.setTitle("BlackJack");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
