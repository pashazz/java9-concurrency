package io.github.pashazz.chapter5.filesearch;

import io.github.pashazz.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountedCompleter;

public class DirectoryProcessor extends CountedCompleter<List<String>> {

    private final String path;
    private final String extension;
    private List<DirectoryProcessor> subdirTasks;
    private List<String> results;

    /**
     * Initialize DirectoryProcessor as a child task of another CountedCompleter
     */
    protected DirectoryProcessor(CountedCompleter<?> completer, String path, String extension) {
        super(completer);
        this.path = path;
        this.extension = extension;
    }

    DirectoryProcessor(String path, String extension) {
        this.path = path;
        this.extension = extension;
    }

    @Override
    public List<String> getRawResult() {
        return getResults();
    }

    @Override
    public void compute() {
        results = new ArrayList<>();
        subdirTasks = new ArrayList<>();

        // 1. get subdirs
        File dir = new File(path);
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    //2. for each subdir spawn a child task
                    DirectoryProcessor subdir = new DirectoryProcessor(this, file.getAbsolutePath(), extension);
                    subdir.fork();
                    // Use an internal counter to indicate that there are +1 child task
                    addToPendingCount(1);
                    subdirTasks.add(subdir);
                } else {
                    if (checkFile(file)) {
                        results.add(file.getAbsolutePath());
                    }
                }
                if (subdirTasks.size() > 50) {
                    Log.debug("%s: %s subdirs during processing", file.getAbsolutePath(), subdirTasks.size());
                }
            }
            Log.info("tryComplete: %s", path);
            tryComplete();
        }
    }

    /**
     * This function is called when tryComplete() ends
     * @param caller
     */
    @Override
    public void onCompletion(CountedCompleter<?> caller) {
        Log.warn("onCompletion: caller: %s; me: %s", caller, this.path);
        for (DirectoryProcessor subdir : subdirTasks) {
            results.addAll(subdir.getResults());
        }
    }

    /**
     * Check that the file has our extension
     */
    private boolean checkFile(File file) {
        return file.getName().endsWith(String.format(".%s", extension));
    }

    public List<String> getResults() {
        return results;
    }
}