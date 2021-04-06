package io.github.pashazz.chapter4.factorialcalculator;

import io.github.pashazz.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        List<Future<Integer>> results = new ArrayList<>();

        Random random = new Random();
        Log.debug("Calculating 10 random factorials...");
        for(int i = 0; i < 10; ++i) {
            Integer number = random.nextInt(10);
            FactorialCalculator calc = new FactorialCalculator(number);
            Future<Integer> result = executor.submit(calc);
            results.add(result);
        }
        do {
            Log.debug("completed tasks: %s", executor.getCompletedTaskCount());
            //print task status
            for (Future<Integer> future: results) {
                Log.debug("%s: is done: %s", future, future.isDone());
            }
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (executor.getCompletedTaskCount() < results.size());
        Log.info("All tasks were finished");
        for (Future<Integer> future : results) {
            Integer calcResult;
            try {
                calcResult = future.get();
                Log.info("Result: %s", calcResult);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
    }
}
