package annotations;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnnotationsTest {
    @Test
    public void testConvertToJson() throws Exception {
        final String EXPECTED = "{\"personName\": \"Bob Smith\",\"personAge\": \"65\"," +
                "\"personAddress\": \"123 Jerry Street\"}";
        var person = new Person("Bob Smith", 65, "123 Jerry Street");
        var serializer = new ObjectToJsonConverter();
        var result = serializer.convertToJson(person);

        assertEquals(EXPECTED, result);
    }
}