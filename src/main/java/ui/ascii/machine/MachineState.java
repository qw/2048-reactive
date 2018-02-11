package ui.ascii.machine;

/**
 * As the machine could be entering wrong values trying to start a game,
 * we need such state to indicate
 */
public enum MachineState {

  VIEW_SELECTION("view-selection"), MAIN_MENU("main-menu"), SIZE_SELECTION("size-selection"), IDLE("idle"), GAMEOVER("gameover"), SHIFTING("shifting"), BAD_INPUT("bad-input");

  private String text;

  private MachineState(String text) {
    this.text = text;
  }

  public String text() {
    return this.text;
  }
}
