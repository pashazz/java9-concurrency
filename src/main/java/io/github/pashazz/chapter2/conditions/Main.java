package io.github.pashazz.chapter2.conditions;

import io.github.pashazz.Log;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class Main {
    private static int CONSUMERS = 1;
    private static int PRODUCERS = 3;
    private static int lines = 5;

    private static List<Thread> consumers = new ArrayList<>();
    private static  List<Thread> producers = new ArrayList<>();


    public static void main(String[] args) {
        Buffer buffer = new Buffer(lines);

        Log.debug("Starting %s consumers and %s producers on buffer with %s lines", CONSUMERS, PRODUCERS, lines);
        for (int i = 0; i < CONSUMERS; ++i) {
            consumers.add(new Thread(new Consumer(buffer), format("Consumer %s", i)));
            consumers.get(i).start();
        }
        for (int i = 0; i < PRODUCERS; ++i) {
            producers.add(new Thread(new Producer(buffer, new FileMock(100, 10)),
                    format("Producer %s", i)));
            producers.get(i).start();
        }

    }

}
