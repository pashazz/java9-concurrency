package io.github.pashazz.chapter2.conditions;

import io.github.pashazz.Log;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A buffer shared by producers and consumers
 *
 * Состояние семафора:
 * изначальное - 0 пермитов, продюсер не зашел. Консьюмеры висят
 * продюсер зашел - ++ пермит
 * продюсер вышел -- пермит. 0 пермитов, консьюмеры отваливаются
 *
 */
public class Buffer {
    private final LinkedList<String> buffer = new LinkedList<>();
    private  final int maxSize;
    private Semaphore producersSemaphore;
    private boolean producerJoined = false;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition lines = lock.newCondition();
    private final Condition space = lock.newCondition();

    public Buffer(int maxSize) {
        this.maxSize = maxSize;
        this.producersSemaphore = new Semaphore(0);
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
            while (bufferEmpty() && !isProducingFinished()) {
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
        return buffer.size() > 0 || !isProducingFinished();
   }

   public boolean isProducingFinished() {
        Log.debug("consumer request for producers: %s producers left", producersSemaphore.availablePermits());
       return producerJoined && producersSemaphore.availablePermits() == 0;
   }

    public void setProducingFinished(boolean producingFinished) {
        if (producingFinished) {
            try {
                producersSemaphore.acquire(); // permits - 1
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            synchronized (this) { // -> atomic
                producersSemaphore.release(); //permits + 1
                producerJoined = true;
            }
        }
    }
}
