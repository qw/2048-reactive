package ui.jfx.human;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import ui.jfx.TileComponent;

/**
 * Draws the board's tiles, does not assume a set board size
 */
public class BoardComponent extends GridPane {

  // Size of the tile arrangements, e.g. 4 means 4 x 4 tiles
  private int size;

  private static final int TILE_SIZE = 64;

  private static final int TILES_PADDING = 16;

  public BoardComponent(int size) {
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
        this.add(new TileComponent(offsetX, offsetY, TILE_SIZE), column, row);
      }
    }
  }

  private static int offsetCoord(int multiplier) {
    // Multiplier * Desired Spacing
    return multiplier * TILE_SIZE;
  }

  public void redrawTiles(int[][] board) {
    if (board != null) {
      for (Node child : this.getChildren()) {
        TileComponent t = (TileComponent) child;
        if (t == null) return;

        int row = GridPane.getRowIndex(t);
        int col = GridPane.getColumnIndex(t);
        t.paintNewValue(board[row][col]);
      }
    }
  }
}
