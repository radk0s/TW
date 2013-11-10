import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 * User: Radek
 * Date: 10.11.13
 * Time: 13:26
 * To change this template use File | Settings | File Templates.
 */
public class GrainedLockingList {

    Node first = new Node(-1,null);  // wartownik
    Lock initial = new ReentrantLock();

    class Node {
        int value;
        Lock lock;
        Node next;

        Node(int value, Node next){
            this.lock = new ReentrantLock();
            this.next = next;
            this.value = value;
        }

        void lock(){
            lock.lock();
        }

        void unlock(){
            lock.unlock();
        }
    }

    public void print(){
        Node tmp = first;
        while(tmp != null){
            System.out.print(tmp.value+" ");
            tmp = tmp.next;
        }
        System.out.println();
    }

    public boolean add(int obj){
        Node prev = first, tmp = first.next;
        prev.lock();
        while(tmp != null){
            tmp.lock();
            prev.unlock();
            prev = tmp;
            tmp = tmp.next;
        }
        if(tmp!= null) tmp.unlock();
        //prev.unlock();

        if(prev == first){
            first.next = new Node(obj, null); // oznacza, ze lista byla pusta
            return true;
        }else {
            prev.lock();
            try {
                prev.next = new Node(obj,null); // dodajemy element na koniec
                return true;
            } finally {
                prev.unlock();
            }
        }
    }
    public static void main(String... args) {
        final GrainedLockingList list = new GrainedLockingList();

        for(int i = 0; i<1000; i++){
            final int finalI = i;
            Thread b =new Thread(){
                public void run(){
                   /* try {
                        Thread.sleep((long) (Math.random()*3));
                    } catch (InterruptedException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }*/
                    list.add(finalI);
                }
            };
            b.start();
            try {
                b.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        list.print();
    }
}
