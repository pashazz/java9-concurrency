package io.github.pashazz.chapter3.cyclicbarrier;

public class MatrixMock {
    private final int data[][];

    MatrixMock(int rows, int cols) {
        data = new int[rows][cols];
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                data[i][j] = generateRandomNumber();
            }
        }
    }

    private int generateRandomNumber() {
        return (int)(Math.random() * 10);
    }

    int rowCount() {
        return data.length;
    }

    int columnCount() {
        if (data.length == 0)
            return 0;
        return data[0].length;
    }

    int[] getRow(int index) {
        return data[index];
    }

}
