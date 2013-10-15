/**
 * Created with IntelliJ IDEA.
 * User: Radek
 * Date: 15.10.13
 * Time: 19:13
 * To change this template use File | Settings | File Templates.
 */
public class Counter {
    int count = 0;

    public void inc(){
        ++count;
    }
    public void dec(){
        --count;
    }
    public static void main(String... args){

        final Counter CounterObj = new Counter();

        new Thread(){
            public void run(){
                for(int i=0;i<30000;i++)
                    CounterObj.inc();
                System.out.println(CounterObj.count);
            }
        }.start();

        new Thread(){
            public void run(){
                for(int i=0;i<30000;i++)
                    CounterObj.dec();
                System.out.println(CounterObj.count);
            }
        }.start();
    }
}
