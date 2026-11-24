package domainLogic.events;


// alle quellen fuer die richtung oberflaeche -> gl
public record Eingabequellen(Ereignisquelle<HerstellerEinfuegenEvent> herstellerEinfuegen,
                             Ereignisquelle<KuchenEinfuegenEvent> kuchenEinfuegen,
                             Ereignisquelle<HerstellerAnzeigenEvent> herstellerAnzeigen,
                             Ereignisquelle<KuchenAnzeigenEvent> kuchenAnzeigen,
                             Ereignisquelle<AllergeneAnzeigenEvent> allergeneAnzeigen,
                             Ereignisquelle<InspektionSetzenEvent> inspektionSetzen,
                             Ereignisquelle<HerstellerLoeschenEvent> herstellerLoeschen,
                             Ereignisquelle<KuchenEntfernenEvent> kuchenEntfernen) {
    public Eingabequellen() {
        this(new Ereignisquelle<>(), new Ereignisquelle<>(), new Ereignisquelle<>(), new Ereignisquelle<>(),
                new Ereignisquelle<>(), new Ereignisquelle<>(), new Ereignisquelle<>(), new Ereignisquelle<>());
    }
}
