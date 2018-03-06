package ui.jfx.components.board;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import ui.jfx.components.tile.ColorfulTileComponent;
import ui.jfx.components.tile.TileComponent;

/**
 * Draws the board's tiles, does not assume a set board size
 */
public class ColorfulBoardComponent extends BoardComponent {

  public ColorfulBoardComponent(int size) {
    super(size);
  }

  public void repaintTiles(int[][] board) {
    if (board != null) {
      for (Node child : this.getChildren()) {
        TileComponent t = (TileComponent) child;
        if (t == null) return;

        int row = GridPane.getRowIndex(t);
        int col = GridPane.getColumnIndex(t);
        t.repaint(board[row][col]);
      }
    }
  }

  @Override
  protected TileComponent getTile(double offsetX, double offsetY, double size) {
    return new ColorfulTileComponent(offsetX, offsetY, size);
  }

}