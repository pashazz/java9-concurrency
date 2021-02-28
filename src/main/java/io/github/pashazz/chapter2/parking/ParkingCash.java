package io.github.pashazz.chapter2.parking;

import io.github.pashazz.Log;

public class ParkingCash {
    public static final int COST = 2;
    private long cash;

    public synchronized void vehiclePay() {
        Log.debug("Cash income: %s: %s -> %s", COST, cash, cash + COST);
        cash += COST;
    }
    public synchronized void close() {
        long totalAmount = cash;
        Log.info("Closing accounting");
        cash = 0;
        Log.info("The total amount is %s\n", totalAmount);
    }
}
