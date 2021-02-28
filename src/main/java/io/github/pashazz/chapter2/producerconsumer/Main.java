package io.github.pashazz.chapter2.producerconsumer;

import io.github.pashazz.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.lang.String.format;

public class Main {
    private static final int ITEMS = 20;

    public static void main(String[] args) {
        final int producers_count = 2;
        final int consumers_count = 1;
        Log.to_sysout = false;

        Log.debug("Starting with %s producers and %s consumers", producers_count, consumers_count);
        EventStorage es = new EventStorage();
        List<Thread> producers = new ArrayList<>();
        List<Thread> consumers = new ArrayList<>();

        for (int i = 0; i < producers_count; ++i) {
            Thread t = new Thread(new Producer(es));
            producers.add(t);
            t.setName(format("Producer %s", i));
            t.start();
        }

        for (int i = 0; i < consumers_count; ++i) {
            Thread t = new Thread(new Consumer(es));
            consumers.add(t);
            t.setName(format("Consumer %s", i));
            t.start();
        }
        try {
            for (Thread t: consumers) {
                t.join();
            }
            for (Thread t: producers) {
                t.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class Producer implements Runnable {

        private EventStorage storage;

        Producer(EventStorage storage) {

            this.storage = storage;
        }

        @Override
        public void run() {
            for (int i = 0; i < ITEMS; ++i) {
                storage.set();
            }
        }
    }


    private static class Consumer implements Runnable {

        private EventStorage storage;

        private Consumer(EventStorage storage) {
            this.storage = storage;
        }

        @Override
        public void run() {
            for (int i = 0; i < ITEMS; ++i) {
                storage.get();
            }
        }
    }
}
