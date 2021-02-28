package io.github.pashazz.chapter2.parking;

import java.util.concurrent.TimeUnit;

public class Sensor implements Runnable{
    public static final int NUM_CARS = 2;
    public static final int NUM_MOTOS = 1;
    public static final int ATTEMPTS = 10;

    private ParkingStats stats;

    public Sensor(ParkingStats stats) {
        this.stats = stats;
    }

    private void sleepThread() {
        try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
        }
    }

    @Override
    public void run() {
        for (int i  = 0; i < ATTEMPTS; ++i) {
            for (int j = 0; j < NUM_CARS; ++j) {
                stats.carComeIn();
            }
            sleepThread();
            for (int j = 0; j < NUM_MOTOS; ++j) {
                stats.motoComeIn();
            }
            sleepThread();
            for (int j = 0; j < NUM_MOTOS; ++j) {
                stats.motoGoOut();
            }
            for (int j = 0; j < NUM_CARS; ++j) {
                stats.carGoOut();
            }
        }

    }
}
