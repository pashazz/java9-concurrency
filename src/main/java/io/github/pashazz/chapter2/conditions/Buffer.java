package io.github.pashazz.chapter2.conditions;

import io.github.pashazz.Log;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A buffer shared by producers and consumers
 */
public class Buffer {
    private final LinkedList<String> buffer = new LinkedList<>();
    private  final int maxSize;
    private boolean producingFinished;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition lines = lock.newCondition();
    private final Condition space = lock.newCondition();

    public Buffer(int maxSize) {
        this.maxSize = maxSize;
        this.producingFinished = false;
    }

    private boolean bufferFull() {
        return buffer.size() == maxSize;
    }

    private boolean bufferEmpty() {
        return buffer.size() == 0;
    }
    //Producer
    public void insert(String line) {
        lock.lock();
        try {
            while (bufferFull()) {
                Log.debug("Awaiting for space");
                space.await();
            }
            buffer.offer(line);
            Log.info("Inserted line %s", buffer.size());
            lines.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    //Consumer
    public String get() {
        try {
            lock.lock();
            while (bufferEmpty() && !producingFinished) {
                Log.debug("Awaiting for lines");
                lines.await();
            }
            Log.debug("Buffer size: %s", buffer.size());
            String line = buffer.poll();
            Log.info("Polled line %s", buffer.size());
            space.signalAll();
            return line;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }

   public synchronized boolean needMoreConsuming() {
        return !producingFinished || buffer.size() > 0;
   }

    public synchronized void setProducingFinished(boolean producingFinished) {
        this.producingFinished = producingFinished;
    }
}
