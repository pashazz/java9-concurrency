package io.github.pashazz.chapter5.joining;

import io.github.pashazz.Log;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

public class LineTask extends RecursiveTask<Integer> {

    private final String[] words;
    private final int start;
    private final int end;
    private final String word;

    public LineTask(String[] words, int start, int end, String word) {
        this.words = words;
        this.start = start;
        this.end = end;
        this.word = word;
    }



    @Override
    protected Integer compute() {
        if (end - start < 10) {
            return count();
        } else {
            int mid = (start + end) / 2;
            LineTask task1 = new LineTask(words, start, mid, word);
            LineTask task2 = new LineTask(words, mid, end, word);
            return combine(task1, task2);
        }
    }

    private Integer combine(LineTask task1, LineTask task2) {
        invokeAll(task1, task2);
        try {
            Integer result =  task1.get() + task2.get();

            Integer test = (int)Arrays.stream(words, start, end)
                    .filter(word -> word.equals(this.word))
                    .count();
            if (!result.equals(test)) {
                String lineBit = Arrays.stream(words, start, end).collect(Collectors.joining(" "));
                Log.error("Warning! Test failed! Expected: %s; Calculated: %s; start: %s; end: %s; words: %s",
                        test, result, start, end, String.join("; ", lineBit));
            }
            return result;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Integer count() {
        Integer counter = 0;
        for (int i = start; i < end; i++) {
            if (words[i].equals(word)) {
                counter++;
            }
        }
        return counter;
    }
}
