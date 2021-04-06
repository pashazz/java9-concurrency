package io.github.pashazz.chapter5.filesearch;

import io.github.pashazz.Log;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ForkJoinPool pool = new ForkJoinPool();
        Log.debug("Finding dynamic libraries in /usr/lib");
        DirectoryProcessor usr = new DirectoryProcessor("/Users/pasha ", "doc");
        Log.debug("Finding dynamic libraries in /Applications");
  /*      DirectoryProcessor apps = new DirectoryProcessor("/Applications", "dylib");
        Log.debug("Finding dynamic libraries in /Library");
        DirectoryProcessor library = new DirectoryProcessor("/Library", "dylib");
*/
        pool.execute(usr);
        //pool.execute(apps);
        //pool.execute(library);

        do {
            Log.warn("Active Threads: %s", pool.getActiveThreadCount());
            Log.warn("Queued Tasks: %s", pool.getQueuedTaskCount());
            Log.warn("Steal Count: %s", pool.getStealCount());
            Log.warn("Task Done?: %s", usr.isDone());
            TimeUnit.SECONDS.sleep(1);
        } while (Stream.of(usr/*, apps, library*/).anyMatch(task -> !task.isDone()));
        Log.debug("Shutting down");
        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.DAYS);

        List<String> results;
        results = usr.join();
        Log.info("/usr: %s", results);
        /*results = apps.join();
        Log.info("/Apps: %s", results);
        results = library.join();
        Log.info("/Library: %s", results);
*/
    }
}
