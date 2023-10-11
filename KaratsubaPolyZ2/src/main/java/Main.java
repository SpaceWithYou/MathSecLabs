import java.util.Random;
import java.util.Scanner;

/**Алгоритм Карацубы умножения многочленов над Z_2**/
public class Main {
    private static final int time = 1000000;
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
     * Note: Вместо использования базы вида if (n == 1) c[0] = a[0] * b[0], имеет смысл, начиная с какого-то размера задачи,
     * использовать более эффективное наивное умножение за квадрат.
     * Note: Степень многочлена - степень двойки**/
    private static int[] karatsubaZ2Init(int []P, int []Q, int n) {
        if(n == 1) return new int[] {P[0] * Q[0], 0};
        int k = n / 2;
        int[] sP = new int[k];
        int[] sQ = new int[k];
        for(int i = 0; i < k; i++) {
            sP[i] = (P[i] + P[i + k]) % 2;
            sQ[i] = (Q[i] + Q[i + k]) % 2;
        }
        int[] sPQ = karatsubaZ2Init(sP, sQ, k);                     //(P0 + P1)(Q0 + Q1)
        for(int i = 0; i < k; i++) {
            sP[i] = P[i];
            sQ[i] = Q[i];
        }
        int[] PQ0 = karatsubaZ2Init(sP, sQ, k);                     //P0Q0
        for(int i = 0; i < k; i++) {
            sP[i] = P[i + k];
            sQ[i] = Q[i + k];
        }
        int[] PQ1 = karatsubaZ2Init(sP, sQ, k);                    //P1Q1
        int[] res = new int[2 * n];
        for(int i = 0; i < n; i++) {
            sPQ[i] = (sPQ[i] - PQ1[i] - PQ0[i]) % 2;
            if(sPQ[i] < 0) sPQ[i] += 2;
        }
        //fill res
        System.arraycopy(PQ0, 0, res, 0, n - 1);
        for(int i = k; i < n + k; i++) {
            res[i] = (res[i] + sPQ[i - k]) % 2;
        }
        for(int i = n; i < n + n; i++) {
            res[i] = (res[i] + PQ1[i - n]) % 2;
        }
        return  res;
    }

    private static int[] multiZ2(int []P, int []Q, int n) {
        int []res = new int[2 * n];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                res[i + j] = (P[i] * Q[j] + res[i + j]) % 2;
            }
        }
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

    /**Возращает рандомный многочлен над Z2 степени n - 1**/
    private static int[] getPoly(int n, long seed) {
        Random rand = new Random(seed);
        int []res = new int[n];
        for(int i = 0; i < n; i++) {
            res[i] = rand.nextInt() % 2;
            if(res[i] < 0) res[i] += 2;
        }
        return res;
    }

    private static void tests() {
        int []tP, tQ, res;
        int []testValues = new int[] {binPower(3), binPower(5), binPower(10), binPower(15), binPower(20)};
        long startTime, endTime;
        long seed1 = 111111111, seed2 = 101010101;
        for(int volume : testValues) {
            System.out.println("Running volume = " + volume);
            tP = getPoly(volume, seed1);
            tQ = getPoly(volume, seed2);
            startTime = System.nanoTime();
            res = karatsubaZ2Init(tP, tQ, volume);
            endTime = System.nanoTime();
            System.out.println("Karatsuba time in ms " + (double)((endTime - startTime) / time));
            startTime = System.nanoTime();
            res = multiZ2(tP, tQ, volume);
            endTime = System.nanoTime();
            System.out.println("Native time in ms " +  (double)((endTime - startTime) / time));
            System.out.println();
        }
    }

    public static void main(String[] args) {
//        System.out.println("Количество коэффициентов должно совпадать и равняться степени 2, заполните нулями, если количество коэффициентов не совпадает");
//        Scanner scanner = new Scanner(System.in);
//        int[] P = onStart(scanner);
//        int[] Q = onStart(scanner);
//        int pSize = P.length, k = (int) Math.floor(Math.log(pSize) / Math.log(2));
//        if(pSize != Q.length || binPower(k) != pSize) {
//            System.out.println("Mismatch in size");
//            System.exit(-1);
//        }
//        long startTime = System.nanoTime();
//        int[] res = karatsubaZ2Init(P, Q, pSize);
//        long endTime = System.nanoTime();
//        for (int re : res) {
//            System.out.print(re + " ");
//        }
//        System.out.println();
//        System.out.println("Karatsuba time in nano seconds " + (endTime - startTime));
//        startTime = System.nanoTime();
//        res = multiZ2(P, Q, pSize);
//        endTime = System.nanoTime();
//        for (int re : res) {
//            System.out.print(re + " ");
//        }
//        System.out.println();
//        System.out.println("Native time in nano seconds " + (endTime - startTime));
//            tests();
    }
}