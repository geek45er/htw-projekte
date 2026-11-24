package org.htw.prog2.aufgabe2.gui;

import org.htw.prog2.aufgabe2.DICOMFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

// gemeinsame basis für serien- und frame-thumbnails (aus SeriesThumbnail rausgezogen)
public abstract class ImageThumbnail extends JPanel {
    protected DICOMFrame frame;
    private BufferedImage image;
    private int border;
    private boolean selected;

    public ImageThumbnail(DICOMFrame frame, int size, int border) {
        this.frame = frame;
        this.selected = false;
        this.border = border;
        this.image = new BufferedImage(size-2*border, size-2*border, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = this.image.getGraphics();
        g.drawImage(frame.getImage().getScaledInstance(size-2*border, size-2*border, BufferedImage.SCALE_SMOOTH), 0, 0, null);
        g.dispose();
        setPreferredSize(new Dimension(size, size));
        setMaximumSize(new Dimension(size, size));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, border, border, null);
        g.setColor(Color.LIGHT_GRAY);
        g.drawString(getDescription(), border+2, image.getHeight()-2);
        if(selected) {
            g.setColor(Color.BLUE);
            g.drawRect(border-1, border-1, image.getWidth()+1, image.getHeight()+1);
            g.drawRect(border-2, border-2, image.getWidth()+3, image.getHeight()+3);
        }
    }

    public abstract String getDescription();

    public void setSelected(boolean selected) {
        this.selected = selected;
        repaint();
    }
}
