package ui.jfx.components.board;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import ui.jfx.components.tile.TileComponent;

public abstract class BoardComponent extends GridPane {

  protected static final int TILE_SIZE = 64;

  protected static final int TILES_PADDING = 16;

  // Size of the tile arrangements, e.g. 4 means 4 x 4 tiles
  protected int size;

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
  protected void layTiles() {
    for (int row = 0; row < size; row++) {
      for (int column = 0; column < size; column++) {
        int offsetX = offsetCoord(column);
        int offsetY = offsetCoord(row);
        this.add(getTile(offsetX, offsetY, TILE_SIZE), column, row);
      }
    }
  }

  abstract public void repaintTiles(int[][] board);

  abstract protected TileComponent getTile(double offsetX, double offsetY, double size);

  protected static int offsetCoord(int multiplier) {
    // Multiplier * Desired Spacing
    return multiplier * TILE_SIZE;
  }
}
