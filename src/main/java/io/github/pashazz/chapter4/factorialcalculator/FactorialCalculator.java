package io.github.pashazz.chapter4.factorialcalculator;

import io.github.pashazz.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class FactorialCalculator implements Callable<Integer> {
    private final Integer factorialBase;

    public FactorialCalculator(Integer factorialBase) {
        this.factorialBase = factorialBase;
    }

    @Override
    public String toString() {
        return String.format("Calculating %s!", factorialBase);
    }

    @Override
    public Integer call() throws Exception {
        // A factorial of n is 1 * 2 * ... * n;
        int result = 1;
        //0! = 1 because there is one way how a empty set can be arranged;
        // similarly to how a set with one element can be arranged
        if ((factorialBase == 0) || (factorialBase == 1)) {
            result = 1;
        } else {
            for (int i = 2; i <= factorialBase; ++i) {
                result *= i;
                TimeUnit.MILLISECONDS.sleep(20);
            }
        }
        Log.info("%s! = %s", factorialBase, result);
        return result;
    }
}
