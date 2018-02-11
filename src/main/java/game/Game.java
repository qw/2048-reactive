package game;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public interface Game extends Disposable {

  void newGame(int size);

  void endGame();

  void menu();

  Observable<int[][]> observeBoard();

  Observable<Integer> observeScore();

  Observable<GameState> observeState();

  boolean tryMove(Direction moveDirection);

  boolean hasMove();

}
