public class Main {


    public static void printGreeting(String name) {
        System.out.println("Hallo " + name);
    }

    public static void calculate(int numOne, int numTwo, String operation) {
        double result;

// if ( operation.equals("sum")) {
//     result = numOne + numTwo;
//     System.out.println(numOne + " + " + numTwo + " = " + result);
// } else if (operation.equals("difference")) {
//     result = numOne - numTwo;
//     System.out.println(numOne + " - " + numTwo + " = " + result);
// } else if (operation.equals("quotient")) {
//     result = numOne / numTwo;
//     System.out.println(numOne + " / " + numTwo + " = " + result);
// } else if (operation.equals("product")) {
//     result = numOne * numTwo;
//     System.out.println(numOne + " * " + numTwo + " = " + result);
// } else {
//     System.out.println("unbekannter operator");
// }
//     }

        switch (operation) {        // "lesbarer; weniger code; switch kann schneller sein - jumptables, aber meist wohl erst bei größeren switch cases; neue cases einfacher hinzufügen;" -von claude
            case "sum" -> {
                result = numOne + numTwo;
                System.out.println(numOne + " + " + numTwo + " = " + result);
            }
            case "difference" -> {
                result = numOne - numTwo;
                System.out.println(numOne + " - " + numTwo + " = " + result);
            }
            case "quotient" -> {
                if (numTwo == 0) {
                    System.out.println("Teilen durch 0 nicht möglich");
                    return;
                }
                result = (double) numOne / numTwo; //(double), weil numOne und num Two int sind, und int/int keine Nachkommastelle behält   || 10/3 = 3 (int) & 10/3 = 3.3333333333333335 (double)
                System.out.println(numOne + " / " + numTwo + " = " + result);
            }
            case "product" -> {
                result = numOne * numTwo;
                System.out.println(numOne + " * " + numTwo + " = " + result);
            }
            default -> System.out.println("Unbekannte Operation");
        }
    }

    public static double calculatePerimeter(double sideLength) {
        if (sideLength < 0) {
            System.out.println("Negative Kantenlängen sind nicht möglich");
            return -1; // fehlercode -1 bei ungültiger eingabe, methode wird genau hier beendet
        }
        double perimeter = 4 * sideLength;
        System.out.println("Der Umfang von einem Quadrat mit den Kantenlängen " + sideLength + " beträgt " + perimeter);
        return perimeter;
    }

    public static double calculatePerimeter(double length, double width) {
        if (length < 0 || width < 0) { //oder
            System.out.println("Negative Kantenlängen sind nicht möglich");
            return -1;
        }

        double perimeter = 2 * (length + width);
        System.out.println("Der Umfang von einem Rechteck mit den Kantenlängen " + length + " und " + width + " beträgt " + perimeter);
        return perimeter;
    }


    public static void main(String[] args) {
        System.out.println("\nbegrueßen:");
        printGreeting("Tom");
        printGreeting("Lia" + "\n");

        System.out.println("rechnen simpel:");
        calculate(1, 1, "sum");
        calculate(4, 2, "difference");
        calculate(10, 3, "quotient");
        calculate(5, 10, "product");
        calculate(1, 1, "summmme"); //absichtlich fehler mit: "summmme", zum Testen der funktion
        System.out.println();

        System.out.println("berechnen der Vierecke:");
        calculatePerimeter(10.0);
        calculatePerimeter(5.0, 6.0);
        calculatePerimeter(-10.0);
        calculatePerimeter(-5.0, -6.0);

        System.out.println("\ndone");
    }
}
