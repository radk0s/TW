import lab3.Semaphore;

public class BuforSem {
    final int capacity;
    int count;
    Semaphore full;
    Semaphore empty;
    Semaphore critical;


    public BuforSem(int size) {
        capacity = size;
        empty = new Semaphore(size);
        full = new Semaphore(0);
        critical = new Semaphore(1);
    }

    public void get() {
        System.out.println("Try to remove");
        full.acquire();
        critical.acquire();
        count--;
        System.out.println("Item removed. Avaliable: "+count+"/"+capacity);
        critical.release();
        empty.release();
    }

    public void set() {
        System.out.println("Try to add");
        empty.acquire();
        critical.acquire();
        count++;
        System.out.println("Item added. Avaliable: "+count+"/"+capacity);
        critical.release();
        full.release();
    }

    public static void main(String... args) {
        final BuforSem testBuffor = new BuforSem(20);

        for (int i = 0; i < 1000; i++) {
            new Thread() {
                public void run() {
                    testBuffor.set();
                }
            }.start();

            new Thread() {
                public void run() {
                    testBuffor.get();
                }
            }.start();
        }
    }
}
