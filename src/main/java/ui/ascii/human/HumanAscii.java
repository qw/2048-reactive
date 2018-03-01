package ui.ascii.human;

import exceptions.InvalidDirectionException;
import game.Direction;
import game.Game;
import java.util.Scanner;
import ui.View;
import static java.lang.System.out;

public class HumanAscii implements View {

  private Game game;

  public HumanAscii(Game game) {
    this.game = game;
  }

  @Override
  public void display() {
    boolean hasQuit = false;
    do {
      out.printf("\n");
      out.println("Welcome to 2048\n");
      out.println("(p)lay, or (q)uit:");
      Scanner in = new Scanner(System.in);
      String string = in.nextLine();
      if (string.equals("p") || string.equals("q")) {
        switch (string) {
        case "p":
          int boardSize = selectSize();
          play(boardSize);
          break;
        case "q":
          hasQuit = true;
          out.println("Quitting...\n");
          break;
        }
      } else {
        out.println("Please enter p, or q\n");
      }
    } while (!hasQuit);

    System.exit(0);
  }

  private void play(int boardSize) {
    game.newGame(boardSize);
    game.observeBoard().subscribe(this::printBoard);
    do {
      out.println("Enter your move (w,a,s,d) or (q)uit:");
      Scanner in = new Scanner(System.in);
      String input = in.nextLine();
      if (input.equals("q")) {
        game.endGame();
        break;
      }

      Direction moveDirection;
      try {
        moveDirection = Direction.getDirection(input);
      } catch (InvalidDirectionException e) {
        out.println(e.getMessage() + "\n");
        continue;
      }

      game.tryMove(moveDirection);

      if (!game.hasMove()) {
        out.println("Game over!\n");
        break;
      }
    } while (true);
  }

  private int selectSize() {
    while (true) {
      out.println("Select board size (3 - 9) or (q)uit:");
      Scanner in = new Scanner(System.in);
      String string = in.nextLine();
      if (string.equals("q"))
        break;

      int size;
      try {
        size = Integer.parseInt(string);
      } catch (NumberFormatException e) {
        out.println("Invalid board size, please try again\n");
        continue;
      }
      if (size < 3 || size > 9) {
        out.println("Invalid board size, please try again\n");
      } else {
        return size;
      }
    }

    return 3;
  }

  private void printBoard(int[][] board) {
    final int[] score = new int[1];
    game.observeScore().subscribe((x)->{
      score[0] = x;
    });
    out.println("Score: " + score[0]);

    for (int[] row : board) {
      for (int tile : row) {
        out.printf("%4d", tile);
      }
      out.printf("\n");
    }
  }
}
