package ui.jfx.components;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ScoreComponent extends Text {

  private static final Font FONT = new Font("Arial", 32);

  public ScoreComponent() {
    super("Score: ");
    setFont(FONT);
  }

  public void drawScore(int score) {
    setText("Score: " + score);
  }

}
