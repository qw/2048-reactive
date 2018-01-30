package game;

import game.board.Board;
import game.score.ScoreKeeper;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public class ConcreteGame implements Game {

  private Board board;

  private ScoreKeeper scoreKeeper;

  private Subject<GameState> gameState = BehaviorSubject.createDefault(GameState.MENU);

  public ConcreteGame(Board board, ScoreKeeper scoreKeeper) {
    this.board = board;
    this.scoreKeeper = scoreKeeper;
  }

  @Override
  public void initialise(int size) {
    gameState.onNext(GameState.IDLE);

    board.initialise(size);
    board.spawnRandomTile();
    board.spawnRandomTile();

    scoreKeeper.initialise();
    scoreKeeper.observeNewTile(board.observeNewTile());
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
    gameState.onNext(GameState.SHIFTING);

    boolean hasMoved = board.tryMove(moveDirection);
    if (!board.hasMove()) {
      gameState.onNext(GameState.GAMEOVER);
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
}
