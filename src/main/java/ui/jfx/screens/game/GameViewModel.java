package ui.jfx.screens.game;

import game.Direction;
import game.Game;
import game.GameState;
import io.reactivex.Observable;

public class GameViewModel {

  private Game game;

  private int gameSize;

  public GameViewModel(Game game) {
    this.game = game;

  }

  public boolean tryMove(Direction direction) {
    return game.tryMove(direction);
  }

  public void restartGame(int gameSize) {
    this.gameSize = gameSize;
    game.newGame(gameSize);
  }

  public void endGame() {
    this.game.endGame();
  }

  public int getSize() {
    return gameSize;
  }

  public Observable<int[][]> observeBoard() {
    return game.observeBoard();
  }

  public Observable<Integer> observeScore() {
    return game.observeScore();
  }

  public Observable<GameState> observeState() {
    return game.observeState();
  }
}
