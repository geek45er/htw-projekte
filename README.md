# htw-projekte

projekte und belegarbeiten aus dem bachelorstudium informatik in kultur und gesundheit an der htw berlin

- `programmieren1` midi-generator, minimal music mit midi (clapping music, piano phase), analyse eines social-media-datensatzes (abschlussaufgabe), wöchentliche übungen, übungen aus dem tutorium
- `programmieren2` auswertung von lateral flow assays (prüfungsleistung), hiv-resistenzdiagnostik mit gui, dicom-viewer, schachbrett-gui
- `programmieren3` beleg: verkaufsautomat für kuchen, prototyp 1 (geschäftslogik) und 2 (cli). prototyp 3 und rest offen
- `projektstudium-computermuseum` rest-api für die website des htw-computermuseums
- `betriebssysteme` threads, log-analyse mit bash
- `bildverarbeitung` belegarbeiten 1-3 (farbräume, filter, hough-transformation, klassifikation auf cifar-10)
- `softwareengineering` factory pattern, rest-api mit spring boot
- `deeplearning` project 1.2: lebenserwartung vorhersagen (gapminder)
- `ikg` validierung von genen

## setup

- java: gradle-projekte in `programmieren1` und `programmieren2`, `programmieren3` als intellij-projekt, `softwareengineering/spring-rest-demo` mit maven, restlicher java-code ohne build-tool
- php: laravel, abhängigkeiten über composer
- notebooks: python ≥ 3.12, z. b. in jupyterlab, vscodium oder google colab. `deeplearning` braucht zusätzlich pytorch, pandas und plotly. abhängigkeiten für `bildverarbeitung` in `bildverarbeitung/pyproject.toml`
