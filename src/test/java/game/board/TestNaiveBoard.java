package game.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static game.Direction.DOWN;
import static game.Direction.RIGHT;
import static java.lang.System.out;

public class TestNaiveBoard {

  private NaiveBoard board;

  private static final int TEST_BOARD_SIZE = 4;

  public TestNaiveBoard() {
    board = new NaiveBoard();
  }

  @Before
  public void Setup() {
    board.initialise(TEST_BOARD_SIZE);
  }

  @Test
  public void TestInitialise() {
    board.observeBoard().subscribe((board)->{
      Assert.assertArrayEquals(board, new int[][]{
          { 0, 0, 0, 0 },
          { 0, 0, 0, 0 },
          { 0, 0, 0, 0 },
          { 0, 0, 0, 0 }
      });
    });
  }

  @Test
  public void TestSpawnRandomTile() {
    List<Integer> sums = new ArrayList<>();

    for (int x = 1; x < 1001; x++) {
      board.spawnRandomTile();
      int sum = 0;
      for (int[] row : board.observeBoard().blockingSingle()) {
        for (int i : row) {
          sum += i;
        }
      }

      sums.add(sum);

      board.initialise(TEST_BOARD_SIZE);
    }

    boolean spawnedCorrectly = true;
    for (int sum : sums) {
      if (sum != 2 && sum != 4 && sum != 6) {
        spawnedCorrectly = false;
      }
    }

    Assert.assertTrue(spawnedCorrectly);
  }

  @Test
  public void TestHaveSimpleMove() {
    int[][] testBoard = new int[][]{
        { 2, 2, 2, 2, 2 },
        { 2, 2, 2, 2, 2 },
        { 2, 2, 2, 2, 2 },
        { 2, 2, 2, 2, 2 },
        { 2, 2, 2, 2, 2 }
    };

    board.setBoard(testBoard);

    Assert.assertTrue(board.hasMove());

    testBoard = new int[][]{
        { 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0 },
        { 0, 0, 2, 0, 0 },
        { 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0 }
    };

    board.setBoard(testBoard);

    Assert.assertTrue(board.hasMove());
  }

  @Test
  public void TestTryMoveDown() {
    int[][] preMove = new int[][] {
        { 0, 0, 2, 0, 0 },
        { 0, 0, 2, 0, 0 },
        { 0, 0, 2, 0, 0 },
        { 0, 0, 2, 0, 0 },
        { 0, 0, 0, 0, 0 }
    };

    int[][] postMove = new int[][] {
        { 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0 },
        { 0, 0, 4, 0, 0 },
        { 0, 0, 4, 0, 0 }
    };

    board.setBoard(preMove);
    board.tryMove(DOWN);
    System.out.println(Arrays.deepToString(board.observeBoard().blockingSingle()));

    Assert.assertArrayEquals(postMove, board.observeBoard().blockingSingle());
  }

  @Test
  public void TestTryMoveFull() {
    int[][] preMove = new int[][] {
        { 2, 8, 2, 8, 2 },
        { 2, 8, 2, 8, 2 },
        { 2, 8, 2, 8, 2 },
        { 2, 8, 2, 8, 2 },
        { 2, 8, 2, 8, 2 }
    };

    int[][] postMove = new int[][] {
        { 2, 8, 2, 8, 2 },
        { 2, 8, 2, 8, 2 },
        { 2, 8, 2, 8, 2 },
        { 2, 8, 2, 8, 2 },
        { 2, 8, 2, 8, 2 }
    };

    board.setBoard(preMove);
    for (int i = 0; i < 4; i++) {
      board.tryMove(RIGHT);
    }
    Assert.assertArrayEquals(postMove, board.observeBoard().blockingSingle());

    preMove = new int[][] {
        { 0, 4, 4, 8, 2 },
        { 2, 8, 2, 8, 2 },
        { 2, 8, 2, 8, 2 },
        { 2, 8, 2, 8, 2 },
        { 2, 8, 2, 8, 2 }
    };

    postMove = new int[][] {
        { 0, 0, 8, 8, 2 },
        { 2, 8, 2, 8, 2 },
        { 2, 8, 2, 8, 2 },
        { 2, 8, 2, 8, 2 },
        { 2, 8, 2, 8, 2 }
    };

    board.setBoard(preMove);
    for (int i = 0; i < 1; i++) {
      board.tryMove(RIGHT);
    }
    Assert.assertArrayEquals(postMove, board.observeBoard().blockingSingle());

    preMove = new int[][] {
        { 0, 4, 4, 8, 2 },
        { 2, 8, 2, 8, 2 },
        { 2, 8, 2, 8, 2 },
        { 2, 8, 2, 8, 2 },
        { 2, 8, 2, 8, 2 }
    };

    postMove = new int[][] {
        { 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0 },
        { 0, 4, 4, 8, 2 },
        { 8, 32, 8, 32, 8 }
    };

    board.setBoard(preMove);
    for (int i = 0; i < 10; i++) {
      board.tryMove(DOWN);
    }
    Assert.assertArrayEquals(postMove, board.observeBoard().blockingSingle());
  }

  private void printBoard(int[][] board) {
    for (int[] row : board) {
      for (int tile : row) {
        out.printf("%4d", tile);
      }
      out.printf("\n");
    }

    out.printf("\n");
  }
}
