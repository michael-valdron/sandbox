package leetcode.poweroftwo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {
    @Test
    void testPowerOfTwoOne() {
        var solution = new Solution();
        assertTrue(solution.powerOfTwo(1));
    }

    @Test
    void testPowerOfTwoTwo() {
        var solution = new Solution();
        assertTrue(solution.powerOfTwo(16));
    }

    @Test
    void testPowerOfTwoThree() {
        var solution = new Solution();
        assertFalse(solution.powerOfTwo(3));
    }

    @Test
    void testPowerOfTwoFour() {
        var solution = new Solution();
        assertTrue(solution.powerOfTwo(4));
    }

    @Test
    void testPowerOfTwoFive() {
        var solution = new Solution();
        assertFalse(solution.powerOfTwo(5));
    }
}