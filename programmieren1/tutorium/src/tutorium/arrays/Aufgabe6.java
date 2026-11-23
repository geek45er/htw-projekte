package tutorium.arrays;

public class Aufgabe6 {
    public static void main(final String[] args) {
        /*
        for (int i = 1; 5 >= i; i++) {
       for (int j = 1; j <= i; j++) {
            System.out.print("*");
        }
        System.out.println();
    }

         */

        int row = 5;

        for (int i = 1; i <= row; i++) {
            for (int j = 0; j < row - i; j++) {
                System.out.print(" ");
            }
            for (int j = 0; j < i; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
}



