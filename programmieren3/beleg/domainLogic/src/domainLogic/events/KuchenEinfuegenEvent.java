package domainLogic.events;

import domainLogic.Kuchensorte;
import kuchen.Allergen;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Set;


// obstsorte bzw. kremsorte ist null, wenn die sorte sie nicht hat
public record KuchenEinfuegenEvent(Kuchensorte sorte, String hersteller, BigDecimal preis, int naehrwert,
                                   Duration haltbarkeit, Set<Allergen> allergene, String obstsorte,
                                   String kremsorte) {
    public KuchenEinfuegenEvent {
        allergene = AllergenKopie.von(allergene);
    }
}
