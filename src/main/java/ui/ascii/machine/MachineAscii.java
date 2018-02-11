package ui.ascii.machine;

import com.google.gson.Gson;
import exceptions.InvalidDirectionException;
import game.Direction;
import game.Game;
import game.GameState;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import java.util.Scanner;
import ui.View;

public class MachineAscii extends View {

  private final Gson gson;

  private StandardOutModel outModel;

  private Subject<MachineState> machineStateSubject = BehaviorSubject.createDefault(MachineState.MAIN_MENU);

  public MachineAscii(Game game, Gson gson) {
    super(game);
    this.gson = gson;
  }

  @Override
  public void display() {
    boolean hasQuit = false;
    machineStateSubject.subscribe(this::printState);
    do {
      Scanner in = new Scanner(System.in);
      String string = in.nextLine();
      if (string.equals("p") || string.equals("q")) {
        switch (string) {
        case "p":
          machineStateSubject.onNext(MachineState.SIZE_SELECTION);
          int boardSize = selectSize();

          if (boardSize == -1) {
            machineStateSubject.onNext(MachineState.BAD_INPUT);
            continue;
          }

          machineStateSubject.onNext(MachineState.IDLE);
          play(boardSize);
          machineStateSubject.onNext(MachineState.MAIN_MENU);
          break;
        case "q":
          hasQuit = true;
          game.dispose();
          break;
        }
      } else {
        machineStateSubject.onNext(MachineState.BAD_INPUT);
      }

      machineStateSubject.onNext(MachineState.MAIN_MENU);
    } while (!hasQuit);

    System.exit(0);
  }

  private void play(int boardSize) {
    game.newGame(boardSize);
    game.observeState().subscribe((x)->{
      if (x == GameState.IDLE) {
        machineStateSubject.onNext(MachineState.IDLE);
      } else if (x == GameState.SHIFTING) {
        machineStateSubject.onNext(MachineState.SHIFTING);
      } else if (x == GameState.GAMEOVER) {
        machineStateSubject.onNext(MachineState.GAMEOVER);
      }
    });

    do {
      Scanner in = new Scanner(System.in);
      String string = in.nextLine().toLowerCase();
      if (string.equals("q"))
        break;

      Direction moveDirection;
      try {
        moveDirection = Direction.getDirection(string);
      } catch (InvalidDirectionException e) {
        machineStateSubject.onNext(MachineState.BAD_INPUT);
        continue;
      }

      game.tryMove(moveDirection);

    } while (true);
  }

  private void printState(MachineState state) {
    StandardOutModel out = new StandardOutModel();
    if (game.observeBoard() != null) {
      game.observeBoard().subscribe((x)->{
        out.board = x;
      });
    } else {
      out.board = null;
    }

    if (game.observeScore() != null) {
      game.observeScore().subscribe((x)->{
        out.score = x;
      });
    } else {
      out.score = 0;
    }

    out.machineState = state;
    System.out.println(gson.toJson(out));
  }

  private int selectSize() {
    do {
      Scanner in = new Scanner(System.in);
      String string = in.nextLine();
      if (string.equals("q")) {
        return -1;
      }

      int size;
      try {
        size = Integer.parseInt(string);
      } catch (NumberFormatException e) {
        continue;
      }
      if (size >= 3 && size <= 9) {
        return size;
      }
    } while (true);

  }

}
