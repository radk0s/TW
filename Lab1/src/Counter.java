/**
 * Created with IntelliJ IDEA.
 * User: Radek
 * Date: 15.10.13
 * Time: 19:13
 * To change this template use File | Settings | File Templates.
 */

/*
    Windows 64 bit okolo 2.7sek, z synch okolo 7 sek
 */
public class Counter {
    int count = 0;


    public synchronized void inc(){
        ++count;
    }
    public synchronized void dec(){
        --count;
    }
    public static void main(String... args){

        final Counter CounterObj = new Counter();

        new Thread(){
            public void run(){
                for(int i=0;i<50000000;i++)
                    CounterObj.inc();
                System.out.println(CounterObj.count);
            }
        }.start();

        new Thread(){
            public void run(){
                for(int i=0;i<50000000;i++)
                    CounterObj.dec();
                System.out.println(CounterObj.count);
            }
        }.start();
    }
}
