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
import ui.jfx.ScoreComponent;
import ui.jfx.human.BoardComponent;
import ui.jfx.navigation.Navigator;
import static game.GameState.GAMEOVER;

public class HumanGameScreen extends BorderPane {

  private GameViewModel viewModel;

  private BoardComponent board;

  private ScoreComponent score;

  private Navigator navigator;

  private static final Color BG_COLOR = Color.rgb(187,173,160);

  private static final int PADDING = 16;

  public HumanGameScreen(GameViewModel viewModel, Navigator navigator) {
    this.viewModel = viewModel;
    this.navigator = navigator;

    board = new BoardComponent(viewModel.getSize());

    score = new ScoreComponent();

    this.setCenter(board);
    this.setBottom(score);
    this.setPadding(new Insets(PADDING));
    this.setBackground(new Background(new BackgroundFill(BG_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));

    viewModel.observeState().subscribe(this::updateView);
    viewModel.observeBoard().subscribe(board::redrawTiles);
    viewModel.observeScore().subscribe(score::drawScore);
    this.setOnKeyPressed(this::controls);
  }

  private void controls(KeyEvent keyEvent) {
    viewModel.observeState().take(1).subscribe((state) -> {
      KeyCode keyCode = keyEvent.getCode();
      if (state == GameState.IDLE) {
        switch (keyCode) {
        case UP:
          viewModel.tryMove(Direction.UP);
          break;
        case DOWN:
          viewModel.tryMove(Direction.DOWN);
          break;
        case LEFT:
          viewModel.tryMove(Direction.LEFT);
          break;
        case RIGHT:
          viewModel.tryMove(Direction.RIGHT);
          break;
        }
      } else if (state == GAMEOVER) {
        switch (keyCode) {
        case Y:
          viewModel.restartGame(viewModel.getSize());
          break;
        case N:
          viewModel.endGame();
          navigator.prev();
          break;
        }
      }

    });
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
