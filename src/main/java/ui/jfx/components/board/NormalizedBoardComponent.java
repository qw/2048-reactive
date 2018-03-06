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

public class NormalizedBoardComponent extends BoardComponent {

  private static final int TILE_SIZE = 64;

  private static final int TILES_PADDING = 16;

  // Size of the tile arrangements, e.g. 4 means 4 x 4 tiles
  private int size;

  public NormalizedBoardComponent(int size) {
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
        this.add(new NormalizedTileComponent(offsetX, offsetY, TILE_SIZE), column, row);
      }
    }
  }

  public void repaintTiles(int[][] board) {
    if (board != null) {
      // Find max value of board to normalize tile coloring
      int max = 0;
      for (int[] rows : board) {
        for (int tile : rows) {
          if (tile > max) max = tile;
        }
      }

      for (Node child : this.getChildren()) {
        NormalizedTileComponent t = (NormalizedTileComponent) child;
        if (t == null) return;

        int row = GridPane.getRowIndex(t);
        int col = GridPane.getColumnIndex(t);
        t.repaint((double) board[row][col] / (double) max);
      }
    }
  }

  private static int offsetCoord(int multiplier) {
    // Multiplier * Desired Spacing
    return multiplier * TILE_SIZE;
  }
}

class NormalizedTileComponent extends StackPane {

  private static final Font FONT = new Font("Arial", 48);

  private static final Color OPAQUE_COLOR = Color.rgb(0, 0, 0);

  private Rectangle rectangle;

  NormalizedTileComponent(double x, double y, double size) {
    rectangle = new Rectangle(size, size);
    rectangle.setX(x);
    rectangle.setY(y);

    this.getChildren().addAll(rectangle);
    repaint(1);
  }

  public void repaint(double relativeValue) {
    rectangle.setFill(OPAQUE_COLOR);
    rectangle.setOpacity(relativeValue);
  }

  private static Color getColor(int value) {
    switch (value) {
    case 2:
      return Color.valueOf("#FFFFFF");
    case 4:
      return Color.valueOf("#FDFDFD");
    case 8:
      return Color.valueOf("#FCFCFC");
    case 16:
      return Color.valueOf("#FBFBFB");
    case 32:
      return Color.valueOf("#FAFAFA");
    case 64:
      return Color.valueOf("#DFDFDF");
    case 128:
      return Color.valueOf("#DDDDDD");
    case 256:
      return Color.valueOf("#DCDCDC");
    case 512:
      return Color.valueOf("#DBDBDB");
    case 1024:
      return Color.valueOf("#DADADA");
    case 2048:
      return Color.valueOf("#CFCFCF");
    }
    return Color.valueOf("#000000");
  }

}
