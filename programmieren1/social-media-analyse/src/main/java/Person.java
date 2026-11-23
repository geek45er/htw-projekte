public class Person {

    //attribute für die person
    private final int alter;
    private final String geschlecht;
    private final String platform;
    private final double bildschirmzeit;
    private final boolean triggersComparison;  //ob content vergleiche auslöst
    private final double gad7;  //angst score
    private final double phq9;  //depression score

    //constructor der csv zeile parst
    public Person(final String csvline) throws Exception {
        final String[] felder = csvline.split(",");  //an kommas aufteilen

        //prüfen ob genau 15 felder vorhanden sind
        if (15 != felder.length) {
            throw new Exception("Invalid CSV line: '" + csvline +
                    "'. Expected 15 fields, got " + felder.length + " instead.");
        }

        //werte aus den entsprechenden spalten extrahieren
        alter = Integer.parseInt(felder[1].trim());
        geschlecht = felder[2].trim();
        platform = felder[4].trim();
        bildschirmzeit = Double.parseDouble(felder[5].trim());
        triggersComparison = "1".equals(felder[9].trim());  //1 bedeutet ja
        gad7 = Double.parseDouble(felder[11].trim());
        phq9 = Double.parseDouble(felder[13].trim());
    }
    //getter methoden
    public String getPlatform() {
        return this.platform;
    }

    public double getScreentime() {
        return this.bildschirmzeit;
    }

    public double getGad7() {
        return this.gad7;
    }

    public double getPhq9() {
        return this.phq9;
    }

    public boolean promotesSocialcomparison() {
        return this.triggersComparison;
    }

    public int getAge() {
        return this.alter;
    }

    public boolean isMale() {
        return "Male".equals(geschlecht);
    }

    public boolean isFemale() {
        return "Female".equals(geschlecht);
    }
}