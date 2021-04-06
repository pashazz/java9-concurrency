package io.github.pashazz.chapter3.completablefuture;

import io.github.pashazz.Log;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) {
        Log.debug("Start...");
        CompletableFuture<Integer> seedFuture = new CompletableFuture<>();
        Thread seedThread = new Thread(new SeedGenerator(seedFuture));
        seedThread.start();
        int seed = 0;
        Log.info("Getting the seed");
        try {
            seed = seedFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        Log.info("The seed is %s", seed);

        Log.info("Generating the list of random numbers");
        NumberListGenerator task = new NumberListGenerator(seed);
        var numberListFuture = CompletableFuture.supplyAsync(task);

        // Конфигурируем поиск ближайшего числа к 1000 так, что он запускается после завершения numberListFuture
        var nearTo1000Future = numberListFuture.thenApplyAsync(
                numbers -> {
                    Log.info("Finding the nearest number to 1000");
                    long nearestNumber = 0;
                    long minDistance = Long.MAX_VALUE;
                    long currentDistance;
                    for (Long number : numbers) {
                        currentDistance = Math.abs(number - 1000);
                        if (currentDistance < minDistance) {
                            nearestNumber = number;
                            minDistance = currentDistance;
                        }
                    }
                    Log.info("Closest to 1000nNumber found: %s", nearestNumber);
                    return nearestNumber;
                });
        var maxNumberFuture = numberListFuture.thenApplyAsync(
                numbers -> {
                    Log.info("Finding the maximum of numbers");
                    long max = numbers.stream().max(Long::compare).get();
                    Log.info("Max number found: %s", max);
                    return max;
                });

        var averageFuture = numberListFuture.thenApplyAsync(new AverageBetweenMinAndMax());
        Log.debug("Waiting for tasks to end");
        var waitFuture = CompletableFuture.allOf(nearTo1000Future, maxNumberFuture, averageFuture);

        var endFuture = waitFuture.thenAcceptAsync(param -> {
            Log.info("The completableFuture has been completed");
        });

        endFuture.join();
    }
}
