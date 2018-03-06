package dependency;

import game.ConcreteGame;
import game.Game;
import game.board.NaiveBoard;
import game.score.ConcreteScoreKeeper;
import javafx.scene.layout.StackPane;
import ui.jfx.components.board.BoardComponent;
import ui.jfx.components.board.ColorfulBoardComponent;
import ui.jfx.components.board.NormalizedBoardComponent;
import ui.jfx.components.tile.ColorfulTileComponent;
import ui.jfx.components.tile.NormalizedTileComponent;
import ui.jfx.components.tile.TileComponent;
import ui.jfx.navigation.Navigator;
import ui.jfx.navigation.SimpleNavigator;

/**
 * Singleton.
 * This class is used for pseudo dependency injection.
 */
public class Provider {

  private static Provider instance;

  private Provider() {
  }

  public Game getGame() {
    return new ConcreteGame(new NaiveBoard(), new ConcreteScoreKeeper());
  }

  public Navigator getNavigator(StackPane root) {
    return new SimpleNavigator(root);
  }

  public BoardComponent getHumanBoard(int size) {
    return new ColorfulBoardComponent(size);
  }

  public TileComponent getHumanTile(double x, double y, double size) {
    return new ColorfulTileComponent(x, y, size);
  }

  public TileComponent getMachineTile(double x, double y, double size) {
    return new NormalizedTileComponent(x, y, size);
  }

  public BoardComponent getMachineBoard(int size) {
    return new NormalizedBoardComponent(size);
  }

  public static Provider getInstance() {
    return instance == null ? instance = new Provider() : instance;
  }
}
