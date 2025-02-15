package org.example;

import java.util.HashMap;
import java.util.Map;

public class FizzExtension extends Fizz {

    /*
    always has FizzBuzz in pairs i.e FizzFizzBuzz is not valid -> it should be FizzBuzzFizz
    should give Fizz if the digit is '3' and number is divisible by 3
    should give Buzz if the digit is '5' and number is divisible by 5
     */
    @Override
    public String replaceNum(int num) {

        Map<String, Integer> fizzbuzzMap = computeTotalNumberOfFizzAndBuzzes(num);
        int fizzcount = fizzbuzzMap.get("fizz");
        int buzzcount = fizzbuzzMap.get("buzz");


        if (fizzcount == 0 && buzzcount == 0) {
            return String.valueOf(num);
        } else {
            StringBuilder sb = new StringBuilder();

            //create all the FizzBuzz in front
            int min = Math.min(fizzcount, buzzcount);
            for (int j = 0; j < min; j++) {
                sb.append("FizzBuzz");
            }

            if (fizzcount == buzzcount) {
                return sb.toString();
            }

            //create the extra Fizz or Buzz afterward if there are supposed to be extra Fizz or Buzz
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
            return sb.toString();

        }
    }

    private Map<String, Integer> computeTotalNumberOfFizzAndBuzzes(int num) {

        int fizzcount = 0;
        int buzzcount = 0;

        String currentNum = String.valueOf(num);
        if (currentNum.contains("5")) {
            buzzcount++;
        }
        if (currentNum.contains("3")) {
            fizzcount++;
        }


        if (num % 5 == 0) {
            buzzcount++;
        }
        if (num % 3 == 0) {
            fizzcount++;
        }

        Map<String, Integer> fizzbuzzMap = new HashMap<>();
        fizzbuzzMap.put("fizz", fizzcount);
        fizzbuzzMap.put("buzz", buzzcount);

        return fizzbuzzMap;
    }

}
