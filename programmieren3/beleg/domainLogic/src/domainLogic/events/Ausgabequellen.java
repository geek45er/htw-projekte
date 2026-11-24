package domainLogic.events;


// alle quellen fuer die richtung gl -> oberflaeche
public record Ausgabequellen(Ereignisquelle<FehlerEvent> fehler,
                             Ereignisquelle<HerstellerGeaendertEvent> herstellerGeaendert,
                             Ereignisquelle<KuchenEingefuegtEvent> kuchenEingefuegt,
                             Ereignisquelle<KuchenEntferntEvent> kuchenEntfernt,
                             Ereignisquelle<InspektionGesetztEvent> inspektionGesetzt,
                             Ereignisquelle<HerstellerListeEvent> herstellerListe,
                             Ereignisquelle<KuchenListeEvent> kuchenListe,
                             Ereignisquelle<AllergenListeEvent> allergenListe) {
    public Ausgabequellen() {
        this(new Ereignisquelle<>(), new Ereignisquelle<>(), new Ereignisquelle<>(), new Ereignisquelle<>(),
                new Ereignisquelle<>(), new Ereignisquelle<>(), new Ereignisquelle<>(), new Ereignisquelle<>());
    }
}
