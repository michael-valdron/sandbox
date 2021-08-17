package leetcode.shufflearray;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {
    @Test
    void testShuffleOne() {
        final int[] EXPECTED = new int[]{2, 3, 5, 4, 1, 7};
        var nums = new int[]{2, 5, 1, 3, 4, 7};
        var solution = new Solution();
        assertArrayEquals(EXPECTED, solution.shuffle(nums, 3));
    }

    @Test
    void testShuffleTwo() {
        final int[] EXPECTED = new int[]{1, 4, 2, 3, 3, 2, 4, 1};
        var nums = new int[]{1, 2, 3, 4, 4, 3, 2, 1};
        var solution = new Solution();
        assertArrayEquals(EXPECTED, solution.shuffle(nums, 4));
    }

    @Test
    void testShuffleThree() {
        final int[] EXPECTED = new int[]{1, 2, 1, 2};
        var nums = new int[]{1, 1, 2, 2};
        var solution = new Solution();
        assertArrayEquals(EXPECTED, solution.shuffle(nums, 2));
    }
}