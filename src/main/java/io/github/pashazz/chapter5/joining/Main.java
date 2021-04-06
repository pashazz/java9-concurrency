package io.github.pashazz.chapter5.joining;

import io.github.pashazz.Log;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        Log.info("Staring search word in a document");
        ForkJoinPool pool = new ForkJoinPool();
        DocumentMock generator = new DocumentMock();
        String[][] docs = generator.generateDocument(100, 100, "the");
        DocumentTask task = new DocumentTask(docs, 0, 100, "the");
        Integer test = Arrays.stream(docs).map(line -> Arrays.stream(line)
                .filter(word -> word.equals("the"))
                .count())
                .reduce(0L, Long::sum).intValue();
        Log.debug("Test value: %s", test);
        pool.execute(task);;
        do {
            Log.debug("Active Thread Count: %s", pool.getActiveThreadCount());
            Log.debug("Task Count: %s", pool.getQueuedTaskCount());
            Log.debug("Steal Count: %s", pool.getStealCount());
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } while (!task.isDone());

        pool.shutdown();
        try {
            pool.awaitTermination(1, TimeUnit.DAYS);
            Log.info("Total occurs: %s", task.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        Log.debug("Complete");
    }

}
