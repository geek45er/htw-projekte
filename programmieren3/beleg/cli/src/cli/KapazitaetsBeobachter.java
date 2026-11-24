package cli;

import domainLogic.events.Bestandsaenderung;
import domainLogic.events.Ereignisbehandler;

import java.io.PrintStream;


// meldet nach jeder bestandsaenderung, wenn mehr als 90 % der kapazitaet belegt sind
public class KapazitaetsBeobachter implements Ereignisbehandler<Bestandsaenderung> {
    private final PrintStream out;

    public KapazitaetsBeobachter(PrintStream out) {
        if (out == null) {
            throw new IllegalArgumentException("ausgabe fehlt");
        }
        this.out = out;
    }

    @Override
    public void verarbeite(Bestandsaenderung e) {
        // ganzzahlig statt anzahl / kapazitaet > 0.9
        if (e.anzahl() * 10L > e.kapazitaet() * 9L) {
            out.println("Hinweis: mehr als 90 % der Kapazität belegt (" + e.anzahl() + " von " + e.kapazitaet()
                    + ")");
        }
    }
}
