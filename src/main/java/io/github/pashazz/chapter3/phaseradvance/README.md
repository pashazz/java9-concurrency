# How to extend `Phaser` to inject additional logic when phase advance happens

The `Phaser::onAdvance` method is called each time when a phase changes

* If it returns `true`, the process is finished and Phaser should terminate.
* `false` if shoud continue

By default, it returns true if the number of participants is 0 (everyone is deregistered).

Therefore, everyone should call `arriveAndDeregister()` at the end of the process

But we can inject our own logic
