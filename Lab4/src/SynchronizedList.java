import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SynchronizedList {

    Lock lock = new ReentrantLock();

    private Node list = new Node(new Object() {
        public String toString() {
            return "Wartownik";
        }
    }, null);

    public synchronized boolean add(Object obj) {
        return list.add(obj);
    }

    public synchronized boolean add(Object obj, int milis) {
        return list.add(obj, milis);
    }

    public synchronized boolean remove(Object obj) {
        return list.remove(obj);
    }

    public synchronized boolean contains(Object obj) {
        return list.contains(obj);
    }

    public synchronized boolean contains(Object obj, int milis) {
        return list.contains(obj, milis);
    }

    public void print() {
        Node current = list.next;
        while (current != null) {
            System.out.print(current.getValue() + " ");
            current = current.next;
        }
        System.out.println();
    }
}
