public class Main {
    public static void main(String[] args) {
        try {
            //csv datei einlesen
            PersonList allPersons = new PersonList("social_media_mental_health.csv");

            //gesamtanzahl der personen ausgeben
            System.out.println("Gesamtanzahl Personen: " + allPersons.getNumPeople());
            System.out.println();

            //aufgabe 1
            System.out.println("TEXTAUFGABE 1");

            //personen mit social comparison filtern
            PersonList withComparison = allPersons.filterBoolean(true, "socialcomparison");
            double gad7WithComparison = withComparison.getAverageGad7();
            System.out.println("GAD-7 Score (Social Comparison = true): " + gad7WithComparison);
            System.out.println("Anzahl Personen: " + withComparison.getNumPeople());

            //personen ohne social comparison filtern
            PersonList withoutComparison = allPersons.filterBoolean(false, "socialcomparison");
            double gad7WithoutComparison = withoutComparison.getAverageGad7();
            System.out.println("GAD-7 Score (Social Comparison = false): " + gad7WithoutComparison);
            System.out.println("Anzahl Personen: " + withoutComparison.getNumPeople());

            //differenz berechnen
            double differenz = gad7WithComparison - gad7WithoutComparison;
            System.out.println("Differenz: " + differenz);
            System.out.println();

            //aufgabe 3
            System.out.println("TEXTAUFGABE 3");

            //gruppe 1 = frauen zwischen 18 und 25 jahren
            //schritt 1: nach geschlecht filtern
            PersonList gruppe1_schritt1 = allPersons.filterBoolean(true, "female");
            //schritt 2: nach alter filtern
            PersonList gruppe1 = gruppe1_schritt1.filterNumeric(18, 25, "age");
            double phq9Gruppe1 = gruppe1.getAveragePhq9();
            System.out.println("Gruppe 1 (Frauen, 18-25 Jahre):");
            System.out.println("  PHQ-9 Score: " + phq9Gruppe1);
            System.out.println("  Anzahl: " + gruppe1.getNumPeople());

            //gruppe 2 = männliche personen mit bildschirmzeit > 5h
            //schritt 1: nach geschlecht filtern
            PersonList gruppe2_schritt1 = allPersons.filterBoolean(true, "male");
            //schritt 2: nach bildschirmzeit filtern
            PersonList gruppe2 = gruppe2_schritt1.filterNumeric(5.0, 24.0, "screentime");
            double phq9Gruppe2 = gruppe2.getAveragePhq9();
            System.out.println("Gruppe 2 (Männer, Bildschirmzeit > 5h):");
            System.out.println("  PHQ-9 Score: " + phq9Gruppe2);
            System.out.println("  Anzahl: " + gruppe2.getNumPeople());

            //differenz und ergebnis
            double differenzPhq9 = phq9Gruppe1 - phq9Gruppe2;
            System.out.println("Differenz: " + differenzPhq9);
            System.out.println();
            System.out.println("Ergebnis:");
            if (phq9Gruppe1 > phq9Gruppe2) {
                System.out.println("Gruppe 1 hat höhere Depressionswerte.");
            } else {
                System.out.println("Gruppe 2 hat höhere Depressionswerte.");
            }

        } catch (Exception e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }
}
