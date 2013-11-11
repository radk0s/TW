import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GrainNode {
    private Object val;
    GrainNode next = null;
    private Lock lock = new ReentrantLock();

    public GrainNode(Object val, GrainNode next) {
        this.val = val;
        this.next = next;
    }

    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }

    public boolean contains(Object o) {
        GrainNode current = this;
        current.lock();
        while (current.next != null && !current.next.val.equals(o)) {
            current.next.lock();
            current.unlock();
            current = current.next;
        }
        return current.next != null;
    }

    public boolean contains(Object o,int milis) {
        GrainNode current = this;
        current.lock();
        while (current.next != null && !current.next.val.equals(o)) {
            current.next.lock();
            current.unlock();
            current = current.next;
        }
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return current.next != null;
    }

    public boolean remove(Object o) {
        GrainNode current = this;
        current.lock();
        System.out.println(current.next.val + "  " + o);
        while (current.next != null && !current.next.val.equals(o)) {
            current.next.lock();
            current.unlock();
            current = current.next;
        }
        if (current.next == null) {
            return false;
        } else {
            current.next = current.next.next;
            return true;
        }
    }

    public boolean add(Object o) {
        GrainNode current = this;
        current.lock();
        while (current.next != null) {
            current.next.lock();
            current.unlock();
            current = current.next;
        }
        current.next = new GrainNode(o, null);
        current.unlock();
        return true;
    }

    public boolean add(Object o,int milis) {
        GrainNode current = this;
        current.lock();
        while (current.next != null) {
            current.next.lock();
            current.unlock();
            current = current.next;
        }
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        current.next = new GrainNode(o, null);
        current.unlock();
        return true;
    }

    Object getValue() {
        return val;
    }
}