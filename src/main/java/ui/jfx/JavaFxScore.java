package ui.jfx;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class JavaFxScore extends Text {

  private static final Font FONT = new Font("Arial",32);

  public JavaFxScore() {
    super("Score: ");
    setFont(FONT);
  }

  public void drawScore(int score) {
    setText("Score: " + score);
  }

}
