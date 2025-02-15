package org.example;

import java.util.ArrayList;
import java.util.List;

public class Fizz {

    public List<String> fizzbuzz(int n) {
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            list.add(replaceNum(i));
        }
        return list;
    }

    public String replaceNum(int num) {

        if (num % 15 == 0) {
            return "FizzBuzz";
        }
        if (num % 5 == 0) {
            return "Buzz";
        }
        if (num % 3 == 0) {
            return "Fizz";
        }
        return String.valueOf(num);
    }
}
