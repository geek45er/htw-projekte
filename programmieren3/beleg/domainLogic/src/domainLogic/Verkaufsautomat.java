package domainLogic;

import kuchen.Allergen;
import verwaltung.Hersteller;

import java.time.Clock;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


public class Verkaufsautomat {
    private final int kapazitaet;
    private final Clock clock;
    private final Map<String, HerstellerImpl> hersteller = new LinkedHashMap<>();
    private final Map<Integer, AbstrakterKuchen> kuchen = new TreeMap<>();

    public Verkaufsautomat(int kapazitaet, Clock clock) {
        // konstruktor kann keinen fehlercode liefern
        if (kapazitaet < 0 || clock == null) {
            throw new IllegalArgumentException("ungueltige kapazitaet oder clock");
        }
        this.kapazitaet = kapazitaet;
        this.clock = clock;
    }

    public int getKapazitaet() {
        return kapazitaet;
    }

    public Fehlercode herstellerEinfuegen(Hersteller h) {
        if (h == null) {
            return Fehlercode.FehlenderWert;
        }
        String name = h.getName();
        if (name == null || name.isBlank()) {
            return Fehlercode.FehlenderWert;
        }
        if (hersteller.containsKey(name)) {
            return Fehlercode.HerstellerVorhanden;
        }
        // eigene kopie speichern
        hersteller.put(name, new HerstellerImpl(name));
        return Fehlercode.Ok;
    }

    public Fehlercode herstellerLoeschen(String name) {
        if (!hersteller.containsKey(name)) {
            return Fehlercode.HerstellerUnbekannt;
        }
        if (anzahlKuchen(name) > 0) {
            return Fehlercode.HerstellerHatKuchen;
        }
        hersteller.remove(name);
        return Fehlercode.Ok;
    }

    public Map<Hersteller, Integer> getHerstellerMitAnzahl() {
        Map<Hersteller, Integer> ergebnis = new LinkedHashMap<>();
        for (HerstellerImpl h : hersteller.values()) {
            ergebnis.put(h, anzahlKuchen(h.getName()));
        }
        return ergebnis;
    }

    public Fehlercode einfuegen(AbstrakterKuchen vorlage) {
        if (vorlage == null) {
            return Fehlercode.FehlenderWert;
        }
        if (vorlage.getFachnummer() != 0) {
            return Fehlercode.BereitsEingelagert;
        }
        Fehlercode pruefung = vorlage.pruefen();
        if (pruefung != Fehlercode.Ok) {
            return pruefung;
        }
        HerstellerImpl registriert = hersteller.get(vorlage.getHersteller().getName());
        if (registriert == null) {
            return Fehlercode.HerstellerUnbekannt;
        }
        int fach = freiesFach();
        if (fach == 0) {
            return Fehlercode.Voll;
        }
        kuchen.put(fach, vorlage.einlagern(fach, registriert, clock));
        return Fehlercode.Ok;
    }

    public List<AbstrakterKuchen> getKuchen() {
        return new ArrayList<>(kuchen.values());
    }

    public List<AbstrakterKuchen> getKuchen(Kuchensorte sorte) {
        List<AbstrakterKuchen> ergebnis = new ArrayList<>();
        for (AbstrakterKuchen k : kuchen.values()) {
            if (k.getSorte() == sorte) {
                ergebnis.add(k);
            }
        }
        return ergebnis;
    }

    public Fehlercode inspektionsdatumSetzen(int fachnummer) {
        AbstrakterKuchen k = kuchen.get(fachnummer);
        if (k == null) {
            return Fehlercode.FachLeer;
        }
        k.setInspektionsdatum(clock.instant());
        return Fehlercode.Ok;
    }
    // QUELLE start: gemini 3.5 flash lite 26.10
    public Fehlercode kuchenEntfernen(int fachnummer) {
        if (kuchen.remove(fachnummer) == null) {
            return Fehlercode.FachLeer;
        }
        return Fehlercode.Ok;
    }

    public Set<Allergen> getVorhandeneAllergene() {
        return sammleAllergene();
    }

    public Set<Allergen> getNichtVorhandeneAllergene() {
        return EnumSet.complementOf(sammleAllergene());
    }

    private EnumSet<Allergen> sammleAllergene() {
        EnumSet<Allergen> vorhanden = EnumSet.noneOf(Allergen.class);
        for (AbstrakterKuchen k : kuchen.values()) {
            vorhanden.addAll(k.getAllergene());
        }
        return vorhanden;
    }

    private int anzahlKuchen(String herstellerName) {
        int anzahl = 0;
        for (AbstrakterKuchen k : kuchen.values()) {
            if (k.getHersteller().getName().equals(herstellerName)) {
                anzahl++;
            }
        }
        return anzahl;
    }

    // kleinste freie fachnummer ab 1, 0 heisst automat voll
    private int freiesFach() {
        for (int i = 1; i <= kapazitaet; i++) {
            if (!kuchen.containsKey(i)) {
                return i;
            }
        }
        return 0;
    }
    //Quelle Ende
}