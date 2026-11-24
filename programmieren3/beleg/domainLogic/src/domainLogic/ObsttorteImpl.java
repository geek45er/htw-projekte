package domainLogic;

import kuchen.Allergen;
import kuchen.Obsttorte;
import verwaltung.Hersteller;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Duration;
import java.util.Collection;


public final class ObsttorteImpl extends AbstrakterKuchen implements Obsttorte {
    private final String obstsorte;
    private final String kremsorte;

    public ObsttorteImpl(Hersteller hersteller, BigDecimal preis, int naehrwert, Duration haltbarkeit,
                         Collection<Allergen> allergene, String obstsorte, String kremsorte) {
        super(hersteller, preis, naehrwert, haltbarkeit, allergene);
        this.obstsorte = obstsorte;
        this.kremsorte = kremsorte;
    }

    private ObsttorteImpl(ObsttorteImpl vorlage, int fachnummer, Hersteller hersteller, Clock clock) {
        super(vorlage, fachnummer, hersteller, clock);
        this.obstsorte = vorlage.obstsorte;
        this.kremsorte = vorlage.kremsorte;
    }

    @Override
    AbstrakterKuchen einlagern(int fachnummer, Hersteller hersteller, Clock clock) {
        return new ObsttorteImpl(this, fachnummer, hersteller, clock);
    }

    @Override
    boolean sorteGueltig() {
        return textGueltig(obstsorte) && textGueltig(kremsorte);
    }

    @Override
    public Kuchensorte getSorte() {
        return Kuchensorte.OBSTTORTE;
    }

    @Override
    public String getObstsorte() {
        return obstsorte;
    }

    @Override
    public String getKremsorte() {
        return kremsorte;
    }
}
