package io.github.pashazz.chapter3.cyclicbarrier;

import java.util.Arrays;

public class Reducer implements Runnable {
    private MapResults results;
    int result;

    Reducer(MapResults results) {
        this.results = results;
    }

    @Override
    public void run() {
        result = Arrays.stream(results.getResults()).sum();
    }

    public int getResult() {
        return result;
    }
}
