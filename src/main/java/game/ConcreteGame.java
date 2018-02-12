package game;

import game.board.Board;
import game.score.ScoreKeeper;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public class ConcreteGame implements Game {

  private Board board;

  private ScoreKeeper scoreKeeper;

  private Subject<GameState> gameState;

  private CompositeDisposable teardown = new CompositeDisposable();

  public ConcreteGame(Board board, ScoreKeeper scoreKeeper) {
    this.board = board;
    this.scoreKeeper = scoreKeeper;

    gameState = BehaviorSubject.createDefault(GameState.MENU);

    teardown.add(board);
    teardown.add(Disposables.fromAction(gameState::onComplete));
  }

  @Override
  public void newGame(int size) {
    gameState.onNext(GameState.IDLE);

    board.initialise(size);

    scoreKeeper.initialise();
    scoreKeeper.observeNewTile(board.observeNewTile());

    board.spawnRandomTile();
    board.spawnRandomTile();
  }

  @Override
  public void endGame() {
    gameState.onNext(GameState.GAMEOVER);
  }

  @Override
  public void menu() {
    gameState.onNext(GameState.MENU);
  }

  @Override
  public Observable<int[][]> observeBoard() {
    return board.observeBoard();
  }

  @Override
  public Observable<GameState> observeState() {
    return this.gameState;
  }

  @Override
  public Observable<Integer> observeScore() {
    return scoreKeeper.observeScore();
  }

  @Override
  public boolean tryMove(Direction moveDirection) {
    final boolean hasMoved;

    gameState.onNext(GameState.SHIFTING);

    hasMoved = board.tryMove(moveDirection);
    if (!board.hasMove()) {
      endGame();
    } else {
      board.spawnRandomTile();
      gameState.onNext(GameState.IDLE);
    }

    return hasMoved;
  }

  @Override
  public boolean hasMove() {
    return board.hasMove();
  }

  @Override
  public void dispose() {
    teardown.dispose();
  }

  @Override
  public boolean isDisposed() {
    return teardown.isDisposed();
  }
}
