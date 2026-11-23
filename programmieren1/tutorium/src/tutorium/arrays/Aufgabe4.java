package tutorium.arrays;

public class Aufgabe4 {
    public static boolean isEven(int n) {
        if (n % 2 == 0) {
            return true;
    } else

    {
        return false;
    }
}

public static boolean isUneven(int n) {
        return n%2 == 1;
}


        public static void main(String[] args) {
            System.out.println(isEven(4));
            System.out.println(isEven(7));
            System.out.println(isUneven(2));
            System.out.println(isUneven(3));
        }
    }

