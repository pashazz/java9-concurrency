package io.github.pashazz.chapter4.futuretask;

import io.github.pashazz.Log;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        Log.debug("Starting FutureTask");

        ResultTask resultTasks[] = new ResultTask[5];

        for (int i = 0; i < 5; ++i) {
            ExecutableTask executableTask = new ExecutableTask(String.format("Task %s", i)); // Runnable
            resultTasks[i] = new ResultTask(executableTask); //FutureTask
            var task = executor.submit(resultTasks[i]);
            Log.debug("Returned a Future from the executor.sumbit: %s", task);
            resultTasks[i].run();
            /* // Running w/o thread
            try {
                Log.debug("FutureTask run finished");
                String some = resultTasks[i].get();
                Log.debug("Task result: %s", some);
                resultTasks[i].run();
                 some = resultTasks[i].get();
                Log.debug("Task result: %s", some);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

             */
            resultTasks[i].run();

        }

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < resultTasks.length; ++i) {
            resultTasks[i].cancel(true);
        }

        for (int i = 0; i < resultTasks.length; ++i) {
            if (!resultTasks[i].isCancelled()) {
                try {
                    var value = resultTasks[i].get();
                    Log.info("Result %s: %s", i, value);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }
        }

        executor.shutdown();
    }
}

