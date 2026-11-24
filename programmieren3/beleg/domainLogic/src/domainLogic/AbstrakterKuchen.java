    package domainLogic;

    import kuchen.Allergen;
    import kuchen.Kuchen;
    import verwaltung.Hersteller;
    import verwaltung.Verkaufsobjekt;

    import java.math.BigDecimal;
    import java.time.Clock;
    import java.time.Duration;
    import java.time.Instant;
    import java.util.ArrayList;
    import java.util.Collection;
    import java.util.Date;
    import java.util.EnumSet;


    public abstract class AbstrakterKuchen implements Kuchen, Verkaufsobjekt {
        private final Hersteller hersteller;
        private final BigDecimal preis;
        private final int naehrwert;
        private final Duration haltbarkeit;
        private final Collection<Allergen> allergene;
        private final int fachnummer;
        private final Clock clock;
        private final Instant einfuegedatum;
        private Instant inspektionsdatum;

        // vorlage, noch nicht im automaten
        AbstrakterKuchen(Hersteller hersteller, BigDecimal preis, int naehrwert, Duration haltbarkeit,
                         Collection<Allergen> allergene) {
            this.hersteller = hersteller;
            this.preis = preis;
            this.naehrwert = naehrwert;
            this.haltbarkeit = haltbarkeit;
            this.allergene = allergene == null ? null : new ArrayList<>(allergene);
            this.fachnummer = 0;
            this.clock = null;
            this.einfuegedatum = null;
        }

        // kopie, die im automaten liegt
        AbstrakterKuchen(AbstrakterKuchen vorlage, int fachnummer, Hersteller hersteller, Clock clock) {
            this.hersteller = hersteller;
            this.preis = vorlage.preis;
            this.naehrwert = vorlage.naehrwert;
            this.haltbarkeit = vorlage.haltbarkeit;
            this.allergene = EnumSet.noneOf(Allergen.class);
            this.allergene.addAll(vorlage.allergene);
            this.fachnummer = fachnummer;
            this.clock = clock;
            this.einfuegedatum = clock.instant();
            this.inspektionsdatum = einfuegedatum;
        }

        public abstract Kuchensorte getSorte();

        abstract AbstrakterKuchen einlagern(int fachnummer, Hersteller hersteller, Clock clock);

        abstract boolean sorteGueltig();

        Fehlercode pruefen() {
            if (hersteller == null || preis == null || haltbarkeit == null || allergene == null
                    || allergene.contains(null) || !sorteGueltig()) {
                return Fehlercode.FehlenderWert;
            }
            if (preis.signum() < 0 || naehrwert < 0 || haltbarkeit.isNegative()) {
                return Fehlercode.UngueltigerWert;
            }
            return Fehlercode.Ok;
        }

        boolean textGueltig(String text) {
            return text != null && !text.isBlank();
        }

        void setInspektionsdatum(Instant inspektionsdatum) {
            this.inspektionsdatum = inspektionsdatum;
        }
        // QUELLE start: gemini 3.5 flash lite 26.10
        @Override
        public int getFachnummer() {
            return fachnummer;
        }

        @Override
        public Hersteller getHersteller() {
            return hersteller;
        }

        @Override
        public BigDecimal getPreis() {
            return preis;
        }

        @Override
        public int getNaehrwert() {
            return naehrwert;
        }

        @Override
        public Collection<Allergen> getAllergene() {
            return allergene == null ? null : new ArrayList<>(allergene);
        }

        @Override
        public Date getInspektionsdatum() {
            // neues date, weil date veraenderbar
            return inspektionsdatum == null ? null : Date.from(inspektionsdatum);
        }

        @Override
        public Duration getHaltbarkeit() {
            // vorlage altert nicht
            if (clock == null) {
                return haltbarkeit;
            }
            return haltbarkeit.minus(Duration.between(einfuegedatum, clock.instant()));
        }
        //QUELLE ENDE
    }