package domainLogic.events;

import kuchen.Allergen;

import java.util.Set;



public record AllergenListeEvent(boolean enthalten, Set<Allergen> allergene) {
    public AllergenListeEvent {
        allergene = AllergenKopie.von(allergene);
    }
}
