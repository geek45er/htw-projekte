package domainLogic.events;

import java.util.List;



public record HerstellerListeEvent(List<HerstellerDaten> hersteller) {
    public HerstellerListeEvent {
        hersteller = List.copyOf(hersteller);
    }
}
