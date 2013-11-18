package cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cache<T,V> {

    private AtomicInteger operations = new AtomicInteger(0);
    private Map<T ,CacheElement> cache = new ConcurrentHashMap<T, CacheElement>();
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
        V value;
        long creationTime;
        CacheElement(V value, long currentTime){
            this.value = value;
            this.creationTime = currentTime;
        }
    }


    private void cleanUpCache() {
        System.out.println("Prepare Cleaning... " + cache.size());
        long currentTime = System.currentTimeMillis();
        for(Map.Entry<T, CacheElement> elem: cache.entrySet()){
            if(currentTime - elem.getValue().creationTime > milisToDisable)
                cache.remove(elem.getKey());
        }
        System.out.println("Cache cleaned... "+ cache.size());
    }

    public V getCache(T key) {
            CacheElement result = cache.get(key);
            return result == null || System.currentTimeMillis() - result.creationTime > milisToDisable?null:result.value;
    }

    public void setCache(T key, V value) {
        int count = operations.incrementAndGet();
        if (count > cleanerGap){
            operations.set(0);
            workerLock.lock();
            clean.signal();
            workerLock.unlock();
        }
        cache.put(key, new CacheElement(value,System.currentTimeMillis()));
    }

}
