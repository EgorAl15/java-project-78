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

  protected void setRequired() {
    required = true;
  }

  public final boolean isValid(T value) {
    var requiredCheck = constraints.get("required");

    if (!requiredCheck.test(value)) {
      return !required;
    }

    return constraints.entrySet().stream()
        .filter(entry -> !entry.getKey().equals("required"))
        .allMatch(entry -> entry.getValue().test(value));
  }
}
