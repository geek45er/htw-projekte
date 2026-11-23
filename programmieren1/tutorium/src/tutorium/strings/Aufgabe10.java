package tutorium.strings;

public class Aufgabe10 {
    public static void drawShape(String shape, int size) {
        if (shape == "linie") {
            for (int i = 0; i < size; i++)
                System.out.print("*");
            System.out.println();
        } else if (shape == "quadrat") {
            for (int a = 0; a < size; a++) {
                for (int b = 0; b < size; b++) {
                    System.out.print("*");
                }
                System.out.println();
            }
        } else {
            System.out.println("gibts nicht hund");
        }
    }

    public static void main(String[] args) {
        drawShape("linie", 10);
        System.out.println();
        drawShape("quadrat", 4);
    }
}

