package dependency;

import game.ConcreteGame;
import game.Game;
import game.board.NaiveBoard;
import game.score.ConcreteScoreKeeper;

/**
 * Singleton.
 * Because JavaFx requires a default non-args constructor, this class is used for dependency injection.
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
}
