package tutorium.strings;

public class Aufgabe5 {
    public static void evaluateGame(String player1, String player2) {
        if (player1.equals(player2)) {
            System.out.println("unentschieden");
            return;
        }
        if ((player1.equals("schere") && player2.equals("papier")) || (player1.equals("papier") && player2.equals("stein")) || (player1.equals("stein") && player2.equals("schere"))) {
            System.out.println("spieler 1 gewinnt");
        } else {
            System.out.println("spieler 2 gewinnt");
        }
    }

    public static void main(String[] args) {
        evaluateGame("schere", "stein");
        evaluateGame("stein", "schere");
        evaluateGame("stein", "stein");
        evaluateGame("papier", "schere");
    }
}
