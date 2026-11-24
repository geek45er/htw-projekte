# Prototyp 1: GL
Die Geschäftslogik des Belegs ist prototypisch zu erstellen und exemplarisch zu testen. Für diesen Prototyp müssen nicht alle Anforderungen realisiert werden, aber mindestens Einfügen, Auflisten, Ändern (Inspektionsdatum) und Entfernen (CRUD) für mindestens eine Sorte der im Vertrag vordefinierten Kuchen, z.B. Obstkuchen.

Weitere Informationen stehen in den [Anforderungen](Anforderungen.md) unter der Überschrift „Geschäftslogik (GL)“.

## Abgabeanforderungen
Die Abgabe hat als zip-Datei zu erfolgen, die ein lauffähiges IntelliJ-IDEA-Projekt enthält. Dafür kann der Projektordner direkt in das zip eingepackt werden. Sie muss die befüllte Checkliste im root des Projektes (neben der iml-Datei) enthalten, in der der erreichte Stand bezüglich des Bewertungsschemas vermerkt ist.

Änderungen an der Checkliste sind grundsätzlich nicht zulässig. Davon ausgenommen ist das Befüllen der Checkboxen und ergänzende Anmerkungen, die _kursiv gesetzt_ sind.

## Quellen
Siehe Abschnitt „Übernommener Code“ in den [Anforderungen](Anforderungen.md).

## Bewertung
0 Punkte, wenn die grundsätzlichen Anforderungen nicht erfüllt sind. 1 Punkt für die Erfüllung der Basisanforderung und darauf aufbauend je ein Punkt für die nummerierten Anforderungen.

### grundsätzliche Anforderungen
- [x] übernommener Code gekennzeichnet
- [x] Abgabe als zip-Archiv mit dem Projekt im root
- [x] IntelliJ-Projekt (kein Gradle, Maven o.ä.)
- [x] keine weiteren Bibliotheken außer JUnit5, Mockito und JavaFX (und deren Abhängigkeiten)
- [x] keine Umlaute, Sonderzeichen, etc. in Datei- und Pfadnamen
- [x] kompilierbar
- [x] Trennung zwischen Test- und Produktiv-Code
- [x] geforderte main-Methoden nur im default package des Moduls belegProg3, nicht in den Submodulen (für diesen Prototyp nicht relevant)
- [x] keine vorgetäuschte Funktionalität (inkl. leere und trivial tautologische Tests)
- [x] ausführbar

### Basisanforderung
- [x] CRUD für eine Kuchensorte
- [x] mindestens ein Test

_CRUD für Obstkuchen, Kremkuchen und Obsttorte in domainLogic.Verkaufsautomat_

### 1 Ausbau GL
- [x] Einfügen der Kuchen vollständig implementiert (siehe [Anforderungen](Anforderungen.md))
- [x] Unterstützung von mindestens zwei Kuchensorten

### 2 Testabdeckung
abhängig von 1
- [x] Einfügen der Kuchen vollständig getestet (mindestens Pfadabdeckung)

_siehe domainLogic/tst/domainLogic/EinfuegenTest_

### 3 Stellvertretertests
abhängig von 1
- [x] mindestens zwei Tests mit Mockito

_siehe domainLogic/tst/domainLogic/MockitoTest_

### 4 vollständige GL
abhängig von 1
- [x] GL erfüllt alle Anforderungen lt. [Anforderungsdokument](Anforderungen.md)

