package analyzer;

public class KmpFinder implements Finder {
    @Override
    public boolean contains(String text, String word) {
        return Strings.contains(text, word);
    }
}
