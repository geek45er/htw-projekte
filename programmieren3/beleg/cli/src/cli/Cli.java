package cli;

import domainLogic.Kuchensorte;
import domainLogic.events.AllergeneAnzeigenEvent;
import domainLogic.events.Eingabequellen;
import domainLogic.events.Ereignisquelle;
import domainLogic.events.HerstellerAnzeigenEvent;
import domainLogic.events.HerstellerEinfuegenEvent;
import domainLogic.events.HerstellerLoeschenEvent;
import domainLogic.events.InspektionSetzenEvent;
import domainLogic.events.KuchenAnzeigenEvent;
import domainLogic.events.KuchenEinfuegenEvent;
import domainLogic.events.KuchenEntfernenEvent;
import kuchen.Allergen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.EnumSet;
import java.util.Set;


// zustandsbasiertes cli: liest zeilen, parst sie je nach modus und feuert events richtung gl
public class Cli {
    static final String STANDARD_OBSTSORTE = "Apfel";
    static final String STANDARD_KREMSORTE = "Butter";

    private enum Modus { KEINER, EINFUEGEN, ANZEIGEN, AENDERN, LOESCHEN }

    private final BufferedReader eingabe;
    private final PrintStream ausgabe;
    private final Eingabequellen quellen;
    private Modus modus = Modus.KEINER;
    private boolean beendet;

    public Cli(InputStream in, PrintStream out, Eingabequellen quellen) {
        if (in == null || out == null || quellen == null) {
            throw new IllegalArgumentException("eingabe, ausgabe oder quellen fehlt");
        }
        this.eingabe = new BufferedReader(new InputStreamReader(in));
        this.ausgabe = out;
        this.quellen = quellen;
    }

    // laeuft bis :x oder ende der eingabe
    public void starten() {
        try {
            String zeile;
            while (!beendet && (zeile = eingabe.readLine()) != null) {
                verarbeiteZeile(zeile);
            }
        } catch (IOException e) {
            ausgabe.println("Fehler beim Lesen der Eingabe: " + e.getMessage());
        }
    }

    void verarbeiteZeile(String zeile) {
        if (zeile.isBlank()) {
            fehler("leere Eingabe");
            return;
        }
        String[] tokens = zeile.trim().split("\\s+");
        if (tokens[0].startsWith(":")) {
            modusWechseln(tokens);
            return;
        }
        switch (modus) {
            case EINFUEGEN -> einfuegen(tokens);
            case ANZEIGEN -> anzeigen(tokens);
            case AENDERN -> aendern(tokens);
            case LOESCHEN -> loeschen(tokens);
            case KEINER -> fehler("kein Modus gewählt, zuerst :c, :r, :u oder :d eingeben");
        }
    }

    private void modusWechseln(String[] tokens) {
        if (tokens.length != 1) {
            fehler("Moduswechsel erwartet keine weiteren Angaben");
            return;
        }
        switch (tokens[0]) {
            case ":c" -> setzeModus(Modus.EINFUEGEN, "Einfügemodus");
            case ":r" -> setzeModus(Modus.ANZEIGEN, "Anzeigemodus");
            case ":u" -> setzeModus(Modus.AENDERN, "Änderungsmodus");
            case ":d" -> setzeModus(Modus.LOESCHEN, "Löschmodus");
            case ":p" -> ausgabe.println("Persistenzmodus ist nicht implementiert");
            case ":x" -> {
                beendet = true;
                ausgabe.println("Programm beendet");
            }
            default -> fehler("unbekannter Befehl: " + tokens[0]);
        }
    }

    private void setzeModus(Modus neu, String name) {
        modus = neu;
        ausgabe.println(name);
    }

    private void einfuegen(String[] tokens) {
        if (tokens.length == 1) {
            feuern(quellen.herstellerEinfuegen(), new HerstellerEinfuegenEvent(tokens[0]));
            return;
        }
        // QUELLE START: gemini 3.5 flash lite 11.11
        Kuchensorte sorte = sorte(tokens[0]);
        if (sorte == null) {
            fehler("unbekannte Kuchensorte: " + tokens[0]);
            return;
        }
        int maximum = sorte == Kuchensorte.OBSTTORTE ? 8 : 7;
        if (tokens.length < 6 || tokens.length > maximum) {
            fehler("falsche Anzahl an Angaben, erwartet: Kuchensorte H-Name Preis Nährwert Haltbarkeit Allergene"
                    + " [Obstsorte] [Kremsorte]");
            return;
        }
        BigDecimal preis = preis(tokens[2]);
        Integer naehrwert = zahl(tokens[3]);
        Integer stunden = zahl(tokens[4]);
        Set<Allergen> allergene = allergene(tokens[5]);
        if (preis == null) {
            fehler("ungültiger Preis: " + tokens[2] + " (Beispiel: 4,50)");
        } else if (naehrwert == null) {
            fehler("ungültiger Nährwert: " + tokens[3]);
        } else if (stunden == null) {
            fehler("ungültige Haltbarkeit in Stunden: " + tokens[4]);
        } else if (allergene == null) {
            fehler("ungültige Allergene: " + tokens[5] + " (kommasepariert aus Gluten, Erdnuss, Haselnuss,"
                    + " Sesamsamen oder , für keine)");
        } else {
            String obstsorte = sorte == Kuchensorte.KREMKUCHEN ? null : optional(tokens, 6, STANDARD_OBSTSORTE);
            String kremsorte = switch (sorte) {
                case OBSTKUCHEN -> null;
                case KREMKUCHEN -> optional(tokens, 6, STANDARD_KREMSORTE);
                case OBSTTORTE -> optional(tokens, 7, STANDARD_KREMSORTE);
            };
            feuern(quellen.kuchenEinfuegen(), new KuchenEinfuegenEvent(sorte, tokens[1], preis, naehrwert,
                    Duration.ofHours(stunden), allergene, obstsorte, kremsorte));
        }
        // QUELLE ENDE
    }

    private void anzeigen(String[] tokens) {
        switch (tokens[0]) {
            case "hersteller" -> {
                if (tokens.length != 1) {
                    fehler("hersteller erwartet keine weiteren Angaben");
                } else {
                    feuern(quellen.herstellerAnzeigen(), new HerstellerAnzeigenEvent());
                }
            }
            case "kuchen" -> {
                if (tokens.length == 1) {
                    feuern(quellen.kuchenAnzeigen(), new KuchenAnzeigenEvent(null));
                } else if (tokens.length == 2 && sorte(tokens[1]) != null) {
                    feuern(quellen.kuchenAnzeigen(), new KuchenAnzeigenEvent(sorte(tokens[1])));
                } else {
                    fehler("erwartet: kuchen [Kuchensorte]");
                }
            }
            case "allergene" -> {
                if (tokens.length == 2 && (tokens[1].equals("i") || tokens[1].equals("e"))) {
                    feuern(quellen.allergeneAnzeigen(), new AllergeneAnzeigenEvent(tokens[1].equals("i")));
                } else {
                    fehler("erwartet: allergene i oder allergene e");
                }
            }
            default -> fehler("unbekannter Befehl im Anzeigemodus: " + tokens[0]);
        }
    }

    private void aendern(String[] tokens) {
        Integer fach = tokens.length == 1 ? zahl(tokens[0]) : null;
        if (fach == null) {
            fehler("erwartet: Fachnummer");
            return;
        }
        feuern(quellen.inspektionSetzen(), new InspektionSetzenEvent(fach));
    }

    private void loeschen(String[] tokens) {
        if (tokens.length != 1) {
            fehler("erwartet: H-Name oder Fachnummer");
            return;
        }
        Integer fach = zahl(tokens[0]);
        if (fach == null) {
            feuern(quellen.herstellerLoeschen(), new HerstellerLoeschenEvent(tokens[0]));
        } else {
            feuern(quellen.kuchenEntfernen(), new KuchenEntfernenEvent(fach));
        }
    }

    // ohne angemeldeten handler ist der befehl in dieser konfiguration deaktiviert
    private <E> void feuern(Ereignisquelle<E> quelle, E ereignis) {
        if (!quelle.hatBehandler()) {
            fehler("Befehl ist in dieser Konfiguration nicht verfügbar");
            return;
        }
        quelle.feuern(ereignis);
    }

    private void fehler(String meldung) {
        ausgabe.println("Fehler: " + meldung);
    }

    private static Kuchensorte sorte(String token) {
        for (Kuchensorte s : Kuchensorte.values()) {
            if (s.name().equalsIgnoreCase(token)) {
                return s;
            }
        }
        return null;
    }

    // QUELLE START: gemini 3.5 flash lite 11.11
    private static BigDecimal preis(String token) {
        if (!token.matches("\\d+(,\\d{1,2})?")) {
            return null;
        }
        return new BigDecimal(token.replace(',', '.'));
    }
    // QUELLE ENDE

    private static Integer zahl(String token) {
        try {
            return Integer.parseInt(token);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // einzelnes komma heisst keine allergene
    // QUELLE START: gemini 3.5 flash lite 11.11
    private static Set<Allergen> allergene(String token) {
        EnumSet<Allergen> ergebnis = EnumSet.noneOf(Allergen.class);
        if (token.equals(",")) {
            return ergebnis;
        }
        for (String teil : token.split(",", -1)) {
            Allergen a = allergen(teil);
            if (a == null) {
                return null;
            }
            ergebnis.add(a);
        }
        return ergebnis;
    }
    // QUELLE ENDE

    private static Allergen allergen(String token) {
        for (Allergen a : Allergen.values()) {
            if (a.name().equalsIgnoreCase(token)) {
                return a;
            }
        }
        return null;
    }

    private static String optional(String[] tokens, int index, String standard) {
        return tokens.length > index ? tokens[index] : standard;
    }
}