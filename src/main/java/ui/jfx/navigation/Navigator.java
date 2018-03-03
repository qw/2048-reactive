package ui.jfx.navigation;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public abstract class Navigator {

  protected StackPane root;

  protected Navigator(StackPane root){
    this.root = root;
  }

  public abstract void next(Pane newPane);

  public abstract void prev();

}
