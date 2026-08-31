package hexlet.code.schemas;

import java.util.Map;

public class MapSchema extends BaseSchema<Map<?, ?>> {

  public MapSchema() {
    addConstraint("required", value -> value != null);
  }

  public MapSchema required() {
    setRequired();
    return this;
  }

  public MapSchema sizeof(int size) {
    addConstraint("sizeof", value -> value.size() == size);

    return this;
  }

  public MapSchema shape(Map<String, BaseSchema<String>> schemas) {
    addConstraint(
        "shape",
        value -> {
          for (var entry : schemas.entrySet()) {
            var key = entry.getKey();
            var schema = entry.getValue();

            Object fieldValue = value.get(key);

            if (!(fieldValue == null || fieldValue instanceof String)) {
              return false;
            }

            if (!schema.isValid((String) fieldValue)) {
              return false;
            }
          }

          return true;
        });

    return this;
  }
}
