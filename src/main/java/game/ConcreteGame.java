package game;

import game.board.Board;
import game.score.ScoreKeeper;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.BehaviorSubject;

public class ConcreteGame implements Game {

  private Board board;

  private ScoreKeeper scoreKeeper;

  private BehaviorSubject<GameState> gameState;

  private CompositeDisposable teardown = new CompositeDisposable();

  private BehaviorSubject<Direction> pastMove;

  public ConcreteGame(Board board, ScoreKeeper scoreKeeper) {
    this.board = board;
    this.scoreKeeper = scoreKeeper;

    gameState = BehaviorSubject.createDefault(GameState.IDLE);
    pastMove = BehaviorSubject.create();
  }

  @Override
  public void newGame(int size) {
    gameState.onNext(GameState.IDLE);

    board.initialise(size);

    scoreKeeper.initialise();
    scoreKeeper.observeNewTile(board.observeNewTile());
  }

  @Override
  public void endGame() {
    gameState.onNext(GameState.GAMEOVER);
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
  public Observable<Direction> observeMoveDirection() {
    return this.pastMove;
  }

  @Override
  public Observable<Integer> observeScore() {
    return scoreKeeper.observeScore();
  }

  @Override
  public boolean tryMove(Direction moveDirection) {
    // Don't do anything if no game is currently going
    if (gameState.getValue() == null || gameState.getValue() != GameState.IDLE)
      return false;

    boolean hasMoved;

    gameState.onNext(GameState.SHIFTING);

    hasMoved = board.tryMove(moveDirection);
    pastMove.onNext(moveDirection);
    if (!board.hasMove()) {
      endGame();
    } else {
      gameState.onNext(GameState.IDLE);
    }

    return hasMoved;
  }

  @Override
  public boolean hasMove() {
    return board.hasMove();
  }
}
