package org.example;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**Задача 4-SUM методом Meet in the middle**/
public class Main {
    //Для хранения пар целых чисел
    private static class IntegerPair {
        private int value1;
        private int value2;

        IntegerPair() { }

        IntegerPair(int val1, int val2) {
            this.value1 = val1;
            this.value2 = val2;
        }

        public void setValue1(int value1) {
            this.value1 = value1;
        }

        public void setValue2(int value2) {
            this.value2 = value2;
        }

        public int getValue1() {
            return value1;
        }

        public int getValue2() {
            return value2;
        }

        /**Помещаем первое число**/
        @Override
        public int hashCode() {
            return value1 << 5 ^ value2;
        }

        @Override
        public boolean equals(Object o) {
            if(!(o instanceof IntegerPair pair2)) return false;
            return pair2.value1 == this.value1 && pair2.value2 == this.value2;
        }

        @Override
        protected Object clone() {
            return new IntegerPair(this.value1, this.value2);
        }
    }

    /**(a + b + c + d = 0) <-> a + b = - (c + d).
     * Храним все пары (a, b) с суммой в хеш таблице
     * после заполнения смотрим наличие суммы -(a + b)**/
    private static List<List<Integer>> algo(int[] X) {
        List<List<Integer>> res = new ArrayList<>();
        HashMap<Integer, IntegerPair> map = new HashMap<>();
        IntegerPair pair;
        int n = X.length;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n && j != i; j++) {
                pair = new IntegerPair(X[i], X[j]);
                map.put(X[i] + X[j], pair);
            }
        }
        List<Integer> temp = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n && j != i; j++) {
                if(map.containsKey(-(X[i] + X[j]))) {
                    pair = map.get(-(X[i] + X[j]));
                    temp.add(pair.getValue1());
                    temp.add(pair.getValue2());
                    temp.add(X[i]);
                    temp.add(X[j]);
                    res.add(temp);
                    temp = new ArrayList<>();
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[] X = new int[] {-1, 0, 1, 0};
        List<List<Integer>> res =  algo(X);
        for(List<Integer> solution: res) {
            System.out.print(" " + solution.get(0));
            System.out.print(" " + solution.get(1));
            System.out.print(" " + solution.get(2));
            System.out.print(" " + solution.get(3));
            System.out.println();
        }
    }
}