package io.github.pashazz.chapter3.completablefuture;

import io.github.pashazz.Log;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * В этом классе completableFuture используется для того, чтобы вернуть результат после долгой задачи
 * из отдельного треда
 */
public class SeedGenerator implements Runnable {

    private CompletableFuture<Integer> futureResult;

    public SeedGenerator(CompletableFuture<Integer> futureResult) {
        this.futureResult = futureResult;
    }

    @Override
    public void run() {
        Log.debug("Generating seed...");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int seed = (int)(Math.random() * 10);
        Log.info("Seed generated: %s", seed);
        futureResult.complete(seed);
    }
}
