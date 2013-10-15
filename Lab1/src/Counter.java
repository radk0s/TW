import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * User: Radek
 * Date: 15.10.13
 * Time: 19:13
 * To change this template use File | Settings | File Templates.
 */

/*
    Windows 64 bit prawie natychmiast, z synch okolo 41 sek - core i5-3210m, z atomic okolo 32 sek
    Ubuntu 12.10 OpenJDK 64 bit prawie natychmiast, z synch okolo 45 sek - core2duo p8600
 */
public class Counter {

    private final AtomicInteger count= new AtomicInteger(0);
    //int count = 0;


    public static void main(String... args){

        final Counter CounterObj = new Counter();

        new Thread(){
            public void run(){
                for(int i=0;i<500000000;i++)
                    CounterObj.count.getAndIncrement();
                    /*synchronized(CounterObj){
                        CounterObj.count++;
                    }*/
                System.out.println(CounterObj.count);
            }
        }.start();

        new Thread(){
            public void run(){
                for(int i=0;i<500000000;i++)
                    CounterObj.count.getAndDecrement();
                    /*synchronized(CounterObj){
                        CounterObj.count--;
                    }*/
                System.out.println(CounterObj.count);
            }
        }.start();
    }
}
