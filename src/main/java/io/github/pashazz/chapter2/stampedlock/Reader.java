package io.github.pashazz.chapter2.stampedlock;

import io.github.pashazz.Log;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

public class Reader implements Runnable {
    private final Position pos;
    private final StampedLock lock;
    private static int NUM_READS = 50;

    public Reader(Position pos, StampedLock lock) {
        this.pos = pos;
        this.lock = lock;
    }


    @Override
    public void run() {
        for (int i = 0; i < NUM_READS; ++i) {
            long stamp = lock.readLock();
            try {
                Log.info("Read lock acquired: %s: (%s, %s)", stamp, pos.getX(), pos.getY());
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlockRead(stamp);
                Log.info("Read lock released: %s", stamp);
            }
        }
    }
}
