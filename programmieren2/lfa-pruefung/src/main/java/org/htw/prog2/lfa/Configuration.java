package org.htw.prog2.lfa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

// liest die koordinaten aus der config datei ein
public class Configuration {
    int[] controlCoordinates;
    int[] testCoordinates;
    double ratio;
    boolean borderEnabled = false;
    int borderWidth = 10;

    public Configuration(File configfile) throws IOException, FormatException {
        BufferedReader reader = new BufferedReader(new FileReader(configfile));
        String line;
        while ((line = reader.readLine()) != null) {
            // leere zeilen ignorieren
            if (line.strip().isEmpty()) {
                continue;
            }
            parseLine(line);
        }
        reader.close();
    }

    // je nach name am zeilenanfang wird control, test oder ratio gesetzt
    private void parseLine(String line) throws FormatException {
        String[] teile = line.split(" ");
        String name = teile[0];

        if (name.equals("control")) {
            controlCoordinates = parseCoordinates(line, teile, "control");
        } else if (name.equals("test")) {
            testCoordinates = parseCoordinates(line, teile, "test");
        } else if (name.equals("ratio")) {
            if (teile.length != 2) {
                throw new FormatException(line, "wrong number of fields (expected 2: ratio <ratio>)");
            }
            try {
                ratio = Double.parseDouble(teile[1]);
            } catch (NumberFormatException e) {
                throw new FormatException(line, "ratio could not be parsed as a double");
            }
        } else {
            throw new FormatException(line, "unknown name '" + name + "'");
        }
    }

    // liest die 4 koordinaten aus der zeile raus
    private int[] parseCoordinates(String line, String[] teile, String name) throws FormatException {
        if (teile.length != 5) {
            throw new FormatException(line, "wrong number of fields (expected 5: " + name
                    + " <upper left x> <upper left y> <lower right x> <lower right y>)");
        }

        int[] koordinaten = new int[4];
        try {
            for (int i = 0; i < 4; i++) {
                koordinaten[i] = Integer.parseInt(teile[i + 1]);
            }
        } catch (NumberFormatException e) {
            throw new FormatException(line, "one of the coordinates could not be parsed as an integer");
        }
        return koordinaten;
    }

    public int[] getControlCoordinates() {
        return controlCoordinates;
    }

    public int[] getTestCoordinates() {
        return testCoordinates;
    }

    public double getRatio() {
        return ratio;
    }

    public boolean isBorderEnabled() {
        return borderEnabled;
    }

    public void setBorderEnabled(boolean enabled) {
        borderEnabled = enabled;
    }

    public int getBorderWidth() {
        return borderWidth;
    }
}
