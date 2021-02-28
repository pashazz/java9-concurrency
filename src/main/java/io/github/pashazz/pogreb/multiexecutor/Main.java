package io.github.pashazz.pogreb.multiexecutor;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Runnable task1 = () -> {
            System.out.printf("Hello from task1\n");
        };
        Runnable task2 = () -> {
            System.out.printf("Hello from task2\n");
        };

        MultiExecutor me = new MultiExecutor(Arrays.asList(task1, task2));
        me.executeAll();
    }
}
