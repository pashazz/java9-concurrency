package io.github.pashazz.chapter2.parking;

import io.github.pashazz.Log;

public class ParkingStats {
    private long numberCars;
    private long numberMotorcycles;
    private ParkingCash cash;

    private final Object guardCars = new Object();
    private final Object guardMotos = new Object();

    public ParkingStats(ParkingCash cash) {
        numberCars = 0;
        numberMotorcycles = 0;
        this.cash = cash;
    }

    public void carComeIn() {
        synchronized (guardCars) {
            Log.debug("Car came in: %s -> %s", numberCars, numberCars+1);
            numberCars++;
        }
    }
    public void carGoOut() {

        synchronized (guardCars) {
            Log.debug("Car gone: %s -> %s", numberCars, numberCars - 1);
            numberCars--;
        }
        cash.vehiclePay();
    }

    public void motoComeIn() {

        synchronized (guardMotos) {
            Log.debug("Moto came in: %s -> %s", numberMotorcycles, numberMotorcycles+1);
            numberMotorcycles++;
        }
    }
    public void motoGoOut() {
        Log.debug("Moto gone: %s -> %s", numberMotorcycles, numberMotorcycles-1);
        synchronized (guardMotos) {
            numberMotorcycles--;
        }
        cash.vehiclePay();
    }

    public long getNumberCars() {
        return numberCars;
    }

    public long getNumberMotorcycles() {
        return numberMotorcycles;
    }
}
