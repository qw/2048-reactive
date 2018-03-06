package ui.jfx.components.board;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import ui.jfx.components.tile.NormalizedTileComponent;
import ui.jfx.components.tile.TileComponent;

public class NormalizedBoardComponent extends BoardComponent {

  public NormalizedBoardComponent(int size) {
    super(size);
    this.size = size;
    setHgap(TILES_PADDING);
    setVgap(TILES_PADDING);
    setPadding(new Insets(TILES_PADDING));
    layTiles();
  }

  @Override
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
        TileComponent t = (TileComponent) child;
        if (t == null) return;

        int row = GridPane.getRowIndex(t);
        int col = GridPane.getColumnIndex(t);
        t.repaint((double) board[row][col] / (double) max);
      }
    }
  }

  @Override
  protected TileComponent getTile(double offsetX, double offsetY, double size) {
    return new NormalizedTileComponent(offsetX, offsetY, size);
  }
}