package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public abstract class BaseSchema<T> {

    private final Map<String, Predicate<T>> constraints = new HashMap<>();

    protected void addConstraint(String name, Predicate<T> constraint) {
        constraints.put(name, constraint);
    }

    public boolean isValid(T value) {
        return constraints.values().stream()
                .allMatch(constraint -> constraint.test(value));
    }
}