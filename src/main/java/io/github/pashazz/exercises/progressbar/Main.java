package io.github.pashazz.exercises.progressbar;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {
    public static void main(String[] args)  {
        ProgressManager pm = new ProgressManager();
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        var progressBar = executor.submit(pm.getProgressBar());
        var worker = executor.submit(new Worker(pm));
        var worker2 = executor.submit(new Worker(pm));
        try {
            worker.get();
            worker2.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        progressBar.cancel(true);
        executor.shutdown();
    }
}
