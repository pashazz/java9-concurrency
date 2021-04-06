# FutureTask

This example is discussing FutureTask: https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/FutureTask.html

# Features
FutureTask is used to wrap a `Callable` so that we could
handle the cleanup after `Callable` is finished (both exceptionally and normally).
To do that, reimplement `done()`

FutureTask also caches your Callable result so that if the Callable has been rut it won't run again


A `FutureTask` is both `Future` and `Runnable` so you can feed it into a executor.

# State transitions

* NEW -> COMPLETING -> NORMAL
* NEW -> COMPLETING -> EXCEPTIONAL
* NEW -> CANCELLED
* NEW -> INTERRUPTING -> INTERRUPTED
