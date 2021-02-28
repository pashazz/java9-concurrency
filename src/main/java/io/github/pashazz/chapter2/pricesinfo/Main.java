package io.github.pashazz.chapter2.pricesinfo;

import io.github.pashazz.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

public class Main {
    private static int READERS = 3;
    private static int WRITERS = 2;
    public static void main(String[] args) {
        PricesInfo pricesInfo = new PricesInfo();
        Log.debug("Starting %s readers and %s writers", READERS, WRITERS);
        List<Thread> readers = new ArrayList<>(), writers = new ArrayList<>();
        for (int i = 0; i < READERS; ++i) {
            readers.add(new Thread(new Reader(pricesInfo), format("Reader %s", i)));
            readers.get(i).start();
        }

        for (int i = 0; i < WRITERS; ++i){
            writers.add(new Thread(new Writer(pricesInfo), format("Writer %s", i)));
            writers.get(i).start();
        }
    }

    private static class Reader implements Runnable {
        private static int READ_TIMES = 20;
        private PricesInfo pricesInfo;

        Reader(PricesInfo pricesInfo) {
            this.pricesInfo = pricesInfo;
        }
        @Override
        public void run() {
            for (int i = 0; i < READ_TIMES; ++i) {
                Log.info("Price1: %s; Price2: %s", pricesInfo.getPrice1(), pricesInfo.getPrice2());
            }

        }
    }

    private static class Writer implements Runnable {
        private PricesInfo pricesInfo;
        private static int WRITE_TIMES = 3;

        public Writer(PricesInfo pricesInfo) {
            this.pricesInfo = pricesInfo;

        }

        @Override
        public void run() {
            for (int i = 0; i < WRITE_TIMES; ++i) {
                Log.info("%s: Modifying prices...", Thread.currentThread().getName());
                pricesInfo.setPrices(Math.random() * 10, Math.random() * 10);
                Log.info("Prices have been modified");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
