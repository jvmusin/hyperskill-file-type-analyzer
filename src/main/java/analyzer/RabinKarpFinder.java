package analyzer;

import java.util.List;

import static java.util.Collections.singletonList;

public class RabinKarpFinder implements Finder {
    private static final int BASE = 53;
    private static final int MOD = (int) 1e9 + 9;

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

    @Override
    public boolean contains(String text, String word) {
        return contains(singletonList(text), word);
    }

    @Override
    public boolean contains(List<String> text, String word) {
        int wordHash = 0;
        for (int i = word.length() - 1; i >= 0; i--) {
            wordHash = mul(wordHash, BASE);
            wordHash = add(wordHash, toCode(word.charAt(i)));
        }
        int pow = 1;
        for (int i = 0; i < word.length() - 1; i++) {
            pow = mul(pow, BASE);
        }
        for (String line : text) {
            int lineHash = 0;
            for (int i = line.length() - 1; i >= 0; i--) {
                lineHash = mul(lineHash, BASE);
                lineHash = add(lineHash, toCode(line.charAt(i)));
                if (i + word.length() <= line.length()) {
                    if (lineHash == wordHash && equal(word, line, i)) {
                        return true;
                    }
                    lineHash = sub(lineHash, mul(toCode(line.charAt(i + word.length() - 1)), pow));
                }
            }
        }
        return false;
    }
}
