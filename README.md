# htw-projekte

projekte und belegarbeiten aus dem bachelorstudium informatik in kultur und gesundheit an der htw berlin

- `programmieren1` midi-generator, minimal music mit midi (clapping music, piano phase), analyse eines social-media-datensatzes (abschlussaufgabe), wöchentliche übungen, übungen aus dem tutorium
- `programmieren2` auswertung von lateral flow assays (prüfungsleistung), hiv-resistenzdiagnostik mit gui, dicom-viewer, schachbrett-gui
- `programmieren3` beleg: verkaufsautomat für kuchen
- `projektstudium-computermuseum` rest-api für die website des htw-computermuseums
- `betriebssysteme` threads, log-analyse mit bash
- `bildverarbeitung` belegarbeiten 1-3 (farbräume, filter, hough-transformation, klassifikation auf cifar-10)
- `softwareengineering` factory pattern, rest-api mit spring boot
- `computergrafik` übungen zu webgl und three.js (roboterarm-szene als pflichtteil)
- `mixedreality` webxr-session mit vr/ar und a-frame-szene
- `deeplearning` gruppenprojekte
- `ikg` validierung von genen

## setup

- java: gradle-projekte in `programmieren1` und `programmieren2`, `programmieren3` als intellij-projekt, `softwareengineering/spring-rest-demo` mit maven, restlicher java-code ohne build-tool
- web: `computergrafik` und `mixedreality` über einen lokalen server starten (z. b. live server)
- php: laravel, abhängigkeiten über composer
- notebooks: python ≥ 3.12, z. b. in jupyterlab, vscodium oder google colab. abhängigkeiten für `bildverarbeitung` in `bildverarbeitung/pyproject.toml`, `deeplearning` braucht zusätzlich pytorch, pandas und plotly
