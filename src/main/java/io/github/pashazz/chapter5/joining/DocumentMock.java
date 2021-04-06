package io.github.pashazz.chapter5.joining;

import io.github.pashazz.Log;

import java.util.Random;

public class DocumentMock {
    private static final String[] words = {"the", "hello", "goodbye", "packt", "java",
            "thread", "pool", "random", "class", "main"};

    public String[][] generateDocument(int numLines, int numWords, String searchForWord) {
        int counter = 0;
        String doc[][]  = new String[numLines][numWords];
        Random random = new Random();

        for (int i = 0; i < numLines; ++i) {
            for (int j = 0; j < numWords; ++j) {
                int index = random.nextInt(words.length);
                doc[i][j] = words[index];
                if (doc[i][j].equals(searchForWord)) {
                    counter++;
                }
            }
        }
        //for testing purposes
        Log.info("The word %s appears %s times in the document", searchForWord, counter);
        return doc;
    }
}
