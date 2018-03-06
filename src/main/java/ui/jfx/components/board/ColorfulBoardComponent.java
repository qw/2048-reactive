package ui.jfx.components.board;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

/**
 * Draws the board's tiles, does not assume a set board size
 */
public class ColorfulBoardComponent extends BoardComponent {

  private static final int TILE_SIZE = 64;

  private static final int TILES_PADDING = 16;

  // Size of the tile arrangements, e.g. 4 means 4 x 4 tiles
  private int size;

  public ColorfulBoardComponent(int size) {
    super();
    this.size = size;
    setHgap(TILES_PADDING);
    setVgap(TILES_PADDING);
    setPadding(new Insets(TILES_PADDING));
    layTiles();
  }

  /**
   * Draws Tiles in a square arrangement
   */
  private void layTiles() {
    for (int row = 0; row < size; row++) {
      for (int column = 0; column < size; column++) {
        int offsetX = offsetCoord(column);
        int offsetY = offsetCoord(row);
        this.add(new ColorfulTileComponent(offsetX, offsetY, TILE_SIZE), column, row);
      }
    }
  }

  public void repaintTiles(int[][] board) {
    if (board != null) {
      for (Node child : this.getChildren()) {
        ColorfulTileComponent t = (ColorfulTileComponent) child;
        if (t == null) return;

        int row = GridPane.getRowIndex(t);
        int col = GridPane.getColumnIndex(t);
        t.repaint(board[row][col]);
      }
    }
  }

  private static int offsetCoord(int multiplier) {
    // Multiplier * Desired Spacing
    return multiplier * TILE_SIZE;
  }
}

class ColorfulTileComponent extends StackPane {

  private static final String FONT = "Arial";

  private static final int ARC = 14;

  private Text text;

  private Rectangle rectangle;

  public ColorfulTileComponent(double x, double y, double size) {
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

  public void repaint(int value) {
    rectangle.setFill(getColor(value));
    text.setText(value == 0 ? "" : String.valueOf(value));
    text.setFont(new Font(FONT, getFontSize(value)));
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

  private int getFontSize(int value) {
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

  private static Color getColor(int value) {
    switch (value) {
    case 2:
      return Color.rgb(238, 228, 218);
    case 4:
      return Color.rgb(237, 224, 200);
    case 8:
      return Color.rgb(242, 177, 121);
    case 16:
      return Color.rgb(245, 149, 99);
    case 32:
      return Color.rgb(246, 124, 95);
    case 64:
      return Color.rgb(246, 94, 59);
    case 128:
      return Color.rgb(237, 207, 114);
    case 256:
      return Color.rgb(237, 204, 97);
    case 512:
      return Color.rgb(237, 200, 80);
    case 1024:
      return Color.rgb(237, 197, 63);
    case 2048:
      return Color.rgb(237, 194, 46);
    }
    return Color.rgb(205, 193, 180);
  }

}
