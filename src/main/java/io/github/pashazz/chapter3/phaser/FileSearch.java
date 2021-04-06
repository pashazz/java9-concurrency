package io.github.pashazz.chapter3.phaser;

import io.github.pashazz.Log;

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Ищет в папке initPath рекурсивно файлы с расширением fileExtension
 */
public class FileSearch implements Runnable {

    private final String initPath;
    private final String fileExtension;
    private List<String> results;
    private final List<File> files = new ArrayList<>();
    private final Duration maxSinceModification = Duration.ofDays(1);

    private Phaser phaser;

    @Override
    public void run() {

        Log.debug("%s: Wait until all threads have been created and go to phase %s", phaser.getPhase(), phaser.awaitAdvance(phaser.arrive()));
        Log.info("Starting...");
        File file = new File(initPath);
        if (file.isDirectory()) {
            directoryProcess(file);
        }
        Log.info("Checking that there are files ending in .%s", fileExtension);
        if (!checkResults(files, "filter by extension")){
            return;
        }
        filterResults();
        Log.info("Checking that there are files that were modified sooner than 24h ago");
        if (!checkResults(results, "filter by time")) {
            return;
        }
        showInfo();
        phaser.arriveAndDeregister();
        Log.info("Complete.");
    }

    private void showInfo() {
        results.forEach(fileName ->{
            File file = new File(fileName);
            Log.warn(file.getAbsolutePath());
        });
        phaser.arriveAndAwaitAdvance();
    }

    public FileSearch(String initPath, String fileExtension, Phaser phaser) {
        this.initPath = initPath;
        this.phaser = phaser;
        this.fileExtension = fileExtension;
    }

    private void directoryProcess(File file) {
        File list[] = file.listFiles();
        if (list != null) {
            for (int i = 0; i < list.length; i++) {
                if (list[i].isDirectory()) {
                    directoryProcess(list[i]);
                } else {
                    fileProcess(list[i]);
                }
            }
        }
    }

    private void fileProcess(File file) {
        if (file.getName().endsWith(fileExtension)) {
            files.add(file);
        }
    }

    private void filterResults() {
        results = files
                .stream()
                .filter(file -> {
                    long mod = file.lastModified();
                    Duration sinceModification = Duration.between(
                            Instant.ofEpochMilli(mod),
                            Instant.now()
                    );
                    // alternatively
                    //new Date().getTime() - mod < TimeUnit.MILLISECONDS.convert(Duration.ofDays(1))
                    Log.debug("since modification of %s: %s days", file.getAbsolutePath(), sinceModification.toDays());
                    return sinceModification.compareTo(maxSinceModification) < 0;
                })
                .map(File::getAbsolutePath)
                .collect(Collectors.toList());
    }
    
    
    private boolean checkResults(List<?> listToCheck, String stageName) {
        if (listToCheck == null || listToCheck.isEmpty()) {
            Log.info("Phase %d: %s: 0 results -> end", phaser.getPhase(), stageName);
            
            // Этот метод выводит поток из общего процесса. Не засыпаем, просто уходим
            phaser.arriveAndDeregister();
            return false;
        } else {
            Log.info("Phase %d: %s: %d results -> continue", phaser.getPhase(), stageName, listToCheck.size());
            /**
             * Этот  метод продолжает. Засыпаем до тех пор, пока другие потоки не вызовут
             * {@link Phaser#arriveAndDeregister()}  или {@link Phaser#arriveAndAwaitAdvance()}
             * или {@link Phaser#arriveAndAwaitAdvance()} }
              */
            phaser.arriveAndAwaitAdvance();

            return true;
        }
    }
    
}
