package domainLogic.events;

import kuchen.Allergen;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;


// unveraenderliche kopie in enum-reihenfolge, damit die events immutable bleiben
final class AllergenKopie {
    private AllergenKopie() {
    }

    static Set<Allergen> von(Collection<Allergen> allergene) {
        EnumSet<Allergen> kopie = EnumSet.noneOf(Allergen.class);
        kopie.addAll(allergene);
        return Collections.unmodifiableSet(kopie);
    }
}
