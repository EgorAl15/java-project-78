package hexlet.code.schemas;

import java.util.Map;

public class MapSchema extends BaseSchema<Map<?, ?>> {

    public MapSchema required() {
        addConstraint(
                "required",
                value -> value != null
        );

        return this;
    }

    public MapSchema sizeof(int size) {
        addConstraint(
                "sizeof",
                value -> value == null || value.size() == size
        );

        return this;
    }
}