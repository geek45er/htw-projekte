package org.htw.prog2.aufgabe2.gui;

import org.htw.prog2.aufgabe2.DICOMFrame;

// thumbnail für einen einzelnen markierten frame
public class FrameThumbnail extends ImageThumbnail {
    private DICOMFrame frame;

    public FrameThumbnail(DICOMFrame frame, int size, int border) {
        super(frame, size, border);
        this.frame = frame;
    }

    public DICOMFrame getFrame() {
        return frame;
    }

    @Override
    public String getDescription() {
        return "Markiert";
    }
}
