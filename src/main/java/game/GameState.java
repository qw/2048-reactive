package game;

public enum GameState {
  MENU("menu"), IDLE("idle"), SHIFTING("shifting"), GAMEOVER("gameover");

  String text;

  GameState(String text) {
    this.text = text;
  }

  public String text() {
    return this.text;
  }
}
  