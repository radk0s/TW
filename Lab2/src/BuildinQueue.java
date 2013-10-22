import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created with IntelliJ IDEA.
 * User: Radek
 * Date: 22.10.13
 * Time: 18:47
 * To change this template use File | Settings | File Templates.
 */
public class BuildinQueue {

    public static void main(String... args){
    final BlockingQueue<String> queue = new ArrayBlockingQueue<String>(10);
        for(int i = 0 ; i< 10; i++) {
            new Thread(){
                public void run(){
                    //for(int i = 0;i<10;i++)
                    try {
                        System.out.println("PUT: " + Thread.currentThread());
                        queue.put(" " + Thread.currentThread());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();

            new Thread(){
                public void run(){
                    //for(int i = 0;i<10;i++)
                    try {
                        System.out.println("GET: "+queue.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }
}
