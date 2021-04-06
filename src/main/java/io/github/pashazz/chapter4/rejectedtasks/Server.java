package io.github.pashazz.chapter4.rejectedtasks;

import io.github.pashazz.Log;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {
    private final ThreadPoolExecutor executor;

    Server() {
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        RejectTaskController rejectTaskController = new RejectTaskController();
        executor.setRejectedExecutionHandler(rejectTaskController);
    }

    void executeTask(Task task) {
        Log.debug("a new task has been arrived: %s", task);
        executor.execute(task);
        Log.info("Server Pool Size: %s", executor.getPoolSize());
        Log.info("Server Active Count: %s", executor.getActiveCount());
        Log.info("Server Task Count: %s", executor.getTaskCount());
        Log.info("Completed Task Count: %s", executor.getCompletedTaskCount());

    }
    public void endServer() {
        executor.shutdown();
    }
}
