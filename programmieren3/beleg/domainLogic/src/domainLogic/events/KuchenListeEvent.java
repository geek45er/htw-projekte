package domainLogic.events;

import java.util.List;



public record KuchenListeEvent(List<KuchenDaten> kuchen) {
    public KuchenListeEvent {
        kuchen = List.copyOf(kuchen);
    }
}
