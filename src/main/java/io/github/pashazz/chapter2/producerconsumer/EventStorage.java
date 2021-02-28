package io.github.pashazz.chapter2.producerconsumer;

import io.github.pashazz.Log;

import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

public class EventStorage {
    private static int MAX_SIZE = 10;
    private Queue<Date> storage = new LinkedList<>();

    public EventStorage() {
    }

    public synchronized void set() {
        while(fullStorage()) {
            try {
                Log.debug("Storage is full, waiting");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        storage.offer(new Date());
        Log.debug("Added a new item to the storage, notifying");
        notify();
    }

    public synchronized void get() {
        while(storage.isEmpty()) {
            Log.debug("Storage is empty, waiting");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String item = storage.poll().toString();
        Log.info("Polled from a storage: %s", item);
        notify();
    }

    private boolean fullStorage() {
        return storage.size() == MAX_SIZE;
    }


}
