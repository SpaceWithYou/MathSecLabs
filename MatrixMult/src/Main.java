import java.util.HashMap;

/**Алгоритм четырёх русских для матриц над Z2 с предподсчитанной таблицей скалярных произведений всех векторов длины 10**/
public class Main {
    public static final int k = 10;
    public static HashMap<String[], Integer> scalarMap = new HashMap<>();                        //String - binary representation of the integer

    /**Бинарное возведение в степень 2**/
    private static int binPower(int n) {
        int a = 2;
        int res = 1;
        while(n > 0) {
            if((n % 2) == 1) {
                res *= a;
            }
            a *= a;
            n /= 2;
        }
        return res;
    }

    /**Скалярное произведение a на b как булевых векторов**/
    private static int scalarMult(int a, int b) {
        int i = a, j = b, sum = 0;                                                              //last digit in binary representation
        while (i > 0 && j > 0) {
            sum = (sum + (i % 2) * (j % 2)) % 2;
            i = (i >> 1);                                                                       //get previous digit
            j = (j >> 1);
        }
        return sum;
    }

    /**Заполнение таблицы скалярных произведений**/
    private static void fillScalarMap() {
        for(int i = 0; i < binPower(k); i++)
        {
            for(int j = 0; j < binPower(k); j++) {
                scalarMap.put(new String[] {Integer.toBinaryString(i), Integer.toBinaryString(j)}, scalarMult(i, j));
            }
        }
    }

    /**Note: k | n <br>
     * A - n x n <br>
     * B - n x n <br>
     * C - n x n**/
    private static int[][] alg(int[][] A, int[][] B) {
        int n = A.length;
        int m = n / k;
        int[][] A1 = new int[n][m];
        int[][] B1 = new int[m][n];
        StringBuilder abuilder = new StringBuilder();
        StringBuilder bbuilder = new StringBuilder();
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
            }
        }
        int[][] C = new int[n][n];
        return C;
    }

    private static int[][] matrixMult(int[][] A, int[][] B) {
        int n = A.length;
        int[][] C = new int[n][n];
        return C;
    }

    public static void main(String[] args) {
        fillScalarMap();
    }
}