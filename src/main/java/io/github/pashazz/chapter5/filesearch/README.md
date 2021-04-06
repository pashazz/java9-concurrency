# `ForkJoinTask` with `CountedCompleter`

## Synchronous Way
If you are using `invokeAll` inside your `compute()` code
in a `ForkJoinTask`, which is the way for `RecursiveAction` and `RecursiveTask`,
these task sleep while waiting for their child tasks.

## Asynchronous Way
To invoke child tasks asynchronously, use `CountedCompleter`.
When a child task is spawned, the counter goes up and when it's finished, it goes down.

When counter reaches zero, all child tasks are completed.

## ForkJoinTask: `get()` vs `join()`

Both `get()` and `join()` return results of the task but
`join()` throws a non-checked exception and goes on normally if task is cancelled

`get()` throws InterruptedException (checked) if task is cancelled.

## Example
In this example we would use `CountedCompleter` to traverse
directories asynchronously to filter files with a certain extension