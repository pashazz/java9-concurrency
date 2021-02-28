package io.github.pashazz.chapter2.pricesinfo;

import io.github.pashazz.Log;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class PricesInfo {
    private double price1 = 1.0;
    private double price2 = 2.0;

    private ReadWriteLock lock = new ReentrantReadWriteLock(true);

    public double getPrice1() {
        lock.readLock().lock();
        Log.debug("Read lock acquired; reading price1");
        double value = price1;
        lock.readLock().unlock();
        Log.debug("Read lock released; finished reading price1");
        return value;
    }

    public double getPrice2() {
        lock.readLock().lock();
        Log.debug("Read lock acquired; reading price2");
        double value = price2;
        lock.readLock().unlock();
        Log.debug("Read lock released; finished reading price2");
        return value;
    }

    public void setPrices(double price1, double price2) {
        lock.writeLock().lock();
        Log.info("Write lock acquired");
        try {
            TimeUnit.SECONDS.sleep(10);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.price1 = price1;
        this.price2 = price2;
        lock.writeLock().unlock();
        Log.info("Write lock released");
    }
}