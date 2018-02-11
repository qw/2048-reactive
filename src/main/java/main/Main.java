package main;

import com.google.gson.Gson;
import game.ConcreteGame;
import game.board.NaiveBoard;
import game.score.ConcreteScoreKeeper;
import java.util.Scanner;
import ui.View;
import ui.ascii.human.HumanAscii;
import ui.ascii.machine.MachineAscii;
import static java.lang.System.out;

public class Main {

  private static String[] interfaces = { "machine", "human ASCII" };

  public static void main(String[] args) {
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
    if (i == 1) {
      ui = new HumanAscii(new ConcreteGame(new NaiveBoard(), new ConcreteScoreKeeper()));
    } else if (i == 2) {
      ui = new MachineAscii(new ConcreteGame(new NaiveBoard(), new ConcreteScoreKeeper()), new Gson());
    } else {
      ui = new MachineAscii(new ConcreteGame(new NaiveBoard(), new ConcreteScoreKeeper()), new Gson());
    }
    ui.display();
  }

}
