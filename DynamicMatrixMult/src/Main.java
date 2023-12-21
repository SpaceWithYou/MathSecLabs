import java.util.ArrayList;

public class Main {
    /**Минимальная размерность ответа**/
    private static int MatrixMultOrder(int[] X) {
        ArrayList<Integer> order = new ArrayList<>();
        int n = X.length - 1;
        int[][] minCost = new int[n][n];
        for (int l = 1; l < n; l++) {
            for (int i = 0; i < n - l; i++) {
                int j = i + l;
                minCost[i][j] = Integer.MAX_VALUE;
                for (int k = i; k < j; k++) {
                    minCost[i][j] = Math.min(minCost[i][j], minCost[i][k] + minCost[k + 1][j] + X[i] * X[k + 1] * X[j + 1]);
                }
            }
        }
        return minCost[0][n - 1];
    }

    public static void main(String[] args) {
        int[] X = {10, 100, 5, 50};
        System.out.println(MatrixMultOrder(X));
    }
}