import java.util.Scanner;

/*Алгоритм Карацубы умножения многочленов над Z_2*/
public class Main {
    /**Проверяет корректность ввода**/
    private static boolean check(int [] P) {
        if(P.length == 0) {
            return false;
        }
        for(int t : P) {
            if(t != 0 && t != 1) {
                return false;
            }
        }
        return true;
    }

    /**Обработчик ввода**/
    private static int[] onStart(Scanner scanner) {
        String line = "";
        String[] nums;
        System.out.println("Введите коэффициенты многочлена  в порядке возрастания степеней (1, x, x^2, ..., x^n) в одну строку, через пробел");
        line = scanner.nextLine();
        nums =  line.split(" ");
        int []P = new int[nums.length];
        for(int i = 0; i < nums.length; i++) {
            P[i] = Integer.parseInt(nums[i]);
        }
        if(!check(P)) {
            System.out.println("Wrong parameter");
            System.exit(-1);
        }
        return P;
    }

    /**
     * Подается на вход многочлены P и Q в виде массива его коэффициентов (1, x, x^2, ..., x^n)
     * и так далее, коэффициенты либо 0, либо 1
     * pow = 2^m <= n
     * Note: Вместо использования базы вида if (n == 1) c[0] = a[0] * b[0], имеет смысл, начиная с какого-то размера задачи,
     * использовать более эффективное наивное умножение за квадрат.**/
    public static int[] karatsubaZ2Init(int []P, int []Q, int n, int pow) {
        //n = 2^t
        //P = P0 + x^(2^t)P1, Q = Q0 + x^(2^t)Q1
//        if(pow == 1) {                                                       //Произведение линейных сомножителей
//            return new int[] {P[0] * Q[0], P[0]*Q[1] + P[1]*Q[0], P[1]*Q[1]};
//        }
//        int k = n / 2;
//        int m = pow / 2;
//        int s = m + 1;
//        int[] sP = new int[s];                                            //P0 + P1
//        int[] sQ = new int[s];                                            //Q0 + Q1
//        for(int i = 0; i <= m; i++) {
//            if (i >= k) {
//                sP[i] = P[i];
//                sQ[i] = Q[i];
//            }
//            else {
//                sP[i] = (P[i] + P[i + s]) % 2;
//                sQ[i] = (Q[i] + Q[i + s]) % 2;
//            }
//        }
//        int[] sPQ = karatsubaZ2Init(sP, sQ, n / 2, pow - 1);       //(P0 + P1)*(Q0 + Q1)
//        for(int i = 0; i <= m; i++) {
//            sP[i] = P[i];
//            sQ[i] = Q[i];
//        }
//        int[] PQ = karatsubaZ2Init(sP, sQ, n / 2, pow - 1);        //P0 * Q0
//        for(int i = 0; i <= m; i++) {
//            if (i >= k) {
//                sP[i] = P[i];
//                sQ[i] = Q[i];
//            }
//            else {
//                sP[i] = P[i + s];
//                sQ[i] = Q[i + s];
//            }
//        }
//        int[] PQ1 = karatsubaZ2Init(sP, sQ, n / 2, pow - 1);        //P1 * Q1
//        for(int i = 0; i <= m; i++) {
//            sPQ[i] = (sPQ[i] - PQ[i] - PQ1[i]) % 2;                         //(P0 + P1)*(Q0 + Q1) - P0Q0 - P1Q1
//            if(sPQ[i] < 0) sPQ[i] += 2;
//        }
//        //int[] res = new int[2 * n + 1];                                     //fill result
//        int[] res = new int[3 * pow - 1];                                     //fill result
//        System.arraycopy(PQ, 0, res, 0, m + 1);
//        res[m + 1] = (PQ[m + 1] + sPQ[0]) % 2;
//        for(int i = m + 2; i < 2 * m + 2; i++) {
//            res[i] = sPQ[i - m - 1];
//        }
//        res[2 * m + 2] = (res[2 * m + 2] + PQ1[0]) % 2;
//        for(int i = 2 * m + 3; i <= 3 * pow - 2; i++) {
//            res[i] = PQ1[i - 2 * m - 3];
//        }
//        return res;
        if(n == 1) {
            return new int[] {P[0]*Q[0], P[0]*Q[1] + P[1]*Q[0], P[1]*Q[1]};
        }
        int []sP = new int[pow];
        int []sQ = new int[pow];
        for(int i = 0; i < pow; i ++) {
            if(i + pow  <= n - pow) {
                sP[i] = (P[i] + P[i + pow + 1]) % 2;
                sQ[i] = (Q[i] + Q[i + pow + 1]) % 2;
            }
            else {
                sP[i] = P[i];
                sQ[i] = Q[i];
            }
        }
        int[] sPQ = karatsubaZ2Init(sP, sQ, n - 1, pow / 2);    //(P0 + P1)(Q0 +Q1) "pow" elements
        for(int i = 0; i < pow; i++) {
            sP[i] = P[i];
            sQ[i] = Q[i];
        }
        int[] PQ = karatsubaZ2Init(sP, sQ, n - 1, pow / 2);      //P0Q0 "pow" elements
        for(int i = 0; i < pow; i++) {
            if(i + pow - 1 < n - pow) {
                sP[i] = P[i + pow];
                sQ[i] = Q[i + pow];
            }
            else {
                sP[i] = 0;
                sQ[i] = 0;
            }
        }
        int[] PQ1 = karatsubaZ2Init(sP, sQ, n - 1, pow / 2);     //P1Q1 "pow" elements
        for(int i = 0; i < pow; i++) {                                   //(P0 + P1)(Q0 +Q1) - P0Q0 - P1Q1
            sPQ[i] = (sPQ[i] - PQ[i] - PQ1[i]) % 2;
            if(sPQ[i] < 0) sPQ[i] += 2;
        }
        int[] res = new int[3 * pow - 2];
        System.arraycopy(PQ, 0, res, 0, pow - 1);
        res[pow - 1] = (PQ[pow - 1] + sPQ[0]) % 2;
        System.arraycopy(sPQ, 1, res, 1 + pow - 1, pow - 1);
        res[2 * pow - 2] = (res[2 * pow - 2] + PQ[0]) % 2;
        System.arraycopy(PQ1, 1, res, 1 + 2 * pow - 2, pow - 1);
        return res;
    }

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

    public static void main(String[] args) {
//        System.out.println("Количество коэффициентов должно совпадать, заполните нулями, если количество коэффициентов не совпадает");
//        Scanner scanner = new Scanner(System.in);
//        int[] P = onStart(scanner);
//        int[] Q = onStart(scanner);
//        int pSize = P.length, k = (int) Math.floor(Math.log(pSize) / Math.log(2));
//        if(pSize != Q.length) {
//            System.out.println("Mismatch in size");
//            System.exit(-1);
//        }
        //
        int[] P = new int[]{0, 0, 0, 0, 1};
        int[] Q = new int[]{0, 0, 1, 0, 0};
        int pSize = P.length, k = (int) Math.floor(Math.log(pSize) / Math.log(2));
        int[] res = karatsubaZ2Init(P, Q, pSize - 1, binPower(k));
        for (int re : res) {
            System.out.print(re + " ");
        }
        System.out.println();
    }
}