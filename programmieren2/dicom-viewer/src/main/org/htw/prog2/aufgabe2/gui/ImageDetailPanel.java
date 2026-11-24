package org.htw.prog2.aufgabe2.gui;

import org.htw.prog2.aufgabe2.DICOMFrame;
import org.htw.prog2.aufgabe2.DICOMFrameMark;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ImageDetailPanel extends JPanel {
    private static final int MARK_SIZE = 8;
    private MainFrame mainFrame;
    private DICOMFrame frame;
    private boolean showEdges;

    public ImageDetailPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
                if(mouseWheelEvent.getWheelRotation() < 0) {
                    mainFrame.setPreviousDetailFrame();
                }
                else {
                    mainFrame.setNextDetailFrame();
                }
            }
        });
        // linksklick = markierung setzen, rechtsklick auf markierung = popup zum löschen
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (frame == null) {
                    return;
                }
                if (e.getButton() == MouseEvent.BUTTON1) {
                    addMark(e.getX(), e.getY());
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    DICOMFrameMark mark = getMarkAt(e.getX(), e.getY());
                    if (mark != null) {
                        showMarkPopup(mark, e.getX(), e.getY());
                    }
                }
            }
        });
    }

    // für den ActionListener im popup, da ist "this" der listener und nicht das panel
    private ImageDetailPanel getThis() {
        return this;
    }

    private void addMark(int x, int y) {
        boolean firstMark = frame.getMarks().isEmpty();
        frame.addMark(new DICOMFrameMark(x, y, MARK_SIZE));
        // bei der ersten markierung kommt der frame links in die liste
        if (firstMark) {
            mainFrame.addMarkedFrame(frame);
        }
        repaint();
    }

    private DICOMFrameMark getMarkAt(int x, int y) {
        for (DICOMFrameMark mark : frame.getMarks()) {
            if (mark.isInMark(x, y)) {
                return mark;
            }
        }
        return null;
    }

    private void showMarkPopup(DICOMFrameMark mark, int x, int y) {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem delete = new JMenuItem("Löschen");
        DICOMFrame markedFrame = frame;
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                markedFrame.removeMark(mark);
                // letzte markierung weg -> frame verschwindet aus der liste
                if (markedFrame.getMarks().isEmpty()) {
                    mainFrame.removeMarkedFrame(markedFrame);
                }
                getThis().repaint();
            }
        });
        popup.add(delete);
        popup.show(getThis(), x, y);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(frame == null) {
            g.setColor(Color.BLACK);
            g.drawString("Bitte ein Bild laden", getWidth()/2, getHeight()/2);
        }
        else {
            if(showEdges) {
                g.drawImage(frame.getEdges(10), 0, 0, null);
            } else {
                g.drawImage(frame.getImage(), 0, 0, null);
            }
            // markierungen als rote kreise drüber malen
            g.setColor(Color.RED);
            for (DICOMFrameMark mark : frame.getMarks()) {
                int s = mark.getSize();
                g.drawOval(mark.getX() - s, mark.getY() - s, 2 * s, 2 * s);
                g.drawOval(mark.getX() - s - 1, mark.getY() - s - 1, 2 * s + 2, 2 * s + 2);
            }
        }
    }

    public void setDetailFrame(DICOMFrame frame, boolean showEdges) {
        this.showEdges = showEdges;
        this.frame = frame;
        setPreferredSize(new Dimension(frame.getImage().getWidth(), frame.getImage().getHeight()));
    }
}
