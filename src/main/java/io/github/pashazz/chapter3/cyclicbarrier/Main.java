package io.github.pashazz.chapter3.cyclicbarrier;

import io.github.pashazz.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class Main {
    private static int NUMBER = 7;
    public static void main(String[] args) {
        MatrixMock matrix = new MatrixMock(100000, 1000);
        MapResults results = new MapResults(100);
        Reducer reducer = new Reducer(results);
        CyclicBarrier barrier = new CyclicBarrier(10, reducer);


        Log.debug("Using 10 threads for 100 rows to find count of 7s");
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(
                    new Mapper(matrix, barrier, 10*i, 10*(i+1), results, NUMBER),
                    "Mapper " + i);
            thread.start();
            threads.add(thread);
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.info("Sum of 7s is %s", reducer.getResult());
    }
}
