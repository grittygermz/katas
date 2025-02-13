package org.example;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

    }

    public static class Fizz {

        public Fizz() {
        }

        public List<String> run(int n) {

            ArrayList<String> list = new ArrayList<>();

            for (int i = 1; i <= n; i++) {
                if (i % 15 == 0) {
                    list.add("FizzBuzz");
                    continue;
                }
                if (i % 5 == 0) {
                    list.add("Buzz");
                    continue;
                }
                if (i % 3 == 0) {
                    list.add("Fizz");
                    continue;
                }
                list.add(String.valueOf(i));
            }

            return list;

        }
    }

    public static class FizzV2 {

        public FizzV2() {
        }

        public List<String> run(int n) {

            ArrayList<String> list = new ArrayList<>();

            for (int i = 1; i <= n; i++) {

                int fizzcount = 0;
                int buzzcount = 0;
                String currentNum = String.valueOf(i);
                if (currentNum.contains("5")) {
                    buzzcount++;
                }
                if (currentNum.contains("3")) {
                    fizzcount++;
                }


                if (i % 5 == 0) {
                    buzzcount++;
                }
                if (i % 3 == 0) {
                    fizzcount++;
                }



                if (fizzcount == 0 && buzzcount == 0) {
                    list.add(String.valueOf(i));
                } else {
                    StringBuilder sb = new StringBuilder();
                    //always has FizzBuzz in pairs

                    //create all the FizzBuzz in front
                    int min = Math.min(fizzcount, buzzcount);
                    for (int j = 0; j < min; j++) {
                        sb.append("FizzBuzz");
                    }

                    //create teh extra Fizz or Buzz afterwards
                    if (fizzcount > buzzcount) {
                        int remainder = fizzcount - buzzcount;
                        for (int k = 0; k < remainder; k++) {
                            sb.append("Fizz");
                        }
                    } else {
                        int remainder = buzzcount - fizzcount;
                        for (int k = 0; k < remainder; k++) {
                            sb.append("Buzz");
                        }
                    }

                    list.add(sb.toString());
                }
            }

            return list;

        }
    }
}