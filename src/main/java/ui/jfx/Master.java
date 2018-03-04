package ui.jfx;

import dependency.Provider;
import game.Game;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ui.View;
import ui.ascii.human.MultiDisplayDemoAscii;
import ui.jfx.navigation.Navigator;
import ui.jfx.screens.game.GameScreen;
import ui.jfx.screens.game.GameViewModel;
import ui.jfx.screens.menu.MenuScreen;

public class Master extends Application {

  private static final int WINDOW_HEIGHT = 400;

  private static final int WINDOW_WIDTH = 360;

  private Stage primaryStage;

  private StackPane root;

  private Scene scene;

  private Navigator navigator;

  private Provider provider;

  private GameViewModel gameViewModel;

  private MenuScreen menu;

  @Override
  public void start(Stage primaryStage) throws Exception {
    this.primaryStage = primaryStage;
    provider = Provider.getInstance();

    root = new StackPane();
    navigator = provider.getNavigator(root);

    menu = new MenuScreen(navigator);
    root.getChildren().add(menu);

    root.addEventHandler(KeyEvent.KEY_PRESSED, this::controls);

    scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

    primaryStage.setTitle("2048");
    primaryStage.setMinHeight(WINDOW_HEIGHT);
    primaryStage.setMinWidth(WINDOW_WIDTH);
    primaryStage.setResizable(false);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  // Master handles navigation related key events
  private void controls(KeyEvent keyEvent) {
    KeyCode key = keyEvent.getCode();

    switch (key) {
    case ESCAPE:
      System.exit(0);
      break;
    case ENTER:
      int size = root.getChildren().size();
      if (root.getChildren().get(size - 1) instanceof MenuScreen) {
        displayGameScreens();
      }
      break;
    }
  }

  private void displayGameScreens() {
    Game game = provider.getGame();
    int gameSize = menu.getGameSize();
    // Initialize the JavaFx view model
    gameViewModel = new GameViewModel(game);
    gameViewModel.restartGame(gameSize);
    // Construct the Human UI view
    GameScreen humanGameScreen = new GameScreen(gameViewModel, navigator, provider.getHumanBoard(gameSize));
    navigator.next(humanGameScreen);
    root.autosize();
    primaryStage.setHeight(root.getHeight());
    primaryStage.setWidth(root.getWidth());

    // Construct the Machine UI view
    GameScreen machineGameScreen = new GameScreen(gameViewModel, navigator, provider.getMachineBoard(gameSize));
    machineGameScreen.setBackground(new Background((new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY))));
    machineGameScreen.autosize();
    Scene secondaryScene = new Scene(machineGameScreen, WINDOW_WIDTH, WINDOW_HEIGHT);
    Stage secondaryStage = new Stage();
    secondaryStage.setScene(secondaryScene);
    secondaryStage.setHeight(root.getHeight());
    secondaryStage.setWidth(root.getWidth());
    secondaryStage.show();

    // Initialize the standard out UI
    View view = new MultiDisplayDemoAscii(game);
    view.display();
  }
}
