package hexlet.code.schemas;

public class StringSchema extends BaseSchema<String> {

    public StringSchema required() {
        addConstraint(
                "required",
                value -> value != null && !value.isEmpty()
        );

        return this;
    }

    public StringSchema minLength(int length) {
        addConstraint(
                "minLength",
                value -> value == null
                        || value.isEmpty()
                        || value.length() >= length
        );

        return this;
    }

    public StringSchema contains(String substring) {
        addConstraint(
                "contains",
                value -> value == null
                        || value.isEmpty()
                        || value.contains(substring)
        );

        return this;
    }
}