package ui.jfx.navigation;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class SimpleNavigator extends Navigator {

  public SimpleNavigator(StackPane stackPane) {
    super(stackPane);
  }

  @Override
  public void next(Pane newPane) {
    root.getChildren().add(newPane);
    newPane.requestFocus();
  }

  @Override
  public void prev() {
    int size = root.getChildren().size();
    root.getChildren().remove(size - 1);
    if (size > 1) {
      root.getChildren().get(size - 2).requestFocus();
    } else {
      System.exit(0);
    }
  }

}
