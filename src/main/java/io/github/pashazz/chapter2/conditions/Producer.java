package io.github.pashazz.chapter2.conditions;

import io.github.pashazz.Log;

import java.util.concurrent.TimeUnit;

public class Producer implements  Runnable{
    Buffer buffer;
    FileMock file;

    public Producer(Buffer buffer, FileMock file) {
        this.buffer = buffer;
        this.file = file;
    }
    @Override
    public void run() {
        buffer.setProducingFinished(false);
        while (file.hasMoreLines()) {
            String line = file.getLine();
            buffer.insert(line);
            //Log.debug("Line inserted, sleeping");
            //try {
            //    TimeUnit.SECONDS.sleep(2);
            //} catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
        buffer.setProducingFinished(true);
    }
}
