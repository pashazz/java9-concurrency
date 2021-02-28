package io.github.pashazz.pogreb.multiexecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MultiExecutor {

    // Add any necessary member variables here
    List<Thread> threads;

    /*
     * @param tasks to executed concurrently
     */
    public MultiExecutor(List<Runnable> tasks) {
        // Complete your code here
        threads = tasks
                .stream()
                .map(task -> new Thread(task))
                .collect(Collectors.toList());

    }

    /**
     * Starts and executes all the tasks concurrently
     */
    public void executeAll() {
        threads.stream().
                forEach(thread -> thread.start());
    }
}