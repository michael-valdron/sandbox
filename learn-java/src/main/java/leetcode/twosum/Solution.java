package leetcode.twosum;

import java.util.HashMap;

public class Solution {
    public int[] twoSum(int[] nums, int target) {
        var m = new HashMap<Integer, Integer>();

        for (int i = 0; i < nums.length; i++) {
            var comp = target - nums[i];

            if (m.containsKey(comp))
                return new int[]{m.get(comp), i};

            m.put(nums[i], i);
        }

        return new int[]{};
    }
}
