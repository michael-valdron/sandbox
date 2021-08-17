package leetcode.pow;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {
    private double round(double x, int places) {
        double scale = Math.pow(10, places);
        return Math.round(x * scale) / scale;
    }

    @Test
    void testMyPowOne() {
        var solution = new Solution();
        assertEquals(1024.0, round(solution.myPow(2.0, 10), 3));
    }

    @Test
    void testMyPowTwo() {
        var solution = new Solution();
        assertEquals(9.261, round(solution.myPow(2.1, 3), 3));
    }

    @Test
    void testMyPowThree() {
        var solution = new Solution();
        assertEquals(0.25, round(solution.myPow(2.0, -2), 3));
    }

    @Test
    void testMyPowFour() {
        var solution = new Solution();
        assertEquals(1.0, round(solution.myPow(1.0, -2147483648), 3));
    }
}