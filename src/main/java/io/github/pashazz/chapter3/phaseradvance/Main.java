package io.github.pashazz.chapter3.phaseradvance;

import io.github.pashazz.Log;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Log.debug("Starting");
        List<Thread> students = new ArrayList<>();
        MyPhaser phaser = new MyPhaser();

        for (int i = 0; i < 5; ++i) {
            students.add(new Thread(new Student(phaser), "Student " + (i+1) ));
            students.get(i).start();
        }
    }
}
