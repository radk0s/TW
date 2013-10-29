package lab3;

public class Semaphore {
    private int count;

    public Semaphore(int count){
        this.count = count;
    }

    public synchronized void acquire() {
        while(count == 0)
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        count--;
    }

    public synchronized void release() {
        count++;
        notify();
    }

}