package game.score;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public class ConcreteScoreKeeper implements ScoreKeeper {

  private Subject<Integer> score = BehaviorSubject.createDefault(0);

  @Override
  public void initialise() {
    score.onNext(0);
  }

  @Override
  public void tileAdded(int tileValue) {
    score.take(1).map((x)->x + tileValue).subscribe(score::onNext);
  }

  @Override
  public Observable<Integer> observeScore() {
    return score;
  }

  @Override
  public void observeNewTile(Observable<Integer> newTile) {
    newTile.subscribe(this::tileAdded);
  }

}
