package ui.network;

import com.google.gson.Gson;
import game.Direction;
import game.Game;
import game.GameState;
import io.reactivex.subjects.BehaviorSubject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import javafx.scene.input.KeyCode;
import ui.View;

public class TcpServer implements View {

  private Game game;

  private ServerSocket socket;

  private BehaviorSubject<Integer> score;

  private BehaviorSubject<GameState> gameStateSubject;

  private BehaviorSubject<int[][]> board;

  private final Gson gson;

  private static final String HOST_NAME = "localhost";

  private static final int PORT = 2048;

  public TcpServer(Game game, Gson gson) {
    this.game = game;
    this.gson = gson;

    board = BehaviorSubject.create();
    game.observeBoard().subscribe(board);

    gameStateSubject = BehaviorSubject.createDefault(GameState.MENU);
    game.observeState().subscribe(gameStateSubject);

    score = BehaviorSubject.createDefault(0);
    game.observeScore().subscribe(score);
  }

  @Override
  public void display() {
    try (ServerSocket socket = new ServerSocket(PORT);
         Socket clientSocket = socket.accept();
         Writer out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "UTF8"));
         BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    ) {

      String s;
      while ((s = inFromClient.readLine()) != null) {
        Integer gameSize = 0;
        try {
          gameSize = Integer.parseInt(s);
          if (gameSize >= 3 && gameSize <= 9) {
            game.endGame();
            game.newGame(gameSize);
          }
        } catch (NumberFormatException nFE) {
          // do nothing
        }

        KeyCode keyCode = KeyCode.getKeyCode(s);
        GameState gameState = gameStateSubject.getValue();
          if (gameState != GameState.MENU) {
            switch (gameState) {
            case IDLE:
              makeMove(keyCode);
              break;
            }
          }

          GameDTO gameDTO = new GameDTO();
          gameDTO.gameState = gameState;
          gameDTO.board = board.getValue();

          // BufferedWriter only writes when it is flushed (auto when full or manual)
          out.append(gson.toJson(gameDTO)).append("\r\n");
          out.flush();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  private void makeMove(KeyCode keyCode) {
    if (keyCode == null) return;
    switch (keyCode) {
    case W:
    case UP:
      game.tryMove(Direction.UP);
      break;
    case S:
    case DOWN:
      game.tryMove(Direction.DOWN);
      break;
    case A:
    case LEFT:
      game.tryMove(Direction.LEFT);
      break;
    case D:
    case RIGHT:
      game.tryMove(Direction.RIGHT);
      break;
    }
  }
}
