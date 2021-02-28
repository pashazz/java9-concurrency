package io.github.pashazz.chapter2.conditions;

import io.github.pashazz.Log;

public class Consumer implements  Runnable{
    private Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (buffer.needMoreConsuming()) {
            String line = buffer.get();
            Log.info("Line: %s", line);
        }
        Log.debug("Consumer finished");
    }
}
