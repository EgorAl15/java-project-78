package hexlet.code.schemas;

public class StringSchema extends BaseSchema<String> {

  public StringSchema() {
    addConstraint("required", value -> value != null && !value.isEmpty());
  }

  public StringSchema required() {
    setRequired();
    return this;
  }

  public StringSchema minLength(int length) {
    addConstraint("minLength", value -> value.length() >= length);

    return this;
  }

  public StringSchema contains(String substring) {
    addConstraint("contains", value -> value.contains(substring));

    return this;
  }
}
