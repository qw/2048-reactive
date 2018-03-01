package game.board;

import game.Direction;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import java.util.HashSet;
import java.util.Random;
import utils.MatrixUtils;
import static java.lang.System.err;
import static java.lang.System.out;
import static java.lang.System.setOut;

public class NaiveBoard implements Board {

  private static final int EMPTY_TILE = 0;

  private int[][] board;

  private Random random;

  private Subject<Integer> newTile = BehaviorSubject.create();

  private Subject<int[][]> boardSubject = BehaviorSubject.create();

  public NaiveBoard() {
    random = new Random();
  }

  @Override
  public void initialise(int size) {
    // Reset the board before initialising a new one

    board = new int[size][size];
    boardSubject.onNext(board);

    spawnRandomTile();
    spawnRandomTile();

    boardSubject.onNext(board);
  }

  /**
   * Places a single 2 or 4 Tile
   * P(x = 2) = 90%, P(x = 4) = 10%
   *
   * @param
   */
  @Override
  public boolean spawnRandomTile() {
    boolean spawnedTile = false;

    // Count the number of empty tiles to ensure each empty tile has an equal chance of spawning a new tile,
    int totalEmptyTiles = countTile(EMPTY_TILE);
    if (totalEmptyTiles == 0)
      return spawnedTile;

    int probability = 1 + random.nextInt(100);
    int tileValue = probability <= 90 ? 2 : 4;
    // Counting empty tiles on the board from left to right, top to bottom, if kThSelectedTile = 5, then a new tile will be spawned at the 5th empty tile
    int kThSelectedTile = 1 + random.nextInt(totalEmptyTiles);

    int currentEmptyTile = 0;
    for (int y = 0; y < board.length; y++) {
      for (int x = 0; x < board[y].length; x++) {
        if (board[y][x] == EMPTY_TILE) {
          currentEmptyTile++;
          if (kThSelectedTile == currentEmptyTile) {
            board[y][x] = tileValue;
            newTile.onNext(tileValue);
            spawnedTile = true;
            break;
          }
        }
      }

      if (spawnedTile)
        break;
    }

    return spawnedTile;
  }

  @Override
  public boolean tryMove(Direction moveDirection) {
    boolean madeMove = false;

    int rotations = 0;
    int backRotations = 0;
    switch (moveDirection) {
    case UP:
      rotations = 2;
      backRotations = 2;
      break;
    case DOWN:
      rotations = 0;
      backRotations = 0;
      break;
    case LEFT:
      rotations = 3;
      backRotations = 1;
      break;
    case RIGHT:
      rotations = 1;
      backRotations = 3;
      break;
    }

    boolean justMoved = false;
    HashSet<Position> justCombined = new HashSet<>();
    MatrixUtils.rotate90(this.board, rotations);
    for (int x = 0; x < board[0].length; x++) {
      for (int y = board.length - 2; y >= 0; y--) {
        // The tile we're currently trying to shift
        int currentTile = board[y][x];
        if (currentTile == EMPTY_TILE)
          continue;

        // Find out where the tile can shift to
        int destinationY = y;
        int scanningY = y + 1;
        do {
          int scanningTile = board[scanningY][x];
          if (scanningTile == EMPTY_TILE) {
            destinationY = scanningY;
            scanningY++;
            justMoved = true;
          } else if (!justCombined.contains(new Position(x, scanningY)) && scanningTile == currentTile) {
            destinationY = scanningY;
            justMoved = true;
            break;
          } else {
            break;
          }
        } while (scanningY < board.length);

        // Teleport the tile to its new (shifted) position
        if (justMoved) {
          madeMove = true;
          // Reset the hasMoved flag for current tile
          justMoved = false;
          if (board[destinationY][x] == EMPTY_TILE) {
            board[destinationY][x] = currentTile;
          } else {
            int newTileValue = board[destinationY][x] + board[y][x];
            board[destinationY][x] = newTileValue;
            newTile.onNext(newTileValue);
            justCombined.add(new Position(x, destinationY));
          }
          board[y][x] = EMPTY_TILE;
        }

      }
    }

    // Rotate the board back after it's done
    MatrixUtils.rotate90(board, backRotations);

    if (madeMove)
      spawnRandomTile();

    out.println("#####");
    System.out.println("Move: " + moveDirection);
    System.out.println("Rotations: " + rotations + " BackRs: " + backRotations);
    printBoard(board, new Position(0,0));
    out.println("#####");

    boardSubject.onNext(board);

    return madeMove;

  }

  @Override
  public boolean hasEmptyTile() {
    return this.countTile(EMPTY_TILE) != 0;
  }

  @Override
  public Observable<Integer> observeNewTile() {
    return this.newTile;
  }

  @Override
  public boolean hasMove() {
//    int[][] board = this.board.clone();
    for (int i = 0; i < 4; i++) {
      for (int x = 0; x < board[0].length; x++) {
        for (int y = 0; y < board.length - 1; y++) {
          if (board[y + 1][x] == EMPTY_TILE) {
            return true;
          } else if (board[y + 1][x] == board[y][x]) {
            return true;
          }

        }
      }

      MatrixUtils.rotate90(board, 1);
    }

    return this.hasEmptyTile();
  }

  @Override
  public Observable<int[][]> observeBoard() {
    return boardSubject;
  }

  @Override
  public int getSize() {
    return this.board.length;
  }

  @Override
  public void setBoard(int[][] newBoard) {
    this.board = newBoard;
  }

  private int countTile(int tileType) {
    int tileCount = 0;
    for (int[] row : board) {
      for (int tile : row) {
        if (tile == tileType)
          tileCount++;
      }
    }

    return tileCount;
  }

  // Debugging purposes only
  private void printBoard(int[][] board, Position p) {
    for (int row = 0; row < board.length; row++) {
      for (int column = 0; column < board[row].length; column++) {
        if (p.x() == column && p.y() == row) {
          out.printf("%4s", "x" + board[row][column]);
        } else {
          out.printf("%4d", board[row][column]);
        }
      }
      out.printf("\n");
    }

    out.println("\n");
  }

}

class Position {

  private int x;

  private int y;

  public Position(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int x() {
    return this.x;
  }

  public void x(int x) {
    this.x = x;
  }

  public int y() {
    return this.y;
  }

  public void y(int y) {
    this.y = y;
  }

  public boolean equals(Object o) {
    if (!(o instanceof Position))
      return false;

    Position p = (Position) o;
    return this.x == p.x && this.y == p.y;
  }

  public int hashCode() {
    int hashcode = 1;
    hashcode = (hashcode * 397) ^ this.x;
    hashcode = (hashcode * 397) ^ this.y;
    return hashcode;
  }

}
