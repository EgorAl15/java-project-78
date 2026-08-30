package hexlet.code.schemas;

public class NumberSchema extends BaseSchema<Integer> {

    public NumberSchema required() {
        addConstraint(
                "required",
                value -> value != null
        );

        return this;
    }

    public NumberSchema positive() {
        addConstraint(
                "positive",
                value -> value == null || value > 0
        );

        return this;
    }

    public NumberSchema range(int min, int max) {
        addConstraint(
                "range",
                value -> value == null
                        || (value >= min && value <= max)
        );

        return this;
    }
}