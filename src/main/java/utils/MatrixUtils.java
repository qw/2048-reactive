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

    int rotateCounts = 0;
    do {
      rotateClockwise(matrix);;
      rotateCounts++;
    } while (rotateCounts < rotations);

  }

}
