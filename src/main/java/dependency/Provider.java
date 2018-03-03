package dependency;

import game.ConcreteGame;
import game.Game;
import game.board.NaiveBoard;
import game.score.ConcreteScoreKeeper;
import javafx.scene.layout.StackPane;
import ui.jfx.SimpleNavigator;
import ui.jfx.navigation.Navigator;

/**
 * Singleton.
 * This class is used for pseudo dependency injection.
 */
public class Provider {

  private static Provider instance;

  private Provider() {
  }

  public static Provider getInstance() {
    return instance == null ? instance = new Provider() : instance;
  }

  public Game getGame() {
    return new ConcreteGame(new NaiveBoard(), new ConcreteScoreKeeper());
  }

  public Navigator getNavigator(StackPane root) {
    return new SimpleNavigator(root);
  }
}
