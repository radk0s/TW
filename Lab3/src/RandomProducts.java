import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RandomProducts {
    final Lock lock = new ReentrantLock();
    final Condition notFull  = lock.newCondition();
    final Condition notEmpty = lock.newCondition();

    int capacity = 100;
    int count = 0;

    public RandomProducts(int capacity){
        this.capacity = capacity;
    }

    public void set() throws InterruptedException {
        lock.lock();
        try {
            int newProducts = (int)(Math.random()*capacity);
            System.out.println("Try to add "+newProducts+" products.");
            while (count + newProducts > capacity){
                notFull.await();
                System.out.println("Try again to add "+newProducts+" products.");
            }
            count+=newProducts;
            System.out.println("Added " + newProducts + " products. Avaliable: " + count+"/"+capacity);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public void get() throws InterruptedException {
        lock.lock();
        try {
            int productsToGet = (int)(Math.random()*capacity);
            System.out.println("Try to remove "+productsToGet+" products.");
            while (count - productsToGet < 0){
                notEmpty.await();
                System.out.println("Try again to remove "+productsToGet+" products.");
            }
            count -= productsToGet;
            System.out.println("Removed "+productsToGet+" products. Avaliable: "+count+"/"+capacity);
            notFull.signal();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String... args) {
        final RandomProducts testBuffor = new RandomProducts(300);

        for (int i = 0; i < 1000; i++) {
            new Thread() {
                public void run() {
                    try {
                        testBuffor.set();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();

            new Thread() {
                public void run() {
                    try {
                        testBuffor.get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }
}