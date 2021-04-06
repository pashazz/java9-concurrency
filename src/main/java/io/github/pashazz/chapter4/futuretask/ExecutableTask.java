package io.github.pashazz.chapter4.futuretask;

import io.github.pashazz.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class ExecutableTask implements Callable<String> {

    private final String name;

    public String getName() {
        return name;
    }
    ExecutableTask(String name) {
        this.name = name;
    }

    @Override
    public String call() throws Exception {
        try {
            long duration = (long)(Math.random() * 10);
            Log.debug("%s: waiting %s seconds", name, duration);
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            Log.warn("Task %s has been cancelled", name);
        }
        return "Hello, world. I'm " + name;
    }
}
