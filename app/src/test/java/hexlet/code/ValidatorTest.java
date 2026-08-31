package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import hexlet.code.schemas.BaseSchema;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ValidatorTest {

  @Test
  void testStringRequired() {
    var validator = new Validator();
    var schema = validator.string();

    assertTrue(schema.isValid(null));
    assertTrue(schema.isValid(""));

    schema.required();

    assertFalse(schema.isValid(null));
    assertFalse(schema.isValid(""));
    assertTrue(schema.isValid("hexlet"));
    assertTrue(schema.isValid("what does the fox say"));
  }

  @Test
  void testStringMinLength() {
    var validator = new Validator();
    var schema = validator.string();

    schema.minLength(5);

    assertTrue(schema.isValid(null));
    assertTrue(schema.isValid(""));

    assertFalse(schema.isValid("hex"));
    assertTrue(schema.isValid("hexle"));
    assertTrue(schema.isValid("hexlet"));
  }

  @Test
  void testStringContains() {
    var validator = new Validator();
    var schema = validator.string();

    schema.contains("hex");

    assertTrue(schema.isValid(null));
    assertTrue(schema.isValid(""));
    assertTrue(schema.isValid("hexlet"));
    assertTrue(schema.isValid("I love hexlet"));
    assertFalse(schema.isValid("hello"));
  }

  @Test
  void testStringRulesTogether() {
    var validator = new Validator();

    var schema = validator.string().required().minLength(5).contains("hex");

    assertFalse(schema.isValid(null));
    assertFalse(schema.isValid(""));
    assertFalse(schema.isValid("hex"));
    assertFalse(schema.isValid("hello"));
    assertTrue(schema.isValid("hexlet"));
  }

  @Test
  void testStringRuleReplacement() {
    var validator = new Validator();

    var schema = validator.string();

    schema.contains("wh");
    assertTrue(schema.isValid("what does the fox say"));

    schema.contains("what");
    assertTrue(schema.isValid("what does the fox say"));

    schema.contains("whatthe");
    assertFalse(schema.isValid("what does the fox say"));

    var schema2 = validator.string();

    schema2.minLength(10).minLength(4);

    assertTrue(schema2.isValid("Hexlet"));
  }

  @Test
  void testNumberSchema() {
    var validator = new Validator();
    var schema = validator.number();

    assertTrue(schema.isValid(5));
    assertTrue(schema.isValid(null));

    schema.positive();

    assertTrue(schema.isValid(null));
    assertTrue(schema.isValid(10));
    assertFalse(schema.isValid(-10));
    assertFalse(schema.isValid(0));

    schema.required();

    assertFalse(schema.isValid(null));
    assertTrue(schema.isValid(10));
  }

  @Test
  void testNumberRange() {
    var validator = new Validator();
    var schema = validator.number();

    schema.range(5, 10);

    assertTrue(schema.isValid(5));
    assertTrue(schema.isValid(10));
    assertTrue(schema.isValid(7));

    assertFalse(schema.isValid(4));
    assertFalse(schema.isValid(11));
  }

  @Test
  void testNumberRangeReplacement() {
    var validator = new Validator();
    var schema = validator.number();

    schema.range(5, 10);

    assertTrue(schema.isValid(5));
    assertTrue(schema.isValid(10));

    schema.range(6, 9);

    assertFalse(schema.isValid(5));
    assertFalse(schema.isValid(10));
    assertTrue(schema.isValid(6));
    assertTrue(schema.isValid(9));
  }

  @Test
  void testNumberRulesTogether() {
    var validator = new Validator();

    var schema = validator.number().required().positive().range(5, 10);

    assertFalse(schema.isValid(null));
    assertFalse(schema.isValid(-5));
    assertFalse(schema.isValid(0));
    assertFalse(schema.isValid(4));

    assertTrue(schema.isValid(5));
    assertTrue(schema.isValid(10));

    assertFalse(schema.isValid(11));
  }

  @Test
  void testMapSchema() {
    var validator = new Validator();
    var schema = validator.map();

    assertTrue(schema.isValid(null));

    schema.required();

    assertFalse(schema.isValid(null));
    assertTrue(schema.isValid(new HashMap<>()));

    var data = new HashMap<String, String>();
    data.put("key1", "value1");

    assertTrue(schema.isValid(data));
  }

  @Test
  void testMapSizeof() {
    var validator = new Validator();
    var schema = validator.map();

    var data = new HashMap<String, String>();
    data.put("key1", "value1");

    schema.sizeof(2);

    assertFalse(schema.isValid(data));

    data.put("key2", "value2");

    assertTrue(schema.isValid(data));
  }

  @Test
  void testMapSizeofReplacement() {
    var validator = new Validator();
    var schema = validator.map();

    var data = new HashMap<String, String>();
    data.put("key1", "value1");
    data.put("key2", "value2");

    schema.sizeof(3);
    assertFalse(schema.isValid(data));

    schema.sizeof(2);
    assertTrue(schema.isValid(data));
  }

  @Test
  void testMapSizeofWithoutRequired() {
    var validator = new Validator();
    var schema = validator.map();

    schema.sizeof(2);

    assertTrue(schema.isValid(null));
  }

  @Test
  void testMapShape() {
    var validator = new Validator();
    var schema = validator.map();

    Map<String, BaseSchema<String>> schemas = new HashMap<>();

    schemas.put("firstName", validator.string().required());

    schemas.put("lastName", validator.string().required().minLength(2));

    schema.shape(schemas);

    Map<String, String> human1 = new HashMap<>();
    human1.put("firstName", "John");
    human1.put("lastName", "Smith");

    assertTrue(schema.isValid(human1));

    Map<String, String> human2 = new HashMap<>();
    human2.put("firstName", "John");
    human2.put("lastName", null);

    assertFalse(schema.isValid(human2));

    Map<String, String> human3 = new HashMap<>();
    human3.put("firstName", "Anna");
    human3.put("lastName", "B");

    assertFalse(schema.isValid(human3));
  }

  @Test
  void testMapShapeWithAdditionalConstraints() {
    var validator = new Validator();
    var schema = validator.map();

    Map<String, BaseSchema<String>> schemas = new HashMap<>();

    schemas.put("firstName", validator.string().required().minLength(3).contains("oh"));

    schemas.put("lastName", validator.string().required().minLength(2));

    schema.shape(schemas);

    Map<String, String> validHuman = new HashMap<>();
    validHuman.put("firstName", "John");
    validHuman.put("lastName", "Smith");

    assertTrue(schema.isValid(validHuman));

    Map<String, String> invalidHuman = new HashMap<>();
    invalidHuman.put("firstName", "Jack");
    invalidHuman.put("lastName", "Smith");

    assertFalse(schema.isValid(invalidHuman));
  }

  @Test
  void testMapShapeWithMissingKey() {
    var validator = new Validator();
    var schema = validator.map();

    Map<String, BaseSchema<String>> schemas = new HashMap<>();

    schemas.put("firstName", validator.string().required());
    schemas.put("lastName", validator.string().required());

    schema.shape(schemas);

    Map<String, String> human = new HashMap<>();
    human.put("firstName", "John");

    assertFalse(schema.isValid(human));
  }
}
