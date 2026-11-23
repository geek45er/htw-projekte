package tutorium.strings;

public class Aufgabe7 {
    public static void printFrameWord(String word, String frameSymbol) {
        int length = word.length() + 2;

        System.out.println(frameSymbol.repeat(length));

        System.out.println(frameSymbol + word + frameSymbol);

        System.out.println(frameSymbol.repeat(length));

    }

    public static void main(String[] args) {
        printFrameWord("java", "*");

    }
}




