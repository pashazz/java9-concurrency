package io.github.pashazz.chapter3.phaser;

import java.util.concurrent.Phaser;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Phaser phaser = new Phaser(2);

        FileSearch varlog = new FileSearch("/var/log", "log", phaser);
        FileSearch library = new FileSearch("/Library/Logs", "log", phaser);

        Thread varlogThread = new Thread(varlog, "/var/log");
        Thread libraryThread = new Thread(library, "/Library/Logs");
        varlogThread.start();
        libraryThread.start();

        varlogThread.join();
        libraryThread.join();
    }
}
