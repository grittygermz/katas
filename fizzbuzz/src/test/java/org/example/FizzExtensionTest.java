package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FizzExtensionTest {

    FizzExtension fizz;

    @BeforeEach
    void init() {
        fizz = new FizzExtension();
    }

    @Test
    void ShouldGiveFizzFizz() {
        int input = 3;

        String actual = fizz.replaceNum(input);

        assertThat(actual).isEqualTo("FizzFizz");
    }

    @Test
    void ShouldGiveBuzzBuzz() {
        int input = 5;

        String actual = fizz.replaceNum(input);

        assertThat(actual).isEqualTo("BuzzBuzz");
    }


    @Test
    void ShouldGiveFizz() {
        int input = 6;

        String actual = fizz.replaceNum(input);

        assertThat(actual).isEqualTo("Fizz");
    }

    @Test
    void ShouldGiveBuzz() {
        int input = 10;

        String actual = fizz.replaceNum(input);

        assertThat(actual).isEqualTo("Buzz");
    }

    @Test
    void ShouldGiveFizzBuzzBuzz() {
        int input = 15;

        String actual = fizz.replaceNum(input);

        assertThat(actual).isEqualTo("FizzBuzzBuzz");
    }

    @Test
    void ShouldGiveFizzBuzzFor53() {
        int input = 53;

        String actual = fizz.replaceNum(input);

        assertThat(actual).isEqualTo("FizzBuzz");
    }

    @Test
    void ShouldGiveFizzBuzzBuzzFor35() {
        int input = 35;

        String actual = fizz.replaceNum(input);

        assertThat(actual).isEqualTo("FizzBuzzBuzz");
    }

    @Test
    void ShouldGiveFizzFizzFor33() {
        int input = 33;

        String actual = fizz.replaceNum(input);

        assertThat(actual).isEqualTo("FizzFizz");
    }

    @Test
    void ShouldGiveCorrectOutput() {
        List<String> expected = List.of("1", "2", "FizzFizz", "4", "BuzzBuzz", "Fizz", "7", "8", "Fizz", "Buzz", "11", "Fizz", "Fizz", "14", "FizzBuzzBuzz");
        int input = 15;

        List<String> actual = fizz.fizzbuzz(input);

        assertThat(actual).containsExactlyElementsOf(expected);
    }
}
