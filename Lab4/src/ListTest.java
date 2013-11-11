/**
 * Created with IntelliJ IDEA.
 * User: Radek
 * Date: 11.11.13
 * Time: 15:53
 * To change this template use File | Settings | File Templates.
 */
public class ListTest {
    public static void main(String... args) {
        final GrainedLockingList list = new GrainedLockingList();
        final SynchronizedList list2 = new SynchronizedList();

        long start = System.currentTimeMillis();
        for(int i =0;i< 500; i++){
            final int finalI = i;
            Thread  td = new Thread(){
                public void run(){
                    list.add(finalI,1);
                }
            };
            td.start();
            try {
                td.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long stop = System.currentTimeMillis();
        System.out.println("Time diff add: "+(stop-start));

        start = System.currentTimeMillis();
        for(int i =0;i< 500; i++){
            final int finalI = i;
            Thread  td = new Thread(){
                public void run(){
                    list2.add(finalI,1);
                }
            };
            td.start();
            try {
                td.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        stop = System.currentTimeMillis();
        System.out.println("Time diff add: "+(stop-start));

        start = System.currentTimeMillis();
        for(int i =500; i > 0; i--){
            final int finalI = i;
            Thread  td = new Thread(){
                public void run(){
                    list.contains(finalI);
                }
            };
            td.start();
            try {
                td.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        stop = System.currentTimeMillis();
        System.out.println("Time diff contains: "+(stop-start));

        start = System.currentTimeMillis();
        for(int i =500; i > 0; i--){
            final int finalI = i;
            Thread  td = new Thread(){
                public void run(){
                    list2.contains(finalI);
                }
            };
            td.start();
            try {
                td.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        stop = System.currentTimeMillis();
        System.out.println("Time diff contails: "+(stop-start));

    }
}
