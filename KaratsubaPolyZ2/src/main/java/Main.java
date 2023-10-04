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
     * rP, lP - указатели на левый и правый конец множителя P
     * rQ, lQ - указатели на левый и правый конец множителя Q**/
    public static int[] karatsubaZ2Init(int []P, int []Q, int n, int lP, int rP, int lQ, int rQ) {
        //n = 2^k
        //P = P0 + x^(2^k)P1, Q = Q0 + x^(2^k)Q1
        if(n == 0) {
            return new int[] {P[rP] * Q[rQ]};
        }
        int[] sP = new int[P.length];
        for(int i = 0; i < P.length; i++) {

        }
        int[] P0Q0 = karatsubaZ2Init(P, Q, n / 2, 0, rP / 2, 0, rQ / 2);
        int[] P1Q1 = karatsubaZ2Init(P, Q, n / 2, rP / 2 +  1, rP, rQ / 2 + 1, rQ);
        //int[] Last = karatsubaZ2Init(, n / 2, 0, );

        return null;
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
        System.out.println("Количество коэффициентов должно совпадать, заполните нулями, если количество коэффициентов не совпадает");
        Scanner scanner = new Scanner(System.in);
        int[] P = onStart(scanner);
        int[] Q = onStart(scanner);
        int pSize = P.length, n = (int) Math.floor(Math.log(pSize) / Math.log(2));
        if(pSize != Q.length) {
            System.out.println("Mismatch in size");
            System.exit(-1);
        }
        int k  = binPower(n);
        int[] res = karatsubaZ2Init(P, Q, n, 0, k - 1, 0, k - 1);
        for (int re : res) {
            System.out.println(re);
        }
    }
}