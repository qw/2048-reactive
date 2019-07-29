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
      KeyCode keyCodeReceivedFromKeyboard = keyEvent.getCode();
      if (state == GameState.IDLE) {
        // TIPS:
        // - things following two slashes // are comments, they are not code.
        // - comments help us understand the code.
        // - double equals == means equals in code instead of a single equals = we use in math.
        // - notice that every time we have an open curly brace {, lines are indented (tab) once to the right,
        //   this is to keep code readable. A close curly brace } takes the indentation back once.
        // - what you see below are what's called 'if statements', they let you do things based on conditions
        //   if (condition is true) { do the actions written in here between the curly braces }
        // - for example, EXAMPLE ONE below says `if (key code received from the keyboard is the UP arrow key) {
        //   move the tiles up } '.
        // - note that brackets are used around the condition (condition), while curly braces are used around what
        //   action to do { action here }
        // EXAMPLE ONE
        if (keyCodeReceivedFromKeyboard == KeyCode.UP) { // Check which key was pressed
          // The key pressed was the UP arrow key, move the tiles accordingly
          moveTiles(Direction.UP); // notice there is a semicolon here
          return; // We've found which key was pressed, we can stop checking for any other keys. This is required.
        }

        // ------- Start here -------
        if (keyCodeReceivedFromKeyboard == KeyCode.LEFT) {
          // Complete the code for the LEFT arrow key using the UP code as a guide
        }

        // Complete the same for the other directions

        // ------ Your code ends here -------
        switch (keyCodeReceivedFromKeyboard) {
        case R:
          game.restartGame(game.getSize());
          break;
        case Q:
          navigator.prev();
          break;
        }
      } else if (state == GAMEOVER) {
        switch (keyCodeReceivedFromKeyboard) {
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
