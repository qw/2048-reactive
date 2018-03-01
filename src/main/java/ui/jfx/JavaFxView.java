package ui.jfx;

import dependency.Provider;
import game.Direction;
import game.Game;
import game.GameState;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import static game.GameState.GAMEOVER;

public class JavaFxView extends Application {

  private Game game;

  private int gameSize;

  private Scene gameScene;

  private BorderPane rootPane;

  private GridPane gridPane;

  private Stage primaryStage;

  private static final int WINDOW_HEIGHT = 400;

  private static final int WINDOW_WIDTH = 400;

  private static final int TILE_SIZE = 64;

  private static final int TILES_PADDING = 16;

  private static final Color BG_COLOR = Color.rgb(187,173,160);

  /**
   * JavaFx requires the default non-args constructor.
   * To start this JavaFX Application from another class, use Application.launch(JavaFxView.class), then call initialize
   */
  public JavaFxView() {
  }

  @Override
  public void start(Stage primaryStage) {
    this.primaryStage = primaryStage;
    initialize();
  }

  private void initialize() {
    this.game = Provider.getInstance().getGame();
    restartGame();

    gridPane = new GridPane();
    gridPane.setHgap(TILES_PADDING);
    gridPane.setVgap(TILES_PADDING);
    gridPane.setPadding(new Insets(TILES_PADDING));
    layTiles();

    rootPane = new BorderPane();
    rootPane.setCenter(gridPane);
    rootPane.setBackground(new Background(new BackgroundFill(BG_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));

    gameScene = new Scene(rootPane, WINDOW_WIDTH, WINDOW_HEIGHT);

    game.observeState().subscribe(this::updateView);
    game.observeBoard().subscribe(this::redrawTiles);
    game.observeScore().subscribe(this::drawScore);
    gameScene.addEventHandler(KeyEvent.KEY_PRESSED, this::controls);

    primaryStage.setTitle("2048");
    primaryStage.setScene(gameScene);
    primaryStage.setResizable(false);
    primaryStage.show();
  }

  private void restartGame() {
    game.newGame(4);
    gameSize = 4;
  }

  /**
   * Draws a square board
   */
  private void layTiles() {
    for (int row = 0; row < gameSize; row++) {
      for (int column = 0; column < gameSize; column++) {
        int offsetX = offsetCoord(column);
        int offsetY = offsetCoord(row);
        gridPane.add(new Tile(offsetX, offsetY, TILE_SIZE), column, row);
      }
    }
  }

  private static int offsetCoord(int arg) {
    return arg * TILE_SIZE;
  }

  private void redrawTiles(int[][] board) {
    if (board != null && gameSize != 0) {
      for (Node child : gridPane.getChildren()) {
        Tile t = (Tile) child;
        if (t == null) return;

        int row = GridPane.getRowIndex(t);
        int col = GridPane.getColumnIndex(t);
        t.paintNewValue(board[row][col]);
      }
    }
  }

  private void drawScore(int score) {

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

  private void controls(KeyEvent keyEvent) {
    game.observeState().take(1).subscribe((state) -> {
      KeyCode keyCode = keyEvent.getCode();
      // Global keys
      switch (keyCode) {
      case ESCAPE:
        quit();
        break;
      }

      if (state == GameState.IDLE) {
        switch (keyCode) {
        case UP:
          game.tryMove(Direction.UP);
          break;
        case DOWN:
          game.tryMove(Direction.DOWN);
          break;
        case LEFT:
          game.tryMove(Direction.LEFT);
          break;
        case RIGHT:
          game.tryMove(Direction.RIGHT);
          break;
        }
      } else if (state == GAMEOVER) {
        switch (keyCode) {
        case Y:
          restartGame();
          break;
        case N:
          quit();
          break;
        }
      }

    });
  }

  private void quit() {
    game.endGame();
    System.exit(0);
  }

}
