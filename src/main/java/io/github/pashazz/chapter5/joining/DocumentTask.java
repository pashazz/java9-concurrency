package io.github.pashazz.chapter5.joining;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RecursiveTask;

/**
 * Считает число вхождений слова в документ
 */
public class DocumentTask extends RecursiveTask<Integer> {
    private final String doc[][];
    private final int start, end;
    private final String word;

    public DocumentTask(String[][] doc, int start, int end, String word) {
        this.doc = doc;
        this.start = start;
        this.end = end;
        this.word = word;
    }


    @Override
    protected Integer compute() {
        Integer result = null;
        if (end - start < 10) {
            result = processLines(doc, start, end, word);
        } else {
            int mid = (start + end) / 2;
            DocumentTask half1 = new DocumentTask(doc, start, mid, word);
            DocumentTask half2 = new DocumentTask(doc, mid, end, word);
            invokeAll(half1, half2);
            try {
                result = groupResults(half1.get(), half2.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private Integer groupResults(Integer res1, Integer res2) {
        Integer result = res1 + res2;
        return result;
    }

    private Integer processLines(String[][] doc, int start, int end, String word) {
        List<LineTask> lineTasks = new ArrayList<>();
        for (int i = start; i < end; ++i) {
            LineTask task = new LineTask(doc[i], 0, doc[i].length, word);
            lineTasks.add(task);
        }
        invokeAll(lineTasks);

        int result = 0;
        for (LineTask task: lineTasks) {
            try {
                result += task.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
