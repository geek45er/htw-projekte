package domainLogic;

import kuchen.Allergen;
import kuchen.Kremkuchen;
import verwaltung.Hersteller;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Duration;
import java.util.Collection;


public final class KremkuchenImpl extends AbstrakterKuchen implements Kremkuchen {
    private final String kremsorte;

    public KremkuchenImpl(Hersteller hersteller, BigDecimal preis, int naehrwert, Duration haltbarkeit,
                          Collection<Allergen> allergene, String kremsorte) {
        super(hersteller, preis, naehrwert, haltbarkeit, allergene);
        this.kremsorte = kremsorte;
    }

    private KremkuchenImpl(KremkuchenImpl vorlage, int fachnummer, Hersteller hersteller, Clock clock) {
        super(vorlage, fachnummer, hersteller, clock);
        this.kremsorte = vorlage.kremsorte;
    }

    @Override
    AbstrakterKuchen einlagern(int fachnummer, Hersteller hersteller, Clock clock) {
        return new KremkuchenImpl(this, fachnummer, hersteller, clock);
    }

    @Override
    boolean sorteGueltig() {
        return textGueltig(kremsorte);
    }

    @Override
    public Kuchensorte getSorte() {
        return Kuchensorte.KREMKUCHEN;
    }

    @Override
    public String getKremsorte() {
        return kremsorte;
    }
}
