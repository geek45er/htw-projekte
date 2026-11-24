package org.htw.prog2.aufgabe2;

// eine markierung auf einem frame, x/y ist der mittelpunkt
public class DICOMFrameMark {
    private int x;
    private int y;
    private int size;

    public DICOMFrameMark(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    // liegt der punkt innerhalb von x +- size und y +- size?
    public boolean isInMark(int x, int y) {
        return Math.abs(x - this.x) <= size && Math.abs(y - this.y) <= size;
    }
}
