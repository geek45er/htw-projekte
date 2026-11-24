public class Programmieraufgabe3 {
    public static void main(String[] args) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 7; j++) {
                if (i % 2 == 0) {   //prüft ob i/2 = gerade ist. rechnet in !ganzzahlen! liefert den rest einer division
                    System.out.print("*");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }
}


/*
*******
.......
*******
.......
 */