import java.util.HashMap;

/**Алгоритм четырёх русских для матриц над Z2 с предподсчитанной таблицей скалярных произведений всех векторов длины 10**/
public class Main {
    private static final int k = 10;
    private static HashMap<String, String> scalarMap;               //String - binary representation of the integer

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

    private void fillScalarMap() {
        int res = 0;
        for(int i = 0; i < binPower(k); i++)
        {
            for(int j = 0; j < binPower(k); j++) {
                ////asdad
            }
        }
    }

    private static int[][] alg(int[][] A, int[][] B) {
            return null;
    }

    public static void main(String[] args) {

    }
}