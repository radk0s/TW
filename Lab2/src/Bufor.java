import java.util.LinkedList;
import java.util.List;

public class Bufor {

    final List<String> bufor;
    final int MAX;

    public Bufor(int size) {
        bufor = new LinkedList<String>();
        MAX = size;
    }

    public String get() {
        String val;
        synchronized (bufor) {
            while (bufor.size() == 0) {
                try {
                    System.out.println(Thread.currentThread() + " ----> wait for get");
                    bufor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread() + " ----> remove()");
            val = bufor.remove(0);
            bufor.notifyAll();
        }
        return val;
    }

    /*
        public void put(E e) throws InterruptedException {
            if (e == null) throw new NullPointerException();
            final E[] items = this.items;
            final ReentrantLock lock = this.lock;
            lock.lockInterruptibly();
            try {
                try {
                    while (count == items.length)
                        notFull.await();
                } catch (InterruptedException ie) {
                    notFull.signal(); // propagate to non-interrupted thread
                    throw ie;
                }
                insert(e);
            } finally {
                lock.unlock();
            }
        }
    */
    public void set(String value) {
        synchronized (bufor) {
            while (bufor.size() == MAX) {
                try {
                    System.out.println(Thread.currentThread() + " ----> wait for add");
                    bufor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread() + " ----> add()");
            bufor.add(value);
            bufor.notifyAll();
        }
    }

    /*
        public E take() throws InterruptedException {
            final ReentrantLock lock = this.lock;
            lock.lockInterruptibly();
            try {
                try {
                    while (count == 0)
                        notEmpty.await();
                } catch (InterruptedException ie) {
                    notEmpty.signal(); // propagate to non-interrupted thread
                    throw ie;
                }
                E x = extract();
                return x;
            } finally {
                lock.unlock();
            }
        }
    */
    public static void main(String... args) {
        final Bufor testBuffor = new Bufor(3);

        for (int i = 0; i < 1000; i++) {
            new Thread() {
                public void run() {
                    testBuffor.set(" " + Thread.currentThread());
                }
            }.start();

            new Thread() {
                public void run() {
                    System.out.println("GET: " + testBuffor.get());
                }
            }.start();
        }
    }
}
