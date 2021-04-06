package io.github.pashazz.chapter4.completionservice;

import java.util.concurrent.CompletionService;

public class ReportRequest implements Runnable {
    private final String name;
    private final CompletionService<String> service;

    @Override
    public void run() {
        ReportGenerator rg = new ReportGenerator(name, "Report from " + Thread.currentThread().getName());
        service.submit(rg);
    }

    ReportRequest(String name, CompletionService<String> service) {
        this.name = name;
        this.service = service;
    }
}
