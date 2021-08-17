package leetcode.twosum;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {

    @Test
    void testTwoSumOne() {
        final int[] EXPECTED = new int[]{0, 1};
        var nums = new int[]{2, 7, 11, 15};
        var target = 9;
        var solution = new Solution();
        var result = solution.twoSum(nums, target);
        assertArrayEquals(EXPECTED, result);
    }

    @Test
    void testTwoSumTwo() {
        final int[] EXPECTED = new int[]{1, 2};
        var nums = new int[]{3, 2, 4};
        var target = 6;
        var solution = new Solution();
        var result = solution.twoSum(nums, target);
        assertArrayEquals(EXPECTED, result);
    }

    @Test
    void testTwoSumThree() {
        final int[] EXPECTED = new int[]{0, 1};
        var nums = new int[]{3, 3};
        var target = 6;
        var solution = new Solution();
        var result = solution.twoSum(nums, target);
        assertArrayEquals(EXPECTED, result);
    }
}