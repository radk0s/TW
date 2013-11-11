import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 * User: Radek
 * Date: 11.11.13
 * Time: 15:47
 * To change this template use File | Settings | File Templates.
 */
public class Node {
    private Object val;
    Node next = null;

    public Node(Object val, Node next) {
        this.val = val;
        this.next = next;
    }


    public boolean contains(Object o) {
        Node current = this;
        while (current.next != null && !current.next.val.equals(o)) {
            current = current.next;
        }
        return current.next != null;
    }

    public boolean contains(Object o, int milis) {
        Node current = this;
        while (current.next != null && !current.next.val.equals(o)) {
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
        Node current = this;
        System.out.println(current.next.val + "  " + o);
        while (current.next != null && !current.next.val.equals(o)) {
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
        Node current = this;
        while (current.next != null) {
            current = current.next;
        }
        current.next = new Node(o, null);
        return true;
    }

    public boolean add(Object o,int milis) {
        Node current = this;
        while (current.next != null) {
            current = current.next;
        }
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        current.next = new Node(o, null);
        return true;

    }

    Object getValue() {
        return val;
    }
}
