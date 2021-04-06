package io.github.pashazz.chapter5.forkjoinpool;

import io.github.pashazz.Log;

import java.util.List;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

class Task extends RecursiveAction {

    private final List<Product> products;
    private final int first;
    private final int last;
    private final double increment;

    Task(List<Product> products, int first, int last, double increment) {

        this.products = products;
        this.first = first;
        this.last = last;
        this.increment = increment;
    }

    /**
     * Задача состоит в обновлении цены продуктов
     * Задача считается элементарной,
     * если необходимо обновить цену меньше чем 10 продуктов.
     * Иначе задача делится наопоплам.
     */
    @Override
    protected void compute() {
        if (last - first < 10) {
            updatePrices();
        } else {
            try {
                TimeUnit.MILLISECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int middle = (last + first) / 2;
            Log.info("Pending tasks: %s", getQueuedTaskCount());
            Task half1 = new Task(products, first, middle+1, increment);
            Task half2 = new Task(products, middle+1, last, increment);
            invokeAll(half1, half2);
        }
    }

    private void updatePrices() {
        for (int i = first; i < last; ++i) {
            Product product = products.get(i);
            product.setPrice(product.getPrice()*(1 + increment));
        }
    }
}
