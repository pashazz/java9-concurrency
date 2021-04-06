package io.github.pashazz.chapter4.futuretask;

import io.github.pashazz.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class ResultTask extends FutureTask<String> {

    private final String name;

    public ResultTask(ExecutableTask callable) {
        super(callable);
        this.name = callable.getName();
    }


    @Override
    public void done() {
        if (isCancelled()) {
            Log.error("%s: has been cancelled", name);
        } else {
            Log.info("%s: has finished", name);
        }

    }
}
