package analyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        String filesFolder = args[0];
        String patternsFile = args[1];

        List<Pattern> patterns = readPatterns(patternsFile);
        patterns.sort(Comparator.comparingInt(Pattern::getPriority).reversed());

        Finder finder = new RabinKarpFinder();
        ExecutorService executor = Executors.newCachedThreadPool();

        List<FindSubstringTask> tasks = Files.list(Paths.get(filesFolder))
                .map(file -> new FindSubstringTask(file.getFileName().toString(), readText(file), patterns, executor))
                .collect(Collectors.toList());

        for (Future<String> task : executor.invokeAll(tasks)) {
            System.out.println(task.get());
        }

        executor.shutdown();
    }

    private static HashedText readText(Path p) {
        try {
            return new HashedText(Files.readAllLines(p));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Pattern parsePattern(String s) {
        String[] parts = s.split(";");
        int priority = Integer.parseInt(parts[0]);
        String value = parts[1].replace("\"", "");
        String name = parts[2].replace("\"", "");
        return new Pattern(priority, value, name);
    }

    private static List<Pattern> readPatterns(String fileName) throws IOException {
        return Files.lines(Paths.get(fileName))
                .map(Main::parsePattern)
                .collect(Collectors.toList());
    }
}
