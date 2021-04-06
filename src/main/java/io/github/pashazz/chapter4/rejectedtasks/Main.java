package io.github.pashazz.chapter4.rejectedtasks;

import io.github.pashazz.Log;

import static java.lang.String.format;

public class Main {
    public static void main(String[] args) {
        Server server = new Server();
        Log.info("Starting...");

        for (int i = 0; i < 100; i++) {
            Task task = new Task(format("Task %s", i));
            server.executeTask(task);
        }
        server.endServer(); // If i won't do this, shit will hang
        Log.info("Shutting down Executor");
        Task task = new Task("Rejected Task");
        server.executeTask(task);
        Log.info("Exiting");
    }
}
