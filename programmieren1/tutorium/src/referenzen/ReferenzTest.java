package referenzen;

public class ReferenzTest {
    public static void doSomething(int[] values) {
        values = new int[2];
        values[0] = 5;
    }

    public static void main(String[] args) {
        int[] vals = new int[]{0, 1, 2, 3};
        System.out.println(vals[0]);
        doSomething(vals);
        System.out.println(vals[0]);
    }
}
