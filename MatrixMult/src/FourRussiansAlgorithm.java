import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
/**Подсчет C = A x B алгоритмом 4-х русских**/
public class FourRussiansAlgorithm {
    /**Пара BitSet для двух ключей <br>
     * Поскольку BitSet, которые мы храним имеет размер K, то хеш
     * будем считать как хеш конкатенации BitSet**/
    private class BitSetPair {
        private BitSet key1;
        private BitSet key2;

        public BitSet getKey1() {
            return key1;
        }

        public BitSet getKey2() {
            return key2;
        }

        @Override
        public boolean equals(Object obj) {
            BitSetPair pair = (BitSetPair) obj;
            return pair.getKey1().equals(this.key1) && pair.getKey2().equals(this.key2);
        }

        @Override
        public int hashCode() {
            int hash1 = this.key1.hashCode();
            int hash2 = this.key2.hashCode();
        }
    }

    private static Map<BitSet[], Integer> scalarMap = new HashMap<>(); // bitset x bitset to {0, 1}
    private static final int k = 10;

    private static long binPow (int a, int n) {
        long res = 1;
        while (n > 0) {
            if (n % 2 == 1) {
                res *= a;
            }
            a *= a;
            n >>= 1;
        }
        return res;
    }
    /**Prints n x m Array**/
    private static void PrintArray(BitSet[] Arr, int n, int m) {
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                if(Arr[i].get(j)) {
                    System.out.print("1 ");
                } else {
                    System.out.print("0 ");
                }
            }
            System.out.println();
        }
    }

    private static Integer scalarMult(BitSet set1, BitSet set2) {
        BitSet temp = (BitSet) set1.clone();
        temp.and(set2);
        int res = 0;
        for(int i = 0; i < temp.length(); i++) {
            if(temp.get(i)) {
                res ^= 1;
            }
        }
        return res;
    }
    /**Заполняем матрицу всех возможных скалярных произведений <br>
     * матрица симметрична поэтому достаточно подсчитать половину**/
    private static void calculateScalarMap() {
        BitSet set1 = new BitSet(k), set2 = new BitSet(k);
        long pow = binPow(2, k);
        BitSet[] set;
        int scalar;
        for(long i = 0; i < pow; i++) {
            set1 = BitSet.valueOf(new long[] {i});
            for(long j = 0; j < i; j++) {
                set = new BitSet[2];
                set2 = BitSet.valueOf(new long[] {j});
                set[0] = set1;
                set[1] = set2;
                scalar = scalarMult(set1, set2);
                scalarMap.put(set, scalar);
                set = new BitSet[2];
                set[1] = set1;
                set[0] = set2;
                scalarMap.put(set, scalar);
            }
            set = new BitSet[2];
            set[0] = set1;
            set[1] = set1;
            scalarMap.put(set, scalarMult(set1, set1));
        }
    }
    /**Array of bitset <br>
     * each row of A is bitset <br>
     * each column of B is bitset <br>
     * A - n x t <br>
     * B - t x l
     * @return C - n x l**/
    private static BitSet[] AlgoMult(BitSet[] A, BitSet[] B, int n, int t, int l) {
        BitSet setA = new BitSet(t);
        BitSet setB = new BitSet(t);
        BitSet[] C = new BitSet[n];              //Bitsets are row's of C
        BitSet temp = new BitSet(t);
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < t / k; j += k) {
                for(int m = 0; m < l; m++) {
                    setA = A[i].get(j, j + k - 1);
                    for(int s = 0; s < t / k; s += k) {
                        setB = B[m].get(s, s + k - 1);
                        if(scalarMap.get(new BitSet[] {setA, setB}) == 1) {
                            temp.set(m);
                        }
                    }
                    setB.clear();
                }
                setA.clear();
            }
            C[i] = (BitSet) temp.clone();
            temp.clear();
        }
        return C;
    }
    /**Array of bitset <br>
     * each row of A is bitset <br>
     * each column of B is bitset **/
    private static BitSet[] Mult(BitSet[] A, BitSet[] B, int n, int t, int l) {
        BitSet[] C = new BitSet[n];
        BitSet tempA, tempB;
        BitSet temp = new BitSet();
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < l; j++) {
                tempA = (BitSet) A[i].clone();
                tempB = (BitSet) B[j].clone();
                if(scalarMult(tempA, tempB) == 1) {
                    temp.set(j);
                }
            }
            C[i] = (BitSet) temp.clone();
            temp.clear();
        }
        return C;
    }

    private static BitSet[] parseData(int n) {
        ArrayList<BitSet> list = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            int t;
            String temp;
            for(int i = 0; i < n; i++) {
                temp = reader.readLine();
                String[] strings = temp.split(" ");
                BitSet set = new BitSet(n);
                for (int j = 0; i < strings.length; i++) {
                    t = Integer.getInteger(strings[j]);
                    if(t != 0 && t != 1) {
                        System.out.println("Error");
                        System.exit(-1);
                    } else {
                        if(t == 1) {
                            set.set(i);
                        }
                    }
                }
                list.add(set);
                set.clear();
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return list.toArray(new BitSet[n]);
    }

    /**Добавляет в матрицу 0, чтобы t | n = m**/
    private static BitSet[] fillMatrix(BitSet[] Matrix, int n, int m) {
        int newN = n + (k - n % k);
        int newM = m + (k - m % k);
        BitSet temp = new BitSet(newM);
        BitSet[] res = new BitSet[newN];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                if(Matrix[i].get(j)) {
                    temp.set(j);
                }
            }
            res[i] = (BitSet) temp.clone();
            temp.clear();
        }
        for(int i = n; i < newN; i++) {
            res[i] = temp;
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println("Введите n, l и t. t должно быть кратным k");
//        int n, t, l;
//        try(Scanner scanner = new Scanner(System.in)) {
//            n = scanner.nextInt();
//            l = scanner.nextInt();
//            t = scanner.nextInt();
//            if(t % k != 0 || n <= 0 || l <= 0 || t <= 0) {
//                System.out.println("Error");
//                System.exit(-1);
//            }
//            System.out.println("Введите строки матрицы A");
//            BitSet[] A = parseData(n);
//            System.out.println("Введите столбцы матрицы B");
//            BitSet[] B = parseData(l);
//        } catch (Exception e) {
//            throw new RuntimeException();
//        }
        int n = 3, l = 3, t = 3;
        BitSet[] A = new BitSet[] {new BitSet(3),new BitSet(3), new BitSet(3)};
        BitSet[] B = new BitSet[] {new BitSet(3),new BitSet(3), new BitSet(3)};
        A[0].set(0); A[1].set(1); A[2].set(2);
        B[0].set(2); B[1].set(1); B[2].set(0);
        A = fillMatrix(A, 3, 3);
        B = fillMatrix(B, 3, 3);
        calculateScalarMap();
        long start = System.currentTimeMillis();
        BitSet[] C = Mult(A, B, 10, 10, 10);
        long end = System.currentTimeMillis();
        PrintArray(C, n, l);
        System.out.println("Обычное умножение: " + (end - start));
        start = System.currentTimeMillis();
        C = AlgoMult(A, B, 10, 10, 10);
        end = System.currentTimeMillis();
        PrintArray(C, n, l);
        System.out.println("Методом 4-ех русских умножение: " + (end - start));
    }
}