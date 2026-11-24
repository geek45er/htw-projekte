package domainLogic.events;

import kuchen.Allergen;

import java.util.Set;



public record KuchenEntferntEvent(int fachnummer, int anzahl, int kapazitaet, Set<Allergen> vorhandeneAllergene)
        implements Bestandsaenderung {
    public KuchenEntferntEvent {
        vorhandeneAllergene = AllergenKopie.von(vorhandeneAllergene);
    }
}
