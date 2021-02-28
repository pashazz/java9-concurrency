# Stamped Lock

This example demonstrates using of StampedLock class

StampedLock is a non-reentrant lock and it does not have a thread ownership like `Semaphore`

## Modes

* WRITE `writeLock()` - an exclusive write lock
*  READ`readLock()` - a non-exclusive read lock
* OPTIMISTIC READ  - a non-exclusive read lock that needs to check the status every time (like if a plain `Lock` used for protection of write operations). Use `validate()` to check

## Each of the lock acquire methods returns some code that indicates a unique operation ID
## Hence `Stamp`

### Can convert between modes as well

## Docs
https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/locks/StampedLock.html

____
On the contrary, `ReentrantLock` and `ReentrantReadWriteLock` are reentrant.