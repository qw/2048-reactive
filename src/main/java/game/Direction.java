package game;

import exceptions.InvalidDirectionException;

public enum Direction {
  UP, DOWN, LEFT, RIGHT;

  public static Direction getDirection(char input) throws InvalidDirectionException {
    Direction moveDirection = UP;
    switch (input) {
    case 'w':
      moveDirection = UP;
      break;
    case 's':
      moveDirection = DOWN;
      break;
    case 'a':
      moveDirection = LEFT;
      break;
    case 'd':
      moveDirection = RIGHT;
      break;
    default:
      throw new InvalidDirectionException();
    }

    return moveDirection;
  }

  public static Direction getDirection(String s) throws InvalidDirectionException {
    if (s.length() != 1) {
      throw new InvalidDirectionException();
    }

    return getDirection(s.toCharArray()[0]);
  }
}
