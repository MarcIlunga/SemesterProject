package lasecbachelorprject.epfl.ch.privacypreservinghousing.helpers;

import java.security.SecureRandom;

public class Matrix {
    private final int rows;
    private final int cols;
    private final double [][] data;
    private static SecureRandom secureRandon;

    public Matrix(int rows, int cols){
        this.rows= rows;
        this.cols = cols;
        data = new double[rows][cols];
        secureRandon = new SecureRandom();
    }

    public static Matrix randomMatrix(int rows, int cols){
        Matrix A = new Matrix(rows,cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                A.data[i][j] = secureRandon.nextDouble();
            }
        }
        return A;
    }

}
