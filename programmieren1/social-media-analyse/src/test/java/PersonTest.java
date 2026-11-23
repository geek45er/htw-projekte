import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @Test
    void constructor_withException() {
        final Exception e = assertThrows(Exception.class, () -> new Person("U-02e9f400,18,Female,Digital Minimalist,YouTube,3.19,Gaming,Active,0,0,8.6,8,Mild,0"));
        assertEquals("Invalid CSV line: 'U-02e9f400,18,Female,Digital Minimalist,YouTube,3.19,Gaming,Active,0,0,8.6,8,Mild,0'. Expected 15 fields, got 14 instead.", e.getMessage());
    }

    @Test
    void constructor() {
        assertDoesNotThrow(() -> new Person("U-02e9f400,18,Female,Digital Minimalist,YouTube,3.19,Gaming,Active,0,0,8.6,8,Mild,0,Mild"));
    }

    @Test
    void getAge() {
        final Person p = assertDoesNotThrow(() -> new Person("U-02e9f400,18,Female,Digital Minimalist,YouTube,3.19,Gaming,Active,0,0,8.6,8,Mild,0,Mild"));
        assertEquals(18, p.getAge());
    }

    @Test
    void getPlatform() {
        final Person p = assertDoesNotThrow(() -> new Person("U-02e9f400,18,Female,Digital Minimalist,YouTube,3.19,Gaming,Active,0,0,8.6,8,Mild,0,Mild"));
        assertEquals("YouTube", p.getPlatform());
    }

    @Test
    void getScreentime() {
        final Person p = assertDoesNotThrow(() -> new Person("U-02e9f400,18,Female,Digital Minimalist,YouTube,3.19,Gaming,Active,0,0,8.6,8,Mild,0,Mild"));
        assertEquals(3.19, p.getScreentime());
    }

    @Test
    void getGad7() {
        final Person p = assertDoesNotThrow(() -> new Person("U-02e9f400,18,Female,Digital Minimalist,YouTube,3.19,Gaming,Active,0,0,8.6,8,Mild,0,Mild"));
        assertEquals(8, p.getGad7());
    }

    @Test
    void getPhq9() {
        final Person p = assertDoesNotThrow(() -> new Person("U-02e9f400,18,Female,Digital Minimalist,YouTube,3.19,Gaming,Active,0,0,8.6,8,Mild,0,Mild"));
        assertEquals(0, p.getPhq9());
    }

    @Test
    void isFemale() {
        Person p = assertDoesNotThrow(() -> new Person("U-02e9f400,18,Female,Digital Minimalist,YouTube,3.19,Gaming,Active,0,0,8.6,8,Mild,0,Mild"));
        assertTrue(p.isFemale());
        p = assertDoesNotThrow(() -> new Person("U-02e9f400,18,Male,Digital Minimalist,YouTube,3.19,Gaming,Active,0,0,8.6,8,Mild,0,Mild"));
        assertFalse(p.isFemale());
    }

    @Test
    void isMale() {
        Person p = assertDoesNotThrow(() -> new Person("U-02e9f400,18,Female,Digital Minimalist,YouTube,3.19,Gaming,Active,0,0,8.6,8,Mild,0,Mild"));
        assertFalse(p.isMale());
        p = assertDoesNotThrow(() -> new Person("U-02e9f400,18,Male,Digital Minimalist,YouTube,3.19,Gaming,Active,0,0,8.6,8,Mild,0,Mild"));
        assertTrue(p.isMale());
    }

    @Test
    void promotesSocialcomparison() {
        Person p = assertDoesNotThrow(() -> new Person("U-02e9f400,18,Female,Digital Minimalist,YouTube,3.19,Gaming,Active,0,0,8.6,8,Mild,0,Mild"));
        assertFalse(p.promotesSocialcomparison());
        p = assertDoesNotThrow(() -> new Person("U-02e9f400,18,Female,Digital Minimalist,YouTube,3.19,Gaming,Active,0,1,8.6,8,Mild,0,Mild"));
        assertTrue(p.promotesSocialcomparison());
    }
}