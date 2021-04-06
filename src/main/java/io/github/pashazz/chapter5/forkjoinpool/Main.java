package io.github.pashazz.chapter5.forkjoinpool;

import io.github.pashazz.Log;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        ProductListGenerator plg = new ProductListGenerator();
        Log.debug("Starting ForkJoinPool");
        var products = plg.generate(1000000);

        Task task = new Task(products, 0, products.size(), 0.20);

        ForkJoinPool pool = new ForkJoinPool();
        pool.execute(task);

        //Monitor
        do {
            Log.debug("Thread Count: %s", pool.getActiveThreadCount());
            Log.debug("Thread Steal: %s", pool.getActiveThreadCount());
            Log.debug("Parallelism: %s", pool.getParallelism());
            try {
                TimeUnit.MILLISECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (!task.isDone());

        pool.shutdown();

        if (task.isCompletedNormally()) {
            Log.info("The process has completed normally");
        }

        //Expected price: 10 * (0.20 + 1) = 12
        for (Product product : products){
            if (product.getPrice() != 12) {
                Log.error("%s: price %s", product.getName(), product.getPrice());
            }
        }
    }
}
