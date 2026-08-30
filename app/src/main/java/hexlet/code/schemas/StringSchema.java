package hexlet.code.schemas;

public class StringSchema extends BaseSchema<String> {

    public StringSchema required() {
        setRequired(true);

        addConstraint(
                "required",
                value -> !value.isEmpty()
        );

        return this;
    }

    public StringSchema minLength(int length) {
        addConstraint(
                "minLength",
                value -> value.isEmpty() || value.length() >= length
        );

        return this;
    }

    public StringSchema contains(String substring) {
        addConstraint(
                "contains",
                value -> value.isEmpty() || value.contains(substring)
        );

        return this;
    }
}