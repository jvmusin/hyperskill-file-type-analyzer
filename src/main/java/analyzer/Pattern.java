package analyzer;

public class Pattern {
    private final int priority;
    private final String value;
    private final String name;
    private final Hash hash;

    public Pattern(int priority, String value, String name) {
        this.priority = priority;
        this.value = value;
        this.name = name;
        this.hash = new Hash(value);
    }

    public int getPriority() {
        return priority;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public Hash getHash() {
        return hash;
    }
}
