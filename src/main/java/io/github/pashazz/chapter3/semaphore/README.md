Follow the `../chapter2/conditions` example for use of semaphore

# Semaphore

## A Semaphore is a thing that allows some (variable) number of permits per resource

### Increase number of permits - `release()`
### Decrease number of permits - `acquire()` 

Once semaphore's number of permits reaches 0, the thread that tries to `acquire()` it will wait for permits increase

A semaphore is not reentrant.

Suppose you have a producer-consumer problem. 

All consumers should exit if producers are gone.

So we have a `Semaphore` that controls the number of producers

When producer joins, it `releases` a semaphore, increasing permits

When it exits, it `acquires` a semaphore, decreasing number of permits

A consumer may check the number of `availablePermits()` and if there's no permts, it exits.