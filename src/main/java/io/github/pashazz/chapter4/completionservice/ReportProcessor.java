package io.github.pashazz.chapter4.completionservice;

import io.github.pashazz.Log;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class ReportProcessor implements Runnable {
    private final CompletionService<String> service;
    private volatile boolean end;

    ReportProcessor(CompletionService<String> service) {
        this.service = service;
        end = false;
    }

    @Override
    public void run() {
        while (!end) {
            try {
                Log.debug("Polling for a report (use take to poll with block)...");
                var result = service.poll(20, TimeUnit.SECONDS);
                if (result != null) {
                    String report = result.get();
                    Log.info("Report Received: %s", report);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        Log.info("Finished processing");
    }

    void stopProcessing() {
        end = true;
    }
}
