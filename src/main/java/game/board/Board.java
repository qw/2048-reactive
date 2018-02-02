package game.board;

import game.Direction;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Represents a board of the game.
 * The board has no game logic (e.g. spawning two tiles at the start of a game is game logic),
 * it only has board mechanics such as shifting tiles and spawning tiles.
 */
public interface Board extends Disposable {

  /**
   * Make a new square board of specified size
   *
   * @param size
   */
  void initialise(int size);

  /**
   * Spawn a tile of value 2 or 4 onto the board
   *
   * @return whether the tile was successfully spawned
   */
  boolean spawnRandomTile();

  /**
   * Try and shift the tiles in the given direction
   *
   * @param moveDirection
   * @return whether at least one non-zero tile(s) has shifted
   */
  boolean tryMove(Direction moveDirection);

  /**
   * @return whether the board can be shifted, this means either there are tiles present able to be
   * combined, or there are zero value tiles present.
   */
  boolean hasMove();

  /**
   * @return whether the board has at least one zero-valued tile
   */
  boolean hasEmptyTile();

  /**
   * Observes the value of any new tiles that was formed
   *
   * @return
   */
  Observable<Integer> observeNewTile();

  Observable<int[][]> observeBoard();

  int getSize();

  // TESTING PURPOSES ONLY, DO NOT USE THIS
  void setBoard(int[][] newBoard);
}
