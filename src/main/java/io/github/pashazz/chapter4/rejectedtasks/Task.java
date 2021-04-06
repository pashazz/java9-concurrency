package io.github.pashazz.chapter4.rejectedtasks;

import io.github.pashazz.Log;

import java.util.Date;
import java.util.concurrent.TimeUnit;

class Task implements Runnable {
    private final Date initDate;
    private final String name;

    public Task(String name) {
        initDate = new Date();
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Task %s created on %s", name, initDate);
    }

    @Override
    public void run() {
        Log.info("Starting task %s created on %s",name, initDate);
        long duration = (long)(Math.random() * 10);
        Log.info("Doing a task during %s seconds", duration);
        try {
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.info("Task %s completed", name);
    }
}
