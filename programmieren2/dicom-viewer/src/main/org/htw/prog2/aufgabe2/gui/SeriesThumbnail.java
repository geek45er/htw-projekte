package org.htw.prog2.aufgabe2.gui;

import org.htw.prog2.aufgabe2.DICOMImage;

// thumbnail für die ganze serie, das meiste erbt es jetzt von ImageThumbnail
public class SeriesThumbnail extends ImageThumbnail {
    private DICOMImage dicomimage;

    public SeriesThumbnail(DICOMImage dicomimage, int size, int border) {
        super(dicomimage.getFrame(0), size, border);
        this.dicomimage = dicomimage;
    }

    @Override
    public String getDescription() {
        return "Serie (" + dicomimage.getNumFrames() + ")";
    }
}
