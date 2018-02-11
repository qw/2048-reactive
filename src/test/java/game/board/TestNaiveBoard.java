package game.board;

import io.reactivex.observers.TestObserver;
import io.reactivex.subscribers.TestSubscriber;
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

  @Test
  public void TestComplexMove() {
    int[][] preMove = new int[][] {
        { 2, 8, 8, 16, 2 },
        { 16, 16, 1024, 32, 2 },
        { 2, 4, 512, 512, 2 },
        { 0, 0, 0, 0, 2 },
        { 0, 0, 0, 0, 2 }
    };

    int[][] postMove = new int[][] {
        { 0, 2, 16, 16, 2 },
        { 0, 32, 1024, 32, 2 },
        { 0, 2, 4, 1024, 2 },
        { 0, 0, 0, 0, 2 },
        { 0, 0, 0, 0, 2 }
    };

    board.setBoard(preMove);

    board.tryMove(RIGHT);

    TestObserver<int[][]> testObserver = board.observeBoard().test();
    Assert.assertArrayEquals(postMove, testObserver.values().get(testObserver.valueCount() - 1));

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
