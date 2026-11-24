package cli;

import domainLogic.events.Bestandsaenderung;
import domainLogic.events.Ereignisbehandler;
import kuchen.Allergen;

import java.io.PrintStream;
import java.util.EnumSet;
import java.util.Set;


// merkt sich die zuletzt vorhandenen allergene und meldet hinzugekommene bzw. entfallene
public class AllergenBeobachter implements Ereignisbehandler<Bestandsaenderung> {
    private final PrintStream out;
    // automat startet leer
    private Set<Allergen> bisher = EnumSet.noneOf(Allergen.class);

    public AllergenBeobachter(PrintStream out) {
        if (out == null) {
            throw new IllegalArgumentException("ausgabe fehlt");
        }
        this.out = out;
    }

    // QUELLE START: gemini 3.5 flash lite 11.11
    @Override
    public void verarbeite(Bestandsaenderung e) {
        Set<Allergen> jetzt = EnumSet.noneOf(Allergen.class);
        jetzt.addAll(e.vorhandeneAllergene());
        if (jetzt.equals(bisher)) {
            return;
        }
        Set<Allergen> hinzu = EnumSet.noneOf(Allergen.class);
        hinzu.addAll(jetzt);
        hinzu.removeAll(bisher);
        Set<Allergen> weg = EnumSet.noneOf(Allergen.class);
        weg.addAll(bisher);
        weg.removeAll(jetzt);
        out.println("Hinweis: Allergene geändert, hinzugekommen: " + CliAusgabe.allergene(hinzu)
                + ", entfallen: " + CliAusgabe.allergene(weg));
        bisher = jetzt;
    }
    // QUELLE ENDE
}