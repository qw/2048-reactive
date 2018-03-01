package utils;

public class MatrixUtils {

  public static void rotateClockwise(int[][] matrix) {
    int temp;
    final int len = matrix.length;
    // For each concentric square around the middle of the matrix to rotate...
    // This value will be used as (m, n) offset when moving in.
    // Integer division by 2 will skip center if odd length.
    for (int s = 0; s < len / 2; s++)
      // for the length of this ring
      for (int i = 0; i < len - 2 * s - 1; i++) {
        temp = matrix[s][s + i];
        matrix[s][s + i] = matrix[len - s - i - 1][s];
        matrix[len - s - i - 1][s] = matrix[len - s - 1][len - s - i - 1];
        matrix[len - s - 1][len - s - i - 1] = matrix[s + i][len - s - 1];
        matrix[s + i][len - s - 1] = temp;
      }
  }

  public static void rotate90(int[][] matrix, int rotations) {
    if (rotations <= 0) {
      return;
    }

    String[] temp = { "", "", "", ""};
    for (int i = 0; i < rotations; i++) {
      rotateClockwise(matrix);
      for (int row = 0; row < matrix.length; row ++) {
        for (int col = 0; col < 4; col++) {
          temp[row] += String.format("%4d", matrix[row][col]);
        }
        temp[row] += " | ";
      }
    }

    System.out.println("---");
    for (String s : temp) {
      System.out.printf(s);
      System.out.println();
    }
    System.out.println("---");

  }

}
