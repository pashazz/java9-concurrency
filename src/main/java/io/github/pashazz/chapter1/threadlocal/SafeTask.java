package io.github.pashazz.chapter1.threadlocal;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class SafeTask implements Runnable {
    private static ThreadLocal<Date> startDate = new ThreadLocal<> () {
        @Override
        protected Date initialValue() {
            return new Date();
        }
    };

    @Override
    public void run() {
        System.out.printf("Thread started: id: %s; date: %s\n", Thread.currentThread().getId(), startDate.get());
        try {
            TimeUnit.SECONDS.sleep(getSleepSeconds());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("Thread finished: id: %s; date: %s\n", Thread.currentThread().getId(), startDate.get());
    }
    long getSleepSeconds() {
        return (long)Math.rint(Math.random()*10);
    }
}
