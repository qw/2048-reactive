package ui.jfx.screens.menu;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import ui.jfx.navigation.Navigator;

public class MenuScreen extends Pane {

  private TextField gameSize;

  private Navigator navigator;

  private static final int DEFAULT_GAME_SIZE = 4;

  private static final int MAX_GAME_SIZE = 9;

  private static final int MIN_GAME_SIZE = 3;

  public MenuScreen(Navigator navigator) {
    this.navigator = navigator;

    gameSize = new TextField(String.valueOf(DEFAULT_GAME_SIZE));
    gameSize.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches("\\d*") || newValue.equals("") || Integer.parseInt(newValue) < MIN_GAME_SIZE || Integer.parseInt(newValue) > MAX_GAME_SIZE) {
        gameSize.setText(oldValue);
      }
    });

    this.getChildren().add(gameSize);
    addEventHandler(KeyEvent.KEY_PRESSED, this::controls);
  }

  public int getGameSize() {
    return Integer.parseInt(gameSize.getText());
  }

  private void controls(KeyEvent keyEvent) {
//    int size = Integer.parseInt(gameSize.getText());
//    switch (keyEvent.getCode()) {
//    case LEFT:
//      gameSize.setText(String.valueOf(++size));
//      break;
//    case RIGHT:
//      gameSize.setText(String.valueOf(--size));
//      break;
//    }
  }
}
