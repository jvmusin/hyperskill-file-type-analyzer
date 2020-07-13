package analyzer;

import java.util.ArrayList;
import java.util.List;

public class Hash {
    private static final int BASE = 53;
    private static final int MOD = (int) 1e9 + 9;
    private static final List<Integer> powers = new ArrayList<>();

    static {
        powers.add(0);
    }

    private final int[] hash;
    private final String s;

    public Hash(String s) {
        this.s = s;

        hash = new int[s.length() + 1];
        for (int i = 0; i < s.length(); i++) {
            hash[i + 1] = add(hash[i], mul(toCode(s.charAt(i)), getPower(i)));
        }
    }

    private static synchronized void growPowers(int k) {
        while (k >= powers.size()) {
            int last = powers.get(powers.size() - 1);
            powers.add(mul(last, BASE));
        }
    }

    public static int getPower(int k) {
        if (k >= powers.size()) growPowers(k);
        return powers.get(k);
    }

    private static int add(int a, int b) {
        int c = a + b;
        if (c >= MOD) c -= MOD;
        return c;
    }

    private static int sub(int a, int b) {
        int c = a - b;
        if (c < 0) c += MOD;
        return c;
    }

    private static int mul(int a, int b) {
        long c = a * (long) b;
        return (int) (c % MOD);
    }

    private static int toCode(char c) {
        return c;
    }

    private static boolean equal(String pattern, String text, int from) {
        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) != text.charAt(from + i)) {
                return false;
            }
        }
        return true;
    }

    public boolean contains(Hash other) {
        int n = length();
        int m = other.length();
        if (m > n) {
            return false;
        }

        int otherHash = other.fullHash();
        for (int i = 0; i + m <= n; i++) {
            int curHash = sub(hash[i + m], hash[i]);
            if (otherHash == curHash && equal(other.s, s, i)) {
                return true;
            }
            otherHash = mul(otherHash, BASE);
        }

        return false;
    }

    private int length() {
        return s.length();
    }

    private int fullHash() {
        return hash[s.length()];
    }
}
