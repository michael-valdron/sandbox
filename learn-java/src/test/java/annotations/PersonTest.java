package annotations;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {
    private Person person;

    @BeforeEach
    void setUp() {
        person = new Person("Bob Smith", 18, "345 Somebody Road");
    }

    @AfterEach
    void tearDown() {
        person = null;
    }

    @Test
    void testGetPersonName() {
        final String EXPECTED = "Bob Smith";
        var result = person.getPersonName();

        assertEquals(EXPECTED, result);
    }

    @Test
    void testSetPersonName() {
        final String EXPECTED = "Jerry Smith";
        String result;

        person.setPersonName(EXPECTED);
        result = person.getPersonName();

        assertEquals(EXPECTED, result);
    }

    @Test
    void testGetAge() {
        final int EXPECTED = 18;
        var result = person.getAge();

        assertEquals(EXPECTED, result);
    }

    @Test
    void testSetAge() {
        final int EXPECTED = 64;
        int result;

        person.setAge(EXPECTED);
        result = person.getAge();

        assertEquals(EXPECTED, result);
    }

    @Test
    void testGetAddress() {
        final String EXPECTED = "345 Somebody Road";
        var result = person.getAddress();

        assertEquals(EXPECTED, result);
    }

    @Test
    void testSetAddress() {
        final String EXPECTED = "123 Jerry Lane";
        String result;

        person.setAddress(EXPECTED);
        result = person.getAddress();

        assertEquals(EXPECTED, result);
    }
}