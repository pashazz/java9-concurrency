package io.github.pashazz.chapter2.stampedlock;

import io.github.pashazz.Log;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

public class Writer implements Runnable {
    private static int NUM_WRITES = 10;
    private Position pos;
    private StampedLock lock;

    public Writer(Position pos, StampedLock lock) {
        this.pos = pos;
        this.lock = lock;
    }


    @Override
    public void run() {
        for (int i = 0; i < NUM_WRITES; i++) {
            long stamp = lock.writeLock();
            try {
                Log.info("Write lock acquired %s", stamp);
                pos.setX(pos.getX() + 1);
                pos.setY(pos.getY() + 1);
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlockWrite(stamp);
                Log.info("Write lock released: %s", stamp);
            }

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
