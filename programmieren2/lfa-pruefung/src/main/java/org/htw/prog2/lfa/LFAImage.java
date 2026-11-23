package org.htw.prog2.lfa;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// fuers einlesen vom bild und die intensitaetsberechnung darauf
public class LFAImage {
    BufferedImage image;

    public LFAImage(File imagefile) throws IOException {
        image = ImageIO.read(imagefile);
    }

    public BufferedImage getImage() {
        return image;
    }

    // mittelt die intensitaet ueber den bereich, pixel ausserhalb vom bild zaehlen nicht mit
    public double getAverageIntensity(int[] coords) {
        int x1 = coords[0];
        int y1 = coords[1];
        int x2 = coords[2];
        int y2 = coords[3];

        // bereich auf die tatsaechliche bildgroesse begrenzen
        int startX = Math.max(0, x1);
        int startY = Math.max(0, y1);
        int endX = Math.min(image.getWidth() - 1, x2);
        int endY = Math.min(image.getHeight() - 1, y2);

        int summe = 0;
        int anzahl = 0;

        for (int y = startY; y <= endY; y++) {
            for (int x = startX; x <= endX; x++) {
                Color farbe = new Color(image.getRGB(x, y));
                int rot = farbe.getRed();
                int gruen = farbe.getGreen();
                int blau = farbe.getBlue();
                summe += (256 - rot) + (256 - gruen) + (256 - blau);
                anzahl++;
            }
        }

        // liegt der bereich komplett ausserhalb vom bild, sonst division durch 0
        if (anzahl == 0) {
            return 0;
        }
        return (double) summe / anzahl;
    }

    // wie getAverageIntensity, nur wird die intensitaet vom hintergrund drumrum noch abgezogen
    public double getAverageIntensityCorrected(int[] coords, int border) {
        int x1 = coords[0];
        int y1 = coords[1];
        int x2 = coords[2];
        int y2 = coords[3];

        double haupt = getAverageIntensity(coords);

        // die border pixel direkt ueber dem bereich
        double oben = getAverageIntensity(new int[] {x1, y1 - border, x2, y1 - 1});
        // die border pixel direkt unter dem bereich
        double unten = getAverageIntensity(new int[] {x1, y2 + 1, x2, y2 + border});

        return haupt - (oben + unten) / 2.0;
    }
}
