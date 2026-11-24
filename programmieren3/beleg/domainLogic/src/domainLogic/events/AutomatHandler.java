package domainLogic.events;

import domainLogic.AbstrakterKuchen;
import domainLogic.Fehlercode;
import domainLogic.KremkuchenImpl;
import domainLogic.ObstkuchenImpl;
import domainLogic.ObsttorteImpl;
import domainLogic.Verkaufsautomat;
import verwaltung.Hersteller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


// verarbeitet die events der oberflaeche und meldet ergebnisse bzw. aenderungen ueber die ausgabequellen
public class AutomatHandler {
    private final Verkaufsautomat automat;
    private final Ausgabequellen ausgabe;

    public AutomatHandler(Verkaufsautomat automat, Ausgabequellen ausgabe) {
        if (automat == null || ausgabe == null) {
            throw new IllegalArgumentException("automat oder ausgabe fehlt");
        }
        this.automat = automat;
        this.ausgabe = ausgabe;
    }

    public void herstellerEinfuegen(HerstellerEinfuegenEvent e) {
        Fehlercode code = automat.herstellerEinfuegen(hersteller(e.name()));
        if (code == Fehlercode.Ok) {
            ausgabe.herstellerGeaendert().feuern(new HerstellerGeaendertEvent(e.name(), true));
        } else {
            ausgabe.fehler().feuern(new FehlerEvent(code));
        }
    }

    public void herstellerLoeschen(HerstellerLoeschenEvent e) {
        Fehlercode code = automat.herstellerLoeschen(e.name());
        if (code == Fehlercode.Ok) {
            ausgabe.herstellerGeaendert().feuern(new HerstellerGeaendertEvent(e.name(), false));
        } else {
            ausgabe.fehler().feuern(new FehlerEvent(code));
        }
    }

    // QUELLE START: gemini 3.5 flash lite 11.11
    public void kuchenEinfuegen(KuchenEinfuegenEvent e) {
        Hersteller h = hersteller(e.hersteller());
        AbstrakterKuchen vorlage = switch (e.sorte()) {
            case OBSTKUCHEN -> new ObstkuchenImpl(h, e.preis(), e.naehrwert(), e.haltbarkeit(), e.allergene(),
                    e.obstsorte());
            case KREMKUCHEN -> new KremkuchenImpl(h, e.preis(), e.naehrwert(), e.haltbarkeit(), e.allergene(),
                    e.kremsorte());
            case OBSTTORTE -> new ObsttorteImpl(h, e.preis(), e.naehrwert(), e.haltbarkeit(), e.allergene(),
                    e.obstsorte(), e.kremsorte());
        };
        Fehlercode code = automat.einfuegen(vorlage);
        if (code == Fehlercode.Ok) {
            ausgabe.kuchenEingefuegt().feuern(new KuchenEingefuegtEvent(e.sorte(), automat.getKuchen().size(),
                    automat.getKapazitaet(), automat.getVorhandeneAllergene()));
        } else {
            ausgabe.fehler().feuern(new FehlerEvent(code));
        }
    }
    // QUELLE ENDE

    public void kuchenEntfernen(KuchenEntfernenEvent e) {
        Fehlercode code = automat.kuchenEntfernen(e.fachnummer());
        if (code == Fehlercode.Ok) {
            ausgabe.kuchenEntfernt().feuern(new KuchenEntferntEvent(e.fachnummer(), automat.getKuchen().size(),
                    automat.getKapazitaet(), automat.getVorhandeneAllergene()));
        } else {
            ausgabe.fehler().feuern(new FehlerEvent(code));
        }
    }

    public void inspektionSetzen(InspektionSetzenEvent e) {
        Fehlercode code = automat.inspektionsdatumSetzen(e.fachnummer());
        if (code == Fehlercode.Ok) {
            ausgabe.inspektionGesetzt().feuern(new InspektionGesetztEvent(e.fachnummer()));
        } else {
            ausgabe.fehler().feuern(new FehlerEvent(code));
        }
    }

    public void herstellerAnzeigen(HerstellerAnzeigenEvent e) {
        List<HerstellerDaten> daten = new ArrayList<>();
        for (Map.Entry<Hersteller, Integer> eintrag : automat.getHerstellerMitAnzahl().entrySet()) {
            daten.add(new HerstellerDaten(eintrag.getKey().getName(), eintrag.getValue()));
        }
        ausgabe.herstellerListe().feuern(new HerstellerListeEvent(daten));
    }

    // QUELLE START: gemini 3.5 flash lite 11.11
    public void kuchenAnzeigen(KuchenAnzeigenEvent e) {
        List<AbstrakterKuchen> kuchen = e.sorte() == null ? automat.getKuchen() : automat.getKuchen(e.sorte());
        List<KuchenDaten> daten = new ArrayList<>();
        for (AbstrakterKuchen k : kuchen) {
            daten.add(new KuchenDaten(k.getFachnummer(), k.getSorte(), k.getHersteller().getName(),
                    k.getInspektionsdatum().toInstant(), k.getHaltbarkeit()));
        }
        ausgabe.kuchenListe().feuern(new KuchenListeEvent(daten));
    }
    // QUELLE ENDE

    public void allergeneAnzeigen(AllergeneAnzeigenEvent e) {
        ausgabe.allergenListe().feuern(new AllergenListeEvent(e.enthalten(),
                e.enthalten() ? automat.getVorhandeneAllergene() : automat.getNichtVorhandeneAllergene()));
    }

    // gl legt beim einfuegen ohnehin eine eigene kopie an
    private static Hersteller hersteller(String name) {
        return () -> name;
    }
}