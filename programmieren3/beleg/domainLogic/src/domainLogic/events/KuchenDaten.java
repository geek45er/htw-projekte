package domainLogic.events;

import domainLogic.Kuchensorte;

import java.time.Duration;
import java.time.Instant;



public record KuchenDaten(int fachnummer, Kuchensorte sorte, String hersteller, Instant inspektionsdatum,
                          Duration haltbarkeit) {
}
