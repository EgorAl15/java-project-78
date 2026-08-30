package hexlet.code;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidatorTest {

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

        var schema = validator.number()
                .required()
                .positive()
                .range(5, 10);

        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(-5));
        assertFalse(schema.isValid(0));
        assertFalse(schema.isValid(4));

        assertTrue(schema.isValid(5));
        assertTrue(schema.isValid(10));

        assertFalse(schema.isValid(11));
    }
}