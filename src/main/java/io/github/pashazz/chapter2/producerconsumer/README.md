# This example demonstrates a producer-consumer model using notify() & wait()

Once Thread 1 calls `wait()` inside a synchronized block, it sleeps and  Thread 2
may enter the synchronized block and do work. Thread 2 then calls `notify()`

## Ex:

```java
import java.util.List;
// producer & consumer
class ProducerConsumer() {
    private List<Object> storage;
    private int maxStorageSize = 100;
    
    // note that the method is synchronized on this
    public synchronized void set() { 
        // this is producer
        while (storage.size() == maxStorageSize) {
            // We can't produce more as it isnt consumed
            wait(); //until another thread calls notify on this
        }
        produceThingsToStorage();
        notify(); //awakes thread 2 who is calling get() on loop
    }
    
    //note that method is synchronized on this
    public synchronized void get() {
        // this is consumer
        while(storage.size() == 0) {
            //Nothing to consume
            wait(); //until another thread calls notify on this
        }
        consumeAndRemoveThingsFromStorage();
        notify(); //awakes thread 1 who is calling set() on loop
        
    }
    
}
```


```