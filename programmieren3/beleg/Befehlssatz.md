# Befehlssatz CLI (Prototyp 2)

_Erstellt mithilfe von Gemini 3.5 Flash Lite_

Start: `CliMain <Kapazitaet>`, z.B. `CliMain 10`. Fehlt die Kapazität oder ist sie keine ganze Zahl >= 0, wird eine Verwendungsmeldung ausgegeben und das Programm beendet.

Jeder Befehl wird mit einem Zeilenwechsel abgeschlossen. Ein Ende der Eingabe (EOF) beendet das Programm ebenso wie `:x`.

## Modi

| Befehl | Wirkung |
|---|---|
| `:c` | Wechsel in den Einfügemodus |
| `:r` | Wechsel in den Anzeigemodus |
| `:u` | Wechsel in den Änderungsmodus |
| `:d` | Wechsel in den Löschmodus |
| `:p` | Persistenzmodus, **in P2 nicht implementiert** (Meldung, Modus bleibt unverändert) |
| `:x` | beendet die Anwendung |

Nach dem Start ist kein Modus aktiv; Befehle außer Moduswechseln werden bis zur Wahl eines Modus mit einer Fehlermeldung abgelehnt.

## Einfügemodus (`:c`)

- `[H-Name]` fügt eine Hersteller\*in ein
  - `Alice`
- `[Kuchensorte] [H-Name] [Preis] [Nährwert] [Haltbarkeit] [Allergene] [[Obstsorte]] [[Kremsorte]]` fügt einen Kuchen ein
  - `Kremkuchen Alice 4,50 386 36 Gluten,Erdnuss Butter`
  - `Obsttorte Alice 7,50 632 24 Gluten Apfel Sahne`
  - `Obstkuchen Alice 2,50 200 20 , Erdbeere`
  - `Obstkuchen Alice 2,50 200 20 ,` (Obstsorte per Default)

Kuchensorten: `Obstkuchen`, `Kremkuchen`, `Obsttorte` (Groß-/Kleinschreibung egal).

Sortenabhängige optionale Parameter:

| Sorte | 7. Token | 8. Token |
|---|---|---|
| Obstkuchen | Obstsorte | – |
| Kremkuchen | Kremsorte | – |
| Obsttorte | Obstsorte | Kremsorte |

## Anzeigemodus (`:r`)

- `hersteller` Hersteller\*innen mit Anzahl ihrer Kuchen
- `kuchen [[Kuchensorte]]` Kuchen (ggf. gefiltert) mit Fachnummer, Inspektionsdatum und verbleibender Haltbarkeit in Sekunden
  - `kuchen`
  - `kuchen Obstkuchen`
- `allergene i` vorhandene Allergene, `allergene e` nicht vorhandene Allergene

## Änderungsmodus (`:u`)

- `[Fachnummer]` setzt das Inspektionsdatum auf jetzt, z.B. `1`

## Löschmodus (`:d`)

- `[H-Name]` löscht die Hersteller\*in (nur ohne Kuchen im Automaten), z.B. `Alice`
- `[Fachnummer]` entfernt den Kuchen aus dem Fach, z.B. `2`

Ein Token aus Ziffern wird als Fachnummer gelesen.

## Abweichungen und Festlegungen

- **Haltbarkeit** wird beim Einfügen in **Stunden** (ganze Zahl) angegeben, die Anzeige erfolgt in Sekunden.
- **Preis** mit Dezimalkomma und höchstens zwei Nachkommastellen (`4,50`, `7`); ein Punkt wird abgelehnt.
- **Allergene** kommasepariert ohne Leerzeichen aus `Gluten`, `Erdnuss`, `Haselnuss`, `Sesamsamen`; ein einzelnes `,` bedeutet keine Allergene.
- **Defaults** für fehlende optionale Parameter: Obstsorte `Apfel`, Kremsorte `Butter`.
- **Persistenz** (`:p`, `save`, `load`) ist nicht Teil von Prototyp 2 und gibt nur „Persistenzmodus ist nicht implementiert“ aus.
- Ein Befehl, für den in `CliMain` kein Handler eingehängt ist, wird mit „Befehl ist in dieser Konfiguration nicht verfügbar“ abgelehnt.

## Beobachter

- **Kapazität:** nach jedem Einfügen/Entfernen eine Meldung, solange mehr als 90 % der Kapazität belegt sind.
- **Allergene:** Meldung mit hinzugekommenen und entfallenen Allergenen, sobald sich die Menge der vorhandenen Allergene ändert.

## Beispielablauf

```text
:c
Alice
Obsttorte Alice 7,50 632 24 Gluten Apfel Sahne
Obstkuchen Alice 2,50 200 20 , Erdbeere
:r
kuchen Obstkuchen
allergene i
hersteller
```

Ausgabe (Kapazität 10):

```text
Einfügemodus
Hersteller*in Alice eingefügt
Obsttorte eingefügt
Hinweis: Allergene geändert, hinzugekommen: Gluten, entfallen: keine
Obstkuchen eingefügt
Anzeigemodus
Fach 2: Obstkuchen von Alice, Inspektion 12.11.2026 18:57:32, Haltbarkeit 71999 s
vorhandene Allergene: Gluten
Alice: 2 Kuchen
```
