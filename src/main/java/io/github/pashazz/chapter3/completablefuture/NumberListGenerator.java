package io.github.pashazz.chapter3.completablefuture;

import io.github.pashazz.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class NumberListGenerator implements Supplier<List<Long>> {

    int size;
    NumberListGenerator(int size) {
        this.size = size;
    }

    @Override
    public List<Long> get() {
        List<Long> res = new ArrayList<>();
        Log.info("Start!");
        for (int i = 0; i < size * 10000; ++i) {
            long number = (long)(Math.random() * Long.MAX_VALUE);
            res.add(number);
        }
        Log.debug("Generation ended");
        return res;
    }
}
