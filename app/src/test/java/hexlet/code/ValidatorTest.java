package hexlet.code;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidatorTest {

    @Test
    void testRequired() {
        var validator = new Validator();
        var schema = validator.string();

        // До required() null и пустая строка валидны
        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(""));

        schema.required();

        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(""));
        assertTrue(schema.isValid("hexlet"));
        assertTrue(schema.isValid("what does the fox say"));
    }

    @Test
    void testMinLength() {
        var validator = new Validator();
        var schema = validator.string();

        schema.minLength(5);

        // Без required() отсутствующие значения валидны
        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(""));

        assertFalse(schema.isValid("hex"));
        assertTrue(schema.isValid("hexle"));
        assertTrue(schema.isValid("hexlet"));
    }

    @Test
    void testContains() {
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
    void testRulesTogether() {
        var validator = new Validator();

        var schema = validator.string()
                .required()
                .minLength(5)
                .contains("hex");

        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(""));
        assertFalse(schema.isValid("hex"));
        assertFalse(schema.isValid("hello"));
        assertTrue(schema.isValid("hexlet"));
    }

    @Test
    void testRuleReplacement() {
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
}
