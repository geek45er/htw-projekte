import cli.AllergenBeobachter;
import cli.Cli;
import cli.CliAusgabe;
import cli.KapazitaetsBeobachter;
import domainLogic.Verkaufsautomat;
import domainLogic.events.Ausgabequellen;
import domainLogic.events.AutomatHandler;
import domainLogic.events.Eingabequellen;

import java.time.Clock;


// verdrahtet gl, handler, cli-ausgabe und beobachter; nur hier wird konfiguriert
public class CliMain {
    public static void main(String[] args) {
        Integer kapazitaet = kapazitaet(args);
        if (kapazitaet == null) {
            System.err.println("Verwendung: CliMain <Kapazitaet>   (ganze Zahl >= 0)");
            return;
        }
        Verkaufsautomat automat = new Verkaufsautomat(kapazitaet, Clock.systemDefaultZone());
        Eingabequellen eingabe = new Eingabequellen();
        Ausgabequellen ausgabe = new Ausgabequellen();

        // cli -> gl
        AutomatHandler handler = new AutomatHandler(automat, ausgabe);
        eingabe.herstellerEinfuegen().anmelden(handler::herstellerEinfuegen);
        eingabe.kuchenEinfuegen().anmelden(handler::kuchenEinfuegen);
        eingabe.herstellerAnzeigen().anmelden(handler::herstellerAnzeigen);
        eingabe.kuchenAnzeigen().anmelden(handler::kuchenAnzeigen);
        eingabe.allergeneAnzeigen().anmelden(handler::allergeneAnzeigen);
        eingabe.inspektionSetzen().anmelden(handler::inspektionSetzen);
        eingabe.herstellerLoeschen().anmelden(handler::herstellerLoeschen);
        eingabe.kuchenEntfernen().anmelden(handler::kuchenEntfernen);

        // gl -> cli
        CliAusgabe cliAusgabe = new CliAusgabe(System.out);
        ausgabe.fehler().anmelden(cliAusgabe::fehler);
        ausgabe.herstellerGeaendert().anmelden(cliAusgabe::herstellerGeaendert);
        ausgabe.kuchenEingefuegt().anmelden(cliAusgabe::kuchenEingefuegt);
        ausgabe.kuchenEntfernt().anmelden(cliAusgabe::kuchenEntfernt);
        ausgabe.inspektionGesetzt().anmelden(cliAusgabe::inspektionGesetzt);
        ausgabe.herstellerListe().anmelden(cliAusgabe::herstellerListe);
        ausgabe.kuchenListe().anmelden(cliAusgabe::kuchenListe);
        ausgabe.allergenListe().anmelden(cliAusgabe::allergenListe);

        // beobachter
        KapazitaetsBeobachter kapazitaetsBeobachter = new KapazitaetsBeobachter(System.out);
        ausgabe.kuchenEingefuegt().anmelden(kapazitaetsBeobachter);
        ausgabe.kuchenEntfernt().anmelden(kapazitaetsBeobachter);
        AllergenBeobachter allergenBeobachter = new AllergenBeobachter(System.out);
        ausgabe.kuchenEingefuegt().anmelden(allergenBeobachter);
        ausgabe.kuchenEntfernt().anmelden(allergenBeobachter);

        new Cli(System.in, System.out, eingabe).starten();
    }

    // QUELLE START: gemini 3.5 flash lite 11.11
    private static Integer kapazitaet(String[] args) {
        if (args.length != 1) {
            return null;
        }
        try {
            int k = Integer.parseInt(args[0]);
            return k < 0 ? null : k;
        } catch (NumberFormatException e) {
            return null;
        }
    }
    // QUELLE ENDE
}