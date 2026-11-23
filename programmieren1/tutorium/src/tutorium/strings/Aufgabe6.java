package tutorium.strings;

public class Aufgabe6 {
    public static void buildStairs(String material, int steps) {
        for (int i = 0; i < 5; i++) {
            System.out.print(material);
            for (int j = 0; j < i; j++) {
                System.out.print(material);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        buildStairs("#", 4);
    }
}
