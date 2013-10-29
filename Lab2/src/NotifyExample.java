public class NotifyExample {

    int i = 0;

    public synchronized int inc(){
        while (i == 1) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        i++;
        notify();
        return i;
    }

    public synchronized int dec(){
        while (i == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        i--;
        notify();
        return i;
    }
    public static void main(String... args) {
        final NotifyExample test = new NotifyExample();

        for(int i = 0 ; i< 1000; i++) {
            final Thread t1 = new Thread(){
                public void run(){
                    int current = test.inc();
                    if( current != 0 && current != 1)
                        System.out.println("Alert!  "+current);
                }
             };

            final Thread t2 = new Thread(){
                public void run(){
                    int current = test.dec();
                    if( current != 0 && current != 1)
                        System.out.println("Alert!  "+current);
                }
            };

            t1.start();
            t2.start();
        }
    }
}
