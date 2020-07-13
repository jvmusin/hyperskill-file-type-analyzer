package analyzer;

public class Strings {
    public static int[] prefixFunction(String s) {
        int n = s.length();
        int[] pi = new int[n];
        for (int i = 1, j = 0; i < s.length(); i++) {
            while (j > 0 && s.charAt(i) != s.charAt(j)) {
                j = pi[j - 1];
            }
            if (s.charAt(i) == s.charAt(j)) {
                j++;
            }
            pi[i] = j;
        }
        return pi;
    }

    public static boolean contains(String text, String word) {
        int[] pi = prefixFunction(word);
        for (int i = 0, j = 0; i < text.length(); i++) {
            while (j > 0 && word.charAt(j) != text.charAt(i)) {
                j = pi[j - 1];
            }
            if (word.charAt(j) == text.charAt(i)) {
                j++;
            }
            if (j == word.length()) {
                return true;
            }
        }
        return false;
    }
}
