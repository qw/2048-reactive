package game;

import io.reactivex.Observable;

public interface Game {

  void newGame(int size);

  void endGame();

  void menu();

  Observable<int[][]> observeBoard();

  Observable<Integer> observeScore();

  Observable<GameState> observeState();

  Observable<Direction> observeMoveDirection();

  boolean tryMove(Direction moveDirection);

  boolean hasMove();

}
