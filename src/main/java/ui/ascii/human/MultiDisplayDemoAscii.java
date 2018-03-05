package ui.ascii.human;

import game.Game;
import ui.View;
import static java.lang.System.out;

public class MultiDisplayDemoAscii implements View {

  int count;

  private Game game;

  public MultiDisplayDemoAscii(Game game) {
    this.game = game;
  }

  @Override
  public void display() {
    game.observeBoard().subscribe(this::printBoard);
  }

  private void printBoard(int[][] board) {
    System.out.println();

    for (int[] col : board) {
      for (int tile : col) {
        out.printf("%4d", tile);
      }
      out.printf("\n");
    }
    game.observeScore().take(1).subscribe((score)->out.println("Score: " + score));
  }
}
