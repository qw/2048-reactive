package ui;

import com.google.gson.Gson;
import dependency.Provider;
import game.Game;
import game.GameState;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ui.ascii.human.MultiDisplayDemoAscii;
import ui.jfx.navigation.Navigator;
import ui.jfx.screens.game.GameScreen;
import ui.jfx.screens.game.GameViewModel;
import ui.jfx.screens.menu.MenuScreen;
import ui.network.TcpServer;

public class MasterView extends Application {

  private static final int WINDOW_HEIGHT = 400;

  private static final int WINDOW_WIDTH = 360;

  private Stage primaryStage;

  private StackPane root;

  private Scene scene;

  private Navigator navigator;

  private Provider provider;

  private GameViewModel gameViewModel;

  private MenuScreen menu;

  private Game game;

  @Override
  public void start(Stage primaryStage) throws Exception {
    this.primaryStage = primaryStage;
    provider = Provider.getInstance();
    game = provider.getGame();

    gameViewModel = new GameViewModel(game);

    root = new StackPane();
    navigator = provider.getNavigator(root);

    menu = new MenuScreen(navigator);
    root.getChildren().add(menu);
    root.addEventHandler(KeyEvent.KEY_PRESSED, this::controls);

    scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

    primaryStage.setTitle("2048");
    primaryStage.setScene(scene);
    primaryStage.show();

    gameViewModel.observeState().subscribe(this::setScreen);
    new Thread(() -> new TcpServer(game, new Gson()).display()).start();
  }

  private void setScreen(GameState gameState) {
    int size = root.getChildren().size();
    Node topNode = root.getChildren().get(size - 1);
    switch(gameState) {
    case MENU:
      while (!(topNode instanceof MenuScreen)) {
        navigator.prev();
        topNode = root.getChildren().get(size - 1);
      }
      break;
    case IDLE:
      if (topNode instanceof MenuScreen) {
        displayGameScreens();
      }
      break;
    }
  }

  // MasterView handles navigation related key events
  private void controls(KeyEvent keyEvent) {
    KeyCode key = keyEvent.getCode();

    int size = root.getChildren().size();
    Node topNode = root.getChildren().get(size - 1);
    switch (key) {
    case ESCAPE:
      navigator.prev();
      break;
    case ENTER:
      if (topNode instanceof MenuScreen) {
        MenuScreen menuScreen = (MenuScreen) topNode;
        displayGameScreens();
        gameViewModel.restartGame(menuScreen.getGameSize());
      }
      break;
    }
  }

  /**
   * Only displays the views, does not start the game
   */
  private void displayGameScreens() {
    int gameSize = menu.getGameSize();

    // Construct the Human UI view
    GameScreen humanGameScreen = new GameScreen(gameViewModel, navigator, provider.getHumanBoard(gameSize));

    // Construct the Machine UI view
    GameScreen machineGameScreen = new GameScreen(gameViewModel, navigator, provider.getMachineBoard(gameSize));
    machineGameScreen.setBackground(new Background((new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY))));

    HBox hBox = new HBox();
    hBox.getChildren().addAll(humanGameScreen, machineGameScreen);
    navigator.next(hBox);

    humanGameScreen.requestFocus();

    // Autosize is used to auto-scale the window for a larger board (e.g. 9x9);
    root.autosize();
    primaryStage.setHeight(root.getHeight());
    primaryStage.setWidth(root.getWidth());

    // Initialize the standard out console UI
    View view = new MultiDisplayDemoAscii(game);
    view.display();
  }
}
