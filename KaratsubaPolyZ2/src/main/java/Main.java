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
        //n = 2^k
        //P = P0 + x^(2^k)P1, Q = Q0 + x^(2^k)Q1
        if(pow == 1) {                                                       //Произведение линейных сомножителей
            return new int[] {P[0] * Q[0], P[0]*Q[1] + P[1]*Q[0], P[1]*Q[1]};
        }
        int k = n / 2;
        int[] sP = new int[k + k % 2];                                      //P0 + P1
        int[] sQ = new int[k + k % 2];                                      //Q0 + Q1
        for(int i = 0; i < k + k % 2; i++) {
            if (k + i + k % 2 >) {
                sP[i] = P[i] % 2;
                sQ[i] = Q[i] % 2;
            }
            else {
                sP[i] = (P[i] + P[k + i]) % 2;
                sQ[i] = (Q[i] + Q[k + i]) % 2;
            }
        }
        int[] sPQ = karatsubaZ2Init(sP, sQ, n / 2, pow - 1);      //(P0 + P1)*(Q0 + Q1)
        for(int i = 0; i < k; i++) {
            sP[i] = P[i];
            sQ[i] = Q[i];
        }
        int[] PQ = karatsubaZ2Init(sP, sQ, n / 2, pow - 1);       //P0 * Q0
        for(int i = 0; i < k; i++) {
            sP[i] = P[i + k];
            sQ[i] = Q[i + k];
        }
        int[] PQ1 = karatsubaZ2Init(sP, sQ, n / 2, pow - 1);       //P1 * Q1
        for(int i = 0; i < k; i++) {
            sPQ[i] = (sPQ[i] - PQ[i] - PQ1[i]) % 2;                         //(P0 + P1)*(Q0 + Q1) - P0Q0 - P1Q1
        }
        int[] res = new int[2 * n];                                         //fill result
        if (pow >= 0) System.arraycopy(PQ, 0, res, 0, pow);
        for(int i = pow; i < 2 * pow - 1; i++) {
            res[i] = PQ[i] + sPQ[i - pow];
        }
        if(2 * pow - 1 < n) {
            res[2 * pow - 1] = sPQ[2 * pow - 1];
            for(int i = 2 * pow; i < 2 * pow + n + 1; i++) {
                res[i] = PQ1[i - 2 * pow] + sPQ[i - 2 * pow];
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

    public static void main(String[] args) {
        System.out.println("Количество коэффициентов должно совпадать, заполните нулями, если количество коэффициентов не совпадает");
        Scanner scanner = new Scanner(System.in);
        int[] P = onStart(scanner);
        int[] Q = onStart(scanner);
        int pSize = P.length, k = (int) Math.floor(Math.log(pSize) / Math.log(2));
        if(pSize != Q.length) {
            System.out.println("Mismatch in size");
            System.exit(-1);
        }
        int[] res = karatsubaZ2Init(P, Q, pSize - 1, binPower(k));
        for (int re : res) {
            System.out.print(re + " ");
        }
        System.out.println();
    }
}