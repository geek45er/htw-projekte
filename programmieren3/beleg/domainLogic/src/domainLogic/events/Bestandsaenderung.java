package domainLogic.events;

import kuchen.Allergen;

import java.util.Set;


// gemeinsame sicht auf eingefuegt/entfernt, damit ein beobachter beide quellen abonnieren kann
public interface Bestandsaenderung {
    int anzahl();

    int kapazitaet();

    Set<Allergen> vorhandeneAllergene();
}
