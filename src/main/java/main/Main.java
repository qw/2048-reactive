package main;

import com.google.gson.Gson;
import dependency.Provider;
import game.ConcreteGame;
import game.Game;
import game.board.NaiveBoard;
import game.score.ConcreteScoreKeeper;
import java.util.Scanner;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;
import ui.View;
import ui.ascii.human.HumanAscii;
import ui.ascii.human.MultiDisplayDemoAscii;
import ui.ascii.machine.MachineAscii;
import ui.jfx.JavaFxView;
import ui.jfx.Tile;
import static java.lang.System.out;

public class Main{

  private static String[] interfaces = { "machine", "human ASCII", "human GUI" };

  public static void main(String[] args) {
    Application.launch(JavaFxView.class);
  }

  private static void interfaceSelection() {
    while (true) {
      out.println("Select Interface, or (q)uit:");
      listInterfaces();
      Scanner in = new Scanner(System.in);
      String s = in.nextLine();
      if (s.equals("q'")) {
        System.exit(0);
      }

      int selection;
      try {
        selection = Integer.parseInt(s);
      } catch (NumberFormatException e) {
        out.println("Invalid selection, please try again");
        continue;
      }

      if (selection >= 0 && selection < interfaces.length) {
        initialize(selection);
      } else {
        out.println("Invalid selection, please try again");
        continue;
      }
    }
  }

  private static void listInterfaces() {
    for (int i = 0; i < interfaces.length; i++) {
      out.println(i + 0 + ". " + interfaces[i]);
    }
  }

  // TODO Add dagger dependency injection
  private static void initialize(int i) {
    View ui;
    Game game = Provider.getInstance().getGame();
    if (i == 0) {
      ui = new MachineAscii(game, new Gson());
    } else if (i == 1) {
      ui = new HumanAscii(game);
    } else if (i == 2){
      return;
    } else {
      ui = new MultiDisplayDemoAscii(game);
    }
    ui.display();

  }

}
