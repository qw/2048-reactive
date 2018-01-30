package game.score;

import io.reactivex.Observable;

public interface ScoreKeeper {

  void initialise();

  void tileAdded(int tileValue);

  Observable<Integer> observeScore();

  void observeNewTile(Observable<Integer> newTile);

}
