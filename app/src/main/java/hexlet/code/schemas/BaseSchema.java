package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public abstract class BaseSchema<T> {

    private final Map<String, Predicate<T>> constraints = new HashMap<>();
    private boolean required;

    protected void addConstraint(String name, Predicate<T> constraint) {
        constraints.put(name, constraint);
    }

    protected void setRequired(boolean value) {
        required = value;
    }

    public boolean isValid(T value) {
        if (value == null) {
            return !required;
        }

        return constraints.values().stream()
                .allMatch(constraint -> constraint.test(value));
    }
}