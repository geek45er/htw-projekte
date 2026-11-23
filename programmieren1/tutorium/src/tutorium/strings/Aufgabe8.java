package tutorium.strings;

public class Aufgabe8 {
    public static void makeTag(String tag, String text) {
    System.out.println("<" + tag + ">" + text + "<" + tag+ "/>");
    }
    public static void makeTag(String tag) {
        System.out.println("<" + tag + "/>");
    }

    public static void main(String[] args) {
        makeTag("b","hallo");
        makeTag("br");
    }
}
