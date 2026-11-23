public class SimpleThread {

    static class Counter implements Runnable {
        private final String name;

        Counter(String name) {
            this.name = name;
        }

        public void run() {
            for (int i = 0; i <= 20; i++) {
                System.out.println(name + " " + i);
            }
        }
    }

    public static void main(String[] args) {
        Thread a = new Thread(new Counter("A"));
        Thread b = new Thread(new Counter("B"));
        a.start();
        b.start();

        try {
            a.join();
            b.join();
        }
        catch (InterruptedException e ) {
            System.out.println("thread unterbrochen");
        }

        System.out.println("fertig");

    }
}
