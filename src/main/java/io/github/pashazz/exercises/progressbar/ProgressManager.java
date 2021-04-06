package io.github.pashazz.exercises.progressbar;

import java.util.concurrent.Semaphore;

public class ProgressManager {
    private ProgressBar progressBar;
    private Semaphore semaphore;
    ProgressManager() {
        semaphore = new Semaphore(0);
        progressBar = new ProgressBar(semaphore);
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void increment() {
        increaseProgress(1);
    }

    public void increaseProgress(int percent) {
        semaphore.release(percent);
    }
}
