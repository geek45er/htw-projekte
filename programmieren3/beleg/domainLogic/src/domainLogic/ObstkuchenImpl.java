package domainLogic;

import kuchen.Allergen;
import kuchen.Obstkuchen;
import verwaltung.Hersteller;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Duration;
import java.util.Collection;


public final class ObstkuchenImpl extends AbstrakterKuchen implements Obstkuchen {
    private final String obstsorte;

    public ObstkuchenImpl(Hersteller hersteller, BigDecimal preis, int naehrwert, Duration haltbarkeit,
                          Collection<Allergen> allergene, String obstsorte) {
        super(hersteller, preis, naehrwert, haltbarkeit, allergene);
        this.obstsorte = obstsorte;
    }

    private ObstkuchenImpl(ObstkuchenImpl vorlage, int fachnummer, Hersteller hersteller, Clock clock) {
        super(vorlage, fachnummer, hersteller, clock);
        this.obstsorte = vorlage.obstsorte;
    }

    @Override
    AbstrakterKuchen einlagern(int fachnummer, Hersteller hersteller, Clock clock) {
        return new ObstkuchenImpl(this, fachnummer, hersteller, clock);
    }

    @Override
    boolean sorteGueltig() {
        return textGueltig(obstsorte);
    }

    @Override
    public Kuchensorte getSorte() {
        return Kuchensorte.OBSTKUCHEN;
    }

    @Override
    public String getObstsorte() {
        return obstsorte;
    }
}
