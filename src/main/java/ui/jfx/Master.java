package ui.jfx;

import dependency.Provider;
import game.Game;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import ui.View;
import ui.ascii.human.MultiDisplayDemoAscii;
import ui.jfx.navigation.Navigator;
import ui.jfx.screens.game.GameViewModel;
import ui.jfx.screens.game.HumanGameScreen;
import ui.jfx.screens.menu.MenuScreen;

public class Master extends Application {

  private Stage primaryStage;

  private StackPane root;

  private Scene scene;

  private Navigator navigator;

  private Provider provider;

  private GameViewModel gameViewModel;

  private MenuScreen menu;

  private static final int WINDOW_HEIGHT = 400;

  private static final int WINDOW_WIDTH = 360;

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
    // Initialize the JavaFx view model
    gameViewModel = new GameViewModel(game);
    gameViewModel.restartGame(menu.getGameSize());
    // Construct the Human UI view
    HumanGameScreen humanGameScreen = new HumanGameScreen(gameViewModel, navigator);
    // Initialize the standard out UI
    View view = new MultiDisplayDemoAscii(game);
    view.display();
    navigator.next(humanGameScreen);
    root.autosize();
    primaryStage.setHeight(root.getHeight());
    primaryStage.setWidth(root.getWidth());
//    System.out.println(scene.getHeight() + " WIDTH: " + scene.getWidth() + "ROOT: " + root.getHeight());
  }
}
