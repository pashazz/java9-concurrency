package io.github.pashazz.chapter3.completablefuture;

import io.github.pashazz.Log;

import java.util.List;
import java.util.function.Function;

public class AverageBetweenMinAndMax implements Function<List<Long>, Long> {
    @Override
    public Long apply(List<Long> longs) {
        Log.info("Starting calculating average between min and max");
        Long max = longs.stream().max(Long::compare).get();
        Long min = longs.stream().min(Long::compare).get();
        Long result =  (max + min) / 2;
        Log.info("Result: %s", result);
        return result;
    }
}
