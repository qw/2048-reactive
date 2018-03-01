package ui.jfx;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

public class Tile extends StackPane {

  private Text text;

  private Rectangle rectangle;

  private static final Font FONT = new Font("Arial",48);

  private static final int ARC = 14;

  public Tile (double x, double y, double size) {
    rectangle = new Rectangle(size, size);
    rectangle.setX(x);
    rectangle.setY(y);
    rectangle.setArcHeight(ARC);
    rectangle.setArcWidth(ARC);

    text = new Text("");
    text.setFont(FONT);
    text.setBoundsType(TextBoundsType.VISUAL);

    this.getChildren().addAll(rectangle, text);
    paintNewValue(0);
  }

  public void paintNewValue(int value) {
    rectangle.setFill(getColor(value));
    text.setText(value == 0 ? "" : String.valueOf(value));
    centerText();
  }

  /**
   * Relies on the fact that the rectangle is square
   */
  private void centerText() {
    double r = rectangle.getHeight() / 2;
    double w = text.getBoundsInLocal().getWidth();
    double h = text.getBoundsInLocal().getHeight();
    text.relocate(r - w / 2, r - h / 2);
  }

  private static Color getColor(int value) {
    switch (value) {
    case 2:    return Color.rgb(238,228,218);
    case 4:    return Color.rgb(237,224,200);
    case 8:    return Color.rgb(242,177,121);
    case 16:   return Color.rgb(245,149,99);
    case 32:   return Color.rgb(246,124,95);
    case 64:   return Color.rgb(246,94,59);
    case 128:  return Color.rgb(237,207,114);
    case 256:  return Color.rgb(237,204,97);
    case 512:  return Color.rgb(237,200,80);
    case 1024: return Color.rgb(237,197,63);
    case 2048: return Color.rgb(237,194,46);
    }
    return Color.rgb(205,193,180);
  }

}
