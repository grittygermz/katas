import org.example.Main;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasicTestV2 {

    static Main.FizzV2 fizz;

    @BeforeAll
    static void initAll() {
        fizz = new Main.FizzV2();
    }

    @Test
    @DisplayName("BuzzBuzz")
    void ShouldGiveBuzz() {
        //given
        int input = 3;
        List<String> expected = List.of("1", "2", "FizzFizz");
        //when
        List<String> results = fizz.run(input);
        //then
        assertEquals(expected, results);
        //System.out.println(results);
    }

    @Test
    @DisplayName("FizzFizz")
    void ShouldGiveFizz() {
        //given
        int input = 5;
        List<String> expected = List.of("1", "2", "FizzFizz", "4", "BuzzBuzz");
        //when
        List<String> results = fizz.run(input);
        //then
        assertEquals(expected, results);
        //System.out.println(results);
    }


    @Test
    @DisplayName("FizzFizzBuzz")
    void ShouldGiveFizzBuzz() {
        //given
        int input = 15;
        List<String> expected = List.of("1", "2", "FizzFizz", "4", "BuzzBuzz", "Fizz", "7", "8", "Fizz", "Buzz", "11", "Fizz", "Fizz", "14", "FizzBuzzBuzz");
        //when
        List<String> results = fizz.run(input);
        //then
        assertEquals(expected, results);
        //System.out.println(results);
    }

    @Test
    @DisplayName("53")
    void ShouldGiveFizzBuzzFor53() {
        //given
        int input = 53;
        String expected =  "FizzBuzz";
        //when
        List<String> results = fizz.run(input);
        //then
        assertEquals(expected, results.getLast());
        //System.out.println(results);
    }

    @Test
    @DisplayName("35")
    void ShouldGiveFizzBuzzBuzzFor35() {
        //given
        int input = 35;
        String expected =  "FizzBuzzBuzz";
        //when
        List<String> results = fizz.run(input);
        //then
        assertEquals(expected, results.getLast());
        //System.out.println(results);
    }

    @Test
    @DisplayName("33")
    void ShouldGiveFizzFizzFor33() {
        //given
        int input = 33;
        String expected =  "FizzFizz";
        //when
        List<String> results = fizz.run(input);
        //then
        assertEquals(expected, results.getLast());
        //System.out.println(results);
    }
}
