import org.example.Fizz;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FizzTest {

    static Fizz fizz;

    @BeforeAll
    static void setup() {
        fizz = new Fizz();
    }

    @Test
    void ShouldGiveFizzWhenDivisibleByThree() {
        int input = 3;
        String actual = fizz.replaceNum(input);
        assertThat(actual).isEqualTo("Fizz");
    }

    @Test
    void ShouldGiveBuzzWhenDivisibleByFive() {
        int input = 5;
        String actual = fizz.replaceNum(input);
        assertThat(actual).isEqualTo("Buzz");
    }

    @Test
    void ShouldGiveFizzBuzzWhenDivisibleByFiveAndThree() {
        int input = 15;
        String actual = fizz.replaceNum(input);
        assertThat(actual).isEqualTo("FizzBuzz");
    }

    @Test
    void ShouldNotReplaceNumber() {
        int input = 4;
        String actual = fizz.replaceNum(input);
        assertThat(actual).isEqualTo(String.valueOf(input));
    }

    @Test
    void ShouldGiveCorrectNumberOfOutputs() {
        int input = 15;
        List<String> actual = fizz.fizzbuzz(input);
        assertThat(actual).hasSize(15);
    }

    @Test
    void ShouldGiveExpectedOutput() {
        int input = 15;
        List<String> expected = List.of("1", "2", "Fizz", "4", "Buzz", "Fizz", "7", "8", "Fizz", "Buzz", "11", "Fizz", "13", "14", "FizzBuzz");
        List<String> actual = fizz.fizzbuzz(input);
        assertThat(actual).isEqualTo(expected);
    }
}
