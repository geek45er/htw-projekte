# Prototyp 2: CLI
Es ist ein prototypisches CLI mit 4 Befehlen (und ggf. ohne Modi) zu erstellen: ein Befehl zum Einfügen eines (vordefinierten) Kuchens, ein Befehl zum Anzeigen der Kuchen, ein Befehl zum Ändern des Inspektionsdatums eines Kuchens und ein Befehl zum Entfernen eines Kuchens. Dafür genügt eine Sorte der im Vertrag vordefinierten Kuchen, z.B. Obstkuchen. Als Grundlage ist die Geschäftslogik (GL) aus Prototyp 1 zu verwenden, Änderungen an ihr sind zulässig.

Weitere Informationen stehen in den [Anforderungen](Anforderungen.md) unter der Überschrift „CLI“. Der Persistenzmodus sowie die Ausführung als Client bzw. Server sind nicht Teil dieses Prototyps.

## Abgabeanforderungen
Die Abgabe hat als zip-Datei zu erfolgen, die ein lauffähiges IntelliJ-IDEA-Projekt enthält. Dafür kann der Projektordner direkt in das zip eingepackt werden. Sie muss die befüllte Checkliste im root des Projektes (neben der iml-Datei) enthalten, in der der erreichte Stand bezüglich des Bewertungsschemas vermerkt ist.

Änderungen an der Checkliste sind grundsätzlich nicht zulässig. Davon ausgenommen ist das Befüllen der Checkboxen und ergänzende Anmerkungen, die _kursiv gesetzt_ sind.

## Quellen
Siehe Abschnitt „Übernommener Code“ in den [Anforderungen](Anforderungen.md).

## Bewertung
0 Punkte, wenn die grundsätzlichen Anforderungen nicht erfüllt sind. 1 Punkt für die Erfüllung der Basisanforderung und darauf aufbauend je ein Punkt für die nummerierten Anforderungen.

### grundsätzliche Anforderungen
- [X] übernommener Code gekennzeichnet
- [X] Abgabe als zip-Archiv mit dem Projekt im root
- [x] IntelliJ-Projekt (kein Gradle, Maven o.ä.)
- [x] keine weiteren Bibliotheken außer JUnit5, Mockito und JavaFX (und deren Abhängigkeiten)
- [x] keine Umlaute, Sonderzeichen, etc. in Datei- und Pfadnamen
- [x] kompilierbar
- [x] Trennung zwischen Test- und Produktiv-Code
- [x] geforderte main-Methoden nur im default package des Moduls belegProg3, nicht in den Submodulen
- [x] keine vorgetäuschte Funktionalität (inkl. leere und trivial tautologische Tests)
- [x] ausführbar

_kompiliert und ausgeführt mit javac/java 25.0.4.1 (JBR); main: CliMain in belegProg3/src_

### Basisanforderung
- [x] Oberfläche (CLI) und Geschäftslogik korrekt aufgeteilt (mindestens 2-Schichten-Architektur)
- [x] CLI-Implementierung für CRUD für eine Kuchensorte
- [x] Dokumentation des Befehlssatzes inkl. Beispiele, falls abweichend vom Anforderungsdokument

_Schichten: cli (Oberfläche, Beobachter) hängt von domainLogic ab, nicht umgekehrt; CRUD für Obstkuchen, Kremkuchen und Obsttorte sowie Hersteller\*innen; Befehlssatz und Abweichungen siehe Befehlssatz.md_

### 1 einfaches event-System
- [x] event-System für die Kommunikation vom CLI zur GL realisiert

_cli.Cli feuert Events über domainLogic.events.Eingabequellen, verarbeitet von domainLogic.events.AutomatHandler; verdrahtet nur in CliMain_

### 2 Beobachtermuster
- [x] ein Beobachter gemäß Anforderungen realisiert

_beide Beobachter: cli.KapazitaetsBeobachter (über 90 % Kapazität) und cli.AllergenBeobachter (Änderung der vorhandenen Allergene), eingehängt in CliMain_

### 3 erweitertes event-System
abhängig von 1
- [x] event-System für die Kommunikation von der GL zum CLI realisiert

_AutomatHandler meldet Ergebnisse und Änderungen über domainLogic.events.Ausgabequellen, ausgegeben von cli.CliAusgabe_

### 4 getestetes event-System
abhängig von 3
- [ ] für einen in der [Checkliste des Belegs](Checklist.draft.md) unter „Tests“ geforderten Anwendungsfall alle beteiligten Methoden getestet, von der Eingabe auf System.in bis zur Ausgabe auf System.out
