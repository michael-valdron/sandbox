package leetcode.poweroftwo;

public class Solution {
    public boolean powerOfTwo(int n) {
        if (n <= 0)
            return false;
        return (n & (n - 1)) == 0;
    }
}
