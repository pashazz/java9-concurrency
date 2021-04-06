package io.github.pashazz.chapter4.rejectedtasks;

import io.github.pashazz.Log;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class RejectTaskController implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        Log.warn("Task has been rejected: %s", r);
        Log.warn("Executor: %s", executor);
        Log.warn("Terminating: %s; Terminated: %s", executor.isTerminating(), executor.isTerminated());
    }
}
