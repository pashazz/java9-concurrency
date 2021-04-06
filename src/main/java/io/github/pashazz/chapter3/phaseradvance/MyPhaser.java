package io.github.pashazz.chapter3.phaseradvance;

import io.github.pashazz.Log;

import java.util.concurrent.Phaser;

public class MyPhaser extends Phaser {
    @Override
    protected boolean onAdvance(int phase, int registeredParties) {
        switch (phase) {
            case 0:
                return studentsArrived();
            case 1:
                return finishFirstExercise();
            case 2:
                return finishSecondExerice();
            case 3:
                return finishExam();
            default:
                Log.error("SOMETHING WRONG");
                return true; // Were finished

        }
    }

    private boolean finishExam() {
        Log.info("%d/%d students finished the fucking exam!", getArrivedParties(), getRegisteredParties());
        return true; // this is the last one
    }

    private boolean finishSecondExerice() {
        Log.info("%d/%d students finished 2nd exercise, moving on to the final!", getArrivedParties(), getRegisteredParties());
        return false;

    }

    private boolean finishFirstExercise() {
        Log.info("%d/%d students finished 1st exercise, moving on to the 2nd!", getArrivedParties(), getRegisteredParties());
        return false;
    }

    private boolean studentsArrived() {
        Log.info("%d/%d students arrived, starting!", getArrivedParties(), getRegisteredParties());
        return false;
    }
}
