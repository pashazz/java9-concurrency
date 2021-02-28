package io.github.pashazz.chapter2.stampedlock;

import io.github.pashazz.Log;

import java.util.concurrent.locks.StampedLock;

public class Main {
    public static void main(String[] args) {
        Log.debug("Starting Stamped Lock");
        Position position = new Position(0, 0);
        StampedLock lock = new StampedLock();

        Thread threadWriter = new Thread(new Writer(position, lock), "Write Lock");
        Thread threadReader = new Thread(new Reader(position, lock), "Read Lock");
        Thread theadOptReader = new Thread(new OptimisticReader(position, lock), "Optimistic Thread Lock");

        threadReader.start();
        threadWriter.start();
        theadOptReader.start();
    }
}
