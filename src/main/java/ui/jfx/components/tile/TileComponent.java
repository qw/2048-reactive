package ui.jfx.components.tile;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

public abstract class TileComponent extends StackPane {

  protected static final String FONT = "Arial";

  protected static final int ARC = 14;

  protected Text text;

  protected Rectangle rectangle;

  public TileComponent(double x, double y, double size) {
    rectangle = new Rectangle(size, size);
    rectangle.setX(x);
    rectangle.setY(y);
    rectangle.setArcHeight(ARC);
    rectangle.setArcWidth(ARC);

    text = new Text("");
    text.setFont(new Font(FONT, 48));
    text.setBoundsType(TextBoundsType.VISUAL);

    this.getChildren().addAll(rectangle, text);
    repaint(0);
  }

  public abstract void repaint(double value);

  /**
   * Relies on the fact that the rectangle is square
   */
  protected void centerText() {
    double r = rectangle.getHeight() / 2;
    double w = text.getBoundsInLocal().getWidth();
    double h = text.getBoundsInLocal().getHeight();
    text.relocate(r - w / 2, r - h / 2);
  }

  protected int getFontSize(int value) {
    int size;
    if (value < 10) size = 42;
    else if (value < 100) size = 36;
    else if (value < 1000) size = 32;
    else if (value < 10000) size = 24;
    else if (value < 100000) size = 20;
    else if (value < 1000000) size = 16;
    else size = 12;

    return size;
  }

  protected abstract Color getColor(int value);
}
