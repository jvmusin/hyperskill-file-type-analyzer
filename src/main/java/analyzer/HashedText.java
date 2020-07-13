package analyzer;

import java.util.List;
import java.util.stream.Collectors;

public class HashedText {
    private final List<Hash> hashes;

    public HashedText(List<String> text) {
        this.hashes = text.stream().map(Hash::new).collect(Collectors.toList());
    }

    public boolean contains(Pattern p) {
        return hashes.stream().anyMatch(h -> h.contains(p.getHash()));
    }
}
