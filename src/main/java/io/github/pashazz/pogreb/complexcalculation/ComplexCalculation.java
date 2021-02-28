package io.github.pashazz.pogreb.complexcalculation;

import java.math.BigInteger;

public class ComplexCalculation {
    public static void main(String[] args) {
        ComplexCalculation cc = new ComplexCalculation();
        BigInteger base1 = new BigInteger("3");
        BigInteger power1 = new BigInteger("100500");

        System.out.printf("The result of %s^%s +  %s^%s = %s\n",
                base1, power1, base1, power1,  cc.calculateResult(base1, power1, base1, power1));
    }

    public BigInteger calculateResult(BigInteger base1, BigInteger power1, BigInteger base2, BigInteger power2) {
        BigInteger result;
        /*
            Calculate result = ( base1 ^ power1 ) + (base2 ^ power2).
            Where each calculation in (..) is calculated on a different thread
        */
        var t1 = new PowerCalculatingThread(base1, power1);
        var t2 = new PowerCalculatingThread(base2, power2);
        System.out.printf("Starting calculation...");
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
        System.out.printf("done!\n");
        result = t1.getResult().add(t2.getResult());
        return result;
    }

    private static class PowerCalculatingThread extends Thread {
        private BigInteger result = BigInteger.ONE;
        private BigInteger base;
        private BigInteger power;

        public PowerCalculatingThread(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            try {
                for (BigInteger i = BigInteger.ZERO; i.compareTo(power) < 0; i = i.add(BigInteger.ONE)) {
                    if (currentThread().isInterrupted()) {
                        throw new InterruptedException();
                    }
                    result = result.multiply(base);
                }
            } catch (InterruptedException e) {
                System.out.printf("Thread interruption requested");
                return;
            }
        }

        public BigInteger getResult() { return result; }
    }
}