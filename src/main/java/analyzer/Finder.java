package analyzer;

import java.util.List;

public interface Finder {
    boolean contains(String text, String word);

    default boolean contains(List<String> text, String word) {
        return text.stream().anyMatch(line -> contains(line, word));
    }
}
