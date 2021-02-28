package io.github.pashazz.chapter2.printqueue;

import io.github.pashazz.Log;

public class PrintJob implements Runnable {
    private PrintQueue queue;
    public PrintJob(PrintQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        Log.info("Starting printing... ");
        queue.printJob(new Object());
        Log.info("Finished printing");
    }
}
