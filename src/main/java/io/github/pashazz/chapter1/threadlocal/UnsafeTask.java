package io.github.pashazz.chapter1.threadlocal;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Даты будут выводиться рандомные.
 */
public class UnsafeTask implements Runnable {

    private Date date;
    @Override
    public void run() {
        date = new Date();
        System.out.printf("Thread started: id: %s; date: %s\n" , Thread.currentThread().getId(), date);
        try {
            TimeUnit.SECONDS.sleep(getSleepSeconds());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("Thread finished: id: %s; date: %s\n", Thread.currentThread().getId(), date);
    }

    long getSleepSeconds() {
        return (long)Math.rint(Math.random()*10);
    }
}
