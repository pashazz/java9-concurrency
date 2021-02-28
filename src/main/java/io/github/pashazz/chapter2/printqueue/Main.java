package io.github.pashazz.chapter2.printqueue;

import io.github.pashazz.Log;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class Main {
    private static int THREADS = 10;
    public static void main(String[] args) {
        Log.info("Running example with unfair ReentrantLock (fair-mode = false)");
        testPrintQueue(false);
        Log.info("Running example with fair ReentrantLock (fair-mode = true)");
        testPrintQueue(true);
    }

    private static void testPrintQueue(boolean fair) {
        PrintQueue printQueue = new PrintQueue(fair);
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < THREADS; ++i) {
            threads.add(new Thread(new PrintJob(printQueue), format("Print Job #%s", i)));
            threads.get(i).start();
        }

        for (int i = 0; i < THREADS; ++i) {
            try {
                threads.get(i).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
