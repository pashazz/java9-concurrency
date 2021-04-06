# Executor Framework

This chapter is dedicated to `Executor` framework, `ExecutorService``.

## Executor

A executor is an object that accepts tasks and does all the routine work on creation and managing threads using thread pools.

To use a executor, create an object of `ThreadPoolExecutor` class.

You send them a `Callable` or `Runnable` and get a `Future` that will return the result when it's available.

You can `shutdown()` the executor so that it will no longer accept tasks. THen they will be rejected. 

### `Executor` methods

* `execute()` - execute a `Runnable` on the `Executor`, return nothing (throw it away)
* `submit()` - execute a `Callable` on the `Executor`, return a `Future` that could be used for monitoring progress
* ``

## Examples

* `rejectedtasks` - Monitoring rejected tasks
* `factorialcalculator` - Using `Executor` to execute tasks that return their results
