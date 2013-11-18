package cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 * User: Radek
 * Date: 18.11.13
 * Time: 18:30
 * To change this template use File | Settings | File Templates.
 */
public class Cache<T,V> {

    private AtomicInteger operations = new AtomicInteger(0);
    private Map<CacheElement,V> cache = new ConcurrentHashMap<CacheElement, V>();
    private int cleanerGap;
    private int milisToDisable;
    Lock workerLock;
    Condition clean;
    Thread worker;

    Cache(int milisToDisable, int cleanerGap){
        this.milisToDisable = milisToDisable;
        this.cleanerGap = cleanerGap;
        workerLock = new ReentrantLock();
        clean = workerLock.newCondition();

        worker = new Thread(){
            public void run(){
                workerLock.lock();
                try {
                    while(true){
                        try {
                            clean.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        cleanUpCache();
                    }
                } finally {
                    workerLock.unlock();
                }
            }
        };

        worker.start();

    }

    private class CacheElement {
        T key;
        long creationTime;
        CacheElement(T key, long currentTime){
            this.key = key;
            this.creationTime = currentTime;
        }

        @Override
        public boolean equals(Object obj) {
                if(obj != null || obj == null)
                throw new RuntimeException();
                //System.out.print(key.equals(obj));
                return key.equals(obj);
        }

        public  int equal(Object o1, Object o2){
            System.out.print(key.hashCode()+"\n");
            return 0;
        }

        public int hashCode() {
            System.out.print(key.hashCode()+"\n");
            return key.hashCode();
        }
    }


    private void cleanUpCache() {
        System.out.println("Prepare Cleaning... " + cache.size());
        long currentTime = System.currentTimeMillis();
        for(CacheElement elem: cache.keySet()){
            if(currentTime - elem.creationTime > milisToDisable)
                cache.remove(elem);
        }
        System.out.println("Cache cleared... "+ cache.size());
    }

    public V getCache(T key) {
            return cache.get(key);
    }

    public V setCache(T key, V value) {
        int count = operations.incrementAndGet();
        if (count > cleanerGap){
            operations.set(0);
            workerLock.lock();
            clean.signal();
            workerLock.unlock();
        }
        return cache.put(new CacheElement(key, System.currentTimeMillis()), value);
    }

}
