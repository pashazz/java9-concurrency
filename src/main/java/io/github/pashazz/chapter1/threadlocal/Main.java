package io.github.pashazz.chapter1.threadlocal;

import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        //UnsafeTask task = new UnsafeTask();
        SafeTask task = new SafeTask();
        System.out.printf("Starting 10 threads of %s\n", task.getClass().getSimpleName());
        for (int i  =0; i < 10; ++i) {
            Thread thread = new Thread(task);
            thread.setName(format("Thread №%s", i));
            TimeUnit.SECONDS.sleep(2);
            thread.start();
        }
    }
}
