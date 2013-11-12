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

        for(int i = 0; i < 200 ; i++){
            list.add(i);
            list2.add(i);
        }


        long start = System.currentTimeMillis();
            Thread  td = new Thread(){
                public void run(){
                    for(int i = 200; i > 0 ; i--){
                        list.contains(i,1);
                    }
                }
            };
        Thread  td2 = new Thread(){
                public void run(){
                    for(int i = 200; i > 0 ; i--){
                        list.contains(i,1);
                    }
                }
            };
            td.start();
            td2.start();
            try {
                td.join();
                td2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        long stop = System.currentTimeMillis();
        System.out.println("Time diff contains: "+(stop-start));

        start = System.currentTimeMillis();
        Thread  td3 = new Thread(){
            public void run(){
                for(int i = 200; i > 0 ; i--){
                    list2.contains(i,1);
                }
            }
        };
        Thread  td4 = new Thread(){
            public void run(){
                for(int i = 200; i > 0 ; i--){;
                    list2.contains(i,1);
                }
            }
        };
        td3.start();
        td4.start();
        try {
            td3.join();
            td4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        stop = System.currentTimeMillis();
        System.out.println("Time diff contains: "+(stop-start));
    }
}
