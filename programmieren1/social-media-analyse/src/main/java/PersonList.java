import java.io.BufferedReader; //textdateien lesen
import java.io.FileReader; //dateien öffnen
import java.io.IOException; //für fehlerbehandlung
import java.util.ArrayList; //dynamische arrays
import java.util.List;  //interafce List für Listen

public class PersonList {
    private List<Person> personen;  //liste aller personen

    //constructor der csv datei einliest
    public PersonList(String filename) throws Exception {
        this.personen = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean firstLine = true;
            int lineNumber = 0;  //zeilennummer mitzählen

            while ((line = br.readLine()) != null) {
                lineNumber++;  //zeilennummer erhöhen

                if (firstLine) {  //erste zeile überspringen (header)
                    firstLine = false;
                    continue;
                }
                if (line.trim().isEmpty()) {  //leere zeilen ignorieren
                    continue;
                }
                try {
                    Person person = new Person(line);
                    this.personen.add(person);  //person zur liste hinzufügen
                } catch (Exception e) {
                    //zeilennummer und komplette fehlermeldung ausgeben
                    System.out.println("Warning, skipping invalid line " + lineNumber + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new Exception("Error reading file: " + e.getMessage());
        }
    }

    //leerer constructor
    public PersonList() {
        this.personen = new ArrayList<>();
    }

    //fügt person zur liste hinzu
    public void addPerson(Person p) {
        this.personen.add(p);
    }

    //gibt anzahl der personen zurück
    public int getNumPeople() {
        return this.personen.size();
    }

    //berechnet durchschnittlichen gad7 score
    public double getAverageGad7() {
        if (this.personen.isEmpty()) {  //keine division durch 0
            return 0.0;
        }
        double sum = 0.0;
        for (Person person : this.personen) {  //alle gad7 werte zusammenzählen
            sum += person.getGad7();
        }
        return sum / this.personen.size();  //durchschnitt berechnen
    }

    //berechnet durchschnittlichen phq9 score
    public double getAveragePhq9() {
        if (this.personen.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (Person person : this.personen) {  //alle phq9 werte addieren
            sum += person.getPhq9();
        }
        return sum / this.personen.size();
    }

    //filtert personen nach numerischen kriterien
    public PersonList filterNumeric(double minValue, double maxValue, String key) throws Exception {
        PersonList filtered = new PersonList();  //neue liste für gefilterte personen

        for (Person person : this.personen) {
            double value;

            //je nach key den richtigen wert holen
            switch (key) {
                case "age":
                    value = person.getAge();
                    break;
                case "screentime":
                    value = person.getScreentime();
                    break;
                case "gad7":
                    value = person.getGad7();
                    break;
                case "phq9":
                    value = person.getPhq9();
                    break;
                default:  //ungültiger key
                    throw new Exception("Unknown filter criterion '" + key + "'.");
            }

            //prüft ob wert im bereich liegt
            if (value >= minValue && value <= maxValue) {
                filtered.addPerson(person);
            }
        }

        return filtered;
    }

    //filtert personen nach boolean kriterien
    public PersonList filterBoolean(boolean value, String key) throws Exception {
        PersonList filtered = new PersonList();

        for (Person person : this.personen) {
            boolean personValue;

            //richtigen boolean wert abhängig von key holen
            switch (key) {
                case "female":
                    personValue = person.isFemale();
                    break;
                case "male":
                    personValue = person.isMale();
                    break;
                case "socialcomparison":
                    personValue = person.promotesSocialcomparison();
                    break;
                default:
                    throw new Exception("Unknown filter criterion '" + key + "'.");
            }

            //nur hinzufügen wenn werte übereinstimmen
            if (personValue == value) {
                filtered.addPerson(person);
            }
        }

        return filtered;
    }
}