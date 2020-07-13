package analyzer;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class FindSubstringTask implements Callable<String> {
    private static final String unknownFormatName = "Unknown file type";
    private final String fileName;
    private final HashedText text;
    private final List<Pattern> patterns;
    private final ExecutorService executor;

    public FindSubstringTask(String fileName, HashedText text, List<Pattern> patterns, ExecutorService executor) {
        this.fileName = fileName;
        this.text = text;
        this.patterns = patterns;
        this.executor = executor;
    }

    @Override
    public String call() throws Exception {
        List<Callable<Pattern>> tasks = patterns.stream()
                .map(pattern -> (Callable<Pattern>) () -> text.contains(pattern) ? pattern : null)
                .collect(Collectors.toList());
        List<Future<Pattern>> results = executor.invokeAll(tasks);
        for (Future<Pattern> result : results) {
            Pattern pattern = result.get();
            if (pattern != null) {
                return String.format("%s: %s", fileName, pattern.getName());
            }
        }
        return String.format("%s: %s", fileName, unknownFormatName);
    }
}