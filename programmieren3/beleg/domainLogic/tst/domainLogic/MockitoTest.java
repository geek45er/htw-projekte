package domainLogic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import verwaltung.Hersteller;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class MockitoTest {
    private static final Instant START = Instant.parse("2026-10-01T10:00:00Z");

    private Clock clock;
    private Hersteller schmidt;
    private Verkaufsautomat automat;

    @BeforeEach
    void setUp() {
        clock = mock(Clock.class);
        when(clock.instant()).thenReturn(START);
        schmidt = mock(Hersteller.class);
        when(schmidt.getName()).thenReturn("Schmidt");
        automat = new Verkaufsautomat(2, clock);
    }

    private void obstkuchenEinlagern() {
        automat.herstellerEinfuegen(schmidt);
        automat.einfuegen(new ObstkuchenImpl(schmidt, BigDecimal.ONE, 100, Duration.ofHours(10), List.of(), "Apfel"));
    }

    @Test
    void haltbarkeitNachVierStunden() {
        obstkuchenEinlagern();
        when(clock.instant()).thenReturn(START.plus(Duration.ofHours(4)));
        assertEquals(Duration.ofHours(6), automat.getKuchen().get(0).getHaltbarkeit());
    }

    @Test
    void inspektionsdatumSetzen() {
        obstkuchenEinlagern();
        Instant spaeter = START.plus(Duration.ofDays(2));
        when(clock.instant()).thenReturn(spaeter);
        automat.inspektionsdatumSetzen(1);
        assertEquals(Date.from(spaeter), automat.getKuchen().get(0).getInspektionsdatum());
    }
}
