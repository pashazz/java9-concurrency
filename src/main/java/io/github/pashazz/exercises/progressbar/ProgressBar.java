package io.github.pashazz.exercises.progressbar;

import io.github.pashazz.Log;

import java.util.concurrent.Semaphore;

public class ProgressBar implements Runnable {
    private Semaphore semaphore;
    private int progress;
    private static int MAX = 100;

    ProgressBar(Semaphore semaphore) {
        this.semaphore = semaphore;
        progress = 0;
    }

    @Override
    public void run() {
        while(progress < MAX) {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                System.out.printf("\nCancelled at %s/%s\n", progress, MAX);
                return;
            }
            increaseProgress();
        }
        System.out.println();
        System.out.println("Complete");
    }

    private void increaseProgress() {
        progress++;
        System.out.print("#");
    }

    public int getProgress() {
        return progress;
    }
}
