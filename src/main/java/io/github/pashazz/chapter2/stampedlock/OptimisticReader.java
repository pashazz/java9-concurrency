package io.github.pashazz.chapter2.stampedlock;

import io.github.pashazz.Log;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

public class OptimisticReader implements Runnable {
    private final Position pos;
    private final StampedLock lock;
    private static int NUM_READS = 100;

    public OptimisticReader(Position pos, StampedLock lock) {
        this.pos = pos;
        this.lock = lock;
    }


    @Override
    public void run() {
        for (int i = 0; i < NUM_READS; ++i) {
            long stamp = lock.tryOptimisticRead();
            try {
                int x = pos.getX();
                int y = pos.getY();
                if (lock.validate(stamp)) { //не было записей с момента вызова tryOptimisticRead
                    Log.info("Optimistic read lock %s: (%s, %s)", stamp, pos.getX(), pos.getY());
                } else {
                    Log.info("Unable to obtain optimistic thread lock %s: write happened", stamp);
                }
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
