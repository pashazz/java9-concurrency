# This example demonstrates using of `synchronized` keyword

Ex:

```java
public class Example {
    void callMeFromManyThreads() {
        synchronized (this) {
            System.out.println("Me and callMeToo() can only be called " +
                    "from one thread at a time");
        }
    }
    void callMeToo() {
        synchronized (this) {
            System.out.println("Oh Yeah");
        }
    }
    
    static void callMeByAClass() {
        synchronized (this) {
            System.out.println("I'm static and synchronized on class object");
        }
    }
}
```