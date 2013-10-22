import java.util.concurrent.atomic.AtomicInteger;

/*
    Windows 7 64 bit Java Oracle | natychmiast, z synch okolo 41 sek, z atomic okolo 31 sek - core i5-3210m  2/4
    Ubuntu 12.10 64 bit OpenJDK | natychmiast, z synch okolo 45 sek, z atomic okolo 21 sek - core2duo p8600  2/2


    Przy 1k watkow utrudnione np. sluchanie muzyki, przelaczanie sie pomiedzy programami
    Przy 2k watkow: pierwsze sekundy ok, potem sie nie da nic robic
    cmd test wypisal 7547 watkow
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
                    //synchronized(CounterObj){
                    //    CounterObj.count++;
                    //}
                System.out.println(CounterObj.count);
            }
        }.start();

        new Thread(){
            public void run(){
                for(int i=0;i<500000000;i++)
                    CounterObj.count.getAndDecrement();
                    //synchronized(CounterObj){
                    //    CounterObj.count--;
                    //}
                System.out.println(CounterObj.count);
            }
        }.start();

        for( int i = 0; i <500; i++){
            System.out.println(i);
            new Thread(){
                public void run(){
                    for(;;);
                }
            }.start();
        }

    }
}
