package io.github.pashazz.chapter4.completionservice;

import io.github.pashazz.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

class ReportGenerator implements Callable<String> {
    private final String sender;
    private final String title;
    ReportGenerator(String sender, String title) {
        this.sender = sender;
        this.title = title;
    }

    @Override
    public String call() throws Exception {
        try {
            Long duration = (long)(Math.random() * 10);
            Log.debug("Generating a report during %s seconds", duration);
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String ret = sender + ": " + title;
        return ret;
    }

}
