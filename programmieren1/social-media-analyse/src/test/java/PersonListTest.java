import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class PersonListTest {
    @Test
    void fileConstructorErrors() {
        final String expectedError = "Warning, skipping invalid line 6: Invalid CSV line: 'U-9e1ba278,18,Male,Hyper-Connected,LinkedIn,4.94,Active,1,0,5.3,13,Moderate,10,Moderate'. Expected 15 fields, got 14 instead.";
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final PrintStream ps = new PrintStream(baos);
        final PrintStream sout = System.out;
        System.setOut(ps);
        final PersonList pl = assertDoesNotThrow(() -> new PersonList("social_media_mental_health_errors.csv"));
        System.out.flush();
        System.setOut(sout);
        assertEquals(7999, pl.getNumPeople());
        if(!baos.toString().contains(expectedError)) {
            fail("Erwarteter Text \"" + expectedError + "\" nicht in Ausgabe gefunden.");
        }
    }


    @Test
    void fileConstructor() {
        final PersonList pl = assertDoesNotThrow(() -> new PersonList("social_media_mental_health.csv"));
        assertEquals(8000, pl.getNumPeople());
    }

    @Test
    void fileConstructorMissingfile() {
        final Exception e = assertThrows(Exception.class, () -> new PersonList("DOESNOTEXIST.csv"));
        assertEquals("Error reading file: DOESNOTEXIST.csv (No such file or directory)", e.getMessage());
    }

    @Test
    void emptyConstructor() {
        final PersonList pl = assertDoesNotThrow(() -> new PersonList());
    }

    @Test
    void addPerson() {
        final PersonList pl = new PersonList();
        assertDoesNotThrow(() -> pl.addPerson(new Person("U-02e9f400,18,Female,Digital Minimalist,YouTube,3.19,Gaming,Active,0,0,8.6,8,Mild,0,Mild")));
    }

    @Test
    void getNumPeople() {
        final PersonList pl = new PersonList();
        assertEquals(0, pl.getNumPeople());
        assertDoesNotThrow(() -> pl.addPerson(new Person("U-02e9f400,18,Female,Digital Minimalist,YouTube,3.19,Gaming,Active,0,0,8.6,8,Mild,0,Mild")));
        assertEquals(1, pl.getNumPeople());
    }

    @Test
    void getAverageGad7() {
        final PersonList pl = new PersonList();
        assertDoesNotThrow(() -> pl.addPerson(new Person("U-02e9f400,18,Female,Digital Minimalist,YouTube,3.19,Gaming,Active,0,0,8.6,8,Mild,0,Mild")));
        assertDoesNotThrow(() -> pl.addPerson(new Person("U-02e9f400,18,Female,Digital Minimalist,YouTube,3.19,Gaming,Active,0,0,8.6,4,Mild,0,Mild")));
        assertEquals(6, pl.getAverageGad7(), 0.01);
    }

    @Test
    void getAveragePhq9() {
        final PersonList pl = new PersonList();
        assertDoesNotThrow(() -> pl.addPerson(new Person("U-02e9f400,18,Female,Digital Minimalist,YouTube,3.19,Gaming,Active,0,0,8.6,8,Mild,0,Mild")));
        assertDoesNotThrow(() -> pl.addPerson(new Person("U-02e9f400,18,Female,Digital Minimalist,YouTube,3.19,Gaming,Active,0,0,8.6,4,Mild,10,Mild")));
        assertEquals(5, pl.getAveragePhq9(), 0.01);
    }

    @Test
    void filterNumeric_error() {
        final PersonList pl = new PersonList();
        assertDoesNotThrow(() -> pl.addPerson(new Person("U-02e9f400,18,Female,Digital Minimalist,YouTube,3.19,Gaming,Active,0,0,8.6,8,Mild,0,Mild")));
        assertDoesNotThrow(() -> pl.addPerson(new Person("U-02e9f400,18,Female,Digital Minimalist,YouTube,3.19,Gaming,Active,0,0,8.6,4,Mild,10,Mild")));
        final Exception e = assertThrows(Exception.class, () -> pl.filterNumeric(0, 10, "DOESNOTEXIST"));
        assertEquals("Unknown filter criterion 'DOESNOTEXIST'.", e.getMessage());
    }

    @Test
    void filterBoolean_error() {
        final PersonList pl = new PersonList();
        assertDoesNotThrow(() -> pl.addPerson(new Person("U-02e9f400,18,Female,Digital Minimalist,YouTube,3.19,Gaming,Active,0,0,8.6,8,Mild,0,Mild")));
        assertDoesNotThrow(() -> pl.addPerson(new Person("U-02e9f400,18,Female,Digital Minimalist,YouTube,3.19,Gaming,Active,0,0,8.6,4,Mild,10,Mild")));
        final Exception e = assertThrows(Exception.class, () -> pl.filterBoolean(false, "DOESNOTEXIST"));
        assertEquals("Unknown filter criterion 'DOESNOTEXIST'.", e.getMessage());
    }

    PersonList makeTestList() throws Exception {
        final PersonList pl = new PersonList();
        Person p1=null, p2=null, p3=null, p4=null, p5=null, p6=null, p7=null, p8=null, p9=null;
        p1 = new Person("U-b23639d2,18,Male,Hyper-Connected,Twitter/X,8.5,Gaming,Active,0,1,3.9,9,Mild,5,Mild");
        p2 = new Person("U-e7778765,19,Male,Digital Minimalist,TikTok,0.5,Gaming,Active,0,1,5.5,0,Minimal,8,Mild");
        p3 = new Person("U-76749892,20,Female,Digital Minimalist,Snapchat,0.91,Gaming,Active,0,0,8.9,1,Minimal,3,None-Minimal");
        p4 = new Person("U-dcbbd7f9,21,Female,Hyper-Connected,Snapchat,7.43,Gaming,Active,1,1,6.2,13,Moderate,0,None-Minimal");
        p5 = new Person("U-9e1ba278,22,Male,Hyper-Connected,LinkedIn,4.94,Entertainment/Comedy,Active,1,0,5.3,13,Moderate,10,Moderate");
        p6 = new Person("U-e578c94a,21,Male,Digital Minimalist,YouTube,0.56,Entertainment/Comedy,Active,0,1,6.2,0,Minimal,2,None-Minimal");
        p7 = new Person("U-a04da6a8,20,Female,Hyper-Connected,Facebook,6.57,Lifestyle/Fashion,Active,1,0,4.7,15,Severe,12,Moderate");
        p8 = new Person("U-408fc499,19,Female,Hyper-Connected,Twitter/X,7.18,Lifestyle/Fashion,Active,0,0,4.8,6,Mild,5,Mild");
        p9 = new Person("U-90ce461c,18,Male,Passive Scroller,Facebook,3.41,Lifestyle/Fashion,Passive,0,0,7.0,5,Mild,4,None-Minimal");
        pl.addPerson(p1);
        pl.addPerson(p2);
        pl.addPerson(p3);
        pl.addPerson(p4);
        pl.addPerson(p5);
        pl.addPerson(p6);
        pl.addPerson(p7);
        pl.addPerson(p8);
        pl.addPerson(p9);
        return pl;
    }

    @Test
    void filterNumeric_screentime() {
        PersonList pl;
        PersonList pl2 = null;
        try {
            pl2 = this.makeTestList();
        } catch (final Exception e) {
            fail("Fehler beim Erstellen der Testliste: " + e.getMessage());
        } finally {
            pl = pl2;
        }
        final PersonList filtered = assertDoesNotThrow(() -> pl.filterNumeric(3, 5, "screentime"));
        assertEquals(2, filtered.getNumPeople());
    }

    @Test
    void filterNumeric_gad7() {
        PersonList pl;
        PersonList pl2 = null;
        try {
            pl2 = this.makeTestList();
        } catch (final Exception e) {
            fail("Fehler beim Erstellen der Testliste: " + e.getMessage());
        } finally {
            pl = pl2;
        }
        final PersonList filtered = assertDoesNotThrow(() -> pl.filterNumeric(1, 6, "gad7"));
        assertEquals(3, filtered.getNumPeople());
    }

    @Test
    void filterNumeric_phq9() {
        PersonList pl;
        PersonList pl2 = null;
        try {
            pl2 = this.makeTestList();
        } catch (final Exception e) {
            fail("Fehler beim Erstellen der Testliste: " + e.getMessage());
        } finally {
            pl = pl2;
        }
        final PersonList filtered = assertDoesNotThrow(() -> pl.filterNumeric(5, 20, "phq9"));
        assertEquals(5, filtered.getNumPeople());
    }

    @Test
    void filterNumeric_age() {
        PersonList pl;
        PersonList pl2 = null;
        try {
            pl2 = this.makeTestList();
        } catch (final Exception e) {
            fail("Fehler beim Erstellen der Testliste: " + e.getMessage());
        } finally {
            pl = pl2;
        }
        final PersonList filtered = assertDoesNotThrow(() -> pl.filterNumeric(19, 20, "age"));
        assertEquals(4, filtered.getNumPeople());
    }

    @Test
    void filterBoolean_male() {
        PersonList pl;
        PersonList pl2 = null;
        try {
            pl2 = this.makeTestList();
        } catch (final Exception e) {
            fail("Fehler beim Erstellen der Testliste: " + e.getMessage());
        } finally {
            pl = pl2;
        }
        final PersonList filtered = assertDoesNotThrow(() -> pl.filterBoolean(true, "male"));
        assertEquals(5, filtered.getNumPeople());
    }

    @Test
    void filterBoolean_socialcomparison() {
        PersonList pl;
        PersonList pl2 = null;
        try {
            pl2 = this.makeTestList();
        } catch (final Exception e) {
            fail("Fehler beim Erstellen der Testliste: " + e.getMessage());
        } finally {
            pl = pl2;
        }
        final PersonList filtered = assertDoesNotThrow(() -> pl.filterBoolean(false, "socialcomparison"));
        assertEquals(5, filtered.getNumPeople());
    }

    @Test
    void filterBoolean_female() {
        PersonList pl;
        PersonList pl2 = null;
        try {
            pl2 = this.makeTestList();
        } catch (final Exception e) {
            fail("Fehler beim Erstellen der Testliste: " + e.getMessage());
        } finally {
            pl = pl2;
        }
        final PersonList filtered = assertDoesNotThrow(() -> pl.filterBoolean(true, "female"));
        assertEquals(4, filtered.getNumPeople());
    }
}