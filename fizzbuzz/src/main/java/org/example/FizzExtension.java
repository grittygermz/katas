package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FizzExtension extends Fizz {

    /**
     * always has FizzBuzz in pairs i.e FizzFizzBuzz is not valid -> it should be FizzBuzzFizz
     * should give Fizz if the digit is '3' and number is divisible by 3
     * should give Buzz if the digit is '5' and number is divisible by 5
     */
    @Override
    public String replaceNum(int num) {

        Map<String, Integer> fizzBuzzToCount = computeTotalNumberOfFizzAndBuzzes(num);

        if (hasNothingToBeReplaced(fizzBuzzToCount)) {
            return String.valueOf(num);
        } else {
            return createFizzBuzz(fizzBuzzToCount);
        }
    }

    private Map<String, Integer> computeTotalNumberOfFizzAndBuzzes(int num) {

        int fizzCount = 0;
        int buzzCount = 0;

        String currentNum = String.valueOf(num);
        if (currentNum.contains("5")) {
            buzzCount++;
        }
        if (currentNum.contains("3")) {
            fizzCount++;
        }
        if (num % 5 == 0) {
            buzzCount++;
        }
        if (num % 3 == 0) {
            fizzCount++;
        }

        Map<String, Integer> fizzBuzzToCount = new HashMap<>();
        fizzBuzzToCount.put("fizz", fizzCount);
        fizzBuzzToCount.put("buzz", buzzCount);

        return fizzBuzzToCount;
    }

    private String createFizzBuzz(Map<String, Integer> fizzBuzzToCount) {
        StringBuilder sb = new StringBuilder();

        createPairedFizzBuzz(sb, fizzBuzzToCount);

        if (noTrailingFizzBuzzToCreate(fizzBuzzToCount)) {
            return sb.toString();
        }
        createTrailingFizzBuzz(sb, fizzBuzzToCount);
        return sb.toString();
    }

    private void createTrailingFizzBuzz(StringBuilder sb, Map<String, Integer> fizzBuzzToCount) {
        int fizzCount = fizzBuzzToCount.get("fizz");
        int buzzCount = fizzBuzzToCount.get("buzz");

        if (fizzCount > buzzCount) {
            int remainder = fizzCount - buzzCount;
            appendTerm(remainder, sb, "Fizz");
        } else {
            int remainder = buzzCount - fizzCount;
            appendTerm(remainder, sb, "Buzz");
        }
    }

    private boolean noTrailingFizzBuzzToCreate(Map<String, Integer> fizzBuzzToCount) {
        return Objects.equals(fizzBuzzToCount.get("fizz"), fizzBuzzToCount.get("buzz"));
    }

    private void createPairedFizzBuzz(StringBuilder sb, Map<String, Integer> fizzBuzzToCount) {
        int min = Math.min(fizzBuzzToCount.get("fizz"), fizzBuzzToCount.get("buzz"));
        appendTerm(min, sb, "FizzBuzz");
    }

    private void appendTerm(int remainder, StringBuilder sb, String Fizz) {
        for (int k = 0; k < remainder; k++) {
            sb.append(Fizz);
        }
    }

    private boolean hasNothingToBeReplaced(Map<String, Integer> fizzBuzzToCount) {
        return fizzBuzzToCount.get("fizz") == 0 && fizzBuzzToCount.get("buzz") == 0;
    }
}
