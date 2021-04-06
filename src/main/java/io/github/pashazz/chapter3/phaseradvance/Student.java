package io.github.pashazz.chapter3.phaseradvance;

import io.github.pashazz.Log;

import java.util.concurrent.TimeUnit;

public class Student implements Runnable {

    private MyPhaser phaser;

    public Student(MyPhaser phaser) {
        this.phaser = phaser;
        phaser.register();
    }


    @Override
    public void run() {
        Log.info("I'm at the exam");
        phaser.arriveAndAwaitAdvance();
        Log.info("Starting 1st");
        doExercise();
        Log.info("1st exercise finished");
        phaser.arriveAndAwaitAdvance();
        Log.info("Starting 2nd");
        doExercise();
        Log.info("2nd exercise finished");
        phaser.arriveAndAwaitAdvance();
        Log.info("Starting 3rd");
        doExercise();
        Log.info("3rd exercise finished");
        phaser.arriveAndAwaitAdvance();
        Log.info("I finished the exam");
    }

    void doExercise() { //sleep
        try {
            TimeUnit.SECONDS.sleep((long) (Math.random() * 10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
