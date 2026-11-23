package tutorium.strings;


public class Aufgabe4 {
    public static void repeatText(String text, int count) {
        for (int i = 0; i < count; i++) {
            System.out.print(text);
        }
    }
        public static void main (String[]args){
            repeatText("bla", 3);
        }
    }
