package org.htw.prog2.aufgabe2.gui;

import org.htw.prog2.aufgabe2.DICOMFrame;
import org.htw.prog2.aufgabe2.DICOMImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class ImageListPanel extends JPanel {
    private DICOMImage image;
    private MainFrame mainFrame;
    private SeriesThumbnail seriesThumbnail;
    private LinkedList<FrameThumbnail> frameThumbnails = new LinkedList<>();
    private int width;

    public ImageListPanel(int width, MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.width = width;
        JLabel name = new JLabel("Bilder");
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);
        add(name);
        add(Box.createVerticalStrut(10));
        name.setAlignmentX(0);
        setPreferredSize(new Dimension(width, 400));
    }

    public void setImage(DICOMImage newImage) {
        // altes bild und alte markierte frames rausnehmen
        if (seriesThumbnail != null) {
            remove(seriesThumbnail);
        }
        for (FrameThumbnail t : frameThumbnails) {
            remove(t);
        }
        frameThumbnails.clear();

        image = newImage;
        seriesThumbnail = new SeriesThumbnail(image, getThumbnailSize(), 5);
        seriesThumbnail.setAlignmentX(0);
        seriesThumbnail.setAlignmentY(0);
        seriesThumbnail.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                seriesThumbnail.setSelected(true);
                mainFrame.setDetailSeries(image);
            }
        });
        add(seriesThumbnail);
        updateSize();
    }

    private int getThumbnailSize() {
        return getWidth() > 0 ? getWidth() : width;
    }

    // höhe so setzen, dass alle thumbnails reinpassen (wichtig für das JScrollPane)
    private void updateSize() {
        int w = getThumbnailSize();
        setPreferredSize(new Dimension(w, w * (2 + frameThumbnails.size())));
        revalidate();
        repaint();
    }

    public void addMarkedFrame(DICOMFrame frame) {
        FrameThumbnail thumbnail = new FrameThumbnail(frame, getThumbnailSize(), 5);
        thumbnail.setAlignmentX(0);
        thumbnail.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    showFramePopup(thumbnail, e.getX(), e.getY());
                } else {
                    // linksklick: den markierten frame in der detailansicht anzeigen
                    mainFrame.showFrame(frame);
                }
            }
        });
        frameThumbnails.add(thumbnail);
        add(thumbnail);
        updateSize();
    }

    // rechtsklick-menü auf einem markierten frame: entfernen oder als jpg speichern
    private void showFramePopup(FrameThumbnail thumbnail, int x, int y) {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem removeItem = new JMenuItem("Entfernen");
        JMenuItem saveItem = new JMenuItem("Speichern");
        removeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DICOMFrame frame = thumbnail.getFrame();
                frame.getMarks().clear();
                // nicht direkt entfernen, sondern über MainFrame, damit alles konsistent bleibt
                mainFrame.removeMarkedFrame(frame);
            }
        });
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFrame(thumbnail.getFrame());
            }
        });
        popup.add(removeItem);
        popup.add(saveItem);
        popup.show(thumbnail, x, y);
    }

    private void saveFrame(DICOMFrame frame) {
        JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
        chooser.setFileFilter(new FileNameExtensionFilter("JPEG", "jpg", "jpeg"));
        if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File file = chooser.getSelectedFile();
        if (!file.getName().toLowerCase().endsWith(".jpg")) {
            file = new File(file.getAbsolutePath() + ".jpg");
        }
        try {
            // jpg kann keinen alphakanal, darum vorher in ein rgb bild kopieren
            java.awt.image.BufferedImage source = frame.getImage();
            java.awt.image.BufferedImage rgb = new java.awt.image.BufferedImage(source.getWidth(), source.getHeight(),
                    java.awt.image.BufferedImage.TYPE_INT_RGB);
            rgb.getGraphics().drawImage(source, 0, 0, null);
            ImageIO.write(rgb, "jpg", file);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Fehler beim Speichern: " + ex.getMessage());
        }
    }

    public void removeMarkedFrame(DICOMFrame frame) {
        for (FrameThumbnail t : frameThumbnails) {
            if (t.getFrame() == frame) {
                remove(t);
                frameThumbnails.remove(t);
                break;
            }
        }
        updateSize();
    }

    // nur der thumbnail vom angezeigten frame bekommt den rahmen
    public void setSelectedFrame(DICOMFrame frame) {
        for (FrameThumbnail t : frameThumbnails) {
            t.setSelected(t.getFrame() == frame);
        }
    }
}
