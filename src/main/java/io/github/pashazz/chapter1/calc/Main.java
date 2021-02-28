package io.github.pashazz.chapter1.calc;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.TemporalUnit;

import static io.github.pashazz.utils.Utils.formatDuration;
import static java.lang.String.format;

public class Main {
    private static int NUM_THREADS = 10;
    public static void main(String[] args) {
        System.out.printf("Minimum Priority: %s\nNormal Priority: %s\nMaximum Priority: %s\n",
                Thread.MIN_PRIORITY, Thread.NORM_PRIORITY, Thread.MAX_PRIORITY);
        Thread threads[] = new Thread[NUM_THREADS];
        Thread.State status[] = new Thread.State[NUM_THREADS];

        //Initialize
        for (int i = 0; i < NUM_THREADS; ++i) {
            threads[i] = new Thread(new Calculator());
            if ((i % 2) == 0) {
                threads[i].setPriority(Thread.MIN_PRIORITY);
            } else {
                threads[i].setPriority(Thread.MAX_PRIORITY);
            }
            threads[i].setName(format("Thread %s", i));
        }


        // Write thread info to file
        try(FileWriter fw = new FileWriter("calcthreads.log")) {

            PrintWriter pw = new PrintWriter(fw);
            LocalTime start = LocalTime.now();
            pw.printf("Program started at: %s\n", start);
            // Write initial status
            for (int i = 0; i < NUM_THREADS; ++i) {
                status[i] = threads[i].getState();
                pw.printf("Thread: %s (%s): State before start: %s; now starting...\n", threads[i].getId(), threads[i].getName(), status[i]);
                threads[i].start();
            }

            //Record every state change
            boolean stillRunning = true;

            while (stillRunning) {
                stillRunning = false;
                for (int i = 0; i < NUM_THREADS; ++i) {
                    if (!stillRunning && threads[i].getState() != Thread.State.TERMINATED) {
                        stillRunning = true;
                    }
                    if (threads[i].getState() != status[i]) {
                        pw.printf("Status change of thread: %s (%s): %s -> %s\n", threads[i].getId(), threads[i].getName(), status[i], threads[i].getState());
                        status[i] =  threads[i].getState();
                        if (status[i] == Thread.State.TERMINATED) {
                            pw.printf("Thread %s (%s) priority %s TERMINATED after %s ms\n",
                                    threads[i].getId(), threads[i].getName(), threads[i].getPriority(),
                                    formatDuration(Duration.between(LocalTime.now(), start)));
                        }
                    }
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
