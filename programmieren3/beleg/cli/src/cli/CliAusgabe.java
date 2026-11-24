package cli;

import domainLogic.Fehlercode;
import domainLogic.Kuchensorte;
import domainLogic.events.AllergenListeEvent;
import domainLogic.events.FehlerEvent;
import domainLogic.events.HerstellerDaten;
import domainLogic.events.HerstellerGeaendertEvent;
import domainLogic.events.HerstellerListeEvent;
import domainLogic.events.InspektionGesetztEvent;
import domainLogic.events.KuchenDaten;
import domainLogic.events.KuchenEingefuegtEvent;
import domainLogic.events.KuchenEntferntEvent;
import domainLogic.events.KuchenListeEvent;
import kuchen.Allergen;

import java.io.PrintStream;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


// gibt die events der gl auf der konsole aus
public class CliAusgabe {
    private static final DateTimeFormatter DATUM =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss").withZone(ZoneId.systemDefault());

    private final PrintStream out;

    public CliAusgabe(PrintStream out) {
        if (out == null) {
            throw new IllegalArgumentException("ausgabe fehlt");
        }
        this.out = out;
    }

    public void fehler(FehlerEvent e) {
        out.println("Fehler: " + text(e.code()));
    }

    public void herstellerGeaendert(HerstellerGeaendertEvent e) {
        out.println("Hersteller*in " + e.name() + (e.eingefuegt() ? " eingefügt" : " gelöscht"));
    }

    public void kuchenEingefuegt(KuchenEingefuegtEvent e) {
        out.println(sorte(e.sorte()) + " eingefügt");
    }

    public void kuchenEntfernt(KuchenEntferntEvent e) {
        out.println("Kuchen aus Fach " + e.fachnummer() + " entfernt");
    }

    public void inspektionGesetzt(InspektionGesetztEvent e) {
        out.println("Inspektionsdatum für Fach " + e.fachnummer() + " gesetzt");
    }

    public void herstellerListe(HerstellerListeEvent e) {
        if (e.hersteller().isEmpty()) {
            out.println("keine Hersteller*innen vorhanden");
        }
        for (HerstellerDaten h : e.hersteller()) {
            out.println(h.name() + ": " + h.anzahlKuchen() + " Kuchen");
        }
    }

    // QUELLE START: gemini 3.5 flash lite 11.11
    public void kuchenListe(KuchenListeEvent e) {
        if (e.kuchen().isEmpty()) {
            out.println("keine Kuchen vorhanden");
        }
        for (KuchenDaten k : e.kuchen()) {
            out.println("Fach " + k.fachnummer() + ": " + sorte(k.sorte()) + " von " + k.hersteller()
                    + ", Inspektion " + DATUM.format(k.inspektionsdatum())
                    + ", Haltbarkeit " + k.haltbarkeit().toSeconds() + " s");
        }
    }
    // QUELLE ENDE

    public void allergenListe(AllergenListeEvent e) {
        out.println((e.enthalten() ? "vorhandene Allergene: " : "nicht vorhandene Allergene: ")
                + allergene(e.allergene()));
    }

    static String allergene(Collection<Allergen> allergene) {
        if (allergene.isEmpty()) {
            return "keine";
        }
        List<String> namen = new ArrayList<>();
        for (Allergen a : allergene) {
            namen.add(a.name());
        }
        return String.join(", ", namen);
    }

    private static String sorte(Kuchensorte sorte) {
        String name = sorte.name();
        return name.charAt(0) + name.substring(1).toLowerCase();
    }

    // QUELLE START: gemini 3.5 flash lite 11.11
    private static String text(Fehlercode code) {
        return switch (code) {
            case Ok -> "kein Fehler";
            case FehlenderWert -> "eine Angabe fehlt";
            case UngueltigerWert -> "ungültiger Wert (z.B. negativer Preis, Nährwert oder Haltbarkeit)";
            case Voll -> "der Automat ist voll";
            case HerstellerUnbekannt -> "Hersteller*in ist unbekannt";
            case HerstellerVorhanden -> "Hersteller*in existiert bereits";
            case HerstellerHatKuchen -> "Hersteller*in hat noch Kuchen im Automaten";
            case BereitsEingelagert -> "Kuchen ist bereits eingelagert";
            case FachLeer -> "das Fach ist leer oder existiert nicht";
        };
    }
    // QUELLE ENDE
}