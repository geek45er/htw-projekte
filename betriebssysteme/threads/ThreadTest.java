public class ThreadTest {
    static class ZaehlThread implements Runnable { //statische klasse, die das interface "runnable" implementiet



        public void run() {         // wird ausgeführt, sobald der Thread gestartet wird
            String threadName = Thread.currentThread().getName();      // holt den namen des threads
            for (int i = 0; i <= 15; i++) {     // for schleife, die 16 zahlen ausgibt, 0-15
                System.out.println(threadName + " " + i); //schleifen ausgabe
            }
        }
    }

    public static void main(String[] args) {            //main methode
        //erstellt 2 threads und übergibt jeweils eine neue instanz der runnable klasse als aufgabe
        Thread t1 = new Thread(new ZaehlThread());
        Thread t2 = new Thread(new ZaehlThread());

        //setzt thread namaen
        t1.setName("thread t1:");
        t2.setName("thread t2:");
        //threads werden gestartet, danach laufen sie parallel
        t1.start();
        t2.start();

        try {       //"join" sorgt dafür, dass main() wartet, bis beide Threads fertig sind
                    //ohne join könnte "fertig" früher ausgegeben werden als die thread ausgaben
            t1.join();
            t2.join();
        }
        catch (Exception e) { // falls ein fehler auftritt, wird dieser ausgegeben
            System.out.println("fehler");
        }


        // wird erst ausgegeben, wenn beide Threads fertig sind
        System.out.println("fertig");
    }
}
