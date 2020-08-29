package com.justinaut.wordscope.AsciiSummer;

public class AsciiSummer {
    public static AsciiSum sum(String stringToSum) {
        long sum;
        if (stringToSum == null) {
            stringToSum = "";
        }

        sum = stringToSum.chars()
                .asLongStream()
                .sum();

        return new AsciiSum(stringToSum, sum);
    }
}
