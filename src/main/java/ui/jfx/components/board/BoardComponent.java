package ui.jfx.components.board;

import javafx.scene.layout.GridPane;

public abstract class BoardComponent extends GridPane {

  abstract public void repaintTiles(int[][] board);

}
