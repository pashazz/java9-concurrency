package io.github.pashazz.chapter2.conditions;

import io.github.pashazz.Log;

public class FileMock {
    private String[] lines;
    private int index = 0;

    public FileMock(int size, int length) {
        lines = new String[size];
        for (int i = 0; i < size; ++i) {
            StringBuilder buffer = new StringBuilder(length);
            for (int j = 0; j < length; ++j) {
                int randomChar = (int)(Math.random() * 128);
                buffer.append((char) randomChar);
            }
            lines[i] = buffer.toString();
        }
    }

    public boolean hasMoreLines() {
        return index < lines.length;
    }

    public String getLine() {
        if (this.hasMoreLines()) {
            Log.debug("Lines left: %s", lines.length - index);
            return lines[index++];
        }
        return null;
    }
}
