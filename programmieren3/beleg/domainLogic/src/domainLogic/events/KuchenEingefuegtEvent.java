package domainLogic.events;

import domainLogic.Kuchensorte;
import kuchen.Allergen;

import java.util.Set;



public record KuchenEingefuegtEvent(Kuchensorte sorte, int anzahl, int kapazitaet, Set<Allergen> vorhandeneAllergene)
        implements Bestandsaenderung {
    public KuchenEingefuegtEvent {
        vorhandeneAllergene = AllergenKopie.von(vorhandeneAllergene);
    }
}
