package tutorium.strings;

public class Aufgabe7_1 {
    public static void printFrameWord(String word, String frameSymbol) {
        String border = "";
        for (int i = 0; i < word.length() + 2; i++) {
            border = border + frameSymbol;
        }
        System.out.println(border);
        System.out.println(frameSymbol + word + frameSymbol);
        System.out.println(border);
    }

    public static void main(String[] args) {
        printFrameWord("java","#");
    }
}
