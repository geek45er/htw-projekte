package domainLogic.events;

import java.util.ArrayList;
import java.util.List;


// eine quelle je eventtyp, verteilt an alle angemeldeten behandler
public class Ereignisquelle<E> {
    private final List<Ereignisbehandler<? super E>> behandler = new ArrayList<>();

    public void anmelden(Ereignisbehandler<? super E> b) {
        if (b == null) {
            throw new IllegalArgumentException("behandler fehlt");
        }
        behandler.add(b);
    }

    public void abmelden(Ereignisbehandler<? super E> b) {
        behandler.remove(b);
    }

    public boolean hatBehandler() {
        return !behandler.isEmpty();
    }

    public void feuern(E ereignis) {
        for (Ereignisbehandler<? super E> b : behandler) {
            b.verarbeite(ereignis);
        }
    }
}
