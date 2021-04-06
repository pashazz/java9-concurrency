package io.github.pashazz.chapter4.completionservice;

import io.github.pashazz.Log;

import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();

        CompletionService<String> service = new ExecutorCompletionService<>(executor);

        Log.debug("Starting Report Requests");
        // 1. forming requests
        // They will submit a ReportGenerator objects to the execuroe
        ReportRequest faceRequest = new ReportRequest("face", service);
        ReportRequest onlineRequest = new ReportRequest("online", service);


        ReportProcessor processor = new ReportProcessor(service);
        Thread processingThread = new Thread(processor, "ReportProcessor");
        processingThread.start();

        var faceFuture = executor.submit(faceRequest);
        var onlineFuture = executor.submit(onlineRequest);

        try {
            faceFuture.get();
            onlineFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        processor.stopProcessing(); //this halts the process
        try {
            processingThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.info("Complete");
    }
}
