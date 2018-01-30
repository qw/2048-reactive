package game;

import io.reactivex.Observable;

public interface Game {

  void initialise(int size);

  Observable<int[][]> observeBoard();

  Observable<GameState> observeState();

  Observable<Integer> observeScore();

  boolean tryMove(Direction moveDirection);

  boolean hasMove();

}
