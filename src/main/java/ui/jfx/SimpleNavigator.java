package ui.jfx;

import java.util.Deque;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import ui.jfx.navigation.Navigator;

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
    if (size > 1) {
      root.getChildren().remove(size - 1);
      root.getChildren().get(size - 2).requestFocus();
    }
  }

}
