package io.github.pashazz.exercises.progressbar;

import io.github.pashazz.Log;

import java.util.concurrent.TimeUnit;

public class Worker implements Runnable {
    private ProgressManager progressManager;

    public Worker(ProgressManager progressManager) {
        this.progressManager = progressManager;
    }

    @Override
    public void run() {
        for (int i = 0; i < 90; ++i) {
            long secs = (long)(Math.random() * 100);
            try {
                TimeUnit.MILLISECONDS.sleep(secs);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            progressManager.increment();
        }
    }
}
