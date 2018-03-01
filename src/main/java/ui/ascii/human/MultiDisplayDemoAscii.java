package ui.ascii.human;

import game.Game;
import ui.View;
import static java.lang.System.out;

public class MultiDisplayDemoAscii implements View {

  private Game game;

  int count;

  public MultiDisplayDemoAscii(Game game) {
    this.game = game;
  }

  @Override
  public void display() {
    game.observeBoard().subscribe(this::printBoard);
  }

  private void printBoard(int[][] board) {
    System.out.println();
    game.observeScore().take(1).subscribe((score) -> out.println("Score: " + score));
    game.observeMoveDirection().take(1).subscribe((direction -> out.println("Move: " + direction.toString())));

    for (int[] col : board) {
      for (int tile : col) {
        out.printf("%4d", tile);
      }
      out.printf("\n");
    }
  }
}
