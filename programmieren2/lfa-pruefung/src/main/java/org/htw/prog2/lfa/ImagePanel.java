package org.htw.prog2.lfa;

import javax.swing.*;
import java.awt.*;

// zeigt das bild an und zeichnet die bereiche aus der config mit rein
public class ImagePanel extends JPanel {
    LFAImage image;
    Configuration config;

    public ImagePanel() {
        // groesse einfach starr auf die groesse der beispielbilder setzen
        Dimension groesse = new Dimension(31, 78);
        setPreferredSize(groesse);
        setMinimumSize(groesse);
        setMaximumSize(groesse);
    }

    public void setImage(LFAImage image) {
        this.image = image;
        repaint();
    }

    public void setConfig(Configuration config) {
        this.config = config;
        repaint();
    }

    public LFAImage getImage() {
        return image;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // wenn noch gar nichts geladen ist, einfach nur N/A hinschreiben
        if (image == null && config == null) {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.BLACK);
            g.drawString("N/A", 5, 40);
            return;
        }

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        if (image != null) {
            g.drawImage(image.getImage(), 0, 0, this);
        }

        if (config != null) {
            g.setColor(Color.BLUE);
            zeichneKasten(g, config.getControlCoordinates());
            zeichneKasten(g, config.getTestCoordinates());

            if (config.isBorderEnabled()) {
                g.setColor(Color.BLACK);
                zeichneBorderKaesten(g, config.getControlCoordinates(), config.getBorderWidth());
                zeichneBorderKaesten(g, config.getTestCoordinates(), config.getBorderWidth());
            }
        }
    }

    // rahmen um die koordinaten drumrum
    private void zeichneKasten(Graphics g, int[] coords) {
        int x1 = coords[0];
        int y1 = coords[1];
        int x2 = coords[2];
        int y2 = coords[3];
        g.drawRect(x1, y1, x2 - x1, y2 - y1);
    }

    // die zwei kaestchen ueber und unter dem bereich, die fuer die korrektur benutzt werden
    private void zeichneBorderKaesten(Graphics g, int[] coords, int border) {
        int x1 = coords[0];
        int y1 = coords[1];
        int x2 = coords[2];
        int y2 = coords[3];

        g.drawRect(x1, y1 - border, x2 - x1, border - 1);
        g.drawRect(x1, y2 + 1, x2 - x1, border - 1);
    }
}
