package domainLogic;

import kuchen.Allergen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import verwaltung.Hersteller;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class EinfuegenTest {
    private static final Instant START = Instant.parse("2026-10-01T10:00:00Z");
    private static final BigDecimal PREIS = new BigDecimal("2.50");
    private static final Duration HALTBAR = Duration.ofDays(3);

    private Verkaufsautomat automat;
    private Hersteller schmidt;

    @BeforeEach
    void setUp() {
        automat = new Verkaufsautomat(2, Clock.fixed(START, ZoneOffset.UTC));
        schmidt = mock(Hersteller.class);
        when(schmidt.getName()).thenReturn("Schmidt");
        automat.herstellerEinfuegen(schmidt);
    }

    private ObstkuchenImpl obstkuchen() {
        return new ObstkuchenImpl(schmidt, PREIS, 300, HALTBAR, List.of(Allergen.Gluten), "Apfel");
    }

    @Test
    void einfuegenObstkuchen() {
        assertEquals(Fehlercode.Ok, automat.einfuegen(obstkuchen()));
    }

    @Test
    void einfuegenKremkuchen() {
        assertEquals(Fehlercode.Ok, automat.einfuegen(new KremkuchenImpl(schmidt, PREIS, 300, HALTBAR, List.of(), "Butter")));
    }

    @Test
    void einfuegenObsttorte() {
        assertEquals(Fehlercode.Ok,
                automat.einfuegen(new ObsttorteImpl(schmidt, PREIS, 300, HALTBAR, List.of(), "Kirsche", "Sahne")));
    }

    @Test
    void einfuegenNull() {
        assertEquals(Fehlercode.FehlenderWert, automat.einfuegen(null));
    }

    @Test
    void einfuegenBereitsEingelagert() {
        automat.einfuegen(obstkuchen());
        AbstrakterKuchen eingelagert = automat.getKuchen().get(0);
        assertEquals(Fehlercode.BereitsEingelagert, automat.einfuegen(eingelagert));
    }

    @Test
    void einfuegenOhnePreis() {
        assertEquals(Fehlercode.FehlenderWert,
                automat.einfuegen(new ObstkuchenImpl(schmidt, null, 300, HALTBAR, List.of(), "Apfel")));
    }

    @Test
    void einfuegenNegativerPreis() {
        assertEquals(Fehlercode.UngueltigerWert,
                automat.einfuegen(new ObstkuchenImpl(schmidt, new BigDecimal("-0.01"), 300, HALTBAR, List.of(), "Apfel")));
    }

    @Test
    void einfuegenUnbekannterHersteller() {
        Hersteller mueller = mock(Hersteller.class);
        when(mueller.getName()).thenReturn("Mueller");
        assertEquals(Fehlercode.HerstellerUnbekannt,
                automat.einfuegen(new ObstkuchenImpl(mueller, PREIS, 300, HALTBAR, List.of(), "Apfel")));
    }

    @Test
    void einfuegenVoll() {
        automat.einfuegen(obstkuchen());
        automat.einfuegen(obstkuchen());
        assertEquals(Fehlercode.Voll, automat.einfuegen(obstkuchen()));
    }
}
