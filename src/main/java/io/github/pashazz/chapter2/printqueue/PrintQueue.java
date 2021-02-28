package io.github.pashazz.chapter2.printqueue;

import io.github.pashazz.Log;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintQueue {
    private Lock queueLock;

    public PrintQueue(boolean fair) {
        queueLock = new ReentrantLock(fair);
    }

    public void printJob(Object document) {
        queueLock.lock();

        //simulate printing

        try {
            Long duration = (long) (Math.random() * 1000);
            Log.info("Scheduling a Job for %s ms", duration);
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            queueLock.unlock();
        }
        queueLock.lock();
        try {
            Long duration = (long) (Math.random() * 1000);
            Log.info("Printing a job during %s s", duration / 1000);
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            queueLock.unlock();
        }

    }
}
