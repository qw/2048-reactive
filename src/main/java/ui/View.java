package ui;

import game.Game;

public abstract class View {

  protected Game game;

  public View(Game game) {
    this.game = game;
  }

  ;

  public abstract void display();

}
