package leetcode.pow;

public class Solution {
    private double pow(double x, int n) {
        if (n == 0)
            return 1;
        else if (n == 1)
            return x;
        else if (n == 2)
            return x * x;
        else if (n % 2 == 0) {
            double y = pow(x, n / 2);
            return y * y;
        } else {
            double y = pow(x, n / 2);
            return y * y * x;
        }
    }

    public double myPow(double x, int n) {
        if (n < 0) {
            x = 1 / x;
            n = -n;
        }

        if (x == 0.0 || x == 1.0)
            return x;

        return pow(x, n);
    }
}
