package ui.jfx.screens.game;

import game.Direction;
import game.GameState;
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import ui.jfx.components.ScoreComponent;
import ui.jfx.components.board.BoardComponent;
import ui.jfx.navigation.Navigator;
import static game.GameState.GAMEOVER;

public class GameScreen extends BorderPane {

  private static final Color BG_COLOR = Color.rgb(187, 173, 160);

  private static final int PADDING = 16;

  private GameViewModel game;

  private BoardComponent board;

  private ScoreComponent score;

  private Navigator navigator;

  public GameScreen(GameViewModel game, Navigator navigator, BoardComponent board) {
    this.game = game;
    this.navigator = navigator;
    this.board = board;
    board.autosize();
    score = new ScoreComponent();

    this.setCenter(board);
    this.setBottom(score);
    this.setPadding(new Insets(PADDING));
    this.setBackground(new Background(new BackgroundFill(BG_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
    this.setFocusTraversable(true);

    game.observeState().subscribe(this::updateView);
    game.observeBoard().subscribe(board::repaintTiles);
    game.observeScore().subscribe(score::drawScore);
    this.setOnKeyPressed(this::controls);
  }

  private void controls(KeyEvent keyEvent) {
    game.observeState().take(1).subscribe((state)->{
      KeyCode keyPressed = keyEvent.getCode();
      if (state == GameState.IDLE) {

        if (keyPressed == KeyCode.UP) { // Check which key was pressed
          // The key pressed was the UP arrow key, move the tiles accordingly
          moveTiles(Direction.UP);
          return; // We've found which key was pressed, we can stop checking for any other keys
        }

        // Start here
        if (keyPressed == KeyCode.LEFT) {
          // Complete the code for the LEFT arrow key using the UP code as a guide
        }

        // Complete the same for the other directions

        // Your code ends here
        switch (keyPressed) {
        case R:
          game.restartGame(game.getSize());
          break;
        case Q:
          navigator.prev();
          break;
        }
      } else if (state == GAMEOVER) {
        switch (keyPressed) {
        case Y:
        case R:
          game.restartGame(game.getSize());
          break;
        case N:
          game.endGame();
          navigator.prev();
          break;
        }
      }

    });
  }

  private void moveTiles(Direction direction) {
    game.tryMove(direction);
  }

  private void updateView(GameState gameState) {
    switch (gameState) {
    case IDLE:
      break;
    case SHIFTING:
      break;
    case GAMEOVER:
      break;
    case MENU:
      break;
    }
  }

}
