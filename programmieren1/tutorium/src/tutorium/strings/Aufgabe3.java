package tutorium.strings;

public class Aufgabe3 {
    public static void checkLogin(String password){
        String geheim = "Java123";

        if(geheim.equals(password)) {
            System.out.println("login geht");
        } else {
            System.out.println("du dummer hund");
        }
    }

    public static void main(String[] args) {
    checkLogin("Java1234");
    }
}

