package org.graphic;

// Import your library
import java.lang.Math;

// Do not change the name of the Solution class
public class Solution {
    /**
     *Solution.
     *@author abcd
     */
    // Type your main code here
    private int numerator;
    private int denominator = 1;

    /**
     *Solution.
     *@author abcd
     */
    public Solution(int numerator, int denominator) {
        setNumerator(numerator);
        if (denominator == 0) {
            this.denominator = 1;
        } else {
            this.denominator = denominator;
        }
    }

    public void setNumerator(int num) {
        this.numerator = num;
    }

    /**
     *Solution.
     *@author abcd
     */
    public void setDenominator(int num) {
        if (num == 0) {
            return;
        }
        this.denominator = num;
    }

    public int getNumerator() {
        return this.numerator;
    }

    public int getDenominator() {
        return this.denominator;
    }

    /**
     *Solution.
     *@author abcd
     */
    public int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    /**
     *Solution.
     *@author abcd
     */
    public Solution reduce(Solution that) {
        int gcd = gcd(that.numerator, that.denominator);
        that.numerator /= gcd;
        that.denominator /= gcd;
        return that;
    }

    /**
     *Solution.
     *@author abcd
     */
    public Solution add(Solution other) {
        if (this.denominator == other.denominator) {
            this.numerator += other.numerator;
        } else {
            int lcm = (this.denominator * other.denominator)
                    / gcd(this.denominator, other.denominator);
            this.numerator = (this.numerator * (lcm / this.denominator))
                    + (other.numerator * (lcm / other.denominator));
            this.denominator = lcm;
        }
        reduce(this);
        return this;
    }

    /**
     *Solution.
     *@author abcd
     */
    public Solution subtract(Solution other) {
        if (this.denominator == other.denominator) {
            this.numerator -= other.numerator;
        } else {
            int lcm = (this.denominator * other.denominator)
                    / gcd(this.denominator, other.denominator);
            this.numerator = (this.numerator * (lcm / this.denominator))
                    - (other.numerator * (lcm / other.denominator));
            this.denominator = lcm;
        }
        reduce(this);
        return this;
    }

    /**
     *Solution.
     *@author abcd
     */
    public Solution multiply(Solution other) {
        this.numerator *= other.numerator;
        this.denominator *= other.denominator;
        reduce(this);
        return this;
    }

    /**
     *Solution.
     *@author abcd
     */
    public Solution divide(Solution other) {
        if (other.numerator != 0) {
            this.numerator *= other.denominator;
            this.denominator *= other.numerator;
            reduce(this);
        }
        return this;
    }

    /**
     *Solution.
     *@author abcd
     */
    public boolean equals(Object obj) {
        if (obj instanceof Solution) {
            Solution other = (Solution) obj;
            reduce(other);
            reduce(this);
            if (this.numerator == other.numerator && this.denominator == other.denominator) {
                return true;
            }
            return false;
        }
        return false;
    }

    public static void main(String[] args) {
        Solution ps1 = new Solution(4, 1);
        Solution ps2 = new Solution(0, 6);
        Solution sum = ps1.divide(ps2);
        System.out.println(sum.getNumerator() + " " + sum.getDenominator());
    }
}
