package game;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public interface Game extends Disposable {

  void startGame(int size);

  Observable<int[][]> observeBoard();

  Observable<GameState> observeState();

  Observable<Integer> observeScore();

  boolean tryMove(Direction moveDirection);

  boolean hasMove();

}
