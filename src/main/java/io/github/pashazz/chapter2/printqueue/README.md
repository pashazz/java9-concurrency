# This example demonstrates using of Lock objects

`ReentrantLock` in particular

## Avoid deadlocks

If Thread 1 acquires Lock A and then Lock B, and Thread 2 acquires lock B and then lock A, it's a deadlock

```java
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Example {
    Lock A = new ReentrantLock();
    Lock B = new ReentrantLock();

    void execute() {
        new Thread(this::thread1).start();
        new Thread(this::thread2).start();
    }

    void thread1() {
        A.lock(); //fine
        //Do smth
        B.lock(); //this is locked after thread2 runs. Deadlock here
        //Do smth
        B.unlock();
        A.unlock();
    }

    void thread2() {
        B.lock(); //fine
        //Do smth
        A.lock(); //this is locked after thread1 runs. Deadlock here
        //Do smth 
        A.unlock();

        B.unlock();


    }

}

```