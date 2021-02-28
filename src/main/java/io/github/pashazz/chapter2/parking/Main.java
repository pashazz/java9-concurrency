package io.github.pashazz.chapter2.parking;

import io.github.pashazz.Log;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        ParkingCash cash = new ParkingCash();
        ParkingStats stats = new ParkingStats(cash);

        int sensors = 2 * Runtime.getRuntime().availableProcessors();

        Log.info("Parking Simulator: %s sensors", sensors);

        Thread threads[] = new Thread[sensors];
        for (int i = 0; i < sensors; ++i) {
            threads[i] = new Thread(new Sensor(stats));
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //print stats
        Log.info("Cars: %s", stats.getNumberCars());
        Log.info("Motos: %s", stats.getNumberMotorcycles());
        cash.close();


    }
}
