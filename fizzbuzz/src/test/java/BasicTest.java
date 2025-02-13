import org.example.Main;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasicTest {

    static Main.Fizz fizz;

    @BeforeAll
    static void initAll() {
        fizz = new Main.Fizz();
    }

    @Test
    @DisplayName("Fizz")
    void ShouldGiveFizz() {
        //given
        int input = 5;
        List<String> expected = List.of("1", "2", "Fizz", "4", "Buzz");
        //when
        List<String> results = fizz.run(input);
        //then
        assertEquals(expected, results);
        //System.out.println(results);
    }

    @Test
    @DisplayName("Buzz")
    void ShouldGiveBuzz() {
        //given
        int input = 3;
        List<String> expected = List.of("1", "2", "Fizz");
        //when
        List<String> results = fizz.run(input);
        //then
        assertEquals(expected, results);
        //System.out.println(results);
    }


    @Test
    @DisplayName("FizzBuzz")
    void ShouldGiveFizzBuzz() {
        //given
        int input = 15;
        List<String> expected = List.of("1", "2", "Fizz", "4", "Buzz", "Fizz", "7", "8", "Fizz", "Buzz", "11", "Fizz", "13", "14", "FizzBuzz");
        //when
        List<String> results = fizz.run(input);
        //then
        assertEquals(expected, results);
        //System.out.println(results);
    }
}
