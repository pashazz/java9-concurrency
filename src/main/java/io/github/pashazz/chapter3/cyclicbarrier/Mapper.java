package io.github.pashazz.chapter3.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Mapper implements Runnable {
    private final MatrixMock matrix;
    private final int numberForSearch;
    int firstRow;
    int lastRow;
    private final CyclicBarrier barrier;
    private final MapResults results;


    /**
     *
     * @param matrix
     * @param barrier
     * @param firstRow first number to be accounted for
     * @param lastRow  last number to be accounted for, exclusive
     */
    public Mapper(MatrixMock matrix, CyclicBarrier barrier, int firstRow, int lastRow, MapResults results, int numberForSearch) {
        this.matrix = matrix;
        this.barrier = barrier;
        this.firstRow = firstRow;
        this.lastRow = lastRow;
        this.results = results;
        this.numberForSearch = numberForSearch;
    }

    @Override
    public void run() {
        for (int i = firstRow; i < lastRow; ++i){
            int counter = 0;
            int[] row = matrix.getRow(i);
            for (int j = 0; j < row.length; ++j) {
                if (row[j] == numberForSearch) {
                    counter++;
                }
            }
            results.setResultForRow(i, counter);
        }
        try {
            barrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

}
