# CompletionService

The straightforward algorithm for running concurrent tasks is like this:

1. Create a executor
2. Send a `Runnable` to a `Executor`
3. Obtain a `Future` and wait for the result

But sometimes we want to send tasks in one object and wait for the result in another object.

Therefore, we can use `CompletionService`