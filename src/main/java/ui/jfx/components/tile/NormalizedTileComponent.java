package ui.jfx.components.tile;

import javafx.scene.paint.Color;

public class NormalizedTileComponent extends TileComponent {

  private static final Color OPAQUE_COLOR = Color.rgb(0, 0, 0);

  public NormalizedTileComponent(double x, double y, double size) {
    super(x, y, size);
  }

  @Override
  public void repaint(double value) {
    rectangle.setFill(OPAQUE_COLOR);
    rectangle.setOpacity(value);
  }

  @Override
  protected Color getColor(int value) {
    return null;
  }
}
