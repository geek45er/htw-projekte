package tutorium.strings;

public class Aufgabe9 {
    public static void buyDrink(String drink, int money) {
        int price;
        if (drink.equals("Cola")) {
            price = 2;
        } else if (drink.equals("Wasser")) {
            price = 1;
        } else if (drink.equals("Saft")) {
            price = 3;
        } else {
            System.out.println("bist du dumm?");
            return;
        }


        if (money < price) {
            System.out.println("brokie");
        } else {
            int restgeld = money - price;

            System.out.println("hier ist dein/e: " + drink + " und dein rückgeld: " + restgeld + " euronen.");
        }
    }

    public static void main(String[] args) {
        buyDrink("Saft", 10);
        buyDrink("Cola", 500);
        buyDrink("wasser", 2);
        buyDrink("Limonade", 5);
    }
}
